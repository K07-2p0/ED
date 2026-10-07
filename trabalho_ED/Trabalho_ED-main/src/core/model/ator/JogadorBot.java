package core.model.ator;

import Lists.ListADT;
import core.model.divisao.Divisao;
import java.util.Random; // Usamos a Random do Java, pois não é uma estrutura de dados

/**
 * Representa um Jogador Bot (Modo Automático).
 * Segue uma estratégia de movimento (neste caso, escolha aleatória simples).
 */
public class JogadorBot extends Jogador {

    // private final MovimentoStrategy strategy; // Idealmente, usaria isto
    private final Random random;

    /**
     * Construtor para Jogador Bot.
     *
     * @param nome O nome do bot.
     * @param posicaoInicial A divisão de início.
     */
    public JogadorBot(String nome, Divisao posicaoInicial) {
        super(nome, posicaoInicial);
        // Inicializar a estratégia (ou o gerador aleatório)
        // this.strategy = new EstrategiaAleatoria(); 
        this.random = new Random();
    }

    /**
     * Implementa a lógica de escolha de movimento para um jogador bot.
     * Por defeito, implementamos uma estratégia simples: escolha aleatória.
     *
     * @param caminhosPossiveis Uma lista (ListADT) das divisões adjacentes disponíveis.
     * @return A Divisao (próximo vértice) para onde o bot irá mover-se.
     */
    @Override
    public Divisao escolherMovimento(ListADT<Divisao> caminhosPossiveis) {
        System.out.println("\n" + getNome() + " (Bot) está a calcular o seu movimento...");

        if (caminhosPossiveis.isEmpty()) {
            System.out.println(getNome() + " não tem movimentos disponíveis e passa o turno.");
            return null; // O bot não pode mover
        }
        
        // 1. Obter o número de opções
        int numCaminhos = caminhosPossiveis.size();
        
        // 2. Escolher um índice aleatório
        int indiceEscolhido = random.nextInt(numCaminhos); // nextInt não é uma estrutura de dados da API, mas um utilitário.

        // 3. Devolver a Divisão correspondente
        // Assume-se que ListADT tem um método get(index)
        Divisao destino = caminhosPossiveis.get(indiceEscolhido);
        
        System.out.println(getNome() + " escolhe mover-se para: " + destino.getNome() + " (ID: " + destino.getId() + ")");
        return destino;
    }
}