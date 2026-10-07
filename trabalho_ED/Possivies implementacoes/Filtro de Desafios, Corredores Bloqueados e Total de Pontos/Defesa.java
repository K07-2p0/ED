import core.game.GestorMapa;
import core.game.GameLoader;
import Lists.DoublyLinkedList;
import core.model.itens.Enigma;
import core.model.divisao.Divisao;
import core.model.itens.Corredor;
import java.util.Iterator;

public class Defesa {
    
    public static void main(String[] args) {
        System.out.println("=== A INICIAR TESTE DE DEFESA ===");

        // 1. Instanciar Gestor e Loader (igual ao projeto normal)
        GestorMapa gestor = new GestorMapa();
        
        // O GameLoader precisa de uma lista de enigmas para não dar erro, passamos uma vazia
        GameLoader loader = new GameLoader(gestor, new DoublyLinkedList<Enigma>());
        
        // 2. Carregar Mapa
        // IMPORTANTE: Verifica se o caminho para o JSON está correto no teu computador
        String caminhoMapa = "resources/mapas/labirinto_1.json"; 
        
        System.out.println("A carregar mapa de: " + caminhoMapa);
        loader.carregarMapa(caminhoMapa);
        
        System.out.println("\n--- MAPA CARREGADO COM SUCESSO ---");

        // ---------------------------------------------------------
        // TESTE 1: Salas de Desafio (Enigmas e Alavancas)
        // ---------------------------------------------------------
        System.out.println("\n[TESTE 1] Divisões de Desafio (Enigma/Alavanca):");
        Iterator<Divisao> itDesafio = gestor.getDivisoesDeDesafio();
        int countDesafios = 0;
        
        while(itDesafio.hasNext()) {
            Divisao d = itDesafio.next();
            System.out.println(" - " + d.getNome() + " (ID: " + d.getId() + ")");
            countDesafios++;
        }
        
        if (countDesafios == 0) {
            System.out.println(" (Nenhuma sala de desafio encontrada)");
        }

        // ---------------------------------------------------------
        // TESTE 2: Corredores Bloqueados
        // ---------------------------------------------------------
        System.out.println("\n[TESTE 2] Corredores Bloqueados:");
        Iterator<Corredor> itBloq = gestor.getCorredoresBloqueados();
        int countBloqueados = 0;
        
        while(itBloq.hasNext()) {
            Corredor c = itBloq.next();
            // Nota: O método toString() ou getters dependem da tua classe Corredor
            System.out.println(" - Corredor Bloqueado (Peso: " + c.getPeso() + ")");
            countBloqueados++;
        }
        
        if (countBloqueados == 0) {
            System.out.println(" (Nenhum corredor bloqueado encontrado)");
        }

        // ---------------------------------------------------------
        // TESTE 3: Total de Pontos no Mapa
        // ---------------------------------------------------------
        System.out.println("\n[TESTE 3] Total de Pontos possíveis no Mapa:");
        int total = gestor.calcularTotalPontosMapa();
        System.out.println(" - Soma total dos pontos dos enigmas: " + total);
        
        System.out.println("\n=== FIM DO TESTE ===");
    }
}