package core.model.itens;

public class Enigma {

    private final int id;
    private final String pergunta;
    private final String[] opcoesResposta; 
    private final String respostaCorreta;
    private final int pontos; // NOVO: Pontos que este enigma vale

    public Enigma(int id, String pergunta, String[] opcoesResposta, String respostaCorreta, int pontos) {
        this.id = id;
        this.pergunta = pergunta;
        this.opcoesResposta = opcoesResposta;
        this.respostaCorreta = respostaCorreta;
        this.pontos = pontos;
    }

    public boolean verificarResposta(String respostaDoJogador) {
        if (respostaDoJogador == null) return false;
        // Compara ignorando maiúsculas/minúsculas e espaços extra
        return respostaDoJogador.trim().equalsIgnoreCase(this.respostaCorreta.trim());
    }
    
    public int getId() { return id; }
    public String getPergunta() { return pergunta; }
    public String[] getOpcoesResposta() { return opcoesResposta; }
    public String getRespostaCorreta() { return respostaCorreta; }
    public int getPontos() { return pontos; } // NOVO
}