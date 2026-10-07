package testes.livraria;

// Parte II Ex. 5 - superclasse de todos os tipos de média
public abstract class Media {
    private String titulo;
    private int ano;

    public Media(String titulo, int ano) {
        this.titulo = titulo;
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAno() {
        return ano;
    }

    public String toString() {
        return titulo + " (" + ano + ")";
    }
}
