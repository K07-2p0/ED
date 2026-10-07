package core.game;

import Lists.ListADT;
import core.model.divisao.Divisao;

/**
 * MovimentoStrategy.java
 * Interface que define como os jogadores bots escolhem o seu movimento no labirinto.
 * Implementa o padrão Strategy para permitir diferentes tipos de movimentação
 * (aleatória, inteligente, greedy, etc.).
 * 
 * Utiliza estruturas da API (ListADT) para receber as opções de movimento.
 */
public interface MovimentoStrategy {

    /**
     * Calcula e retorna a próxima divisão para a qual o jogador bot se deve mover.
     * 
     * @param caminhosPossiveis Uma lista (ListADT) das divisões adjacentes disponíveis.
     * @param posicaoAtual A divisão onde o jogador se encontra atualmente.
     * @return A Divisao para onde o bot se irá mover, ou null se não conseguir mover.
     */
    Divisao calcularMovimento(ListADT<Divisao> caminhosPossiveis, Divisao posicaoAtual);

    /**
     * Retorna o nome da estratégia para fins de logging e debugging.
     * 
     * @return Uma string descritiva do tipo de estratégia (ex: "Aleatória", "Greedy", "DFS").
     */
    String getNomeEstrategia();
}

