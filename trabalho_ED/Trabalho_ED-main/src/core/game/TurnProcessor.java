package core.game;

import Lists.ListADT;
import Queues_Stacks.QueueADT;
import Exceptions.EmptyCollectionException;
import core.model.ator.Jogador;
import core.model.ator.JogadorHumano;
import core.model.divisao.Divisao;
import core.model.divisao.SalaAlavanca;
import core.model.divisao.SalaEnigma;
import core.model.divisao.SalaTesouro;
import core.model.itens.Corredor;
import java.util.Scanner;

public class TurnProcessor {

    private final GestorMapa gestorMapa;
    private final QueueADT<Jogador> filaDeTurnos;
    private final PlayerInteraction interaction;
    private boolean isRunning;

    public TurnProcessor(GestorMapa gestorMapa, QueueADT<Jogador> filaDeTurnos, PlayerInteraction interaction) {
        this.gestorMapa = gestorMapa;
        this.filaDeTurnos = filaDeTurnos;
        this.interaction = interaction;
        this.isRunning = true;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stopGame() {
        this.isRunning = false;
    }

    public void reset() {
        this.isRunning = true;
        // Limpa a fila de turnos residual
        while (!filaDeTurnos.isEmpty()) {
            try {
                filaDeTurnos.dequeue();
            } catch (Exception e) { break; }
        }
    }

    private void limparEcra() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 2; i++) System.out.println(); 
    }

    public void processarTurno(Scanner scanner) {
        if (filaDeTurnos.isEmpty()) {
            System.out.println("Não há jogadores na fila.");
            return;
        }

        Jogador jogadorAtual = null;
        try {
            jogadorAtual = filaDeTurnos.dequeue();
        } catch (EmptyCollectionException e) {
            return;
        }

        limparEcra();
        
        System.out.println("__________________________________________________________________");
        System.out.println(" JOGADOR: " + jogadorAtual.getNome().toUpperCase());
        System.out.println("__________________________________________________________________");
        System.out.println("");
        System.out.println(" VOCÊ ESTÁ NA SALA " + jogadorAtual.getPosicaoAtual().getId() + ":");
        System.out.println(" -> " + jogadorAtual.getPosicaoAtual().getNome());
        System.out.println("    \"" + jogadorAtual.getPosicaoAtual().getDescricao() + "\"");
        System.out.println("__________________________________________________________________");
        
        // Menu de Pausa/Opções para Jogador Humano
        if (jogadorAtual instanceof JogadorHumano) {
            while (true) {
                System.out.println("\n(ENTER para jogar ou 'menu' para opções)");
                System.out.print("> ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("menu") || input.equalsIgnoreCase("menujogador")) {
                    limparEcra();
                    boolean sairDoJogo = interaction.exibirMenuJogador(jogadorAtual, scanner);
                    if (sairDoJogo) {
                        this.stopGame();
                        return;
                    }
                    limparEcra();
                    System.out.println("--- Regresso ao Jogo: " + jogadorAtual.getNome() + " ---");
                    System.out.println("Posição: " + jogadorAtual.getPosicaoAtual().getNome());
                } else {
                    break;
                }
            }
        }

        // Verificar se está impedido de jogar
        if (!jogadorAtual.tentarJogar()) {
            filaDeTurnos.enqueue(jogadorAtual);
            System.out.println("\nPressione Enter para continuar...");
            if(jogadorAtual instanceof JogadorHumano) scanner.nextLine();
            return;
        }

        // Efeitos acumulados (apenas Humano decide quando usar)
        if (jogadorAtual instanceof JogadorHumano) {
            interaction.tratarEfeitosAcumulados(jogadorAtual, scanner);
        }

        // 1. Processar Entrada na Sala Onde Já Está (caso haja eventos recorrentes ou interação)
        Divisao divisaoAtual = jogadorAtual.getPosicaoAtual();
        boolean entradaSucesso = divisaoAtual.processarEntrada(jogadorAtual);

        // Se falhar na sala atual (ex: enigma não resolvido que impede saída), lida com isso
        if (!entradaSucesso && jogadorAtual instanceof JogadorHumano) {
            if (divisaoAtual instanceof SalaEnigma) {
                entradaSucesso = interaction.processarSalaEnigmaHumano((SalaEnigma) divisaoAtual, jogadorAtual, scanner);
            } else if (divisaoAtual instanceof SalaAlavanca) {
                entradaSucesso = interaction.processarSalaAlavancaHumano((SalaAlavanca) divisaoAtual, jogadorAtual, scanner);
            }
        } else if (!entradaSucesso && !(jogadorAtual instanceof JogadorHumano)) {
             System.out.println(jogadorAtual.getNome() + " falhou o desafio e perde o turno.");
        }

        if (!entradaSucesso) {
            filaDeTurnos.enqueue(jogadorAtual);
            interaction.aplicarEfeitosNegativosPendentes(jogadorAtual);
            System.out.println("\nTurno terminado (Bloqueado na sala atual). Pressione Enter.");
            if(jogadorAtual instanceof JogadorHumano) scanner.nextLine();
            return;
        }
        
        // 2. Escolher Destino (Movimento)
        ListADT<Divisao> vizinhos = gestorMapa.obterVizinhos(divisaoAtual);
        Divisao destino = interaction.escolherDestino(jogadorAtual, vizinhos, scanner);
        
        if (destino != null) {
            Corredor corredor = gestorMapa.getCorredor(jogadorAtual.getPosicaoAtual(), destino);
            
            boolean podePassar = true;
            if (corredor != null) {
                podePassar = corredor.atravessar(jogadorAtual);
            }
            
            if (podePassar) {
                // Sai da sala anterior
                jogadorAtual.getPosicaoAtual().setJogadorAtual(null); 
                
                // Tenta entrar na nova sala
                boolean entradaNovaSucesso = destino.processarEntrada(jogadorAtual); 

                // Se falhar e for humano, dá chance de interagir
                if (!entradaNovaSucesso && jogadorAtual instanceof JogadorHumano) {
                    if (destino instanceof SalaEnigma) {
                        entradaNovaSucesso = interaction.processarSalaEnigmaHumano((SalaEnigma) destino, jogadorAtual, scanner);
                    } else if (destino instanceof SalaAlavanca) {
                        entradaNovaSucesso = interaction.processarSalaAlavancaHumano((SalaAlavanca) destino, jogadorAtual, scanner);
                    }
                }
                
                if (entradaNovaSucesso) {
                    // Movimento concretizado
                    jogadorAtual.setPosicaoAtual(destino);
                    
                    // --- VERIFICAÇÃO DE VITÓRIA ATUALIZADA ---
                    if (destino instanceof SalaTesouro) {
                        // Chama o novo menu de vitória na PlayerInteraction
                        interaction.processarVitoria(jogadorAtual, scanner);
                        
                        // Encerra o jogo e volta ao main menu
                        this.isRunning = false;
                        return;
                    }
                } else {
                    System.out.println(jogadorAtual.getNome() + " falhou o desafio e permanece em " + divisaoAtual.getNome() + "."); // Recua/Fica na anterior? Lógica depende se o movimento é cancelado. Aqui assume-se que não entrou.
                }
            }
        }

        // Finalizar Turno normal
        interaction.aplicarEfeitosNegativosPendentes(jogadorAtual);
        filaDeTurnos.enqueue(jogadorAtual);
        
        if(jogadorAtual instanceof JogadorHumano) {
            System.out.println("\nTurno concluído. Pressione ENTER.");
            scanner.nextLine();
        }
    }
}