package core.game;

import Lists.ListADT;
import Queues_Stacks.QueueADT;
import core.io.ExportadorJSON; 
import core.model.ator.Jogador;
import core.model.ator.JogadorHumano;
import core.model.divisao.Divisao;
import core.model.divisao.SalaAlavanca;
import core.model.divisao.SalaEnigma;
import core.model.itens.Corredor;
import core.model.itens.Evento;
import java.util.Iterator;
import java.util.Scanner;

public class PlayerInteraction {

    private final GestorMapa gestorMapa;
    private final QueueADT<Jogador> filaDeTurnos;
    private final ListADT<Jogador> listaTodosJogadores; 

    // Construtor
    public PlayerInteraction(GestorMapa gestorMapa, QueueADT<Jogador> filaDeTurnos, ListADT<Jogador> listaTodosJogadores) {
        this.gestorMapa = gestorMapa;
        this.filaDeTurnos = filaDeTurnos;
        this.listaTodosJogadores = listaTodosJogadores;
    }

    /**
     * Menu de Vitória Atualizado.
     * Permite exportar relatório de qualquer jogador ou de todos (Global).
     */
    public void processarVitoria(Jogador vencedor, Scanner scanner) {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n******************************************");
            System.out.println("       PARABÉNS " + vencedor.getNome().toUpperCase() + "!");
            System.out.println("     VOCÊ CONQUISTOU O TESOURO!      ");
            System.out.println("******************************************");
            System.out.println("1. Exportar Relatório Individual (Escolher Jogador)");
            System.out.println("2. Exportar Histórico GLOBAL (Todos os Jogadores)"); 
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("\nEscolha uma opção > ");

            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    menuEscolhaExportacao(scanner);
                    break;
                case "2":
                    // [NOVA LÓGICA] Exporta o histórico de todos os jogadores
                    ExportadorJSON.gerarRelatorioCompleto(listaTodosJogadores);
                    System.out.println("(Pressione ENTER para continuar)");
                    scanner.nextLine();
                    break;
                case "0":
                    System.out.println("A regressar ao menu principal...");
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void menuEscolhaExportacao(Scanner scanner) {
        if (listaTodosJogadores == null || listaTodosJogadores.isEmpty()) {
            System.out.println("Erro: Lista de jogadores vazia.");
            return;
        }

        System.out.println("\n--- SELECIONE O JOGADOR PARA EXPORTAR ---");
        Iterator<Jogador> it = listaTodosJogadores.iterator();
        int i = 1;
        while(it.hasNext()) {
            Jogador j = it.next();
            System.out.println("[" + i + "] " + j.getNome() + " (Pontos: " + j.getPontuacao() + ")");
            i++;
        }
        System.out.println("[0] Cancelar");
        
        System.out.print("Numero do Jogador > ");
        try {
            int escolha = Integer.parseInt(scanner.nextLine().trim());
            if (escolha > 0 && escolha < i) {
                // Encontrar o jogador selecionado
                it = listaTodosJogadores.iterator();
                Jogador selecionado = null;
                for(int k=0; k < escolha; k++) {
                    selecionado = it.next();
                }
                
                if (selecionado != null) {
                    ExportadorJSON.gerarRelatorio(selecionado);
                    System.out.println("(Pressione ENTER para continuar)");
                    scanner.nextLine();
                }
            } else if (escolha != 0) {
                System.out.println("Opção inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    public boolean exibirMenuJogador(Jogador jogador, Scanner scanner) {
        while (true) {
            System.out.println("\n___Menu Jogador:____");
            System.out.println("Nome: " + jogador.getNome());
            System.out.println("Pontuação (Enigmas): " + jogador.getPontuacao());
            System.out.println("Efeitos Ativos: " + listarEfeitos(jogador.getEfeitosPositivosAcumulados()));
            
            System.out.println("\nComandos: 1.listacomandos, 2.editarjogador, 3.verhistorico, 4.voltar, 0.terminarjogo");
            System.out.print("\nOpção > ");
            String input = scanner.nextLine().trim();

            switch (input.toLowerCase()) {
                case "listacomandos": case "1":
                    System.out.println("Comandos disponíveis: menujogador, turno, sair");
                    break;
                case "editarjogador": case "2":
                    System.out.print("Novo nome (Simulação): ");
                    String novo = scanner.nextLine();
                    System.out.println("Nome alterado para " + novo + " (apenas visual neste menu).");
                    break;
                case "verhistorico": case "3":
                    System.out.println("\n--- Histórico ---");
                    Iterator<String> logs = jogador.getHistorico().getLogs().iterator();
                    while(logs.hasNext()) System.out.println("- " + logs.next());
                    break;
                case "voltar": case "4":
                    return false;
                case "terminarjogo": case "0":
                    return true;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    public void visualizarMapaLocal(Jogador jogador) {
        Divisao atual = jogador.getPosicaoAtual();
        System.out.println("Loc: " + atual.getNome());
    }

    public boolean processarSalaEnigmaHumano(SalaEnigma sala, Jogador jogador, Scanner scanner) {
        System.out.println("\n(Responda ao Enigma para entrar)");
        System.out.print("Sua Resposta: ");
        String input = scanner.nextLine().trim();
        return !input.isEmpty() && sala.tentarResolverEnigma(jogador, input);
    }
    
    public boolean processarSalaAlavancaHumano(SalaAlavanca sala, Jogador jogador, Scanner scanner) {
        System.out.print("Escolha a alavanca (A/B): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return false;
        return sala.tentarAtivarAlavanca(jogador, input.toUpperCase().charAt(0));
    }

    public void tratarEfeitosAcumulados(Jogador jogadorAtual, Scanner scanner) {
        ListADT<Evento> efeitos = jogadorAtual.getEfeitosPositivosAcumulados();
        if (efeitos.isEmpty()) return; 
        
        System.out.println("\nEfeitos disponíveis: " + listarEfeitos(efeitos));
        System.out.print("Usar efeito? (Digite o TIPO ou Enter para ignorar): ");
        String usar = scanner.nextLine().trim().toUpperCase();
        
        if(usar.isEmpty()) return;

        Evento eventoEncontrado = null;
        Iterator<Evento> it = efeitos.iterator();
        while(it.hasNext()) {
            Evento e = it.next();
            if (e.getTipoEfeito().equalsIgnoreCase(usar)) {
                eventoEncontrado = e;
                break;
            }
        }
        
        if (eventoEncontrado != null) {
            if (jogadorAtual.consumirEfeitoPositivo(eventoEncontrado)) {
                System.out.println("Efeito " + eventoEncontrado.getTipoEfeito() + " ativado!");
                String tipo = eventoEncontrado.getTipoEfeito();
                if (tipo.equals(Evento.EFEITO_JOGADA_EXTRA) || tipo.equals(Evento.EFEITO_AVANCAR)) { 
                    filaDeTurnos.enqueue(jogadorAtual);
                }
            }
        }
    }
    
    public void aplicarEfeitosNegativosPendentes(Jogador jogadorAtual) { }

    public Divisao escolherDestino(Jogador jogadorAtual, ListADT<Divisao> vizinhos, Scanner scanner) {
        if (vizinhos.isEmpty()) {
            System.out.println("\nAVISO: Sem saídas disponíveis! (Beco sem saída)");
            return null; 
        }
        
        if (jogadorAtual instanceof JogadorHumano) {
            System.out.println("\n PODE IR PARA A SALA:");
            Iterator<Divisao> it = vizinhos.iterator();
            Divisao[] ops = new Divisao[vizinhos.size()];
            int i = 1;
            while(it.hasNext()) {
                Divisao d = it.next();
                ops[i-1] = d;
                Corredor c = gestorMapa.getCorredor(jogadorAtual.getPosicaoAtual(), d);
                String estado = (c != null && c.isEstaBloqueado()) ? " [BLOQUEADO]" : "";
                System.out.println("   " + i + " - " + d.getNome() + estado);
                i++;
            }
            
            System.out.print("\nOpcao: ");
            try {
                String input = scanner.nextLine().trim();
                if(input.isEmpty()) return null;
                int esc = Integer.parseInt(input);
                if (esc >= 1 && esc <= vizinhos.size()) return ops[esc - 1];
            } catch (Exception e) {
                System.out.println("Escolha inválida.");
            }
            return null;
        } else {
            return jogadorAtual.escolherMovimento(vizinhos);
        }
    }
    
    private String listarEfeitos(ListADT<Evento> efeitos) {
        StringBuilder sb = new StringBuilder();
        Iterator<Evento> it = efeitos.iterator();
        while (it.hasNext()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(it.next().getTipoEfeito());
        }
        return sb.toString();
    }
}