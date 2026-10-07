package core.game;

import Lists.DoublyLinkedList;
import Lists.ListADT;
import Graphs.Network;
import Graphs.NetworkADT;
import core.model.divisao.Divisao;
import core.model.divisao.PontoPartida;
import core.model.divisao.SalaTesouro;
import core.model.divisao.SalaEnigma;   // [IMPORT NOVO]
import core.model.divisao.SalaAlavanca; // [IMPORT NOVO]
import core.model.itens.Corredor;
import java.util.Iterator; 
import Queues_Stacks.CircularArrayQueue;
import Queues_Stacks.QueueADT;
import Exceptions.EmptyCollectionException; 

public class GestorMapa {

    private NetworkADT<Divisao> grafo; 
    private ListADT<Divisao> listaDivisoes;
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

    /**
     * Adiciona corredores com lógica especial para o Ponto de Partida.
     */
    public void adicionarCorredor(int idOrigem, int idDestino, Corredor corredor) {
        Divisao origem = getDivisaoPorId(idOrigem);
        Divisao destino = getDivisaoPorId(idDestino);

        if (origem != null && destino != null) {
            corredor.setOrigem(origem);
            corredor.setDestino(destino);
            
            try {
                // 1. Caminho de IDA (Sempre adicionado)
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
            // Verifica conexões que partem da sala atual
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
    
    // Algoritmo BFS (Mantido igual)
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

    // =========================================================================
    // === NOVOS MÉTODOS PARA A DEFESA (IMPLEMENTADOS AQUI) ====================
    // =========================================================================

    /**
     * Exemplo 1: Devolve um iterador com divisões que contêm desafios (Enigma ou Alavanca).
     */
    public Iterator<Divisao> getDivisoesDeDesafio() {
        DoublyLinkedList<Divisao> resultados = new DoublyLinkedList<>();
        Iterator<Divisao> it = this.listaDivisoes.iterator();
        
        while (it.hasNext()) {
            Divisao d = it.next();
            // Verifica se a divisão é uma instância de SalaEnigma ou SalaAlavanca
            if (d instanceof SalaEnigma || d instanceof SalaAlavanca) {
                resultados.add(d);
            }
        }
        return resultados.iterator();
    }

    /**
     * Exemplo 2: Devolve um iterador com todos os corredores que estão bloqueados.
     */
    public Iterator<Corredor> getCorredoresBloqueados() {
        DoublyLinkedList<Corredor> bloqueados = new DoublyLinkedList<>();
        // A listaCorredores guarda objetos auxiliares (CorredorConexao), precisamos extrair o Corredor
        Iterator<CorredorConexao> it = this.listaCorredores.iterator();
        
        while (it.hasNext()) {
            CorredorConexao conexao = it.next();
            Corredor c = conexao.corredor;
            
            if (c.isEstaBloqueado()) {
                // Adicionamos à lista de resultados
                bloqueados.add(c);
            }
        }
        return bloqueados.iterator();
    }

    /**
     * Exemplo 3: Calcula a soma total dos pontos de todos os enigmas no mapa.
     */
    public int calcularTotalPontosMapa() {
        int total = 0;
        Iterator<Divisao> it = this.listaDivisoes.iterator();
        
        while (it.hasNext()) {
            Divisao d = it.next();
            
            // Se for uma sala de enigma, fazemos cast e somamos os pontos
            if (d instanceof SalaEnigma) {
                SalaEnigma sala = (SalaEnigma) d;
                if (sala.getEnigmaAssociado() != null) {
                    total += sala.getEnigmaAssociado().getPontos();
                }
            }
        }
        return total;
    }

    // =========================================================================
    
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
}