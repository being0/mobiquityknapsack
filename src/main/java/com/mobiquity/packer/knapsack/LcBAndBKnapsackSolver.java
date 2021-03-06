package com.mobiquity.packer.knapsack;

import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.KnapsackProblem;
import com.mobiquity.packer.model.KnapsackSolution;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Solve 0/1 knapsack with real numbers on weight and value by using Least Cost Branch and Bound algorithm.
 * LcBAndBKnapsackSolver uses **Least Cost Branch And Bound** algorithm to solve this problem in most efficient approach(However this problem is polynomial and may end to exponential time complexity).
 * Instead of normal brute force approach that checks all possible solutions, **Branch and Bound** Algorithm tries
 * to eliminate candidates that their **Upper Bound** is not better than the current best solution(So in this case the subtree is not continued).
 * Here in Knapsack to calculate Upper Bound we sort items by ratio of value/weight and we take fraction of last item(fraction is not used in solution but it is used for upper bound).
 * Also We can traverse the tree using FIFO and LIFO data structures. Here to optimize more we use **PriorityQueue**,
 * using PriorityQueue allows us to apply **Least Cost** strategy. The priority queue let us to peek the node with the lowest bound at the first(**Lower Bound** is the value of actual solution at the node)

 * Since 0/1 Knapsack is about maximizing the total value, we cannot directly use the LC Branch and Bound technique to solve this. Instead, we convert this into a minimization problem by taking negative of the given values.
 *
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class LcBAndBKnapsackSolver implements KnapsackSolver {

    static class Node {
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

        private Node() {
        }

        private Node(Node cpy) {
            this.tv = cpy.tv;
            this.tw = cpy.tw;
            this.ub = cpy.ub;
            this.lb = cpy.lb;
            this.level = cpy.level;
            this.selected = cpy.selected;
        }

    }

    // Comparator to sort based on lower bound
    static class sortByLb implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return a.lb.compareTo(b.lb) >= 1 ? 1 : -1;
        }
    }

    static class sortByRatio implements Comparator<Item> {
        public int compare(Item a, Item b) {
            return b.getRatio().compareTo(a.getRatio()) >= 1 ? 1 : -1;
        }
    }

    private static final KnapsackSolution NO_SOLUTION = new KnapsackSolution(Collections.emptyList(), BigDecimal.ZERO, BigDecimal.ZERO);

    @Override
    public KnapsackSolution solve(KnapsackProblem knapsackProblem) {
        if (knapsackProblem == null || knapsackProblem.getItems() == null) return NO_SOLUTION;

        Item[] items = knapsackProblem.getItems().toArray(new Item[0]);
        int size = items.length;
        BigDecimal capacity = knapsackProblem.getMaxWeight();

        // Sort the items based on the
        // profit/weight ratio
        Arrays.sort(items, new sortByRatio());

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
        PriorityQueue<Node> pq = new PriorityQueue<>(new sortByLb());

        // Insert a dummy node
        pq.add(current);

        // curr_path -> Boolean itemsay to store
        // at every index if the element is
        // included or not

        // final_path -> Boolean itemsay to store
        // the result of selection itemsay when
        // it reached the last level
        boolean[] currPath = new boolean[size];
        boolean[] finalPath = new boolean[size];

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
                        finalPath[items[i].getIndex() - 1]
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
            assign(right, upperBound(current.tv, current.tw, level + 1, items, size, capacity),
                    lowerBound(current.tv, current.tw, level + 1, items, size, capacity),
                    level + 1, false,
                    current.tv, current.tw);

            if (current.tw.add(items[current.level].getWeight()).compareTo(capacity) <= 0) {

                // left node -> includes current item
                // c and lb should be calculated
                // including the current item.
                left.ub = upperBound(
                        current.tv.subtract(items[level].getValue()),
                        current.tw.add(items[level].getWeight()),
                        level + 1, items, size, capacity);
                left.lb = lowerBound(
                        current.tv.subtract(items[level].getValue()),
                        current.tw.add(items[level].getWeight()),
                        level + 1,
                        items, size, capacity);
                assign(left, left.ub, left.lb,
                        level + 1, true,
                        current.tv.subtract(items[level].getValue()),
                        current.tw.add(items[level].getWeight()));
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


        ArrayList<Item> itemsInSolution = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (finalPath[i])
                itemsInSolution.add(knapsackProblem.getItems().get(i));
            else {
                // Not selected
            }
        }

        return itemsInSolution.size() > 0 ? new KnapsackSolution(itemsInSolution, finalWeight, finalLB.negate()) : NO_SOLUTION;
    }

    // Function to calculate upper bound
    // (includes fractional part of the items)
    private BigDecimal upperBound(BigDecimal tv, BigDecimal tw, int idx, Item[] items, int size, BigDecimal capacity) {
        BigDecimal value = tv;
        BigDecimal weight = tw;
        for (int i = idx; i < size; i++) {
            BigDecimal newWeight = weight.add(items[i].getWeight());
            if (newWeight.compareTo(capacity) <= 0) {
                weight = newWeight;
                value = value.subtract(items[i].getValue());
            } else {
                // Take fraction
                value = value.subtract((capacity.subtract(weight)).divide(items[i].getWeight(), 10, RoundingMode.HALF_UP)
                        .multiply(items[i].getValue()));
                break;
            }
        }
        return value;
    }

    // Calculate lower bound (doesn't
    // include fractional part of items)
    private BigDecimal lowerBound(BigDecimal tv, BigDecimal tw, int idx, Item[] items, int size, BigDecimal capacity) {
        BigDecimal value = tv;
        BigDecimal weight = tw;
        for (int i = idx; i < size; i++) {
            BigDecimal newWeight = weight.add(items[i].getWeight());
            if (newWeight.compareTo(capacity) <= 0) {
                weight = newWeight;
                value = value.subtract(items[i].getValue());
            } else {
                break;
            }
        }
        return value;
    }

    private void assign(Node a, BigDecimal ub, BigDecimal lb, int level,
                        boolean selected, BigDecimal tv, BigDecimal tw) {
        a.ub = ub;
        a.lb = lb;
        a.level = level;
        a.selected = selected;
        a.tv = tv;
        a.tw = tw;
    }

}
