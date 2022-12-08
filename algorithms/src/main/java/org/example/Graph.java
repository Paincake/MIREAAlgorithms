package org.example;

import com.sun.jdi.connect.AttachingConnector;

import java.util.*;



public class Graph {
    private final Character NULL_VERTEX = '*';
    Map<Integer, Character> nodes = new HashMap<>();
    List<Integer> vertexIn;
    int edgesCount;

    int[][] adjacencyMatrix;
    private int[][] weightMatrix;

    public int firstAdjacentIndex(int v){
        int index = 0;
        for(int i = 0; i < adjacencyMatrix.length; i++){
            if(adjacencyMatrix[v][i] == 1){
                for (int[] matrix : adjacencyMatrix) {
                    if (matrix[i] == 1) index++;
                }
                break;
            }
        }
        return index;
    }
    public String nextAdjacentIndex(Character v, int i){
        List<Integer> indexes = new ArrayList<>();
        int index = 0;
        while(nodes.get(index) != v) index++;
        for(int j = 0; j < adjacencyMatrix.length; j++){
            if(adjacencyMatrix[index][j] == 1) indexes.add(vertexIn.get(j));
        }
        String result = null;

        indexes.add(i);
        List<Integer> a = indexes.stream().sorted().toList();
        for(int j = 1; j < indexes.size(); j++){
            if(a.get(j - 1) == i && a.get(j) != i){
                result = "Result index is " + String.valueOf(a.get(j));
            }
        }
        if(result == null) result = "NULL_VERTEX";
        return result;



    }
    public int adjacentToWithIndex(int v, int i){
        int[] vertexes = adjacencyMatrix[v];
        int[] vIndexes = new int[vertexes.length];
        for(int j = 0; j < vertexes.length; j++){
            if(adjacencyMatrix[v][j] == 1){
                int index = 0;
                for(int c = 0; c < vertexes.length; c++){
                    if(adjacencyMatrix[c][j] == 1) index++;
                }
                vIndexes[j] = index;
            }
        }
        for(int j = 0; j < vIndexes.length; j++){
            if(vIndexes[j] == i) return j;
        }
        return -1;
    }

    public void addEdge(char markFirst, char markSecond, int weight){
        int i = 0;
        while(nodes.get(i) != markFirst) i++;
        int j = 0;
        while(nodes.get(j) != markSecond) j++;
        adjacencyMatrix[i][j] = 1;
        editEdge(i, j, weight);
    }
    public void addEdge(int i, int j, int weight){
        adjacencyMatrix[i][j] = 1;
        editEdge(i, j, weight);
    }
    public void editEdge(Character markFirst, Character markSecond, int weight){
        int i = 0;
        while(nodes.get(i) != markFirst) i++;
        int j = 0;
        while(nodes.get(j) != markSecond) j++;
        weightMatrix[i][j] = weight;
    }
    public void editEdge(int i, int j, int weight){
        weightMatrix[i][j] = weight;
    }
    public void removeEdge(char markFirst, char markSecond){
        int i = 0;
        while(nodes.get(i) != markFirst) i++;
        int j = 0;
        while(nodes.get(j) != markSecond) j++;
        removeEdge(i, j);
    }
    public void removeEdge(int i, int j){
        adjacencyMatrix[i][j] = 0;
        weightMatrix[i][j] = 0;
        edgesCount--;
        vertexIn.set(j, vertexIn.get(j) - 1);
    }
    public void addNode(Character mark){
        nodes.put(nodes.size(), mark);
        vertexIn.add(0);
        int n = adjacencyMatrix.length;
        int[][] adjMatNew = new int[n + 1][n + 1];
        int[][] weightMatNew = new int[n + 1][n + 1];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                adjMatNew[i][j] = adjacencyMatrix[i][j];
                weightMatNew[i][j] = weightMatrix[i][j];
            }
        }
        for(int i = 0; i < adjMatNew.length; i++){
            adjMatNew[n][i] = 0;
            weightMatNew[n][i] = 0;
        }
        for(int i = 0; i < adjMatNew.length; i++){
            adjMatNew[i][n] = 0;
            weightMatNew[i][n] = 0;
        }
        adjacencyMatrix = adjMatNew;
        weightMatrix = weightMatNew;
    }

    public void removeNode(Character mark){
        int index = 0;
        while(nodes.get(index) != mark) index++;
        nodes.remove(index);
        int inEdges = 0;
        int outEdges = vertexIn.get(index);
        for(int i = 0; i < adjacencyMatrix.length; i++){
            if(adjacencyMatrix[i][index] == 1) inEdges++;
        }
        edgesCount = edgesCount - inEdges - outEdges;
        vertexIn.remove(index);

        int[][] newAdjMatrix = new int[adjacencyMatrix.length - 1][adjacencyMatrix.length - 1];
        int[][] newWeightMatrix = new int[adjacencyMatrix.length - 1][adjacencyMatrix.length - 1];
        int rCounter = 0;
        int colCounter = 0;
        int row = 0;
        int column = 0;
        while(rCounter < adjacencyMatrix.length){
            column = 0;
            colCounter = 0;
            while(colCounter < adjacencyMatrix.length){
                newAdjMatrix[row][column] = adjacencyMatrix[rCounter][colCounter];
                newWeightMatrix[row][column] = weightMatrix[rCounter][colCounter];
                if(colCounter != index) column++;
                colCounter++;
            }
            if(rCounter != index) row++;
            rCounter++;
        }
        adjacencyMatrix = newAdjMatrix;
        weightMatrix = newWeightMatrix;
    }

    public void editNodeMark(Character mark, Character newMark){
        int key = 0;
        while(nodes.get(key) != mark) key++;
        nodes.replace(key, newMark);
    }
    public void editNodeMark(int key, Character newMark){
        nodes.replace(key, newMark);
    }

    public Graph(int n){
        adjacencyMatrix = new int[n][n];
        weightMatrix = new int[n][n];
        vertexIn = new ArrayList<>(Collections.nCopies(n, 0));
    }
    public Graph(List<Edge> edges, int n){
        this(n);
        edgesCount = edges.size();
        for(Edge i : edges){
            adjacencyMatrix[i.source][i.dest] = 1;
            vertexIn.set(i.dest, vertexIn.get(i.dest) + 1);
        }
        Character mark = 'A';
        for(int i = 0; i < n; i++){
            nodes.put(i, mark);
            mark++;
        }
    }
}


