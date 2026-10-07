package testes.livraria;

// Parte II Ex. 5 - tipo de média: livro
public class Livro extends Media {
    private String autor;

    public Livro(String titulo, int ano, String autor) {
        super(titulo, ano);
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }

    public String toString() {
        return "Livro: " + super.toString() + " - " + autor;
    }
}
