import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Sarah Chen
 * @userid schen475
 * @GTID 903190753
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
                                            Graph<T> graph) {
        if (start == null || graph == null
                || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Invalid "
                    + "or non-existant input.");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        visited.add(start);

        List<Vertex<T>> path = new LinkedList<>();
        path.add(start);

        Queue<Vertex<T>> queue = new LinkedList<>();
        queue.add(start);
        while (queue.peek() != null) {
            for (Edge<T> s : graph.getAdjList().get(queue.poll())) {
                if (!visited.contains(s.getV())) {
                    visited.add(s.getV());
                    path.add(s.getV());
                    queue.add(s.getV());
                }
            }
        }
        return path;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                            Graph<T> graph) {
        if (start == null || graph == null
                || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Invalid "
                    + "or non-existent input.");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        visited.add(start);

        List<Vertex<T>> path = new LinkedList<>();
        path.add(start);

        Map<Vertex<T>, List<Edge<T>>> adjList = graph.getAdjList();
        dfs(start, adjList, visited, path);

        return path;
    }

    /**
     * Performs depth-first-search.
     *
     * @param vertex the current vertex being explored
     * @param adjList the adjacency list of the graph
     * @param visited the visited set
     * @param path the path
     * @param <T> the generic typing of the data
     */
    private static <T> void dfs(Vertex<T> vertex,
                                Map<Vertex<T>, List<Edge<T>>> adjList,
                                Set<Vertex<T>> visited,
                                List<Vertex<T>> path) {
        for (Edge<T> s : adjList.get(vertex)) {
            if (!visited.contains(s.getV())) {
                visited.add(s.getV());
                path.add(s.getV());
                dfs(s.getV(), adjList, visited, path);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as it's efficient.
     *
     * You should implement the version of Dijkstra's where you terminate the
     * algorithm once either all vertices have been visited or the PQ becomes
     * empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start index representing which vertex to start at (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every other node
     *         in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                      Graph<T> graph) {
        if (start == null || graph == null
                || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Invalid "
                    + "or non-existent input.");
        }
        Set<Vertex<T>> visited = new HashSet<>();

        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        pq.add(new Edge<>(start, start, 0));

        Map<Vertex<T>, Integer> minDistance = new HashMap<Vertex<T>, Integer>();
        for (Vertex<T> s : graph.getVertices()) {
            minDistance.put(s, Integer.MAX_VALUE);
        }
        minDistance.put(start, 0);

        while (!pq.isEmpty()
                && !(visited.size() == graph.getVertices().size())) {
            Vertex<T> smol = pq.poll().getV();
            visited.add(smol);
            for (Edge<T> s : graph.getAdjList().get(smol)) {
                if (minDistance.get(s.getV()) > minDistance.get(s.getU())
                        + s.getWeight()) {
                    minDistance.put(s.getV(), minDistance.get(s.getU())
                            + s.getWeight());
                    if (!visited.contains(s)) {
                        pq.add(s);
                    }
                }
            }
        }
        return minDistance;
    }

    /**
     * Runs Prim's algorithm on the given graph and return the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * opposite edge to the set as well. This is for testing purposes.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops into the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface, as long as it's efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null
                || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Invalid "
                    + "or non-existent input.");
        }
        Set<Edge<T>> mst = new HashSet<>();

        Set<Vertex<T>> visited = new HashSet<>();

        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        Vertex<T> curr = start;
        for (Edge<T> t : graph.getAdjList().get(curr)) {
            if (!visited.contains(curr)) {
                pq.clear();
                pq.addAll(graph.getAdjList().get(curr));
                visited.add(curr);
                while (!pq.isEmpty()
                        && mst.size() < 2 * (graph.getVertices().size() - 1)) {
                    Edge<T> temp = pq.poll();
                    if (!visited.contains(temp.getV())
                            || !visited.contains(temp.getU())) {
                        mst.add(temp);
                        for (Edge<T> s : graph.getAdjList().get(temp.getV())) {
                            if (s.getV().equals(temp.getU())) {
                                mst.add(s);
                            } else {
                                pq.add(s);
                            }
                        }
                    }
                    curr = temp.getV();
                    visited.add(curr);
                }
            }
        }
        if (graph.getVertices().size() == visited.size()) {
            return mst;
        } else {
            return null;
        }
    }
}