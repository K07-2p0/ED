package core.model.divisao;

import core.model.ator.Jogador;
import core.model.ator.JogadorHumano;
import core.model.itens.Enigma;
import java.util.Random; 

public class SalaEnigma extends Divisao {

    private Enigma enigmaAssociado;
    private boolean enigmaResolvido;
    private final Random random = new Random(); 

    public SalaEnigma(int id, String nome, String descricao, Enigma enigma) {
        super(id, nome, descricao);
        this.enigmaAssociado = enigma;
        this.enigmaResolvido = false; 
    }

    @Override
    public boolean processarEntrada(Jogador jogador) {
        setJogadorAtual(jogador);
        System.out.println(jogador.getNome() + " entrou em " + getNome() + ".");

        if (enigmaResolvido) {
            System.out.println("Enigma já resolvido! A passagem está aberta.");
            return true; 
        }

        // Apresenta o Enigma
        System.out.println("\n--- ENIGMA: " + enigmaAssociado.getPergunta() + " ---");
        char option = 'A';
        for (String opcao : enigmaAssociado.getOpcoesResposta()) {
            System.out.println("[" + option++ + "] " + opcao);
        }

        if (jogador instanceof JogadorHumano) {
            // CORREÇÃO: Não pedir input aqui. O PlayerInteraction pedirá no início do turno.
            return false; // Retorna false para impedir movimento imediato
        } else {
            return tentarResolverEnigmaBot(jogador);
        }
    }
    
    public boolean tentarResolverEnigma(Jogador jogador, String resposta) {
        boolean acertou = enigmaAssociado.verificarResposta(resposta);

        if (acertou) {
            System.out.println(jogador.getNome() + " acertou no enigma! Pode continuar.");
            this.enigmaResolvido = true; 
            jogador.getHistorico().adicionarEnigmaResolvido(enigmaAssociado);
            return true;
        } else {
            System.out.println(jogador.getNome() + " falhou. Terá de tentar novamente numa jogada futura.");
            return false;
        }
    }
    
    private boolean tentarResolverEnigmaBot(Jogador jogador) {
        boolean acertou = random.nextBoolean(); 
        if (acertou) {
            System.out.println(jogador.getNome() + " (Bot) acertou no enigma! Pode continuar.");
            this.enigmaResolvido = true;
            jogador.getHistorico().adicionarEnigmaResolvido(enigmaAssociado);
            return true;
        } else {
            System.out.println(jogador.getNome() + " (Bot) falhou. Terá de tentar novamente numa jogada futura.");
            return false;
        }
    }

    @Override
    public void atualizarEstado() {}

    public Enigma getEnigmaAssociado() {
        return enigmaAssociado;
    }

    public boolean isEnigmaResolvido() {
        return enigmaResolvido;
    }
}