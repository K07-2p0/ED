import core.game.GameLoader;
import core.game.GestorMapa;
import core.model.divisao.Divisao;
import core.model.itens.Enigma;
import Lists.DoublyLinkedList;
import Lists.ListADT;
import java.util.Iterator;

public class DefesaED {

    public static void main(String[] args) {
        System.out.println("=== DEFESA ED: Divisões sem Items nem Eventos ===");

        // 1. Instanciar o GestorMapa
        GestorMapa gestorMapa = new GestorMapa();
        
        // 2. Instanciar lista auxiliar de Enigmas (necessária para o construtor do GameLoader)
        ListADT<Enigma> enigmasDummy = new DoublyLinkedList<>();
        
        // 3. Configurar o Loader
        GameLoader loader = new GameLoader(gestorMapa, enigmasDummy);

        // 4. Carregar o Mapa (Usar um dos JSONs disponíveis)
        String caminhoMapa = "resources/mapas/labirinto_1.json"; // Podes trocar por labirinto_2.json
        
        try {
            System.out.println("-> A carregar mapa: " + caminhoMapa);
            loader.carregarMapa(caminhoMapa);
            System.out.println("-> Mapa carregado com sucesso.\n");
        } catch (Exception e) {
            System.out.println("Erro ao carregar mapa: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // 5. Executar a funcionalidade pedida
        System.out.println("--- LISTAGEM DE DIVISÕES LIMPAS (Sem Items/Eventos) ---");
        
        // Chamada ao método criado no GestorMapa
        Iterator<Divisao> it = gestorMapa.getDivisoesSemItensNemEventos();
        
        int total = 0;
        if (!it.hasNext()) {
            System.out.println("Nenhuma divisão encontrada com estes critérios.");
        } else {
            while (it.hasNext()) {
                Divisao d = it.next();
                total++;
                
                System.out.println("Divisão [" + d.getId() + "]: " + d.getNome());
                System.out.println("   -> Tipo: " + d.getClass().getSimpleName());
                System.out.println("   -> Descrição: " + d.getDescricao());
                System.out.println("------------------------------------------------");
            }
        }
        
        System.out.println("\nTotal de divisões encontradas: " + total);
        System.out.println("=== FIM DA DEFESA ===");
    }
}