package com.numberengineer.aoc;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[] ints = getInts(10000000);
        TikTok tikTok = new TikTok();
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            tikTok.tic();
            if (i % 2 == 0) {
                final var strings = notStream(ints);
                tikTok.toc(System.out, " Not Stream");
                sum += strings;
            } else {
                final var stream = stream(ints);
                tikTok.toc(System.out, " Stream");
                sum += stream;
            }
        }
    }

    static long notStream(int[] ints) {
        int sum = 0;
        for (int i : ints) {
            sum += i;
        }
        return sum;
    }

    static long stream(int[] ints) {
        return Arrays.stream(ints).sum();
    }
//    static String[] notStream(int[] ints) {
//        List<String> list = new ArrayList<>();
//        for (int i : ints) {
//            String s = String.valueOf(i);
//            list.add(s);
//        }
//        return list.toArray(new String[0]);
//    }

//    static String[] stream(int[] ints) {
//        return Arrays.stream(ints).mapToObj(String::valueOf).toArray(String[]::new);
//    }

    private static int[] getInts(int i1) {
        int[] ints = new int[i1];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = i;
        }
        return ints;
    }
}
