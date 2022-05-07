package com.abdallah;

public class Prim {
    int vertices;

    Prim(int vertices){
        this.vertices = vertices;
    }

    public int longestPath(int[] distance, Boolean[] set){
        int max = 0;
        int maxIndex = -1;

        for (int i = 0; i < vertices; i++){
            if(!set[i] && distance[i] > max){
                max = distance[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    public void prim(int[][] graph){
        int[] source = new int[vertices];
        int[] weight = new int[vertices];
        Boolean[] set = new Boolean[vertices];

        for(int i = 0; i < vertices; i++){
            weight[i] = 0;
            set[i] = false;

        }
        weight[0] = Integer.MAX_VALUE;
        source[0] = -1;

        for(int i = 0; i < vertices - 1; i++){
            int max = longestPath(weight, set);
            set[max] = true;

            for(int v = 0; v < vertices; v++){
                if(graph[v][max] != 0 && !set[v] && graph[v][max] > weight[v]){
                    source[v] = max;
                    weight[v] = graph[v][max];
                }
            }
        }

        System.out.println("\nPRIMS LONGEST SPANNING TREE \n");
        System.out.println("Edge    Weight");
        for (int i = 1; i < vertices; i++)
            System.out.println(source[i] + " -> " + i + "    " + graph[i][source[i]]);
    }

}
