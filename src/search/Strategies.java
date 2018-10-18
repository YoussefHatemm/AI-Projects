package search;

import utils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Strategies {
    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> BF = (queue, expandedNodes) -> {
        queue.addAll(expandedNodes);
        return queue;
    };

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> DF = (queue, expandedNodes) -> {
        expandedNodes.addAll(queue);
        return expandedNodes;
    };

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> ID = (queue, expandedNodes) -> {
        expandedNodes.addAll(queue);
        return expandedNodes;
    };

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> UC = (queue, expandedNodes) -> {
        Collections.sort(expandedNodes);
        return sortArrLists(queue, expandedNodes);
    };

    public static ArrayList<Node> sortArrLists(ArrayList<Node> queue, ArrayList<Node> expandedNodes) {
        ArrayList<Node> output = new ArrayList<Node>();
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
            node.heuristicValue = simpleCountStabHeur((node));
        }
        Collections.sort(expandedNodes, Comparator.comparing(Node::getHeuristicValue));
        return sortByHueristic(queue, expandedNodes);
    };


    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> GR2 = (queue, expandedNodes) -> {

        for (Node node : expandedNodes) {
            node.heuristicValue = closestClusterHeur((node));
        }
        Collections.sort(expandedNodes, Comparator.comparing(Node::getHeuristicValue));
        return sortByHueristic(queue, expandedNodes);
    };

    public static ArrayList<Node> sortByHueristic(ArrayList<Node> arr1, ArrayList<Node> arr2) {
        ArrayList<Node> output = new ArrayList<Node>();
        int i = 0, j = 0;

        while (i < arr1.size() || j < arr2.size()) {

            if (i >= arr1.size()) {  // length of arg passed
                output.add(arr2.get(j++));
                continue;
            } else if (j >= arr2.size()) {
                output.add(arr1.get(i++));
                continue;
            }

            if (arr1.get(i).heuristicValue == arr2.get(j).heuristicValue) {
                output.add(arr1.get(i));
                output.add(arr2.get(j));
                i++;
                j++;
            } else if (arr1.get(i).heuristicValue < arr2.get(j).heuristicValue) {
                output.add(arr1.get(i));
                i++;
            } else {
                output.add(arr2.get(j));
                j++;
            }
        }

        return output;
    }

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> AS1 = (queue, expandedNodes) -> {
        for (Node node : expandedNodes) {
            node.heuristicValue = simpleCountStabHeur((node));
        }
        Collections.sort(expandedNodes);
        return sortArrLists(queue, expandedNodes);
    };

    public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> AS2 = (queue, expandedNodes) -> {

        for (Node node : expandedNodes) {
            node.heuristicValue = closestClusterHeur(node);
        }
        Collections.sort(expandedNodes);
        return sortArrLists(queue, expandedNodes);
    };




    public static int simpleCountStabHeur(Node node) {
        int walkersAlive = ((WesterosState)node.state).walkersAlive;
        if(walkersAlive % 3 == 0)
            return walkersAlive / 3;

        return (walkersAlive / 3) + 1;
    }

    public static int closestClusterHeur(Node node) {
        Node parent = node.parent;

        int moveCost = 3, stab1Cost = 16, stab2Cost = 8, stab3Cost = 1;

        WesterosState.Occupant[][] parentGrid = ((WesterosState) parent.state).wGrid.grid;
        WesterosState state = (WesterosState)node.state;
        Pair jonLocation = new Pair(state.yCoord, state.xCoord);

//      Find a trio of walkers
        int value = trioClosest(jonLocation, parentGrid);

       if (value != -1)
            return value * moveCost + stab3Cost;

       value = duoClosest(jonLocation, parentGrid);
        if (value != -1)
            return value* moveCost + stab2Cost;

        value = unoClosest(jonLocation, parentGrid) ;
        return value * moveCost + stab1Cost;
    }

    public static int unoClosest(Pair jonLocation, WesterosState.Occupant[][] grid) {
        int upperBound = (grid.length * grid[0].length) * 10;
        int shortestDistance = upperBound;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == WesterosState.Occupant.WALKER) {
                    if (i < grid.length -1 && isAppproachable(grid[i+1][j]))
                        shortestDistance = Math.min(shortestDistance,findDistance(new Pair(i+1,j), jonLocation));

                    if (i > 0 && isAppproachable(grid[i-1][j]))
                        shortestDistance = Math.min(shortestDistance,findDistance(new Pair(i-1,j), jonLocation));

                    if (j < grid[0].length -1 && isAppproachable(grid[i][j+1]))
                        shortestDistance = Math.min(shortestDistance,findDistance(new Pair(i, j+1), jonLocation));

                    if (j > 0 && isAppproachable(grid[i][j-1]))
                        shortestDistance = Math.min(shortestDistance,findDistance(new Pair(i, j-1), jonLocation));
                }

            }
        }
        return shortestDistance;
    }

    public static int duoClosest(Pair jonLocation, WesterosState.Occupant[][]grid) {
        int upperBound = (grid.length * grid[0].length) * 10;
        int shortestDistance = upperBound;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if (grid[i][j] != WesterosState.Occupant.WALKER)
                    continue;

                if (i == 0) {
                    ArrayList<Pair> locations = checkUpperDuo(i,j,grid);
                    if (!locations.isEmpty()) {
                        shortestDistance = Math.min(shortestDistance,locations.stream().map(loc -> findDistance(loc, jonLocation)).reduce((x,y) -> x < y? x : y).get());
                    }
                    continue;
                }

                if (i == grid.length -1) {
                    ArrayList<Pair> locations = checkLowerDuo(i,j,grid);
                    if (!locations.isEmpty()) {
                        shortestDistance = Math.min(shortestDistance,locations.stream().map(loc -> findDistance(loc, jonLocation)).reduce((x,y) -> x < y? x : y).get());
                    }
                    continue;
                }

                ArrayList<Pair> upper = checkUpperDuo(i,j,grid);
                ArrayList<Pair> lower = checkLowerDuo(i,j,grid);
                int temp1 =  upper.isEmpty()? upperBound: upper.stream().map(loc -> findDistance(loc, jonLocation)).reduce((x,y) -> x < y? x : y).get();
                int temp2 = lower.isEmpty()? upperBound: lower.stream().map(loc -> findDistance(loc, jonLocation)).reduce((x,y) -> x < y? x : y).get();
                shortestDistance = Math.min(temp1,Math.min(temp2, shortestDistance));
            }
        }
        if (shortestDistance != upperBound)
            return shortestDistance;

        return -1;
    }

    public static int trioClosest(Pair jonLocation, WesterosState.Occupant[][] grid) {
        int upperBound = (grid.length * grid[0].length) * 10;
        int shortestDistance = upperBound;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length -1; j++) {
                if (grid[i][j] != WesterosState.Occupant.WALKER)
                    continue;

                if (i == 0) {
                    Pair location = checkUpper(i,j,grid);
                    if (location != null) {
                        int temp = findDistance(location, jonLocation);
                        if (temp < shortestDistance)
                            shortestDistance = temp;
                    }
                    continue;
                }

                if (i == grid.length -1) {
                    Pair location = checkLower(i,j,grid);
                    if (location != null) {
                        int temp = findDistance(location, jonLocation);
                        if (temp < shortestDistance)
                            shortestDistance = temp;
                    }
                    continue;
                }
                Pair upperLocation =  checkUpper(i,j,grid);
                Pair lowerLocation = checkLower(i,j,grid);
                int temp1,temp2;
                temp1 = temp2 = upperBound;
                if (upperLocation != null)
                    temp1 = findDistance(upperLocation, jonLocation);
                if (lowerLocation != null)
                    temp2 = findDistance(lowerLocation, jonLocation);

                shortestDistance = Math.min(temp1,Math.min(temp2, shortestDistance));
            }
        }
        if (shortestDistance != upperBound)
            return shortestDistance;

        return -1;
    }


    public static int findDistance(Pair starting, Pair ending) {
        int yDiff = Math.abs((int)ending.getFirst() - (int)starting.getFirst());
        int xDiff = Math.abs((int)ending.getSecond() - (int)starting.getSecond());
        return xDiff + yDiff;
    }

    public static boolean isAppproachable(WesterosState.Occupant occ) {
         return occ  == WesterosState.Occupant.FREE || occ == WesterosState.Occupant.DRAGONSTONE;
    }

    public static Pair checkUpper(int i, int j, WesterosState.Occupant[][] grid) {
        if (grid[i + 1][j-1] == WesterosState.Occupant.WALKER && // up right
            grid[i+1][j+1] == WesterosState.Occupant.WALKER && // up left
                isAppproachable(grid[i+1][j])
        ) {
            return new Pair(i+1, j);
        } else {
            return null;
        }
    }

    public static ArrayList<Pair> checkUpperDuo(int i, int j, WesterosState.Occupant[][] grid) {
        ArrayList<Pair> output = new ArrayList<>();

//        upper right
        if (grid[i+1][j-1] == WesterosState.Occupant.WALKER) {
            if(isAppproachable(grid[i][j-1]))
                output.add(new Pair(i,j-1));

            if (isAppproachable(grid[i+1][j]))
                output.add(new Pair(i + 1, j));
        }

//        upper left
        if (grid[i+1][j+1] == WesterosState.Occupant.WALKER) {
            if(isAppproachable(grid[i][j+1]))
                output.add(new Pair(i,j+1));

            if(isAppproachable(grid[i+1][j]))
                output.add(new Pair(i+1,j));
        }
        return output;
    }

    public static ArrayList<Pair> checkLowerDuo(int i, int j, WesterosState.Occupant[][] grid) {
        ArrayList<Pair> output = new ArrayList<>();

//        lower right
        if (grid[i-1][j-1] == WesterosState.Occupant.WALKER) {
            if(isAppproachable(grid[i][j-1]))
                output.add(new Pair(i,j-1));

            if (isAppproachable(grid[i-1][j]))
                output.add(new Pair(i - 1, j));
        }

//        lower left
        if (grid[i-1][j+1] == WesterosState.Occupant.WALKER) {
            if(isAppproachable(grid[i][j+1]))
                output.add(new Pair(i,j+1));

            if(isAppproachable(grid[i-1][j]))
                output.add(new Pair(i-1,j));
        }
        return output;
    }

    public static Pair checkLower(int i, int j, WesterosState.Occupant[][] grid) {
        if (grid[i - 1][j-1] == WesterosState.Occupant.WALKER &&
                grid[i-1][j+1] == WesterosState.Occupant.WALKER &&
                isAppproachable(grid[i-1][j])
        ) {
            return new Pair(i-1, j);
        } else {
            return null;
        }
    }


        /*public static BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> ID = (queue, expandedNodes) -> {
        	
            return queue;
        };*/

}
