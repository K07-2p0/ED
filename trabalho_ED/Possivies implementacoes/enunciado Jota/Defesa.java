import core.game.GameLoader;
import core.game.GestorMapa;
import core.model.divisao.Divisao;
import core.model.divisao.SalaTesouro;
import core.model.divisao.PontoPartida;
import core.model.itens.Enigma;
import Lists.DoublyLinkedList;
import Lists.ListADT;
import java.util.Iterator;

public class Defesa {
    
    public static void main(String[] args) {
        
        System.out.println("--- Teste Funcionalidade Defesa ED ---");
        
        // Inicializar GestorMapa
        GestorMapa gestorMapa = new GestorMapa();
        
        // Estruturas de dados necessárias para o GameLoader
        ListADT<Enigma> enigmas = new DoublyLinkedList<>();
        GameLoader loader = new GameLoader(gestorMapa, enigmas);
        
        // Carregar recursos essenciais (para evitar erros de referência no GameLoader)
        loader.carregarEnigmas("resources/enigmas/enigma_1.json");
        loader.carregarEventos("resources/eventos/eventos.json");
        
        // 1. Carregar um mapa existente em JSON (usando o labirinto_3.json para teste)
        String mapaPath = "resources/mapas/labirinto_3.json";
        System.out.println("\nCarregando Mapa: " + mapaPath);
        loader.carregarMapa(mapaPath);
        
        // 2. Apresentar no main a funcionalidade pedida
        System.out.println("\n--- Divisões Simples (Sem Enigma ou Alavanca) ---");
        
        // Chama o novo método em GestorMapa
        Iterator<Divisao> itSimples = gestorMapa.obterDivisoesSimples();
        int contador = 0;
        
        // Itera sobre o resultado e imprime
        while (itSimples.hasNext()) {
            Divisao d = itSimples.next();
            String tipo = "";
            
            // Determina o tipo concreto para a apresentação
            if (d instanceof PontoPartida) {
                tipo = "PontoPartida";
            } else if (d instanceof SalaTesouro) {
                tipo = "SalaTesouro";
            } else {
                tipo = d.getClass().getSimpleName() + " (ERRO)"; 
            }
            
            System.out.printf("-> ID: %-3d | Tipo: %-15s | Nome: %s\n", d.getId(), tipo, d.getNome());
            contador++;
        }
        
        System.out.println("\nTotal de Divisões Simples Encontradas: " + contador);
        
        System.out.println("--- Fim do Teste ---");
    }
}