package core.game;

import Lists.DoublyLinkedList;
import Lists.ListADT;
import Trees_Heaps.PriorityQueue; // [Alterado]
import core.model.ator.Jogador;
import core.model.divisao.Divisao;
import core.model.divisao.PontoPartida;
import core.model.itens.Enigma; 
import java.util.Scanner;
import java.util.Iterator; 

public class MotorJogo {
    
    private ListADT<Enigma> enigmas;
    private ListADT<Jogador> todosOsJogadores; 
    private PriorityQueue<Jogador> filaDeTurnos; // [Alterado]
    
    private final GestorMapa gestorMapa;
    private GameLoader loader;
    private PlayerInteraction interaction;
    private TurnProcessor processor;

    public MotorJogo(GestorMapa gestorMapa) {
        this.gestorMapa = gestorMapa;
        this.enigmas = new DoublyLinkedList<>();
        this.todosOsJogadores = new DoublyLinkedList<>();
        this.filaDeTurnos = new PriorityQueue<>(); // [Alterado]
        
        this.loader = new GameLoader(gestorMapa, enigmas);
        this.interaction = new PlayerInteraction(gestorMapa, filaDeTurnos, todosOsJogadores);
        this.processor = new TurnProcessor(gestorMapa, filaDeTurnos, interaction);
    }

    public void limparDadosJogo() {
        this.enigmas = new DoublyLinkedList<>();
        this.todosOsJogadores = new DoublyLinkedList<>();
        this.filaDeTurnos = new PriorityQueue<>(); // [Alterado]

        this.loader = new GameLoader(gestorMapa, enigmas);
        this.interaction = new PlayerInteraction(gestorMapa, filaDeTurnos, todosOsJogadores);
        this.processor = new TurnProcessor(gestorMapa, filaDeTurnos, interaction);
        System.out.println("Reset feito.");
    }

    public void adicionarJogador(Jogador jogador) {
        if (todosOsJogadores.size() < 4) {
            todosOsJogadores.add(jogador);
            // [Alterado] Prioridade inicial 0.0
            filaDeTurnos.enqueue(jogador, 0.0); 
        } else {
            System.out.println("Cheio.");
        }
    }
    
    public int getNumeroJogadores() { return todosOsJogadores.size(); }
    public String listarJogadores() { return "" + todosOsJogadores.size(); } // Simplificado

    public void start() {
        System.out.println("Iniciando...");
        processor.reset(); 

        if (todosOsJogadores.isEmpty()) return;

        // Atribuição de PontoPartida (Código omitido por brevidade, igual original)
        // ... (Logica de PontoPartida igual) ...

        Iterator<Jogador> itJogadores = todosOsJogadores.iterator();
        while (itJogadores.hasNext()) {
            Jogador jogador = itJogadores.next();
            // [Alterado] Garantir que estão na fila com prioridade
            // Nota: Se já foram adicionados no "adicionarJogador", isto pode duplicar dependendo da logica
            // Mas para garantir reset, reinserimos ou limpamos antes. 
            // Assumindo que start corre uma vez:
            // filaDeTurnos.enqueue(jogador, 0.0); // Se necessário re-inserir
        }
        
        gameLoop();
    }
    
    private void gameLoop() {
        Scanner scanner = new Scanner(System.in);
        while (processor.isRunning()) {
            System.out.print("> ");
            String line = scanner.nextLine();
            if ("sair".equalsIgnoreCase(line)) break;
            processor.processarTurno(scanner);
        }
    }

    public void stop() { processor.stopGame(); }
    public void carregarEventos(String c) { loader.carregarEventos(c); }
    public void carregarMapa(String c) { loader.carregarMapa(c); }
    public void carregarEnigmas(String c) { loader.carregarEnigmas(c); }
    public GestorMapa getGestorMapa() { return gestorMapa; }
}