package org.example;

import java.util.*;

public class EulerPathFinder {
    String path = "";
    private void depthFirstSearch(Graph graph, int v, boolean[] visited){
        visited[v] = true;
        for(int i = 0; i < graph.adjacencyMatrix.length; i++){
            if(graph.adjacencyMatrix[v][i] == 1){
                if(!visited[i]) depthFirstSearch(graph, i, visited);
            }
        }
    }

    private Graph transpose(Graph graph, int n){
        Graph transposedGraph = new Graph(n);
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(graph.adjacencyMatrix[i][j] == 1) {
                    transposedGraph.adjacencyMatrix[j][i] = 1;
                }
            }
        }
        return transposedGraph;
    }

    private boolean isVisited(Graph graph, boolean[] visited){
        for(int i = 0; i < visited.length; i++){
            if(Arrays.stream(graph.adjacencyMatrix[i]).sum() > 0 && !visited[i]){
                return false;
            }
        }
        return true;
    }

    private boolean isSC(Graph graph, int n){
        boolean[] visited = new boolean[n];
        int i;
        for(i = 0; i < n; i++){
            if(Arrays.stream(graph.adjacencyMatrix[i]).sum() > 0){
                depthFirstSearch(graph, i, visited);
                break;
            }
        }
        if(!isVisited(graph, visited)) return false;

        Arrays.fill(visited, false);
        Graph g = transpose(graph, n);
        depthFirstSearch(g, i, visited);
        return isVisited(g, visited);
    }

    private boolean hasEulerCycle(Graph graph, int n){
        for(int i = 0; i < n; i++){
            if(Arrays.stream(graph.adjacencyMatrix[i]).sum() != graph.vertexIn.get(i)){
                return false;
            }
        }
        return isSC(graph, n);
    }

    private void createEulerPath(Graph graph, int n, List<Set<Integer>> edges, int passed, int start){
        //проходить по матрице
        //проверять, пройдено ли такое ребро;
        //если нет:
        //проверять, есть ли непройденные пути у нашего кандидата
        //если нет, проверить, сколько путей уже пройдено всего
        //если n-1, то заходим и завершаем работу
        //если меньше, то выбираем другой путь
        for(int j = 0; j < n; j++){
            if(graph.adjacencyMatrix[start][j] == 1){
                if(!edges.get(start).contains(j)){ //если ребро не пройдено
                    if(edges.get(j).size() == graph.vertexIn.get(0)){ //если из пути j все пути уже пройдены
                        if(passed + 1 == graph.edgesCount){ //конец пути
                            //path = path + String.valueOf(start) + " " + String.valueOf(j) ;
                            path = path + graph.nodes.get(start) + " " + graph.nodes.get(j) ;
                            edges.get(start).add(j);
                            passed++;
                            return;
                        }
                        //иначе выберется другой маршрут
                    }
                    else{
                        //path = path + String.valueOf(start) + " ";
                        path = path + graph.nodes.get(start) + " ";
                        edges.get(start).add(j);
                        passed++;
                        createEulerPath(graph, n, edges, passed, j);
                    }
                }
            }
        }

    }
    public String getEulerCycle(Graph graph, int n){
        if(hasEulerCycle(graph, n)){
            List<Set<Integer>> edges = new ArrayList<>();
            for(int i = 0; i < graph.edgesCount; i++){
                edges.add(new HashSet<>());
            }
            createEulerPath(graph, n, edges, 0, 0);
            String res = path;
            path = "";
            return res;
        }
        return "Euler cycle does not exist";

    }

}
