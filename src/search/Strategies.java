package search;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class Strategies {

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> bfs = (queue, expandedNodes) -> {
        queue.addAll(expandedNodes);
        return queue;
    };
}