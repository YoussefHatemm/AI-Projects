package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Strategies {

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> BF = (queue, expandedNodes) -> {
        queue.addAll(expandedNodes);
        return queue;
    };

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> DF = (queue, expandedNodes) -> {
        expandedNodes.addAll(queue);
        return expandedNodes;
    };

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> UC = (queue, expandedNodes) -> {
        Collections.sort(expandedNodes);
        return sortArrLists(queue, expandedNodes);
    };

    public static ArrayList<Node> sortArrLists(ArrayList<Node> queue, ArrayList<Node> expandedNodes) {
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
    }

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> GR1 = (queue, expandedNodes) -> {

        for (Node node : expandedNodes) {
            node.heuristicValue = simpleCountStab((node));
        }

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

            if (queue.get(i).heuristicValue == expandedNodes.get(j).heuristicValue) {
                output.add(queue.get(i));
                output.add(expandedNodes.get(j));
                i++;
                j++;
            } else if (queue.get(i).heuristicValue < expandedNodes.get(j).heuristicValue) {
                output.add(queue.get(i));
                i++;
            } else {
                output.add(expandedNodes.get(j));
                j++;
            }
        }

        return output;


    };

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> AS1 = (queue, expandedNodes) -> {
        for (Node node : expandedNodes) {
            node.heuristicValue = simpleCountStab((node));
        }
        return sortArrLists(queue, expandedNodes);
    };



        public static int simpleCountStab(Node node) {
        int walkersAlive = ((WesterosState)node.state).walkersAlive;
        if(walkersAlive % 3 == 0)
            return walkersAlive / 3;

        return (walkersAlive / 3) + 1;
    }



//        for(int i = 0; i < expandedNodes.size(); i++) {
//            for(int j = queue.size(); j >= 0; j--) {
//                if( < 0)
//            }
//        }

}