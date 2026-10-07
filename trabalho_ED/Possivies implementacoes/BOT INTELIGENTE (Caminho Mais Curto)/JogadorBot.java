package core.model.ator;

import Lists.ListADT;
import core.model.divisao.Divisao;
import core.game.GestorMapa; 
import java.util.Iterator;
import java.util.Random;

/**
 * Representa um Jogador Bot (Modo Automático).
 * ALTERAÇÃO: Usa Dijkstra (shortestPath) para ir para o Tesouro.
 */
public class JogadorBot extends Jogador {

    private final Random random;
    private final GestorMapa gestorMapa; // Referência para aceder ao grafo

    /**
     * Construtor para Jogador Bot.
     * Recebe GestorMapa para poder calcular rotas.
     */
    public JogadorBot(String nome, Divisao posicaoInicial, GestorMapa gestorMapa) {
        super(nome, posicaoInicial);
        this.random = new Random();
        this.gestorMapa = gestorMapa;
    }

    @Override
    public Divisao escolherMovimento(ListADT<Divisao> caminhosPossiveis) {
        System.out.println("\n" + getNome() + " (Bot Inteligente) a calcular rota...");

        try {
            // 1. Tenta encontrar a Sala do Tesouro (ID 99 é padrão)
            Divisao tesouro = gestorMapa.getDivisaoPorId(99); 

            if (tesouro != null) {
                // 2. Calcula o caminho mais curto usando a API
                // Requer que o GestorMapa tenha o método getGrafo() público
                Iterator<Divisao> it = gestorMapa.getGrafo().iteratorShortestPath(getPosicaoAtual(), tesouro);

                // 3. O iterador inclui a sala atual como primeiro elemento, avançamos um
                if (it.hasNext()) {
                    it.next(); // Ignora a sala onde já estamos
                }

                // 4. Pega na próxima sala do caminho ótimo
                if (it.hasNext()) {
                    Divisao proximoPasso = it.next();

                    // 5. Validação: Verifica se é um vizinho válido na lista recebida
                    Iterator<Divisao> itPossiveis = caminhosPossiveis.iterator();
                    while (itPossiveis.hasNext()) {
                        Divisao d = itPossiveis.next();
                        if (d.getId() == proximoPasso.getId()) {
                            System.out.println(getNome() + " segue o caminho ótimo para: " + d.getNome());
                            return d;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Bot: Erro ao calcular rota, usando aleatório. " + e.getMessage());
        }

        // --- FALLBACK (Aleatório) ---
        if (caminhosPossiveis.isEmpty()) {
            System.out.println(getNome() + " não tem movimentos disponíveis.");
            return null;
        }
        
        int indiceEscolhido = random.nextInt(caminhosPossiveis.size());
        Divisao destino = caminhosPossiveis.get(indiceEscolhido);
        System.out.println(getNome() + " escolhe mover-se aleatoriamente para: " + destino.getNome());
        return destino;
    }
}