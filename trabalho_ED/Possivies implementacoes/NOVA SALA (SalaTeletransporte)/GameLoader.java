package core.game;

import Lists.DoublyLinkedList;
import Lists.ListADT;
import core.io.LeitorJSON;
import core.model.divisao.*;
import core.model.itens.Corredor;
import core.model.itens.Enigma;
import core.model.itens.Evento;
import java.util.Iterator;
import java.util.Random;

public class GameLoader {

    private final GestorMapa gestorMapa;
    private final ListADT<Enigma> enigmas; 
    private final ListADT<Evento> eventosDisponiveis; 
    private final Random random;

    public GameLoader(GestorMapa gestorMapa, ListADT<Enigma> enigmas) {
        this.gestorMapa = gestorMapa;
        this.enigmas = enigmas;
        this.eventosDisponiveis = new DoublyLinkedList<>();
        this.random = new Random();
    }
    
    public void carregarEventos(String caminho) {
         try {
            System.out.println("Loader: Carregar eventos...");
            Object parsed = LeitorJSON.parseFile(caminho);
            if (parsed instanceof java.util.List) {
                java.util.List<?> list = (java.util.List<?>) parsed;
                for (Object o : list) {
                    if (o instanceof java.util.Map) {
                        java.util.Map<?,?> map = (java.util.Map<?,?>) o;
                        eventosDisponiveis.add(new Evento((String)map.get("nome"), (String)map.get("descricao"), (String)map.get("tipo_efeito")));
                    }
                }
            }
         } catch(Exception e) { eventosDisponiveis.add(new Evento("NADA", "...", "NADA")); }
    }
    
    public void carregarEnigmas(String caminho) {
         try {
            System.out.println("Loader: Carregar enigmas...");
            Object parsed = LeitorJSON.parseFile(caminho);
            if (parsed instanceof java.util.Map) {
                java.util.Map<String, Object> root = (java.util.Map<String, Object>) parsed;
                java.util.List<?> questoesList = (java.util.List<?>) root.get("questoes");
                for (Object o : questoesList) {
                    java.util.Map<String, Object> map = (java.util.Map<String, Object>) o;
                    int id = LeitorJSON.getInteger(map, "id");
                    String p = LeitorJSON.getString(map, "pergunta");
                    String r = LeitorJSON.getString(map, "resposta_correta");
                    int pts = (map.containsKey("pontos_por_acerto")) ? LeitorJSON.getInteger(map, "pontos_por_acerto") : 10;
                    java.util.List<?> optList = (java.util.List<?>) map.get("opcoes");
                    String[] opt = new String[optList.size()];
                    for(int i=0; i<optList.size(); i++) opt[i]=(String)optList.get(i);
                    enigmas.add(new Enigma(id, p, opt, r, pts));
                }
            }
         } catch(Exception e) { e.printStackTrace(); }
    }

    private Enigma getEnigmaAleatorio() {
        if (enigmas.isEmpty()) return new Enigma(0,"?",new String[]{"A"},"A",0);
        int idx = random.nextInt(enigmas.size());
        Iterator<Enigma> it = enigmas.iterator();
        Enigma e = it.next();
        for(int i=0; i<idx; i++) if(it.hasNext()) e=it.next();
        return e;
    }

    public Evento getEventoAleatorio() {
        if (eventosDisponiveis.isEmpty()) return new Evento("N","N","NADA");
        int idx = random.nextInt(eventosDisponiveis.size());
        Iterator<Evento> it = eventosDisponiveis.iterator();
        Evento e = it.next();
        for(int i=0; i<idx; i++) if(it.hasNext()) e=it.next();
        return e;
    }

    public void carregarMapa(String caminho) {
         try {
            System.out.println("Loader: Carregar mapa...");
            Object parsed = LeitorJSON.parseFile(caminho);
            if (parsed instanceof java.util.Map) {
                java.util.Map<String, Object> root = (java.util.Map<String, Object>) parsed;
                int cap = root.containsKey("tamanho_network") ? ((Number)root.get("tamanho_network")).intValue() : 20;
                gestorMapa.reiniciarGrafo(cap);
                
                java.util.List<?> divisoesList = (java.util.List<?>) root.get("divisoes");
                for (Object o : divisoesList) {
                    java.util.Map<?, ?> divMap = (java.util.Map<?, ?>) o;
                    int id = ((Number) divMap.get("id")).intValue();
                    String tipo = (String) divMap.get("tipo");
                    String nome = (String) divMap.get("nome");
                    String desc = (String) divMap.get("descricao");
                    
                    Divisao novaDivisao = null;
                    
                    // [HACK PARA A DEFESA]
                    // Se o ID for 1 (geralmente Entrada Este), forçamos ser SalaTeletransporte
                    // Isto evita ter de criar JSONs novos durante o teste.
                    if (id == 1) {
                        System.out.println("--> [DEBUG] Sala ID 1 convertida em SalaTeletransporte.");
                        novaDivisao = new SalaTeletransporte(id, nome + " (Portal)", desc, gestorMapa);
                    } else {
                        switch (tipo) {
                            case "SalaTesouro": novaDivisao = new SalaTesouro(id, nome, desc); break;
                            case "SalaEnigma": novaDivisao = new SalaEnigma(id, nome, desc, getEnigmaAleatorio()); break;
                            case "SalaAlavanca":
                                 int[] cIds = new int[0]; // Simplificado
                                 novaDivisao = new SalaAlavanca(id, nome, desc, 'A', cIds, getEventoAleatorio(), getEventoAleatorio());
                                 break;
                            case "PontoPartida": default: novaDivisao = new PontoPartida(id, nome, desc);
                        }
                    }
                    gestorMapa.adicionarDivisao(novaDivisao);
                }
                
                java.util.List<?> corredoresList = (java.util.List<?>) root.get("corredores");
                for (Object o : corredoresList) {
                    java.util.Map<?, ?> cMap = (java.util.Map<?, ?>) o;
                    int id = ((Number) cMap.get("id")).intValue();
                    int orig = ((Number) cMap.get("origem_id")).intValue();
                    int dest = ((Number) cMap.get("destino_id")).intValue();
                    double peso = cMap.containsKey("peso") ? ((Number)cMap.get("peso")).doubleValue() : 1.0;
                    Corredor c = new Corredor(id, "Corredor "+id, peso, getEventoAleatorio());
                    if(LeitorJSON.getBoolean((java.util.Map<String,Object>)cMap, "bloqueado") == Boolean.TRUE) c.setEstaBloqueado(true);
                    gestorMapa.adicionarCorredor(orig, dest, c);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}