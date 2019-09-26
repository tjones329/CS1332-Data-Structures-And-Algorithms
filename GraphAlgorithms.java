import java.util.*;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Tyler Jones
 * @userid tjones329
 * @GTID 903283773
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
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start can not be null");
        } else if (graph == null) {
            throw new IllegalArgumentException("graph can not be null");
        } else if (graph.getAdjList().get(start) == null) {
            throw new IllegalArgumentException("graph did not contain"
                    + " the given index");
        }
        List<Vertex<T>> returnthis = new ArrayList<>();
        Set<Vertex<T>> vs = new HashSet<>();
        Queue<Vertex<T>> q = new LinkedList<>();
        q.add(start);
        while(!q.isEmpty()) {
            Vertex<T> v = q.remove();
            if (!vs.contains(v)) {
                returnthis.add(v);
                vs.add(v);
            }
            for(VertexDistance<T> vertexpair: graph.getAdjList().get(v)) {
                Vertex<T> adjacent = vertexpair.getVertex();
                if(!vs.contains(adjacent)) {
                    q.add(adjacent);
                }
            }
        }
        return returnthis;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
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
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start can not be null");
        } else if (graph == null) {
            throw new IllegalArgumentException("graph can not be null");
        } else if (graph.getAdjList().get(start) == null) {
            throw new IllegalArgumentException("graph did not contain"
                    + " the given index");
        }

        List<Vertex<T>> returnthis = new ArrayList<>();
        Set<Vertex<T>> vs = new HashSet<>();
        dfsRecursive(start, graph, returnthis, vs);
        return returnthis;
    }

    /**
     *
     * @param curr the current vertex we are adding
     * @param graph the graph we are searching
     * @param returnthis the list of vertices
     * @param vs the set we are adding to
     * @param <T> generic typing
     */
    private static <T> void dfsRecursive(Vertex<T> curr, Graph<T> graph, List<Vertex<T>> returnthis, Set<Vertex<T>> vs) {
        if(!vs.contains(curr)) {
            vs.add(curr);
            returnthis.add(curr);
        }
        for(VertexDistance<T> vertexpair : graph.getAdjList().get(curr)) {
            Vertex<T> adjacent = vertexpair.getVertex();
            if(!vs.contains(adjacent)) {
                dfsRecursive(adjacent, graph, returnthis, vs);
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
     * to that node from {@code start}, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from {@code start} to every
     *          other node in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start can not be null");
        } else if (graph == null) {
            throw new IllegalArgumentException("graph can not be null");
        } else if (graph.getAdjList().get(start) == null) {
            throw new IllegalArgumentException("graph did not contain"
                    + " the given index");
        }
        Set<Vertex<T>> vs = new HashSet<>();
        Map<Vertex<T>, Integer> returnMap = new HashMap<>();
        PriorityQueue<VertexDistance<T>> distances = new PriorityQueue<>();
        for (Vertex<T> vertex : graph.getAdjList().keySet()) {
            if (vertex.equals(start)) {
                returnMap.put(vertex, 0);
            } else {
                returnMap.put(vertex, Integer.MAX_VALUE);
            }
        }
        VertexDistance<T> startdistance = new VertexDistance<>(start, 0);
        distances.add(startdistance);
        while (!distances.isEmpty()) {
            VertexDistance<T> smallest = distances.remove();
            Vertex<T> pairVertex = smallest.getVertex();
            int pairDistance = smallest.getDistance();
            if (!vs.contains(pairVertex)) {
                vs.add(pairVertex);
                if (pairDistance < returnMap.get(pairVertex)) {
                    returnMap.put(pairVertex, pairDistance);
                }
                for (VertexDistance<T> adjVertex : graph.getAdjList().get(pairVertex)) {
                    int dist1 = pairDistance;
                    int distcurr = adjVertex.getDistance();
                    int totaldist = dist1 + distcurr;
                    distances.add(new VertexDistance<T>(adjVertex.getVertex(), totaldist));
                }
            }

        }
        return returnMap;
    }


    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start can not be null");
        } else if (graph == null) {
            throw new IllegalArgumentException("graph can not be null");
        } else if (graph.getAdjList().get(start) == null) {
            throw new IllegalArgumentException("graph did not contain"
                    + " the given index");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Set<Edge<T>> edgeSet = new HashSet<>();
        PriorityQueue<Edge<T>> priorityQueue = new PriorityQueue<>();

        for (Edge<T> edge : graph.getEdges()) {
            priorityQueue.add(edge);
        }
        visitedSet.add(start);
        while (!priorityQueue.isEmpty()) {
            Edge<T> checkEdge = priorityQueue.remove();
            if (!visitedSet.contains(checkEdge.getV())) {
                visitedSet.add(checkEdge.getV());
                edgeSet.add(checkEdge);
                Vertex<T> w = checkEdge.getV();
                for (Edge<T> edge : graph.getEdges()) {
                    if (!visitedSet.contains(edge.getV())) {
                        priorityQueue.add(edge);
                    }
                }
            }
        }
        return edgeSet;
    }
}