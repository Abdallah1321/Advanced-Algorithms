package com.abdallah;

import java.util.*;

public class WeightedGraph {
    static class Edge {
        char source;
        char destination;
        int weight;

        public Edge(char source, char destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Graph {
        int vertices;
        private final int[][] adjacencymatrix;
        LinkedList<LinkedList<Edge>> adjacencylist = new LinkedList<LinkedList<Edge>>();


        Graph(int vertices) {
            this.vertices = vertices;
            //adjacencylist = new LinkedList[vertices];
            adjacencymatrix = new int[vertices][vertices];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i <vertices ; i++) {
                adjacencylist.add(i, new LinkedList<Edge>());
            }
        }

        public void addEdge(char source, char destination, int weight) {
            Edge edge = new Edge(source, destination, weight);
            adjacencylist.get(Character.getNumericValue(source) - 10).addFirst(edge); //for directed graph
            adjacencymatrix[Character.getNumericValue(source) - 10][Character.getNumericValue(destination) - 10] = weight;
        }

        public void printMatrix() {

            System.out.println("\n ADJACENCY MATRIX: \n ");
            System.out.print("          ");
            for (int x = 0; x < vertices; x++){
                System.out.print((char)(x+65) + "       ");
            }
            System.out.println('\n');

            for (int i = 0; i < vertices; i++) {
                System.out.print((char)(i+65) + ": ");
                for (int j = 0; j < vertices; j++) {
                    System.out.format("%8s", (adjacencymatrix[i][j]));
                }
                System.out.println();
            }
        }

        public void printList(){
            System.out.println("\nADJACENCY LIST:\n");
            for (int i = 0; i <vertices ; i++) {
                LinkedList<Edge> list = adjacencylist.get(i);
                for (int j = 0; j <list.size() ; j++) {
                    System.out.println("vertex " + (char)list.get(j).source + " points to " +
                            (char)list.get(j).destination + " with weight " + list.get(j).weight);
                }
            }
        }


        public void breadthFirst(char start){
            System.out.println("\nBREADTH FIRST TRAVERSAL \n");
            boolean[] visited = new boolean[vertices];
            int numStart = Character.getNumericValue(start) - 10;
            Queue<Integer> queue = new LinkedList<>();

            visited[numStart] = true;
            queue.add(numStart);

            while (queue.size() != 0){
                numStart = queue.poll();
                System.out.println((char)(numStart+65) + " ");

                for (Edge n : adjacencylist.get(numStart)) {
                    if (!visited[n.destination - 65]) {
                        visited[n.destination - 65] = true;
                        queue.add(n.destination - 65);
                    }
                }
            }
        }

        public void depthFirst(int start, boolean[] visited){
            visited[start] = true;
            System.out.println((char)(start+65) + " ");
            for (Edge n : adjacencylist.get(start)) {
                if (!visited[n.destination - 65])
                    depthFirst(n.destination - 65, visited);
            }
        }

        public void depthFirst(char start){
            System.out.println("\nDEPTH FIRST TRAVERSAL \n");

            boolean[] visited = new boolean[vertices];
            int numStart = Character.getNumericValue(start) - 10;
            depthFirst(numStart, visited);

        }
    }
}