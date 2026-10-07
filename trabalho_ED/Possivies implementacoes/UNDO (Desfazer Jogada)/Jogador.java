package core.model.ator;

import core.model.divisao.Divisao;
import Lists.ListADT;
import Lists.DoublyLinkedList;
import Queues_Stacks.LinkedStack; // [Importante]
import Exceptions.EmptyCollectionException;
import core.model.itens.Enigma;
import core.model.itens.Evento; 
import java.util.Iterator;

public abstract class Jogador {

    private final String nome;
    private Divisao posicaoAtual;
    private final Historico historico; 
    private int turnosImpedido; 
    private final ListADT<Evento> efeitosPositivosAcumulados;
    
    // [NOVO] Pilha para o Undo
    protected LinkedStack<Divisao> historicoMovimentos;

    public Jogador(String nome, Divisao posicaoInicial) {
        this.nome = nome;
        this.posicaoAtual = posicaoInicial;
        this.historico = new Historico(this.nome);
        this.turnosImpedido = 0;
        this.efeitosPositivosAcumulados = new DoublyLinkedList<>();
        
        // Inicializa a pilha
        this.historicoMovimentos = new LinkedStack<>();
    }

    public abstract Divisao escolherMovimento(ListADT<Divisao> caminhosPossiveis);

    // [ALTERADO] Setter com push na stack
    public void setPosicaoAtual(Divisao novaPosicao) {
        if (this.posicaoAtual != null) {
            this.historicoMovimentos.push(this.posicaoAtual);
        }
        this.posicaoAtual = novaPosicao;
        this.historico.adicionarMovimento(novaPosicao);
    }

    // [NOVO] Método Undo
    public boolean desfazerUltimoMovimento() {
        if (historicoMovimentos.isEmpty()) return false;
        try {
            Divisao anterior = historicoMovimentos.pop();
            this.posicaoAtual = anterior; // Atualiza sem push
            System.out.println("UNDO: Regressou a " + anterior.getNome());
            return true;
        } catch (EmptyCollectionException e) {
            return false;
        }
    }

    public int getPontuacao() {
        int total = 0;
        Iterator<Enigma> it = historico.getEnigmasResolvidos().iterator();
        while (it.hasNext()) total += it.next().getPontos();
        return total;
    }

    public boolean podeJogar() { return turnosImpedido <= 0; }
    public void finalizarTurno() { if (turnosImpedido > 0) turnosImpedido--; }
    public boolean tentarJogar() {
        if (turnosImpedido > 0) {
            System.out.println(nome + " bloqueado (" + turnosImpedido + " turnos).");
            turnosImpedido--;
            return false;
        }
        return true; 
    }
    public void aplicarBloqueio(int turnos) { this.turnosImpedido += turnos; }
    public void adicionarEfeitoPositivo(Evento efeito) {
        this.efeitosPositivosAcumulados.add(efeito);
        this.historico.adicionarEventoAplicado(efeito); 
    }
    public boolean consumirEfeitoPositivo(Evento tipo) {
        try { efeitosPositivosAcumulados.remove(tipo); return true; } catch (Exception e) { return false; }
    }

    public String getNome() { return nome; }
    public Divisao getPosicaoAtual() { return posicaoAtual; }
    public Historico getHistorico() { return historico; }
    public int getTurnosImpedido() { return turnosImpedido; }
    public ListADT<Evento> getEfeitosPositivosAcumulados() { return efeitosPositivosAcumulados; }
}