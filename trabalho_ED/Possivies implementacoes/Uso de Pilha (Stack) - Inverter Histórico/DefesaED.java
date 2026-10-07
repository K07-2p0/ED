import core.game.GameLoader;
import core.game.GestorMapa;
import core.model.ator.Jogador;
import core.model.ator.JogadorHumano;
import core.model.divisao.Divisao;
import core.model.itens.Enigma;
import Lists.DoublyLinkedList;
import Lists.ListADT;
import Queues_Stacks.LinkedStack; // Importante: Usar a Stack da API
import Queues_Stacks.StackADT;
import java.util.Iterator;

public class DefesaED {

    public static void main(String[] args) {
        System.out.println("=== DEFESA ED: Inverter Histórico com Stack ===");

        // 1. Instanciar Gestor e Loader
        GestorMapa gestorMapa = new GestorMapa();
        ListADT<Enigma> listaEnigmasDummy = new DoublyLinkedList<>();
        GameLoader loader = new GameLoader(gestorMapa, listaEnigmasDummy);

        // 2. Carregar o Mapa
        String caminhoFicheiroMapa = "resources/mapas/labirinto_1.json";
        try {
            loader.carregarMapa(caminhoFicheiroMapa);
            System.out.println("-> Mapa carregado.");
        } catch (Exception e) {
            System.out.println("Erro ao carregar mapa.");
            e.printStackTrace();
            return;
        }

        // 3. Preparar o Cenário (Simulação)
        // Precisamos de criar um jogador e movê-lo para ter dados no histórico
        System.out.println("\n--- A Simular Movimentos ---");
        
        // Obter algumas salas para mover o jogador
        Divisao start = gestorMapa.getDivisaoPorId(0); // Ponto Partida
        Divisao sala1 = gestorMapa.getDivisaoPorId(10); // Sala Enigma
        Divisao sala2 = gestorMapa.getDivisaoPorId(99); // Sala Tesouro (exemplo)

        if (start == null || sala1 == null) {
            System.out.println("Erro: IDs de salas não encontrados no mapa carregado.");
            return;
        }

        // Criar jogador
        Jogador jogador = new JogadorHumano("AlunoDefesa", start);
        System.out.println("1. Jogador criado em: " + start.getNome());

        // Simular movimento 1 (O método setPosicaoAtual adiciona ao histórico automaticamente)
        jogador.setPosicaoAtual(sala1);
        System.out.println("2. Jogador moveu-se para: " + sala1.getNome());

        // Simular movimento 2
        if (sala2 != null) {
            jogador.setPosicaoAtual(sala2);
            System.out.println("3. Jogador moveu-se para: " + sala2.getNome());
        }

        // 4. Testar a Inversão
        System.out.println("\n-----------------------------------------");
        System.out.println("Histórico Original (Cronológico):");
        Iterator<String> itOriginal = jogador.getHistorico().getLogs().iterator();
        while(itOriginal.hasNext()) {
            System.out.println(" -> " + itOriginal.next());
        }

        System.out.println("\n-----------------------------------------");
        System.out.println("Histórico Invertido (Via Stack):");
        
        // Chamada ao método de resolução
        apresentarHistoricoInvertido(jogador);
        
        System.out.println("-----------------------------------------");
        System.out.println("=== FIM DA DEFESA ===");
    }

    /**
     * [MÉTODO PARA A DEFESA]
     * Recebe um jogador, coloca o seu histórico numa Stack e imprime em ordem inversa.
     */
    public static void apresentarHistoricoInvertido(Jogador jogador) {
        // 1. Instanciar a Pilha (Stack) usando a API fornecida (API_ED.jar)
        // Podes usar LinkedStack ou ArrayStack (se disponível)
        StackADT<String> pilhaHistorico = new LinkedStack<>();

        // 2. Obter o histórico original (Lista)
        ListADT<String> logsOriginais = jogador.getHistorico().getLogs();
        Iterator<String> it = logsOriginais.iterator();

        // 3. Preencher a Pilha (Push)
        // O primeiro evento entra primeiro (fica no fundo), o último fica no topo.
        while (it.hasNext()) {
            String log = it.next();
            pilhaHistorico.push(log);
        }

        // 4. Esvaziar a Pilha (Pop) e Imprimir
        // Como é LIFO (Last In, First Out), o último evento sai primeiro.
        if (pilhaHistorico.isEmpty()) {
            System.out.println("(Histórico vazio)");
        } else {
            while (!pilhaHistorico.isEmpty()) {
                try {
                    String logInvertido = pilhaHistorico.pop();
                    System.out.println(" <- " + logInvertido);
                } catch (Exception e) {
                    System.out.println("Erro ao retirar da pilha: " + e.getMessage());
                }
            }
        }
    }
}