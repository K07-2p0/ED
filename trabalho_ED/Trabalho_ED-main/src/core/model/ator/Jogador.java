package core.model.ator;

import core.model.divisao.Divisao;
import Lists.ListADT;
import Lists.DoublyLinkedList;
import core.model.itens.Enigma;
import core.model.itens.Evento; 
import java.util.Iterator; // Importação necessária para o Iterator

/**
 * Representa um Jogador no Labirinto da Glória.
 * É a classe base (Abstract) para JogadorHumano e JogadorBot.
 */
public abstract class Jogador {

    private final String nome;
    private Divisao posicaoAtual;
    private final Historico historico; 
    private int turnosImpedido; 
    private final ListADT<Evento> efeitosPositivosAcumulados;

    public Jogador(String nome, Divisao posicaoInicial) {
        this.nome = nome;
        this.posicaoAtual = posicaoInicial;
        this.historico = new Historico(this.nome);
        this.turnosImpedido = 0;
        this.efeitosPositivosAcumulados = new DoublyLinkedList<>();
    }

    public abstract Divisao escolherMovimento(ListADT<Divisao> caminhosPossiveis);

    // --- MÉTODO QUE FALTAVA (Correção do Erro) ---
    public int getPontuacao() {
        int total = 0;
        // Itera sobre os enigmas que o jogador já resolveu
        Iterator<Enigma> it = historico.getEnigmasResolvidos().iterator();
        while (it.hasNext()) {
            // Soma os pontos de cada enigma
            total += it.next().getPontos();
        }
        return total;
    }

    // --- Métodos de Controlo de Turnos ---

    public boolean podeJogar() {
        return turnosImpedido <= 0;
    }
    
    public void finalizarTurno() {
        if (turnosImpedido > 0) {
            turnosImpedido--;
        }
    }

    public boolean tentarJogar() {
        if (turnosImpedido > 0) {
            System.out.println(nome + " está impedido de jogar. Faltam " + turnosImpedido + " turnos.");
            turnosImpedido--;
            return false;
        }
        return true; 
    }
    
    public void aplicarBloqueio(int turnos) {
        this.turnosImpedido += turnos;
        System.out.println(nome + " foi BLOQUEADO por " + turnos + " turnos!");
    }
    
    // --- Métodos de Gestão de Efeitos ---
    
    public void adicionarEfeitoPositivo(Evento efeito) {
        this.efeitosPositivosAcumulados.add(efeito);
        System.out.println(nome + " ganhou o efeito: " + efeito.getDescricao());
        this.historico.adicionarEventoAplicado(efeito); 
    }
    
    public boolean consumirEfeitoPositivo(Evento tipo) {
        try {
            efeitosPositivosAcumulados.remove(tipo);
            System.out.println(nome + " usou o efeito: " + tipo.getDescricao());
            return true;
        } catch (Exception e) {
            return false; // Efeito não encontrado
        }
    }

    // --- Getters e Setters ---

    public String getNome() {
        return nome;
    }

    public Divisao getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Divisao novaPosicao) {
        this.posicaoAtual = novaPosicao;
        this.historico.adicionarMovimento(novaPosicao);
    }

    public Historico getHistorico() {
        return historico;
    }

    public int getTurnosImpedido() {
        return turnosImpedido;
    }
    
    public ListADT<Evento> getEfeitosPositivosAcumulados() {
        return efeitosPositivosAcumulados;
    }
}