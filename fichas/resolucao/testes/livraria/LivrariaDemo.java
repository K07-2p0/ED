package testes.livraria;


// Teste do Ex. 5 (Parte II)
public class LivrariaDemo {
    public static void main(String[] args) {

        // Livraria só de livros
        Livraria<Livro> livros = new Livraria<Livro>();
        livros.adicionar(new Livro("Os Maias", 1888, "Eca de Queiros"));
        livros.adicionar(new Livro("Ensaio sobre a Cegueira", 1995, "Jose Saramago"));

        // Livraria só de CDs
        Livraria<CD> cds = new Livraria<CD>();
        cds.adicionar(new CD("Abbey Road", 1969, "The Beatles", 17));

        // Livraria com tudo misturado
        Livraria<Media> todas = new Livraria<Media>();
        todas.adicionar(new Livro("Os Lusiadas", 1572, "Luis de Camoes"));
        todas.adicionar(new Video("Matrix", 1999, 136));
        todas.adicionar(new CD("Thriller", 1982, "Michael Jackson", 9));

        System.out.println("Livros:");
        Livraria.imprimir(livros);
        System.out.println("CDs:");
        Livraria.imprimir(cds);
        System.out.println("Todas:");
        Livraria.imprimir(todas);

        // Obter sem cast: livros.obter() já devolve um Livro
        Livro primeiro = livros.obter(0);
        System.out.println("Autor do primeiro livro: " + primeiro.getAutor());

        System.out.println("Procurar 'matrix': " + todas.obterPorTitulo("matrix"));

        todas.remover(todas.obter(0));
        System.out.println("Depois de remover o primeiro, 'todas' tem " + todas.tamanho() + " itens");

        // Erro de compilação: uma livraria de livros não aceita CDs
        // livros.adicionar(new CD("Abbey Road", 1969, "The Beatles", 17));
    }
}
