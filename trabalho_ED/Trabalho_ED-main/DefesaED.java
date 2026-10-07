import core.game.GameLoader;
import core.game.GestorMapa;
import core.model.itens.Enigma;
import core.model.itens.Evento;
import Lists.DoublyLinkedList;
import Lists.ListADT;
import java.util.Iterator;

public class DefesaED {

    public static void main(String[] args) {
        System.out.println("=== DEFESA ED: Iterar Eventos do Jogo ===");

        GestorMapa gestorMapa = new GestorMapa();
        ListADT<Enigma> listaEnigmasDummy = new DoublyLinkedList<>();

        GameLoader loader = new GameLoader(gestorMapa, listaEnigmasDummy);


        String ficheiroEventos = "resources/eventos/eventos.json";
        loader.carregarEventos(ficheiroEventos);
        
        String ficheiroMapa = "resources/mapas/labirinto_1.json";
        loader.carregarMapa(ficheiroMapa);

        System.out.println("\n--- LISTAGEM DE EVENTOS DISPONÍVEIS ---");
        
        Iterator<Evento> itEventos = loader.getEventosDoJogo();
        
        int count = 0;
        while (itEventos.hasNext()) {
                Evento e = itEventos.next();
                count++;
                System.out.println("Evento" + count + e.getTipoEfeito() + e.getNome());
                System.out.println("Descrição: " + e.getDescricao());
            }
        
        System.out.println("\nTotal de eventos carregados: " + count);
    }
}