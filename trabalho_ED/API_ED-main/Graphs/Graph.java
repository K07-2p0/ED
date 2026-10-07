package Graphs;

import Exceptions.EmptyCollectionException;
import Queues_Stacks.CircularArrayQueue;
import Queues_Stacks.LinkedStack;
import Lists.DoublyLinkedList;
import Lists.ListADT;
import java.util.Iterator;

public class Graph<T> implements GraphADT<T> {
    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices;   
    protected boolean[][] adjMatrix; 
    protected T[] vertices;      

    @SuppressWarnings("unchecked")
    public Graph() {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[])(new Object[DEFAULT_CAPACITY]);
    }

    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length)
            expandCapacity();

        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;
    }

    @Override
    public void removeVertex(T vertex) {
        int index = getIndex(vertex);
        if (indexIsValid(index)) {
            numVertices--;
            for (int i = index; i < numVertices; i++)
                vertices[i] = vertices[i+1];
            
            for (int i = index; i < numVertices; i++)
                for (int j = 0; j <= numVertices; j++)
                    adjMatrix[i][j] = adjMatrix[i+1][j];
            
            for (int i = index; i < numVertices; i++)
                for (int j = 0; j < numVertices; j++)
                    adjMatrix[j][i] = adjMatrix[j][i+1];
        }
    }

    @Override
    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2));
    }

    protected void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true; 
        }
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = false;
            adjMatrix[index2][index1] = false;
        }
    }

    @Override
    public Iterator<T> iteratorBFS(T startVertex) throws EmptyCollectionException {
        int startIndex = getIndex(startVertex);
        if (!indexIsValid(startIndex)) return new DoublyLinkedList<T>().iterator();

        CircularArrayQueue<Integer> queue = new CircularArrayQueue<>();
        DoublyLinkedList<T> resultList = new DoublyLinkedList<>();
        boolean[] visited = new boolean[numVertices];

        queue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            resultList.addLast(vertices[current]);

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[current][i] && !visited[i]) {
                    queue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    @Override
    public Iterator<T> iteratorDFS(T startVertex) throws EmptyCollectionException {
        int startIndex = getIndex(startVertex);
        if (!indexIsValid(startIndex)) return new DoublyLinkedList<T>().iterator();

        LinkedStack<Integer> stack = new LinkedStack<>();
        DoublyLinkedList<T> resultList = new DoublyLinkedList<>();
        boolean[] visited = new boolean[numVertices];

        stack.push(startIndex);
        visited[startIndex] = true;

        while (!stack.isEmpty()) {
            int current = stack.pop();
            resultList.addLast(vertices[current]);

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[current][i] && !visited[i]) {
                    stack.push(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws EmptyCollectionException {
        return iteratorBFS(startVertex);
    }

    @Override
    public boolean isEmpty() {
        return numVertices == 0;
    }

    @Override
    public boolean isConnected() throws EmptyCollectionException {
        if (isEmpty()) return false;
        Iterator<T> it = iteratorBFS(vertices[0]);
        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        return count == numVertices;
    }

    @Override
    public int size() {
        return numVertices;
    }

    protected int getIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertex)) return i;
        }
        return -1;
    }

    protected boolean indexIsValid(int index) {
        return (index >= 0 && index < numVertices);
    }

    @SuppressWarnings("unchecked")
    protected void expandCapacity() {
        T[] largerVertices = (T[])(new Object[vertices.length * 2]);
        boolean[][] largerAdjMatrix = new boolean[vertices.length * 2][vertices.length * 2];

        System.arraycopy(vertices, 0, largerVertices, 0, numVertices);
        for (int i = 0; i < numVertices; i++) {
            System.arraycopy(adjMatrix[i], 0, largerAdjMatrix[i], 0, numVertices);
        }
        vertices = largerVertices;
        adjMatrix = largerAdjMatrix;
    }
    

    /* 
    @Override
    public String toString() {
        if (numVertices == 0) return "Grafo vazio";
        StringBuilder sb = new StringBuilder();
        sb.append("Vértices: \n");
        for (int i = 0; i < numVertices; i++) {
            sb.append(vertices[i]).append("\n");
            sb.append("Ligações: ");
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j]) {
                    sb.append(vertices[j]).append(" ");
                }
            }
            sb.append("\n\n");
        }
        return sb.toString();
    }
    */
   
    @Override
    public ListADT<T> getNeighbors(T vertex) {
        // Usando a DoublyLinkedList da sua API
        DoublyLinkedList<T> neighbors = new DoublyLinkedList<>();
        int index = getIndex(vertex); // getIndex é um método protegido em Graph.java
        
        if (indexIsValid(index)) {
            for (int i = 0; i < numVertices; i++) {
                // Verifica a matriz de adjacências interna
                if (adjMatrix[index][i]) { 
                    // Adiciona o vértice vizinho (sala)
                    neighbors.addLast(vertices[i]);
                }
            }
        }
        // Retorna ListADT<T>, que é uma DoublyLinkedList da sua API
        return neighbors;
    }

}