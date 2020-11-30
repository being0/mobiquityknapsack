package com.mobiquity.packer.knapsack.test;// Java Program to implement
// 0/1 knapsack using LC 
// Branch and Bound 

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

class Item {

    // Stores the weight
    // of items
    BigDecimal weight;

    // Stores the values
    // of items
    BigDecimal value;

    BigDecimal ratio;

    // Stores the index
    // of items
    int idx;

    public Item() {
    }

    public Item(BigDecimal value, BigDecimal weight,
                int idx) {
        this.value = value;
        this.weight = weight;
        this.idx = idx;
        this.ratio = value.divide(weight, 10, RoundingMode.HALF_UP);
    }

    public BigDecimal getRatio() {
        return ratio;
    }
}

class Node {
    // Upper Bound: Best case
    // (Fractional Knapsack)
    BigDecimal ub;

    // Lower Bound: Worst case
    // (0/1)
    BigDecimal lb;

    // Level of the node in
    // the decision tree
    int level;

    // Stores if the current
    // item is selected or not
    boolean selected;

    // Total Value: Stores the
    // sum of the values of the
    // items included
    BigDecimal tv;

    // Total Weight: Stores the sum of
    // the weights of included items
    BigDecimal tw;

    public Node() {
    }

    public Node(Node cpy) {
        this.tv = cpy.tv;
        this.tw = cpy.tw;
        this.ub = cpy.ub;
        this.lb = cpy.lb;
        this.level = cpy.level;
        this.selected = cpy.selected;
    }

}

// Comparator to sort based on lower bound 
class sortByLb implements Comparator<Node> {
    public int compare(Node a, Node b) {

        return a.lb.compareTo(b.lb) >= 1 ? 1 : -1;
    }
}

class sortByRatio implements Comparator<Item> {
    public int compare(Item a, Item b) {
        return b.getRatio().compareTo(a.getRatio()) >= 1 ? 1 : -1;
    }
}

class knapsack {

    private static int size;
    private static BigDecimal capacity;

    // Function to calculate upper bound
    // (includes fractional part of the items)
    static BigDecimal upperBound(BigDecimal tv, BigDecimal tw,
                                 int idx, Item[] arr) {
        BigDecimal value = tv;
        BigDecimal weight = tw;
        for (int i = idx; i < size; i++) {
            BigDecimal newWeight = weight.add(arr[i].weight);
            if (newWeight.compareTo(capacity) <= 0) {
                weight = newWeight;
                value = value.subtract(arr[i].value);
            } else {
                // Take fraction
                value = value.subtract((capacity.subtract(weight)).divide(arr[i].weight, 10, RoundingMode.HALF_UP)
                        .multiply(arr[i].value));
                break;
            }
        }
        return value;
    }

    // Calculate lower bound (doesn't
    // include fractional part of items)
    static BigDecimal lowerBound(BigDecimal tv, BigDecimal tw,
                                 int idx, Item[] arr) {
        BigDecimal value = tv;
        BigDecimal weight = tw;
        for (int i = idx; i < size; i++) {
            BigDecimal newWeight = weight.add(arr[i].weight);
            if (newWeight.compareTo(capacity) <= 0) {
                weight = newWeight;
                value = value.subtract(arr[i].value);
            } else {
                break;
            }
        }
        return value;
    }

    static void assign(Node a, BigDecimal ub, BigDecimal lb, int level, boolean selected, BigDecimal tv, BigDecimal tw) {
        a.ub = ub;
        a.lb = lb;
        a.level = level;
        a.selected = selected;
        a.tv = tv;
        a.tw = tw;
    }

    public static void solve(Item[] arr) {
        // Sort the items based on the
        // profit/weight ratio
        Arrays.sort(arr, new sortByRatio());

        Node current, left, right;
        current = new Node();
        left = new Node();
        right = new Node();

        // min_lb -> Minimum lower bound
        // of all the nodes explored

        // final_lb -> Minimum lower bound
        // of all the paths that reached
        // the final level
        BigDecimal minLB = BigDecimal.ZERO, finalLB = BigDecimal.valueOf(Integer.MAX_VALUE), finalWeight = BigDecimal.valueOf(Integer.MAX_VALUE);
        current.tv = current.tw = current.ub
                = current.lb = BigDecimal.ZERO;
        current.level = 0;
        current.selected = false;

        // Priority queue to store elements
        // based on lower bounds
        PriorityQueue<Node> pq
                = new PriorityQueue<Node>(
                new sortByLb());

        // Insert a dummy node
        pq.add(current);

        // curr_path -> Boolean array to store
        // at every index if the element is
        // included or not

        // final_path -> Boolean array to store
        // the result of selection array when
        // it reached the last level
        boolean currPath[] = new boolean[size];
        boolean finalPath[] = new boolean[size];

        while (!pq.isEmpty()) {
            current = pq.poll();
            if (current.ub.compareTo(minLB) > 0 || current.ub.compareTo(finalLB) > 0 ||
                    (current.ub.compareTo(finalLB) == 0 && current.tw.compareTo(finalWeight) > 0)) {
                // if the current node's best case
                // value is not optimal than minLB,
                // then there is no reason to
                // explore that node. Including
                // finalLB eliminates all those
                // paths whose best values is equal
                // to the finalLB
                continue;
            }

            if (current.level != 0)
                currPath[current.level - 1]
                        = current.selected;

            if (current.level == size) {
                if (current.lb.compareTo(finalLB) < 0 ||
                        (current.lb.compareTo(finalLB) == 0 && current.tw.compareTo(finalWeight) < 0)) {
                    // Reached last level
                    for (int i = 0; i < size; i++)
                        finalPath[arr[i].idx]
                                = currPath[i];
                    finalLB = current.lb;
                    finalWeight = current.tw;
                }
                continue;
            }

            int level = current.level;

            // right node -> Exludes current item
            // Hence, cp, cw will obtain the value
            // of that of parent
            assign(right, upperBound(current.tv,
                    current.tw,
                    level + 1, arr),
                    lowerBound(current.tv, current.tw,
                            level + 1, arr),
                    level + 1, false,
                    current.tv, current.tw);

            if (current.tw.add(arr[current.level].weight).compareTo(capacity) <= 0) {

                // left node -> includes current item
                // c and lb should be calculated
                // including the current item.
                left.ub = upperBound(
                        current.tv.subtract(arr[level].value),
                        current.tw.add(arr[level].weight),
                        level + 1, arr);
                left.lb = lowerBound(
                        current.tv.subtract(arr[level].value),
                        current.tw.add(arr[level].weight),
                        level + 1,
                        arr);
                assign(left, left.ub, left.lb,
                        level + 1, true,
                        current.tv.subtract(arr[level].value),
                        current.tw.add(arr[level].weight));
            }

            // If the left node cannot
            // be inserted
            else {

                // Stop the left node from
                // getting added to the
                // priority queue
                left.ub = left.lb = BigDecimal.ONE;
            }

            // Update minLB
            if (minLB.compareTo(left.lb) > 0) minLB = left.lb;
            if (minLB.compareTo(right.lb) > 0) minLB = right.lb;

            if (minLB.compareTo(left.ub) >= 0)
                pq.add(new Node(left));
            if (minLB.compareTo(right.ub) >= 0)
                pq.add(new Node(right));
        }
        System.out.println("Items taken"
                + "into the knapsack are");
        for (int i = 0; i < size; i++) {
            if (finalPath[i])
                System.out.print("1 ");
            else
                System.out.print("0 ");
        }
        System.out.println("\nMaximum profit"
                + " is " + (finalLB.negate()));
        System.out.println("\nMaximum weight"
                + " is " + (finalWeight));
    }

    // Driver code
    public static void main(String args[]) {
		size = 4;
		capacity = new BigDecimal("15");

		Item arr[] = new Item[size];
		arr[0] = new Item(new BigDecimal("10"), new BigDecimal("2"), 0);
		arr[1] = new Item(new BigDecimal("10"), new BigDecimal("4"), 1);
		arr[2] = new Item(new BigDecimal("12"), new BigDecimal("6"), 2);
		arr[3] = new Item(new BigDecimal("18"), new BigDecimal("9"), 3);

		solve(arr);

        size = 9;
        capacity = new BigDecimal("56");
        arr = new Item[size];
        arr[0] = new Item(new BigDecimal("13"), new BigDecimal("90.72"), 0);
        arr[1] = new Item(new BigDecimal("40"), new BigDecimal("33.80"), 1);
        arr[2] = new Item(new BigDecimal("10"), new BigDecimal("43.15"), 2);
        arr[3] = new Item(new BigDecimal("16"), new BigDecimal("37.97"), 3);
        arr[4] = new Item(new BigDecimal("36"), new BigDecimal("46.81"), 4);
        arr[5] = new Item(new BigDecimal("79"), new BigDecimal("48.77"), 5);
        arr[6] = new Item(new BigDecimal("45"), new BigDecimal("81.80"), 6);
        arr[7] = new Item(new BigDecimal("79"), new BigDecimal("19.36"), 7);
        arr[8] = new Item(new BigDecimal("64"), new BigDecimal("6.76"), 8);

        solve(arr);

        size = 6;
        capacity = new BigDecimal("81");
         arr= new Item[size];
        arr[0] = new Item(new BigDecimal("45"), new BigDecimal("53.38"), 0);
        arr[1] = new Item(new BigDecimal("98"), new BigDecimal("88.62"), 1);
        arr[2] = new Item(new BigDecimal("3"), new BigDecimal("78.48"), 2);
        arr[3] = new Item(new BigDecimal("76"), new BigDecimal("72.30"), 3);
        arr[4] = new Item(new BigDecimal("9"), new BigDecimal("30.18"), 4);
        arr[5] = new Item(new BigDecimal("48"), new BigDecimal("46.34"), 5);
        solve(arr);

//        size = 1;
//        capacity = 8;
//        Item arr[] = new Item[size];
//        arr[0] = new Item(34, 53.38f, 0);
//
//        solve(arr);
    }
} 
