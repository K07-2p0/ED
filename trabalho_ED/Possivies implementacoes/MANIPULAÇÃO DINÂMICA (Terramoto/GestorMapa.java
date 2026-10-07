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
    // Precisamos de acesso à lista interna para remover logicamente também
    private ListADT<CorredorConexao> listaCorredores;

    public GestorMapa() {
        this.grafo = new Network<>(); 
        this.listaDivisoes = new DoublyLinkedList<>();
        this.listaCorredores = new DoublyLinkedList<>();
    }
    
    // --- [NOVO MÉTODO PARA A DEFESA] ---
    public boolean destruirCorredorEntre(Divisao d1, Divisao d2) {
        if (d1 == null || d2 == null) return false;
        
        System.out.println("!!! TERRAMOTO: O corredor entre " + d1.getNome() + " e " + d2.getNome() + " desabou! !!!");
        
        // 1. Remover a aresta do Grafo (Network)
        grafo.removeEdge(d1, d2);
        
        // 2. Remover da lista auxiliar de corredores (para não aparecer nos menus)
        // Como a ListADT pode não ter remove condicional complexo fácil, vamos iterar e remover pelo objeto
        CorredorConexao alvo = null;
        Iterator<CorredorConexao> it = listaCorredores.iterator();
        while(it.hasNext()) {
            CorredorConexao cx = it.next();
            // Verifica se é a ligação d1->d2 ou d2->d1
            boolean sentido1 = (cx.origemId == d1.getId() && cx.destinoId == d2.getId());
            boolean sentido2 = (cx.origemId == d2.getId() && cx.destinoId == d1.getId());
            
            if (sentido1 || sentido2) {
                alvo = cx;
                break; // Removemos apenas a primeira ocorrência encontrada (pode haver ida e volta)
            }
        }
        
        if (alvo != null) {
            try {
                listaCorredores.remove(alvo);
                // Tenta remover o par inverso se existir (grafo não orientado simulado)
                destruirCorredorInverso(d2, d1); 
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
    
    private void destruirCorredorInverso(Divisao d1, Divisao d2) {
        // Método auxiliar silencioso para limpar o caminho de volta
        CorredorConexao alvo = null;
        Iterator<CorredorConexao> it = listaCorredores.iterator();
        while(it.hasNext()) {
            CorredorConexao cx = it.next();
            if (cx.origemId == d1.getId() && cx.destinoId == d2.getId()) {
                alvo = cx; break;
            }
        }
        if(alvo!=null) try { listaCorredores.remove(alvo); } catch(Exception e){}
    }

    // ... (RESTANTE CÓDIGO IGUAL AO ORIGINAL: reiniciarGrafo, adicionarDivisao, etc.) ...
    
    public void reiniciarGrafo(int capacidade) {
        this.grafo = new Network<>(); 
        this.listaDivisoes = new DoublyLinkedList<>();
        this.listaCorredores = new DoublyLinkedList<>();
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
            corredor.setOrigem(origem); corredor.setDestino(destino);
            try {
                grafo.addEdge(origem, destino, corredor.getPeso());
                listaCorredores.add(new CorredorConexao(idOrigem, idDestino, corredor));
                if (!(origem instanceof PontoPartida)) {
                    grafo.addEdge(destino, origem, corredor.getPeso());
                    listaCorredores.add(new CorredorConexao(idDestino, idOrigem, corredor));
                }
            } catch (Exception e) {}
        }
    }

    public Divisao getDivisaoPorId(int id) {
        Iterator<Divisao> it = listaDivisoes.iterator();
        while (it.hasNext()) { Divisao d = it.next(); if (d.getId() == id) return d; }
        return null;
    }

    public Corredor getCorredor(Divisao origem, Divisao destino) {
        if (origem == null || destino == null) return null;
        Iterator<CorredorConexao> it = listaCorredores.iterator();
        while (it.hasNext()) {
            CorredorConexao cx = it.next();
            if (cx.origemId == origem.getId() && cx.destinoId == destino.getId()) return cx.corredor;
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
                    if (destino != null) vizinhos.add(destino);
                }
            }
        }
        return vizinhos;
    }
    
    // BFS e Classes internas mantidas...
    public int getDistanciaParaTesouro(Divisao origem) { /* Código Original */ return -1; }
    
    private class DistanciaDivisao { int divisaoId; int distancia; public DistanciaDivisao(int i, int d){divisaoId=i;distancia=d;} }
    private class CorredorConexao {
        int origemId; int destinoId; Corredor corredor;
        public CorredorConexao(int o, int d, Corredor c) { origemId=o; destinoId=d; corredor=c; }
    }
}