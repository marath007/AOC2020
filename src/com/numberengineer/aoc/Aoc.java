package com.numberengineer.aoc;


import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.numberengineer.aoc.AocPostCompetition.readFile;

class Aoc {
    public static void main(String[] args) {


        day14();

    }

    public static void day14() {
        TikTok tikTok = new TikTok(true);
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();


//        boolean testMode = true;
        boolean testMode = false;
        String data;
        if (testMode) {
            data = "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
                    "mem[8] = 11\n" +
                    "mem[7] = 101\n" +
                    "mem[8] = 0";
            data = "mask = 000000000000000000000000000000X1001X\n" +
                    "mem[42] = 100\n" +
                    "mask = 00000000000000000000000000000000X0XX\n" +
                    "mem[26] = 1";
        } else {
            data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\data\\" + methodName + ".txt");
        }

        record MemoryAddress(long index) {

        }
        record MemoryValue(long value) {

        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
            long andMask = 0;
            long orMask = 0;
            long[] andMasks = {0};
            long[] orMasks = {0};
        };
        HashMap<MemoryAddress, MemoryValue> addresses = new HashMap<>();
        final var split = data.split("\n");

        final var memExtractor = Pattern.compile("mem\\[([0-9]*)] = ([0-9]*)");
        Arrays.stream(split).forEach(s -> {
            if (s.startsWith("mask")) {
                final var mask = s.replace("mask = ", "");
                o.andMask = Long.parseLong(mask.replace("X", "1"), 2);
                o.orMask = Long.parseLong(mask.replace("X", "0"), 2);

            } else {
                final var matcher = memExtractor.matcher(s);
                final var matches = matcher.matches();
                final var address = Long.parseLong(matcher.group(1));
                final var value = Long.parseLong(matcher.group(2));
                final var newValue = (value | o.orMask) & o.andMask;
                addresses.put(new MemoryAddress(address), new MemoryValue(newValue));
                if (testMode) {
                    System.out.println(newValue);
                }
            }
        });
        o.part1 = addresses.values().stream().mapToLong(MemoryValue::value).sum();
        addresses.clear();
        Arrays.stream(split).forEach(s -> {
            if (s.startsWith("mask")) {
                final var mask = s.replace("mask = ", "");
                final var x = mask.length() - mask.replace("X", "").length();
                long max = (long) Math.pow(2, x);
                o.andMasks = new long[(int) max];
                o.orMasks = new long[(int) max];
                for (int i = 0; i < max; i++) {
                    var ref = new Object() {
                        int j = 0;
                        int xss = 0;
                    };
                    int finalI = i;
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < mask.length(); j++) {
                        char c = mask.charAt(j);
                        if (c == 'X') {
                            final var offset = 1 << ref.xss;
                            if ((offset & finalI) == 0) {
                                c = '0';
                            } else {
                                c = '1';
                            }
                            ref.xss++;
                        } else if (c == '0') {
                            c = 'X';
                        }
                        stringBuilder.append(c);
                    }
                    String newMask = stringBuilder.toString();
                    if (testMode) {
                        System.out.println(newMask);
                    }
                    o.andMasks[i] = Long.parseLong(newMask.replace("X", "1"), 2);
                    o.orMasks[i] = Long.parseLong(newMask.replace("X", "0"), 2);
                }
            } else {
                final var matcher = memExtractor.matcher(s);
                final var matches = matcher.matches();
                final var address = Long.parseLong(matcher.group(1));
                final var value = Long.parseLong(matcher.group(2));
                for (int i = 0; i < o.andMasks.length; i++) {
                    final var newAddress = (address | o.orMasks[i]) & o.andMasks[i];
                    addresses.put(new MemoryAddress(newAddress), new MemoryValue(value));
                    if (testMode) {
                        System.out.println(newAddress);
                    }
                }
            }
        });
        o.part2 = addresses.values().stream().mapToLong(MemoryValue::value).sum();

        if (testMode) {
            System.out.println("#######################################");
            System.out.println("##        THIS IS JUST A TEST        ##");
            System.out.println("#######################################");
        }
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

    public static void dayN() {
        TikTok tikTok = new TikTok(true);
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();


//        boolean testMode = true;
        boolean testMode = false;
        String data;
        if (testMode) {
            data = "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
                    "mem[8] = 11\n" +
                    "mem[7] = 101\n" +
                    "mem[8] = 0";
        } else {
            data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\data\\" + methodName + ".txt");
        }

        record MemoryAddress(long index) {

        }
        record MemoryValue(long value) {

        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        HashMap<MemoryAddress, MemoryValue> addresses = new HashMap<>();
        final var split = data.split("\n");


        if (testMode) {
            System.out.println("#######################################");
            System.out.println("##        THIS IS JUST A TEST        ##");
            System.out.println("#######################################");
        }
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

}