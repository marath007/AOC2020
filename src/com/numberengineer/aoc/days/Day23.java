package com.numberengineer.aoc.days;

import com.numberengineer.aoc.TikTok;
import com.numberengineer.aoc.Utils;

import java.util.HashMap;

import static com.numberengineer.aoc.Utils.endOfWork;
import static com.numberengineer.aoc.Utils.getDay;

public class Day23 {
    public static void day23() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean verbose = true;
        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "389125467";
        if (!testMode) {
            data = "469217538";
        }
        class Node {
            int data;
            Node left;
            Node right;

            public Node(long data) {
                this.data = (int) data;
            }

            Node removeFromCircle() {
                final var right = this.right;
                right.left = left;
                left.right = right;
                left = null;
                this.right = null;
                return this;
            }

            public void insertToTheRight(Node... set) {
                Node n = this;
                for (Node node : set) {
                    n.insertToTheRight(node);
                    n = node;
                }
            }

            public void insertToTheRight(Node set) {
                final var right = this.right;
                set.left = this;
                set.right = this.right;
                this.right = set;
                right.left = set;
            }

        }
        class NodeCircle {
            Node currentNode = null;
            int size = 0;
            int fullSize = 0;
            HashMap<Integer, Node> fastMap = new HashMap<>();

            Node contains(int data) {
                return fastMap.get(clean(data));
//                data = clean(data);
//                var currentNode = this.currentNode;
//                for (int i = 0; i < size; i++) {
//                    if (currentNode.data == data) {
//                        return currentNode;
//                    } else {
//                        currentNode = currentNode.right;
//                    }
//                }
//                return null;
            }

            void sow() {
                fullSize = size + 1;
                Node rightEnd = currentNode;
                while (currentNode.left != null) {
                    currentNode = currentNode.left;
                }
                rightEnd.right = currentNode;
                currentNode.left = rightEnd;
            }

            Node pullRightOne() {
                size--;
                fastMap.remove(currentNode.right.data);
                return currentNode.right.removeFromCircle();
            }


            void putNode(Node n) {
                size++;
                fastMap.put(n.data, n);
                if (currentNode != null) {
                    currentNode.right = n;
                    n.left = currentNode;
                }
                currentNode = n;
            }

            int clean(int i) {
                i %= fullSize;
                if (i < 0)
                    i += fullSize;
                return i;
            }

            public Node pullData(int j) {
                j = clean(j);
                var currentNode = this.currentNode;
                for (int i = 0; i < size; i++) {
                    if (currentNode.data == j) {
                        currentNode.removeFromCircle();
                        return currentNode;
                    }
                }
                return null;
            }

            @Override
            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                var currentNode = this.currentNode;
                for (int i = 0; i < Math.min(size, 100); i++) {
                    stringBuilder.append(currentNode.data);
                    currentNode = currentNode.right;
                }
                return stringBuilder.toString();
            }
        }

        var o = new Object() {
            String part1 = "";
            long part2 = 0;

        };
        NodeCircle nodeCircle = new NodeCircle();
        data.chars().mapToLong(c -> c - '0').mapToObj(Node::new).forEach(nodeCircle::putNode);
        nodeCircle.sow();
        for (int j = 0; j < 100; j++) {
            if (verbose) {
                System.out.println(nodeCircle);
            }
            System.out.print("\r Part 1:" + Utils.consoleProgressBar(j / 100f));
            Node[] set = new Node[3];
            for (int i = 0; i < 3; i++) {
                set[i] = nodeCircle.pullRightOne();
            }

            int delta = 0;
            Node destination = null;
            while ((destination = nodeCircle.contains(nodeCircle.currentNode.data + --delta)) == null) {
            }
            destination.insertToTheRight(set);
            for (int i = 0; i < set.length; i++) {
                nodeCircle.fastMap.put(set[i].data, set[i]);
            }
            nodeCircle.size += set.length;
            nodeCircle.currentNode = nodeCircle.currentNode.right;
        }
        System.out.print("\r Part 1:" + Utils.consoleProgressBar(1f));
        System.out.println();
        if (verbose) {
            System.out.println(nodeCircle);
        }

        final var contains = nodeCircle.contains(1);
        nodeCircle.currentNode = contains;
        o.part1 = nodeCircle.toString().replace("1", "");


        nodeCircle = new NodeCircle();
        data.chars().mapToLong(c -> c - '0').mapToObj(Node::new).forEach(nodeCircle::putNode);
        while (nodeCircle.size < 1000000) {
            nodeCircle.putNode(new Node(nodeCircle.size + 1));
        }
        nodeCircle.sow();
        for (int j = 0; j < 10000000; j++) {

            if (j % 76543 == 0) {
                System.out.print("\r Part 2:" + Utils.consoleProgressBar(j / 10000000f));
            }

            Node[] set = new Node[3];
            for (int i = 0; i < 3; i++) {
                set[i] = nodeCircle.pullRightOne();
            }

            int delta = 0;
            Node destination = null;
            while ((destination = nodeCircle.contains(nodeCircle.currentNode.data + --delta)) == null) {
            }
            destination.insertToTheRight(set);
            for (int i = 0; i < set.length; i++) {
                nodeCircle.fastMap.put(set[i].data, set[i]);
            }
            nodeCircle.size += set.length;
            nodeCircle.currentNode = nodeCircle.currentNode.right;
        }
        System.out.print("\r Part 2:" + Utils.consoleProgressBar(1));
        System.out.println();
        final var right = nodeCircle.contains(1).right;
        final var rightRight = right.right;
        if (testMode) {
            System.out.println(right.data);
            System.out.println(rightRight.data);
        }
        o.part2 = (long) right.data * rightRight.data;

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

}

