package core.game;

import Lists.DoublyLinkedList;
import Lists.ListADT;
import Queues_Stacks.CircularArrayQueue;
import Queues_Stacks.QueueADT;
import core.model.ator.Jogador;
import core.model.divisao.Divisao;
import core.model.divisao.PontoPartida;
import core.model.itens.Enigma; 
import java.util.Scanner;
import java.util.Iterator; 

public class MotorJogo {
    
    private ListADT<Enigma> enigmas;
    private ListADT<Jogador> todosOsJogadores; 
    private QueueADT<Jogador> filaDeTurnos;
    private final GestorMapa gestorMapa;
    
    private GameLoader loader;
    private PlayerInteraction interaction;
    private TurnProcessor processor;

    public MotorJogo(GestorMapa gestorMapa) {
        this.gestorMapa = gestorMapa;
        this.enigmas = new DoublyLinkedList<>();
        this.todosOsJogadores = new DoublyLinkedList<>();
        this.filaDeTurnos = new CircularArrayQueue<>();
        
        this.loader = new GameLoader(gestorMapa, enigmas);
        this.interaction = new PlayerInteraction(gestorMapa, filaDeTurnos, todosOsJogadores);
        this.processor = new TurnProcessor(gestorMapa, filaDeTurnos, interaction);
    }

    public void limparDadosJogo() {
        this.enigmas = new DoublyLinkedList<>();
        this.todosOsJogadores = new DoublyLinkedList<>();
        this.filaDeTurnos = new CircularArrayQueue<>();
        this.loader = new GameLoader(gestorMapa, enigmas);
        this.interaction = new PlayerInteraction(gestorMapa, filaDeTurnos, todosOsJogadores);
        this.processor = new TurnProcessor(gestorMapa, filaDeTurnos, interaction);
        System.out.println("Dados do jogo reiniciados.");
    }

    public void adicionarJogador(Jogador jogador) {
        if (todosOsJogadores.size() < 4) {
            todosOsJogadores.add(jogador);
            filaDeTurnos.enqueue(jogador); 
        } else {
            System.out.println("Limite de jogadores atingido (Máx: 4).");
        }
    }
    
    public int getNumeroJogadores() { return todosOsJogadores.size(); }
    
    public String listarJogadores() {
        StringBuilder sb = new StringBuilder();
        Iterator<Jogador> it = todosOsJogadores.iterator();
        while (it.hasNext()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(it.next().getNome());
        }
        return sb.toString();
    }

    public void start() {
        System.out.println("MotorJogo iniciado.");
        processor.reset(); 

        if (todosOsJogadores.isEmpty()) {
            System.out.println("Erro: Nenhum jogador registado.");
            return;
        }

        ListADT<Divisao> pontosDePartida = new DoublyLinkedList<>();
        for (int i = 0; i < 500; i++) {
            Divisao d = gestorMapa.getDivisaoPorId(i);
            if (d != null && d instanceof PontoPartida) pontosDePartida.add(d);
        }
        if (pontosDePartida.isEmpty()) {
            Divisao fallback = gestorMapa.getDivisaoPorId(0);
            if(fallback != null) pontosDePartida.add(fallback);
        }

        Iterator<Jogador> itJogadores = todosOsJogadores.iterator();
        Iterator<Divisao> itEntradas = pontosDePartida.iterator();

        while (itJogadores.hasNext()) {
            Jogador jogador = itJogadores.next();
            filaDeTurnos.enqueue(jogador);
            if (!itEntradas.hasNext()) itEntradas = pontosDePartida.iterator();
            if (itEntradas.hasNext()) {
                Divisao entrada = itEntradas.next();
                jogador.setPosicaoAtual(entrada);
                entrada.setJogadorAtual(jogador); 
            }
        }
        gameLoop();
    }
    
    private void gameLoop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- JOGO INICIADO --- (Digite 'turno' ou ENTER)");
        while (processor.isRunning()) {
            System.out.print("> ");
            String line = null;
            try { if (!scanner.hasNextLine()) break; line = scanner.nextLine().trim(); } catch (Exception e) { break; }
            if (line == null) break;
            if ("sair".equalsIgnoreCase(line)) { stop(); break; }
            else if ("status".equalsIgnoreCase(line)) System.out.println("Jogadores: " + todosOsJogadores.size());
            else processor.processarTurno(scanner);
        }
    }

    public void stop() { processor.stopGame(); System.out.println("Jogo terminado."); }
    public void carregarEventos(String caminho) { loader.carregarEventos(caminho); }
    public void carregarMapa(String caminho) { loader.carregarMapa(caminho); }
    public void carregarEnigmas(String caminho) { loader.carregarEnigmas(caminho); }

    // --- [NOVO] Getter para o Menu usar ao criar Bots ---
    public GestorMapa getGestorMapa() {
        return this.gestorMapa;
    }
}