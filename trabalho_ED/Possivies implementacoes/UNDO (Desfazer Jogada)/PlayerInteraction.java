package core.game;

import Lists.ListADT;
import Queues_Stacks.QueueADT;
import core.io.ExportadorJSON; 
import core.model.ator.Jogador;
import core.model.ator.JogadorHumano;
import core.model.divisao.Divisao;
import core.model.divisao.SalaAlavanca;
import core.model.divisao.SalaEnigma;
import core.model.itens.Evento;
import java.util.Iterator;
import java.util.Scanner;

public class PlayerInteraction {

    private final GestorMapa gestorMapa;
    private final QueueADT<Jogador> filaDeTurnos;
    private final ListADT<Jogador> listaTodosJogadores; 

    public PlayerInteraction(GestorMapa gestorMapa, QueueADT<Jogador> filaDeTurnos, ListADT<Jogador> listaTodosJogadores) {
        this.gestorMapa = gestorMapa;
        this.filaDeTurnos = filaDeTurnos;
        this.listaTodosJogadores = listaTodosJogadores;
    }

    public boolean exibirMenuJogador(Jogador jogador, Scanner scanner) {
        while (true) {
            System.out.println("\n___Menu Jogador: " + jogador.getNome() + "___");
            // [NOVO] Adicionado "5. DESFAZER"
            System.out.println("1. Comandos | 2. Editar | 3. Histórico | 4. Voltar | 5. DESFAZER | 0. Sair");
            System.out.print("Opção > ");
            String input = scanner.nextLine().trim();

            switch (input.toLowerCase()) {
                case "listacomandos": case "1":
                    System.out.println("Comandos: menujogador, turno, sair"); break;
                case "editarjogador": case "2":
                    System.out.print("Novo nome: "); scanner.nextLine(); break;
                case "verhistorico": case "3":
                    Iterator<String> logs = jogador.getHistorico().getLogs().iterator();
                    while(logs.hasNext()) System.out.println(logs.next()); break;
                case "voltar": case "4":
                    return false;
                
                // [NOVO] Lógica de Undo
                case "desfazer": case "5":
                    if (jogador.desfazerUltimoMovimento()) {
                        System.out.println("Movimento desfeito!");
                        return false; // Sai do menu para atualizar o ecrã
                    } else {
                        System.out.println("Impossível desfazer.");
                    }
                    break;
                    
                case "terminarjogo": case "0": return true;
                default: System.out.println("Inválido.");
            }
        }
    }

    public void processarVitoria(Jogador vencedor, Scanner scanner) {
        System.out.println("PARABÉNS " + vencedor.getNome() + "!");
        System.out.println("1. Relatório Individual | 2. Relatório Global | 0. Sair");
        String op = scanner.nextLine();
        if(op.equals("1")) ExportadorJSON.gerarRelatorio(vencedor);
        else if(op.equals("2")) ExportadorJSON.gerarRelatorioCompleto(listaTodosJogadores);
    }

    public void visualizarMapaLocal(Jogador jogador) { System.out.println("Sala: " + jogador.getPosicaoAtual().getNome()); }
    public boolean processarSalaEnigmaHumano(SalaEnigma sala, Jogador jogador, Scanner scanner) {
        System.out.print("Resposta: "); String input = scanner.nextLine().trim();
        return sala.tentarResolverEnigma(jogador, input);
    }
    public boolean processarSalaAlavancaHumano(SalaAlavanca sala, Jogador jogador, Scanner scanner) {
        System.out.print("Alavanca (A/B): "); String input = scanner.nextLine().trim();
        if(input.isEmpty()) return false;
        return sala.tentarAtivarAlavanca(jogador, input.toUpperCase().charAt(0));
    }
    public void tratarEfeitosAcumulados(Jogador j, Scanner s) {} 
    public void aplicarEfeitosNegativosPendentes(Jogador j) {}

    public Divisao escolherDestino(Jogador jogador, ListADT<Divisao> vizinhos, Scanner scanner) {
        if (vizinhos.isEmpty()) return null;
        if (jogador instanceof JogadorHumano) {
            System.out.println("Opções:");
            Iterator<Divisao> it = vizinhos.iterator(); int i=1;
            Divisao[] ops = new Divisao[vizinhos.size()];
            while(it.hasNext()) { Divisao d = it.next(); ops[i-1]=d; System.out.println(i++ + ". " + d.getNome()); }
            try { int esc = Integer.parseInt(scanner.nextLine()); if(esc>0 && esc<=vizinhos.size()) return ops[esc-1]; } catch(Exception e){}
            return null;
        } else return jogador.escolherMovimento(vizinhos);
    }
}