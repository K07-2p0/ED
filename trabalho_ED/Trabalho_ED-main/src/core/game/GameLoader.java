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
    // Esta é a lista que guarda todos os eventos do jogo
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
            System.out.println("A carregar eventos de: " + caminho);
            Object parsed = LeitorJSON.parseFile(caminho);
            if (parsed instanceof java.util.List) {
                java.util.List<?> list = (java.util.List<?>) parsed;
                for (Object o : list) {
                    if (o instanceof java.util.Map) {
                        java.util.Map<?,?> map = (java.util.Map<?,?>) o;
                        String nome = (String) map.get("nome");
                        String desc = (String) map.get("descricao");
                        String tipo = (String) map.get("tipo_efeito");
                        eventosDisponiveis.add(new Evento(nome, desc, tipo));
                    }
                }
            }
         } catch(Exception e) {
             System.out.println("Erro eventos: " + e.getMessage());
             eventosDisponiveis.add(new Evento("Nada", "Nada", Evento.EFEITO_NADA));
         }
    }
    
    public void carregarEnigmas(String caminho) {
         try {
            System.out.println("A carregar enigmas de: " + caminho);
            Object parsed = LeitorJSON.parseFile(caminho);
            if (parsed instanceof java.util.Map) {
                java.util.Map<String, Object> root = (java.util.Map<String, Object>) parsed;
                Object questoesObj = root.get("questoes");
                if (questoesObj instanceof java.util.List) {
                    java.util.List<?> questoesList = (java.util.List<?>) questoesObj;
                    for (Object o : questoesList) {
                        java.util.Map<String, Object> map = (java.util.Map<String, Object>) o;
                        
                        int id = LeitorJSON.getInteger(map, "id");
                        String pergunta = LeitorJSON.getString(map, "pergunta");
                        String respostaCorreta = LeitorJSON.getString(map, "resposta_correta");
                        
                        Integer pontosObj = LeitorJSON.getInteger(map, "pontos_por_acerto");
                        int pontos = (pontosObj != null) ? pontosObj : 10;

                        java.util.List<?> opcoesList = (java.util.List<?>) map.get("opcoes");
                        String[] opcoesResposta = new String[opcoesList.size()];
                        for (int i = 0; i < opcoesList.size(); i++) opcoesResposta[i] = (String) opcoesList.get(i);
                        
                        enigmas.add(new Enigma(id, pergunta, opcoesResposta, respostaCorreta, pontos));
                    }
                }
            }
         } catch(Exception e) { e.printStackTrace(); }
    }

    private Enigma getEnigmaAleatorio() {
        if (enigmas.isEmpty()) return new Enigma(0, "Sem enigmas", new String[]{"A"}, "A", 0);
        int index = random.nextInt(enigmas.size());
        Iterator<Enigma> it = enigmas.iterator();
        Enigma escolhido = it.next();
        for(int i=0; i<index; i++) if (it.hasNext()) escolhido = it.next();
        return escolhido;
    }

    public Evento getEventoAleatorio() {
        if (eventosDisponiveis.isEmpty()) return new Evento("Vazio", "...", Evento.EFEITO_NADA);
        int index = random.nextInt(eventosDisponiveis.size());
        Iterator<Evento> it = eventosDisponiveis.iterator();
        Evento escolhido = it.next();
        for (int i = 0; i < index; i++) if (it.hasNext()) escolhido = it.next();
        return escolhido;
    }

    public void carregarMapa(String caminho) {
         try {
            System.out.println("A carregar mapa de: " + caminho);
            Object parsed = LeitorJSON.parseFile(caminho);
            if (parsed instanceof java.util.Map) {
                java.util.Map<String, Object> root = (java.util.Map<String, Object>) parsed;
                int capacidadeGrafo = 20; 
                if (root.containsKey("tamanho_network")) {
                    capacidadeGrafo = ((Number) root.get("tamanho_network")).intValue();
                }
                gestorMapa.reiniciarGrafo(capacidadeGrafo);
                
                Object divisoesObj = root.get("divisoes");
                if (divisoesObj instanceof java.util.List) {
                    java.util.List<?> divisoesList = (java.util.List<?>) divisoesObj;
                    for (Object o : divisoesList) {
                        java.util.Map<?, ?> divMap = (java.util.Map<?, ?>) o;
                        int id = ((Number) divMap.get("id")).intValue();
                        String tipo = (String) divMap.get("tipo");
                        String nome = (String) divMap.get("nome");
                        String desc = (String) divMap.get("descricao");
                        
                        Divisao novaDivisao = null;
                        switch (tipo) {
                            case "PontoPartida": novaDivisao = new PontoPartida(id, nome, desc); break;
                            case "SalaTesouro": novaDivisao = new SalaTesouro(id, nome, desc); break;
                            case "SalaEnigma":
                                novaDivisao = new SalaEnigma(id, nome, desc, getEnigmaAleatorio());
                                break;
                            case "SalaAlavanca":
                                 char escolha = random.nextBoolean() ? 'A' : 'B';
                                 java.util.List<Object> corrIdsList = LeitorJSON.getArray((java.util.Map<String, Object>)divMap, "corredores_controlados_ids");
                                 int[] corrIds = new int[0];
                                 if (corrIdsList != null) {
                                     corrIds = new int[corrIdsList.size()];
                                     for(int i=0; i<corrIdsList.size(); i++) corrIds[i] = ((Number)corrIdsList.get(i)).intValue();
                                 }
                                 novaDivisao = new SalaAlavanca(id, nome, desc, escolha, corrIds, getEventoAleatorio(), getEventoAleatorio());
                                 break;
                            default: novaDivisao = new PontoPartida(id, nome, desc);
                        }
                        gestorMapa.adicionarDivisao(novaDivisao);
                    }
                }
                
                Object corredoresObj = root.get("corredores");
                if (corredoresObj instanceof java.util.List) {
                    java.util.List<?> corredoresList = (java.util.List<?>) corredoresObj;
                    for (Object o : corredoresList) {
                        java.util.Map<?, ?> corrMap = (java.util.Map<?, ?>) o;
                        int id = ((Number) corrMap.get("id")).intValue();
                        int origemId = ((Number) corrMap.get("origem_id")).intValue();
                        int destinoId = ((Number) corrMap.get("destino_id")).intValue();
                        double peso = (corrMap.get("peso") != null) ? ((Number) corrMap.get("peso")).doubleValue() : 1.0;
                        
                        Evento eventoDoCorredor = (random.nextDouble() < 0.3) ? getEventoAleatorio() : new Evento("Calmo", "Nada", Evento.EFEITO_NADA);
                        Corredor corredor = new Corredor(id, "Corredor " + id, peso, eventoDoCorredor);
                        Boolean bloqueado = LeitorJSON.getBoolean((java.util.Map<String, Object>) corrMap, "bloqueado");
                        if(bloqueado != null) corredor.setEstaBloqueado(bloqueado);

                        gestorMapa.adicionarCorredor(origemId, destinoId, corredor);
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }


    public Iterator<Evento> getEventosDoJogo() {
        return this.eventosDisponiveis.iterator();
    }
}