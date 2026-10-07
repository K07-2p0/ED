package testes.livraria;

// Parte II Ex. 5 - tipo de média: vídeo
public class Video extends Media {
    private int duracaoMinutos;

    public Video(String titulo, int ano, int duracaoMinutos) {
        super(titulo, ano);
        this.duracaoMinutos = duracaoMinutos;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public String toString() {
        return "Video: " + super.toString() + " - " + duracaoMinutos + " min";
    }
}
