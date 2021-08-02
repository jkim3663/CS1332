import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various graph algorithms.
 *
 * @author Jun Yeop Kim
 * @version 1.0
 * @userid jkim3663
 * @GTID 903624126
 * <p>
 * Collaborators: None
 * <p>
 * Resources: YouTube
 */
public class GraphAlgorithms {

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * NOTE: You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The input from the parameter is null.");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("The graph does not contain the start vertex.");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> list = new ArrayList<>();
        visited.add(start);

        dfs(start, graph, visited, list);

        return list;
    }

    /**
     * Helper method for dfs method.
     *
     * @param <T>     the generic typing of the data
     * @param u       the vertex to begin the dfs on
     * @param graph   the graph to search through
     * @param visited the visited vertices
     * @param list    the list of visited vertices
     */
    public static <T> void dfs(Vertex<T> u, Graph<T> graph, Set<Vertex<T>> visited, List<Vertex<T>> list) {
        visited.add(u);
        list.add(u);

        for (VertexDistance<T> w : graph.getAdjList().get(u)) {
            if (!visited.contains(w.getVertex())) {
                dfs(w.getVertex(), graph, visited, list);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The input from the parameter is null.");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("The graph does not contain the start vertex.");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        Queue<VertexDistance<T>> queue = new PriorityQueue<>();

        for (Vertex<T> v : graph.getAdjList().keySet()) {
            if (v.equals(start)) {
                distanceMap.put(v, 0);
            } else {
                distanceMap.put(v, Integer.MAX_VALUE);
            }
        }

        queue.add(new VertexDistance<>(start, 0));

        while (!queue.isEmpty()
                && visited.size() < graph.getVertices().size()) {
            VertexDistance<T> curr = queue.poll();
            if (!visited.contains(curr.getVertex())) {
                visited.add(curr.getVertex());
                for (VertexDistance<T> vd : graph.getAdjList().get(curr.getVertex())) {
                    int distance = curr.getDistance() + vd.getDistance();
                    if (!visited.contains(vd.getVertex())
                            && distanceMap.get(vd.getVertex()) > distance) {
                        distanceMap.put(vd.getVertex(), distance);
                        queue.add(new VertexDistance<>(vd.getVertex(), distance));
                    }
                }

            }
        }

        if (distanceMap.size() != graph.getVertices().size()) {
            for (Vertex<T> v : graph.getVertices()) {
                if (!distanceMap.containsKey(v)) {
                    distanceMap.put(v, Integer.MAX_VALUE);
                }
            }
        }

        return distanceMap;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * You should NOT allow self-loops or parallel edges in the MST.
     * <p>
     * You may import/use java.util.PriorityQueue, java.util.Set, and any
     * class that implements the aforementioned interface.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     * <p>
     * The only instance of java.util.Map that you may use is the adjacency
     * list from graph. DO NOT create new instances of Map for this method
     * (storing the adjacency list in a variable is fine).
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The input from the parameter is null.");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("The graph does not contain the start vertex.");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        Set<Edge<T>> returnSet = new HashSet<>(); // for the returned set of edges.
        Queue<Edge<T>> queue = new PriorityQueue<>();

        for (VertexDistance<T> vd : graph.getAdjList().get(start)) {
            queue.add(new Edge<>(start, vd.getVertex(), vd.getDistance()));
        }

        visited.add(start);

        while (!queue.isEmpty()
                && visited.size() != graph.getVertices().size()) {
            Edge<T> edge = queue.remove();
            if (!visited.contains(edge.getV())) {
                visited.add(edge.getV());
                returnSet.add(new Edge<>(edge.getU(), edge.getV(), edge.getWeight()));
                returnSet.add(new Edge<>(edge.getV(), edge.getU(), edge.getWeight()));

                for (VertexDistance<T> vd : graph.getAdjList().get(edge.getV())) {
                    if (!visited.contains(vd.getVertex())) {
                        queue.add(new Edge<>(edge.getV(), vd.getVertex(), vd.getDistance()));
                    }
                }
            }
        }

        if (returnSet.size() / 2 == graph.getVertices().size() - 1) {
            return returnSet;
        }

        return null;
    }
}
