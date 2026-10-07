package Graphs;

/**
 * NetworkADT define a interface para um grafo ponderado (com pesos nas arestas).
 */
public interface NetworkADT<T> extends GraphADT<T> {
    /** Adiciona uma aresta entre dois vértices com um peso associado */
    void addEdge(T vertex1, T vertex2, double weight);

    /** Retorna o peso do caminho mais curto entre dois vértices */
    double shortestPathWeight(T vertex1, T vertex2);
}