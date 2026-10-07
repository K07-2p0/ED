package core.game;

import Lists.ListADT;
import Trees_Heaps.PriorityQueue; // [Alterado]
import Exceptions.EmptyCollectionException;
import core.model.ator.Jogador;
import core.model.ator.JogadorHumano;
import core.model.divisao.Divisao;
import core.model.divisao.SalaTesouro;
import core.model.itens.Corredor;
import java.util.Scanner;

public class TurnProcessor {

    private final GestorMapa gestorMapa;
    private final PriorityQueue<Jogador> filaDeTurnos; // [Alterado]
    private final PlayerInteraction interaction;
    private boolean isRunning;

    public TurnProcessor(GestorMapa gestorMapa, PriorityQueue<Jogador> filaDeTurnos, PlayerInteraction interaction) {
        this.gestorMapa = gestorMapa;
        this.filaDeTurnos = filaDeTurnos;
        this.interaction = interaction;
        this.isRunning = true;
    }

    public boolean isRunning() { return isRunning; }
    public void stopGame() { this.isRunning = false; }
    public void reset() { this.isRunning = true; }

    public void processarTurno(Scanner scanner) {
        if (filaDeTurnos.isEmpty()) return;

        Jogador jogadorAtual = null;
        try {
            // Remove o jogador com menor prioridade (menos pontos)
            jogadorAtual = filaDeTurnos.dequeue();
        } catch (EmptyCollectionException e) { return; }

        System.out.println(">>> TURNO: " + jogadorAtual.getNome() + " (Pontos: " + jogadorAtual.getPontuacao() + ")");

        if (jogadorAtual instanceof JogadorHumano) {
            System.out.println("ENTER para jogar...");
            String in = scanner.nextLine();
            if(in.equalsIgnoreCase("menu")) { 
                if(interaction.exibirMenuJogador(jogadorAtual, scanner)) { stopGame(); return; }
            }
        }

        boolean jogou = jogadorAtual.tentarJogar();
        if (jogou) {
            Divisao atual = jogadorAtual.getPosicaoAtual();
            if (atual.processarEntrada(jogadorAtual)) {
                ListADT<Divisao> vizinhos = gestorMapa.obterVizinhos(atual);
                Divisao destino = interaction.escolherDestino(jogadorAtual, vizinhos, scanner);
                if (destino != null) {
                    Corredor c = gestorMapa.getCorredor(atual, destino);
                    if (c != null && c.atravessar(jogadorAtual)) {
                        jogadorAtual.getPosicaoAtual().setJogadorAtual(null);
                        if (destino.processarEntrada(jogadorAtual)) {
                            jogadorAtual.setPosicaoAtual(destino);
                            if (destino instanceof SalaTesouro) {
                                interaction.processarVitoria(jogadorAtual, scanner);
                                stopGame();
                                return;
                            }
                        }
                    }
                }
            }
        }

        // [IMPORTANTE] Re-inserir com prioridade atualizada
        double prioridade = (double) jogadorAtual.getPontuacao();
        filaDeTurnos.enqueue(jogadorAtual, prioridade);
    }
}