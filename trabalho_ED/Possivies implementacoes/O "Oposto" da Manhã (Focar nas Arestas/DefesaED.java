import core.game.GameLoader;
import core.game.GestorMapa;
import core.model.itens.Corredor;
import core.model.itens.Enigma;
import Lists.DoublyLinkedList;
import Lists.ListADT;
import java.util.Iterator;

public class DefesaED {

    public static void main(String[] args) {
        System.out.println("=== DEFESA ED: Filtrar Corredores Bloqueados ===");

        // 1. Instanciar o GestorMapa
        GestorMapa gestorMapa = new GestorMapa();
        
        // 2. Criar uma lista vazia de enigmas para satisfazer o GameLoader
        // (O GameLoader precisa disto no construtor, mesmo que não usemos enigmas aqui)
        ListADT<Enigma> listaEnigmasDummy = new DoublyLinkedList<>();
        
        // 3. Inicializar o Loader
        GameLoader loader = new GameLoader(gestorMapa, listaEnigmasDummy);

        // 4. Carregar o Mapa
        // IMPORTANTE: Altera manualmente um corredor no JSON para "bloqueado": true para testares!
        String caminhoFicheiroMapa = "resources/mapas/labirinto_1.json";
        
        try {
            System.out.println("-> A carregar mapa de: " + caminhoFicheiroMapa);
            loader.carregarMapa(caminhoFicheiroMapa);
            System.out.println("-> Mapa carregado com sucesso.\n");
            
        } catch (Exception e) {
            System.out.println("ERRO CRÍTICO: Não foi possível carregar o mapa.");
            System.out.println("Verifica se o ficheiro existe em: " + caminhoFicheiroMapa);
            e.printStackTrace();
            return;
        }

        // 5. Testar a Nova Funcionalidade
        System.out.println("--- LISTA DE CORREDORES BLOQUEADOS ---");
        
        // Chamada ao método que criámos no GestorMapa
        Iterator<Corredor> it = gestorMapa.getCorredoresBloqueados();
        
        int contagem = 0;
        
        if (!it.hasNext()) {
            System.out.println("[AVISO] Não foram encontrados corredores bloqueados neste mapa.");
            System.out.println("(Dica: Edita o JSON e muda 'bloqueado': false para true num corredor)");
        } else {
            while (it.hasNext()) {
                Corredor c = it.next();
                contagem++;
                
                // Apresentar informações do corredor
                System.out.println("Corredor #" + contagem);
                // Assume-se que o corredor tem origem/destino definidos no carregamento
                String origem = (c.getOrigem() != null) ? c.getOrigem().getNome() : "Desconhecido";
                String destino = (c.getDestino() != null) ? c.getDestino().getNome() : "Desconhecido";
                
                System.out.println("   - Ligação: " + origem + " <-> " + destino);
                System.out.println("   - Peso: " + c.getPeso());
                System.out.println("   - Estado: " + (c.isEstaBloqueado() ? "BLOQUEADO" : "Livre"));
                System.out.println("----------------------------------------");
            }
        }
        
        System.out.println("\nTotal encontrado: " + contagem);
        System.out.println("=== FIM DO TESTE ===");
    }
}