import core.game.MotorJogo;
import core.game.GestorMapa;
import core.ui.Menu;
import core.ui.EditorMapa;

public class Main {
    
    public static void main(String[] args) {
        // 1. Instanciar GestorMapa (usa API_ED.jar)
        GestorMapa gestorMapa = new GestorMapa();
        
        // 2. Injetar GestorMapa no MotorJogo
        MotorJogo motorJogo = new MotorJogo(gestorMapa);
        
        // 3. Iniciar UI
        EditorMapa editorMapa = new EditorMapa(gestorMapa);
        Menu menu = new Menu(motorJogo, editorMapa);
        
        // Suporte a argumentos de linha de comando
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                String a = args[i];
                if ("--mapa".equals(a) && i + 1 < args.length) {
                    motorJogo.carregarMapa(args[++i]);
                } else if ("--enigmas".equals(a) && i + 1 < args.length) {
                    motorJogo.carregarEnigmas(args[++i]);
                }
            }
        }

        menu.iniciar();

        
    }
}