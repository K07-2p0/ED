package core.model.divisao;

import core.model.ator.Jogador;

/**
 * Representa um Ponto de Partida ou uma Divisão Comum no Labirinto.
 * Não possui enigmas nem alavancas, sendo apenas um local de passagem.
 */
public class PontoPartida extends Divisao {

    /**
     * Construtor para um Ponto de Partida.
     *
     * @param id O identificador único da divisão.
     * @param nome O nome da divisão.
     * @param descricao A descrição detalhada da divisão.
     */
    public PontoPartida(int id, String nome, String descricao) {
        // Chama o construtor da classe base (Divisao)
        super(id, nome, descricao);
    }

    /**
     * Implementação do método abstrato da classe Divisao.
     * Num Ponto de Partida, a entrada é sempre processada com sucesso
     * e o jogador pode continuar o seu turno, pois não há desafios a resolver.
     *
     * @param jogador O jogador que acabou de entrar na divisão.
     * @return Sempre true, indicando que o jogador pode continuar a sua jogada.
     */
    @Override
    public boolean processarEntrada(Jogador jogador) {
        // Atualiza a posição do jogador
        setJogadorAtual(jogador);
        
        // Exibe uma mensagem de cortesia na consola
        System.out.println(jogador.getNome() + " entrou em " + getNome() + ".");
        
        // Não há desafios, o jogador segue em frente.
        return true; 
    }

    /**
     * Implementação do método abstrato da classe Divisao.
     * Num Ponto de Partida, não há estado especial (como "resolvido" ou "ativado")
     * para atualizar.
     */
    @Override
    public void atualizarEstado() {
        // Não faz nada, pois não há estado a gerir nesta divisão.
    }
}