package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Strategies {

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> bfs = (queue, expandedNodes) -> {
        queue.addAll(expandedNodes);
        return queue;
    };

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> dfs = (queue, expandedNodes) -> {
        expandedNodes.addAll(queue);
        return expandedNodes;
    };

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> ucs = (queue, expandedNodes) -> {
        Collections.sort(expandedNodes);
        ArrayList output = new ArrayList();
        int i = 0, j = 0;

        while (i < queue.size() || j < expandedNodes.size()) {

            if (i >= queue.size()) {  // length of arg passed
                output.add(expandedNodes.get(j++));
                continue;
            } else if (j >= expandedNodes.size()) {
                output.add(queue.get(i++));
                continue;
            }

            if (queue.get(i).compareTo(expandedNodes.get(j)) == 0) {
                output.add(queue.get(i));
                output.add(expandedNodes.get(j));
                i++;
                j++;
            } else if (queue.get(i).compareTo(expandedNodes.get(j)) < 0) {
                output.add(queue.get(i));
                i++;
            } else {
                output.add(expandedNodes.get(j));
                j++;
            }
        }

        return output;

//        for(int i = 0; i < expandedNodes.size(); i++) {
//            for(int j = queue.size(); j >= 0; j--) {
//                if( < 0)
//            }
//        }
    };

    }