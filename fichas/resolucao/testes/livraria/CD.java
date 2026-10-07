package testes.livraria;

// Parte II Ex. 5 - tipo de média: CD de música
public class CD extends Media {
    private String artista;
    private int numFaixas;

    public CD(String titulo, int ano, String artista, int numFaixas) {
        super(titulo, ano);
        this.artista = artista;
        this.numFaixas = numFaixas;
    }

    public String getArtista() {
        return artista;
    }

    public int getNumFaixas() {
        return numFaixas;
    }

    public String toString() {
        return "CD: " + super.toString() + " - " + artista + ", " + numFaixas + " faixas";
    }
}
