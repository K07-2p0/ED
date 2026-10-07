package testes.cesarcypher;

import structures.queue.CircularArrayQueue;

// Ex. 4 - codificação de mensagens com uma chave de repetição (slides 11 a 13),
// usando uma CircularArrayQueue para guardar a chave
public class CodificadorCircularArrayQueue {

    public static void main(String[] args) {
        // Chave de repetição do slide 12
        int[] chave = { 3, 1, 7, 4, 2, 5 };
        String mensagem = "All programmers are playwrights and all computers are lousy actors.";

        // Uma fila para codificar e outra para descodificar,
        // cada uma com a chave completa
        CircularArrayQueue<Integer> chaveCodificar = new CircularArrayQueue<Integer>();
        CircularArrayQueue<Integer> chaveDescodificar = new CircularArrayQueue<Integer>();
        for (int i = 0; i < chave.length; i++) {
            chaveCodificar.enqueue(chave[i]);
            chaveDescodificar.enqueue(chave[i]);
        }

        // Mostrar a chave antes de começar (depois de usada pode ficar rodada)
        System.out.println("Chave:                  " + chaveCodificar);

        // Codificar: cada caracter avança o valor da chave que está à frente da fila;
        // esse valor volta para o fim, por isso a chave recomeça quando se esgota
        String codificada = "";
        for (int i = 0; i < mensagem.length(); i++) {
            int deslocamento = chaveCodificar.dequeue();
            codificada += (char) (mensagem.charAt(i) + deslocamento);
            chaveCodificar.enqueue(deslocamento);
        }

        // Descodificar: o mesmo processo, mas a recuar o valor da chave
        String descodificada = "";
        for (int i = 0; i < codificada.length(); i++) {
            int deslocamento = chaveDescodificar.dequeue();
            descodificada += (char) (codificada.charAt(i) - deslocamento);
            chaveDescodificar.enqueue(deslocamento);
        }

        System.out.println("Mensagem original:      " + mensagem);
        System.out.println("Mensagem codificada:    " + codificada);
        System.out.println("Mensagem descodificada: " + descodificada);
    }
}
