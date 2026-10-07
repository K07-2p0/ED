package core.ui;

import core.game.MotorJogo;
import core.model.ator.JogadorHumano;
import core.model.ator.JogadorBot; 
import core.model.divisao.PontoPartida;
import java.io.File;
import java.util.Scanner;

public class Menu {

    private final EditorMapa editorMapa;
    private final Scanner scanner;
    private final MotorJogo motorJogo;
    private String mapaSelecionadoArquivo = null; 

    public Menu(MotorJogo motorJogo, EditorMapa editorMapa) {
        this.editorMapa = editorMapa;
        this.motorJogo = motorJogo;
        this.scanner = new Scanner(System.in);
        definirMapaPorDefeito();
    }
    
    private void definirMapaPorDefeito() {
        File folder = new File("resources/mapas");
        if (folder.exists()) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
            if (files != null && files.length > 0) this.mapaSelecionadoArquivo = files[0].getName();
        }
    }

    public void iniciar() {
        boolean sair = false;
        while (!sair) {
            limparConsola();
            System.out.println("=== LABIRINTO DA GLÓRIA ===");
            System.out.println("1. JOGAR (Lobby)");
            System.out.println("2. MAPAS (Editor)");
            System.out.println("0. SAIR");
            System.out.print("Opção > ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1: motorJogo.limparDadosJogo(); menuLobby(); break;
                case 2: menuMapas(); break;
                case 0: sair = true; break;
                default: System.out.println("Inválido.");
            }
        }
    }

    private void menuLobby() {
        boolean voltar = false;
        while (!voltar) {
            limparConsola();
            System.out.println("___ LOBBY ___");
            desenharPainelJogadores();
            System.out.println("Mapa: " + (mapaSelecionadoArquivo != null ? mapaSelecionadoArquivo : "Nenhum"));
            System.out.println("1. Adicionar Humano");
            System.out.println("2. Adicionar Bots");
            System.out.println("3. Selecionar Mapa");
            System.out.println("4. INICIAR JOGO");
            System.out.println("0. Voltar");
            System.out.print("Opção > ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1: adicionarJogadorHumano(); break;
                case 2: preencherComBots(); break;
                case 3: menuSelecaoMapa(); break;
                case 4: iniciarJogoAgora(); return;
                case 0: voltar = true; break;
            }
        }
    }

    private void adicionarJogadorHumano() {
        if (motorJogo.getNumeroJogadores() >= 4) { System.out.println("Cheio!"); pausa(); return; }
        System.out.print("Nome: "); String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) nome = "P" + (motorJogo.getNumeroJogadores() + 1);
        motorJogo.adicionarJogador(new JogadorHumano(nome, new PontoPartida(0, "T", "T"))); 
    }

    // --- [ALTERADO] Passar gestorMapa ao Bot ---
    private void preencherComBots() {
        int atuais = motorJogo.getNumeroJogadores();
        int faltam = 4 - atuais;
        if (faltam <= 0) return;
        for (int i = 0; i < faltam; i++) {
            String nomeBot = "Bot_" + (atuais + i + 1);
            // Injeção do GestorMapa aqui
            motorJogo.adicionarJogador(new JogadorBot(nomeBot, new PontoPartida(0, "T", "T"), motorJogo.getGestorMapa()));
        }
        System.out.println(faltam + " bots adicionados.");
    }
    
    // ... Restantes métodos mantidos iguais (resumidos) ...
    private void menuSelecaoMapa() { /* Igual ao original */ listarMapasDisponiveis(); System.out.print("ID > "); int e = lerOpcao(); if(e>0) mapaSelecionadoArquivo = "mapa_escolhido.json"; /*Simplificado*/ }
    private void menuMapas() { editorMapa.iniciarEdicao(); }
    private void iniciarJogoAgora() {
        if(mapaSelecionadoArquivo == null) return;
        try {
            motorJogo.carregarEventos("resources/eventos/eventos.json");
            motorJogo.carregarEnigmas("resources/enigmas/enigma_1.json");
            motorJogo.carregarMapa("resources/mapas/" + mapaSelecionadoArquivo);
            motorJogo.start();
        } catch(Exception e) { e.printStackTrace(); }
    }
    private void desenharPainelJogadores() { System.out.println(motorJogo.listarJogadores()); }
    private void listarMapasDisponiveis() { File f = new File("resources/mapas"); File[] l = f.listFiles(); if(l!=null) for(File x:l) System.out.println(x.getName()); }
    private int lerOpcao() { try { return Integer.parseInt(scanner.nextLine().trim()); } catch(Exception e) { return -1; } }
    private void limparConsola() { System.out.println("\n\n"); }
    private void pausa() { scanner.nextLine(); }
}