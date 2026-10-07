package core.model.divisao;

import core.model.ator.Jogador;

/**
 * Representa uma Divisão do Labirinto da Glória.
 * É a classe base (vértice) utilizada na estrutura de grafo/rede.
 */
public abstract class Divisao {

    private final int id;
    private final String nome;
    private final String descricao;
    private Jogador jogadorAtual; // Pode ser null se a divisão estiver vazia

    /**
     * Construtor da Divisão.
     * * @param id O identificador único da divisão.
     * @param nome O nome da divisão.
     * @param descricao A descrição detalhada da divisão.
     */
    public Divisao(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.jogadorAtual = null;
    }

    /**
     * Define o comportamento que ocorre quando um jogador entra nesta divisão.
     * Este é um método abstrato que deve ser implementado pelas subclasses
     * (e.g., SalaEnigma, SalaAlavanca).
     * * @param jogador O jogador que acabou de entrar na divisão.
     * @return true se o jogador puder continuar o seu turno ou se o desafio for resolvido, false caso contrário.
     */
    public abstract boolean processarEntrada(Jogador jogador);

    /**
     * Método para atualizar o estado interno da divisão (e.g., resolvido, ativado).
     * O comportamento depende da subclasse.
     */
    public abstract void atualizarEstado();

    // --- Getters ---

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }
    
    // --- Setters ---

    /**
     * Define o jogador que se encontra na divisão.
     * @param jogador O jogador que ocupa a divisão, ou null se estiver vazia.
     */
    public void setJogadorAtual(Jogador jogador) {
        this.jogadorAtual = jogador;
    }

    /**
     * Verifica se a divisão está ocupada por um jogador.
     * @return true se a divisão estiver ocupada, false caso contrário.
     */
    public boolean estaOcupada() {
        return this.jogadorAtual != null;
    }

    // --- Métodos de Comparação e Representação ---

    /**
     * Retorna a representação em String da Divisão.
     */
    @Override
    public String toString() {
        return "Divisao{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

    /**
     * Permite que as divisões sejam usadas corretamente como chaves ou elementos
     * em estruturas de dados como a 'Network' da API.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Divisao divisao = (Divisao) o;
        return id == divisao.id;
    }

    /**
     * O hashCode deve ser consistente com o método equals.
     */
    @Override
    public int hashCode() {
        return id;
    }
}