package testes.cesarcypher;

import structures.queue.LinkedQueue;

// Ex. 5 - o mesmo programa do ex. 4 (codificação com chave de repetição, slides 11 a 13),
// agora usando uma LinkedQueue para guardar a chave
public class EncoderLinkedQueue {

    public static void main(String[] args) {
        // Chave de repetição do slide 12 e mensagem do slide 13
        int[] key = { 3, 1, 7, 4, 2, 5 };
        String message = "knowledge is power";

        // Uma fila para codificar e outra para descodificar,
        // cada uma com a chave completa
        LinkedQueue<Integer> encodeKey = new LinkedQueue<Integer>();
        LinkedQueue<Integer> decodeKey = new LinkedQueue<Integer>();
        for (int i = 0; i < key.length; i++) {
            encodeKey.enqueue(key[i]);
            decodeKey.enqueue(key[i]);
        }

        // Codificar: cada letra avança o valor da chave que está à frente da fila;
        // esse valor volta para o fim, por isso a chave recomeça quando se esgota.
        // Os espaços ficam iguais e não gastam valores da chave (como no slide 13)
        String encoded = "";
        String usedKey = "";
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c >= 'a' && c <= 'z') {
                int shift = encodeKey.dequeue();
                encoded += (char) ('a' + (c - 'a' + shift) % 26);
                usedKey += shift;
                encodeKey.enqueue(shift);
            } else {
                encoded += c;
                usedKey += " ";
            }
        }

        // Descodificar: o mesmo processo, mas a recuar o valor da chave
        String decoded = "";
        for (int i = 0; i < encoded.length(); i++) {
            char c = encoded.charAt(i);
            if (c >= 'a' && c <= 'z') {
                int shift = decodeKey.dequeue();
                decoded += (char) ('a' + (c - 'a' - shift + 26) % 26);
                decodeKey.enqueue(shift);
            } else {
                decoded += c;
            }
        }

        // Mesma disposição do slide 13
        System.out.println("Mensagem Codificada:    " + encoded);
        System.out.println("Chave:                  " + usedKey);
        System.out.println("Mensagem Descodificada: " + decoded);
    }
}
