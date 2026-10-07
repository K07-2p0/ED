package Graphs;

import Exceptions.EmptyCollectionException;
import Trees_Heaps.PriorityQueue;
import Lists.DoublyLinkedList;
import java.util.Iterator;

public class Network<T> extends Graph<T> implements NetworkADT<T> {
    private final double[][] adjMatrixWeight; 

    public Network() {
        super();
        this.adjMatrixWeight = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);
        
        if (indexIsValid(index1) && indexIsValid(index2)) {
            super.addEdge(index1, index2); // Atualiza a matriz booleana
            adjMatrixWeight[index1][index2] = weight;
            adjMatrixWeight[index2][index1] = weight;
        }
    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {
        int startIndex = getIndex(vertex1);
        int targetIndex = getIndex(vertex2);
        
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) 
            return Double.POSITIVE_INFINITY;
            
        // Dijkstra para calcular apenas o peso
        double[] dist = new double[numVertices];
        boolean[] visited = new boolean[numVertices];
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 0; i < numVertices; i++) {
            dist[i] = Double.POSITIVE_INFINITY;
            visited[i] = false;
        }

        dist[startIndex] = 0;
        pq.enqueue(startIndex, 0);

        while (!pq.isEmpty()) {
            int u;
            try {
                u = pq.dequeue();
            } catch (EmptyCollectionException e) {
                break;
            }

            if (visited[u]) continue;
            visited[u] = true;

            if (u == targetIndex) return dist[u]; // Encontrou o destino, retorna o custo

            for (int v = 0; v < numVertices; v++) {
                if (adjMatrix[u][v] && !visited[v]) {
                    double newDist = dist[u] + adjMatrixWeight[u][v];
                    
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        pq.enqueue(v, newDist);
                    }
                }
            }
        }
        
        return Double.POSITIVE_INFINITY; // Caminho não encontrado
    }

    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws EmptyCollectionException {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex))
            return new DoublyLinkedList<T>().iterator();

        double[] dist = new double[numVertices];
        int[] pred = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 0; i < numVertices; i++) {
            dist[i] = Double.POSITIVE_INFINITY;
            pred[i] = -1;
            visited[i] = false;
        }

        dist[startIndex] = 0;
        pq.enqueue(startIndex, 0);

        while (!pq.isEmpty()) {
            int u = pq.dequeue(); // O try-catch pode ser omitido se tiveres a certeza que não está vazia pelo while

            if (visited[u]) continue;
            visited[u] = true;

            if (u == targetIndex) break;

            for (int v = 0; v < numVertices; v++) {
                if (adjMatrix[u][v] && !visited[v]) {
                    double newDist = dist[u] + adjMatrixWeight[u][v];
                    
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        pred[v] = u;
                        // CORREÇÃO: Já não é necessário fazer cast para (int)
                        pq.enqueue(v, newDist); 
                    }
                }
            }
        }

        // Reconstruir o caminho
        DoublyLinkedList<T> path = new DoublyLinkedList<>();
        
        if (dist[targetIndex] == Double.POSITIVE_INFINITY) return path.iterator();

        int current = targetIndex;
        while (current != -1) {
            path.addFirst(vertices[current]);
            current = pred[current];
        }

        return path.iterator();
    }
}