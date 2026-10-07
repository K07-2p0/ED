package core.model.ator;

import Lists.ListADT;
import core.model.divisao.Divisao;

/**
 * Representa um Jogador Humano (Modo Manual).
 * A escolha do movimento é delegada à Interface de Utilizador (MotorJogo).
 */
public class JogadorHumano extends Jogador {

    /**
     * Construtor para Jogador Humano.
     */
    public JogadorHumano(String nome, Divisao posicaoInicial) {
        super(nome, posicaoInicial);
    }

    /**
     * Implementa a lógica de escolha de movimento para um jogador humano.
     * Este método apenas apresenta as opções, e o MotorJogo irá solicitar a escolha real.
     */
    @Override
    public Divisao escolherMovimento(ListADT<Divisao> caminhosPossiveis) {
        
        System.out.println("\n" + getNome() + ", é a sua vez de escolher o movimento.");
        
        // O MotorJogo não deve chamar este método diretamente para obter o input,
        // mas sim para dar início à fase de escolha.
        return null; 
    }
}