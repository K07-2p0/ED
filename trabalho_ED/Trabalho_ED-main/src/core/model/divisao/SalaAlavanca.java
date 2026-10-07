package core.model.divisao;

import core.model.ator.Jogador;
import core.model.ator.JogadorHumano;
import core.model.itens.Evento;
import java.util.Random; 

public class SalaAlavanca extends Divisao {

    private final char escolhaCorreta; 
    private final char escolhaIncorreta;
    private final int[] corredoresControladosIds;
    private boolean alavancaAtivada;
    
    // Agora recebe Objetos Evento (não Enum)
    private final Evento efeitoPositivo; 
    private final Evento efeitoNegativo;

    public SalaAlavanca(int id, String nome, String descricao, char escolhaCorreta, int[] corredoresControladosIds, Evento ePositivo, Evento eNegativo) {
        super(id, nome, descricao);
        this.escolhaCorreta = Character.toUpperCase(escolhaCorreta);
        this.escolhaIncorreta = (this.escolhaCorreta == 'A' ? 'B' : 'A');
        this.corredoresControladosIds = corredoresControladosIds;
        this.alavancaAtivada = false;
        
        // Os eventos vêm de fora (GameLoader), garantindo que vieram do JSON
        this.efeitoPositivo = ePositivo;
        this.efeitoNegativo = eNegativo;
    }

    @Override
    public boolean processarEntrada(Jogador jogador) {
        setJogadorAtual(jogador);
        System.out.println(jogador.getNome() + " entrou em " + getNome());

        if (alavancaAtivada) {
            System.out.println("Alavanca já ativada! Passagem livre.");
            return true;
        }

        System.out.println("\n--- SALA DA ALAVANCA ---");
        System.out.println("    [" + this.escolhaCorreta + "] Opção 1: " + this.efeitoPositivo.getDescricao());
        System.out.println("    [" + this.escolhaIncorreta + "] Opção 2: " + this.efeitoNegativo.getDescricao());
        System.out.print("Qual alavanca escolhes? ");
        
        if (jogador instanceof JogadorHumano) {
            return false; // Aguarda input
        } else {
            char escolhaBot = (new Random().nextBoolean()) ? this.escolhaCorreta : this.escolhaIncorreta;
            return tentarAtivarAlavanca(jogador, escolhaBot);
        }
    }
    
    public boolean tentarAtivarAlavanca(Jogador jogador, char escolha) {
        char escolhaDoJogador = Character.toUpperCase(escolha);

        if (escolhaDoJogador == this.escolhaCorreta) {
            System.out.println("Sucesso! " + this.efeitoPositivo.getNome());
            this.alavancaAtivada = true;
            jogador.adicionarEfeitoPositivo(this.efeitoPositivo);
            return true;
        } else if (escolhaDoJogador == this.escolhaIncorreta) {
            System.out.println("Falhou! " + this.efeitoNegativo.getNome());
            aplicarEfeitoNegativoImediato(jogador);
            return false;
        } else {
             System.out.println("Escolha inválida.");
             return false;
        }
    }
    
    private void aplicarEfeitoNegativoImediato(Jogador jogador) {
        jogador.getHistorico().adicionarEventoAplicado(this.efeitoNegativo);
        
        // Verificação usando String constant em vez de Enum
        if (this.efeitoNegativo.getTipoEfeito().equals(Evento.EFEITO_BLOQUEIO_TURNO)) {
            jogador.aplicarBloqueio(1);
        } 
        // Outros efeitos negativos são processados pelo PlayerInteraction/MotorJogo
    }

    @Override
    public void atualizarEstado() {}
    
    public int[] getCorredoresControladosIds() { return corredoresControladosIds; }
}