package org.example;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Graph graph1 = new Graph(List.of(
                new Edge(0, 1),
                new Edge(0, 2),
                new Edge(1, 2),
                new Edge(1, 3),
                new Edge(2, 3),
                new Edge(2, 4),
                new Edge(3, 0),
                new Edge(3, 4),
                new Edge(4, 0),
                new Edge(4, 1)
        ), 5);

        Graph graph2 = new Graph(List.of(
                new Edge(0, 1),
                new Edge(0, 4),
                new Edge(0, 3),
                new Edge(1, 0),
                new Edge(2, 3),
                new Edge(2, 0),
                new Edge(3, 1),
                new Edge(4, 2)
        ), 5);

        EulerPathFinder finder = new EulerPathFinder();
        System.out.print("Graph One: ");
        System.out.println(finder.getEulerCycle(graph1, 5));
        System.out.print("Graph Two: ");
        System.out.println(finder.getEulerCycle(graph2, 5));

    }
}