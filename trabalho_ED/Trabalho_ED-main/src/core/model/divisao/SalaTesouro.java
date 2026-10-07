package core.model.divisao;

import core.model.ator.Jogador;

/**
 * Representa a Sala Central do Labirinto, onde se encontra o Tesouro.
 * É a condição de vitória do jogo.
 */
public class SalaTesouro extends Divisao {

    private boolean tesouroConquistado;
    
    /**
     * Construtor para a Sala do Tesouro.
     *
     * @param id O identificador único da divisão.
     * @param nome O nome da divisão (deve ser "Sala Central" ou similar).
     * @param descricao A descrição detalhada da sala.
     */
    public SalaTesouro(int id, String nome, String descricao) {
        // Chama o construtor da classe base (Divisao)
        super(id, nome, descricao);
        this.tesouroConquistado = false; // Ninguém começou na sala
    }

    /**
     * Implementação do método abstrato da classe Divisao.
     * Quando um jogador entra nesta sala, ele vence a partida.
     *
     * @param jogador O jogador que acabou de entrar na divisão.
     * @return true, pois a jogada é sempre concluída com sucesso (com vitória).
     */
    @Override
    public boolean processarEntrada(Jogador jogador) {
        // 1. Atualizar a posição do jogador
        setJogadorAtual(jogador);

        // 2. Marcar a vitória
        this.tesouroConquistado = true;
        
        // 3. Notificar o jogador (e o MotorJogo)
        System.out.println("\n*** VITÓRIA ***");
        System.out.println(jogador.getNome() + " alcançou o centro do labirinto e conquistou o tesouro em " + getNome() + "!");
        
        // Esta informação será usada pelo MotorJogo para terminar a partida.
        return true; 
    }

    /**
     * Implementação do método abstrato da classe Divisao.
     * O estado é simples: se foi conquistada ou não.
     */    @Override
    
    public void atualizarEstado() {
        // Simplesmente garante que o estado pode ser atualizado se necessário,
        // mas para esta classe a mudança mais importante ocorre em processarEntrada.
    }

    /**
     * Verifica se o tesouro já foi conquistado por algum jogador.
     * @return true se o tesouro foi conquistado, false caso contrário.
     */
    public boolean isTesouroConquistado() {
        return tesouroConquistado;
    }
}