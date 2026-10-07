package core.game;

import Lists.DoublyLinkedList;
import Lists.ListADT;
import Graphs.Network;
import Graphs.NetworkADT;
import core.model.divisao.Divisao;
import core.model.divisao.PontoPartida;
import core.model.divisao.SalaTesouro;
import core.model.itens.Corredor;
import java.util.Iterator; 
import Queues_Stacks.CircularArrayQueue;
import Queues_Stacks.QueueADT;
import Exceptions.EmptyCollectionException; 

public class GestorMapa {

    private NetworkADT<Divisao> grafo; 
    private ListADT<Divisao> listaDivisoes;
    // Atenção: Esta lista guarda objetos auxiliares 'CorredorConexao', não 'Corredor' diretamente
    private ListADT<CorredorConexao> listaCorredores;

    public GestorMapa() {
        this.grafo = new Network<>(); 
        this.listaDivisoes = new DoublyLinkedList<>();
        this.listaCorredores = new DoublyLinkedList<>();
    }

    public void reiniciarGrafo(int capacidade) {
        this.grafo = new Network<>(); 
        this.listaDivisoes = new DoublyLinkedList<>();
        this.listaCorredores = new DoublyLinkedList<>();
        System.out.println("Grafo reiniciado. Capacidade prevista no JSON: " + capacidade);
    }

    public void adicionarDivisao(Divisao divisao) {
        if (divisao != null) {
            grafo.addVertex(divisao);
            listaDivisoes.add(divisao);
        }
    }

    public void adicionarCorredor(int idOrigem, int idDestino, Corredor corredor) {
        Divisao origem = getDivisaoPorId(idOrigem);
        Divisao destino = getDivisaoPorId(idDestino);

        if (origem != null && destino != null) {
            corredor.setOrigem(origem);
            corredor.setDestino(destino);
            
            try {
                // 1. Caminho de IDA
                grafo.addEdge(origem, destino, corredor.getPeso());
                listaCorredores.add(new CorredorConexao(idOrigem, idDestino, corredor));
                
                // 2. Caminho de VOLTA (Condicional)
                if (!(origem instanceof PontoPartida)) {
                    grafo.addEdge(destino, origem, corredor.getPeso());
                    listaCorredores.add(new CorredorConexao(idDestino, idOrigem, corredor));
                }
                
            } catch (Exception e) {
                System.out.println("Erro ao adicionar corredor: " + e.getMessage());
            }
        }
    }

    public Divisao getDivisaoPorId(int id) {
        Iterator<Divisao> it = listaDivisoes.iterator();
        while (it.hasNext()) {
            Divisao d = it.next();
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    public Corredor getCorredor(Divisao origem, Divisao destino) {
        if (origem == null || destino == null) return null;
        
        Iterator<CorredorConexao> it = listaCorredores.iterator();
        while (it.hasNext()) {
            CorredorConexao cx = it.next();
            if (cx.origemId == origem.getId() && cx.destinoId == destino.getId()) {
                return cx.corredor;
            }
        }
        return null;
    }

    public ListADT<Divisao> obterVizinhos(Divisao divisaoAtual) {
        ListADT<Divisao> vizinhos = new DoublyLinkedList<>();
        int idAtual = divisaoAtual.getId();
        
        Iterator<CorredorConexao> it = listaCorredores.iterator();
        while (it.hasNext()) {
            CorredorConexao cx = it.next();
            if (cx.origemId == idAtual) {
                if (!cx.corredor.isEstaBloqueado()) {
                    Divisao destino = getDivisaoPorId(cx.destinoId);
                    if (destino != null) {
                        vizinhos.add(destino);
                    }
                }
            }
        }
        return vizinhos;
    }
    
    public int getDistanciaParaTesouro(Divisao origem) {
        if (origem == null) return -1;
        
        Divisao tesouro = null;
        Iterator<Divisao> itDiv = listaDivisoes.iterator();
        while (itDiv.hasNext()) {
            Divisao d = itDiv.next();
            if (d instanceof SalaTesouro) {
                tesouro = d; 
                break;
            }
        }
        
        if (tesouro == null) return -1; 

        QueueADT<Divisao> fila = new CircularArrayQueue<>();
        ListADT<DistanciaDivisao> distancias = new DoublyLinkedList<>(); 
        
        fila.enqueue(origem);
        distancias.add(new DistanciaDivisao(origem.getId(), 0));
        
        try {
            while (!fila.isEmpty()) {
                Divisao atual = fila.dequeue();
                int distAtual = getDistanciaPorId(distancias, atual.getId());
                
                if (atual.equals(tesouro)) return distAtual;
                
                ListADT<Divisao> vizinhos = obterVizinhos(atual);
                Iterator<Divisao> itViz = vizinhos.iterator();
                while (itViz.hasNext()) {
                    Divisao vizinho = itViz.next();
                    if (getDistanciaPorId(distancias, vizinho.getId()) == -1) { 
                        distancias.add(new DistanciaDivisao(vizinho.getId(), distAtual + 1));
                        fila.enqueue(vizinho);
                    }
                }
            }
        } catch (EmptyCollectionException e) {}
        
        return -1;
    }
    
    private int getDistanciaPorId(ListADT<DistanciaDivisao> distancias, int id) {
        Iterator<DistanciaDivisao> it = distancias.iterator();
        while (it.hasNext()) {
            DistanciaDivisao dd = it.next();
            if (dd.divisaoId == id) return dd.distancia;
        }
        return -1;
    }
    
    // --- CLASSES INTERNAS AUXILIARES ---
    
    private class DistanciaDivisao {
        int divisaoId;
        int distancia;
        public DistanciaDivisao(int id, int dist) {
            this.divisaoId = id;
            this.distancia = dist;
        }
    }

    private class CorredorConexao {
        int origemId;
        int destinoId;
        Corredor corredor;
        public CorredorConexao(int origemId, int destinoId, Corredor corredor) {
            this.origemId = origemId;
            this.destinoId = destinoId;
            this.corredor = corredor;
        }
    }

    // =========================================================================
    // === NOVA FUNCIONALIDADE PARA A DEFESA (Filtro de Corredores) ===
    // =========================================================================
    
    /**
     * Retorna um iterador com os corredores que estão marcados como BLOQUEADOS.
     */
    public Iterator<Corredor> getCorredoresBloqueados() {
        // Lista temporária para guardar os resultados
        ListADT<Corredor> bloqueados = new DoublyLinkedList<>();
        
        // Iterar sobre todas as conexões guardadas
        Iterator<CorredorConexao> it = listaCorredores.iterator();
        
        while (it.hasNext()) {
            CorredorConexao conexao = it.next();
            Corredor c = conexao.corredor;
            
            // Verificar se o corredor está bloqueado
            if (c.isEstaBloqueado()) {
                // Adiciona à lista. 
                // Nota: Se o grafo for bidirecional, o mesmo objeto Corredor pode aparecer 
                // em duas conexões (ida e volta). Para a defesa, listar duplicados costuma ser aceite,
                // mas se quiseres evitar, podes verificar se a lista 'bloqueados' já contém 'c'.
                boolean jaExiste = false;
                Iterator<Corredor> itCheck = bloqueados.iterator();
                while(itCheck.hasNext()) {
                    if(itCheck.next() == c) {
                        jaExiste = true;
                        break;
                    }
                }
                
                if (!jaExiste) {
                    bloqueados.add(c);
                }
            }
        }
        
        return bloqueados.iterator();
    }
}