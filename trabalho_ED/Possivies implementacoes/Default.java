import core.game.GestorMapa;
import core.game.GameLoader;
import Lists.DoublyLinkedList;
import core.model.itens.Enigma;
import java.util.Iterator;
import core.model.divisao.Divisao;
import core.model.itens.Corredor;

public class Default {
    public static void main(String[] args) {
        // 1. Instanciar Gestor e Carregar Mapa
        // Precisamos de instanciar o GestorMapa pois ele contém a lista de divisões
        GestorMapa gestor = new GestorMapa();
        
        // O GameLoader precisa de uma lista de enigmas para inicializar, criamos uma vazia
        GameLoader loader = new GameLoader(gestor, new DoublyLinkedList<Enigma>());
        
        // Carregar um dos mapas existentes nos resources
        loader.carregarMapa("resources/mapas/labirinto_1.json"); 
        
        System.out.println("Mapa carregado com sucesso. A testar funcionalidade...\n");

        // 2. CHAMADA DO MÉTODO DA DEFESA (Descomentar o exemplo que queres testar)
        
        // --- Exemplo 1: Filtrar Salas de Desafio ---
        /*
        Iterator<Divisao> it = gestor.getDivisoesDeDesafio();
        while(it.hasNext()) {
            System.out.println("Sala de Desafio encontrada: " + it.next().getNome());
        }
        */

        // --- Exemplo 2: Filtrar Corredores Bloqueados ---
        /*
        Iterator<Corredor> itCB = gestor.getCorredoresBloqueados();
        while(itCB.hasNext()) {
            Corredor c = itCB.next();
            System.out.println("Corredor Bloqueado ID " + c.toString()); // Assumindo toString ou getId
        }
        */
       
        // --- Exemplo 3: Total de Pontos ---
        // System.out.println("Total de pontos no mapa: " + gestor.calcularTotalPontosMapa());
    }
}