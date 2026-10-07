package core.model.divisao;

import core.game.GestorMapa;
import core.model.ator.Jogador;
import java.util.Random;

public class SalaTeletransporte extends Divisao {

    private final GestorMapa gestorMapa;
    private final Random random;

    public SalaTeletransporte(int id, String nome, String descricao, GestorMapa gestorMapa) {
        super(id, nome, descricao);
        this.gestorMapa = gestorMapa;
        this.random = new Random();
    }

    @Override
    public boolean processarEntrada(Jogador jogador) {
        // Define o jogador nesta sala temporariamente
        setJogadorAtual(jogador);
        
        System.out.println("\n************************************************");
        System.out.println(" ATENÇÃO: " + jogador.getNome() + " entrou num PORTAL MÁGICO!");
        System.out.println(" A sala começou a vibrar e a dissolver-se...");
        System.out.println("************************************************");

        // Tentar encontrar um destino aleatório válido (diferente do atual)
        Divisao destino = null;
        int tentativas = 0;
        
        // Assume-se um ID maximo seguro de 30 para o teste, ou tenta aleatório
        while (destino == null && tentativas < 20) {
            int idAleatorio = random.nextInt(30); // Tenta IDs entre 0 e 29
            
            // Não teleportar para a própria sala
            if (idAleatorio != this.getId()) {
                destino = gestorMapa.getDivisaoPorId(idAleatorio);
                // Evitar teleportar para null ou para a Sala do Tesouro (opcional)
                if (destino != null) {
                    break;
                }
            }
            tentativas++;
        }

        if (destino != null) {
            System.out.println(" *** ZWOOSH *** ");
            System.out.println("O jogador foi teleportado para: " + destino.getNome());
            
            // 1. Remover jogador desta sala
            this.setJogadorAtual(null);
            
            // 2. Atualizar jogador para a nova sala
            jogador.setPosicaoAtual(destino);
            
            // 3. Processar entrada na nova sala (pode desencadear enigmas, etc.)
            // Nota: Retornamos true aqui porque o turno "nesta" sala acabou, 
            // a lógica da nova sala será tratada ou no próximo turno ou imediatamente aqui.
            // Para simplicidade, apenas movemos.
            return true;
        } else {
            System.out.println("O portal falhou (interferência mágica). Ficas aqui.");
            return true;
        }
    }

    @Override
    public void atualizarEstado() {
        // Não aplicável
    }
}