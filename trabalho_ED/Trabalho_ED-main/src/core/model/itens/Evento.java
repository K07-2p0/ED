package core.model.itens;

/**
 * Representa um Evento carregado dinamicamente de um ficheiro JSON.
 * Mantém os dados descritivos e um identificador para a lógica (tipoEfeito).
 */
public class Evento {

    // Constantes para mapear o 'tipo_efeito' do JSON para a lógica no código
    public static final String EFEITO_JOGADA_EXTRA = "JOGADA_EXTRA";
    public static final String EFEITO_TROCA_POSICAO = "TROCA_POSICAO";
    public static final String EFEITO_RECUAR = "RECUAR";
    public static final String EFEITO_AVANCAR = "AVANCAR";
    public static final String EFEITO_BLOQUEIO_TURNO = "BLOQUEIO_TURNO";
    public static final String EFEITO_TROCA_GLOBAL = "TROCA_GLOBAL";
    public static final String EFEITO_NADA = "NADA";

    private final String nome;
    private final String descricao;
    private final String tipoEfeito; // O ID que liga ao código Java (ex: "RECUAR")

    public Evento(String nome, String descricao, String tipoEfeito) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipoEfeito = tipoEfeito;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipoEfeito() {
        return tipoEfeito;
    }

    @Override
    public String toString() {
        return nome + ": " + descricao;
    }
}