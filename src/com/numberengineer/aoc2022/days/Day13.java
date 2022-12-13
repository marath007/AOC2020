package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import java.util.ArrayList;
import java.util.Comparator;

import static com.numberengineer.aoc.Utils.*;

public class Day13 {
    public static void day13() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "[1,1,3,1,1]\n" +
                "[1,1,5,1,1]\n" +
                "\n" +
                "[[1],[2,3,4]]\n" +
                "[[1],4]\n" +
                "\n" +
                "[9]\n" +
                "[[8,7,6]]\n" +
                "\n" +
                "[[4,4],4,4]\n" +
                "[[4,4],4,4,4]\n" +
                "\n" +
                "[7,7,7,7]\n" +
                "[7,7,7]\n" +
                "\n" +
                "[]\n" +
                "[3]\n" +
                "\n" +
                "[[[]]]\n" +
                "[[]]\n" +
                "\n" +
                "[1,[2,[3,[4,[5,6,7]]]],8,9]\n" +
                "[1,[2,[3,[4,[5,6,0]]]],8,9]";
        if (!testMode) {
            data = getData(day, "2022");
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        ArrayList<String> strings = new ArrayList<>();

        final var packetPairs = data.split("\n\n");
        for (int i = 0; i < packetPairs.length; i++) {
            final var packets = packetPairs[i].split("\n");
            final var packet1 = packets[0];
            final var packet2 = packets[1];
            strings.add(packet1);
            strings.add(packet2);
            try {
                if (comparePacket(packet1, packet2)) {
                    System.out.println(i + 1);
                    o.part1 += i + 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        strings.add("[[2]]");
        strings.add("[[6]]");
        strings.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                final boolean b;
                try {
                    b = comparePacket(o1, o2);
                } catch (Exception e) {
                    return 0;
                }
                return b
                       ? -1
                       : 1;
            }
        });
        o.part2 = (long) (strings.indexOf("[[2]]") + 1) * (strings.indexOf("[[6]]") + 1);

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static ArrayList<String> unWrap(String s) {
        if (!s.contains("[")) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(s);
            return arrayList;
        }
        int bCount = 0;
        int from = 0;
        int to = 0;
        boolean escapeNext = false;
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (!escapeNext) {
                if (s.charAt(i) == '[') {
                    bCount++;
                    if (bCount == 1) {
                        from = i;
                    }
                } else if (s.charAt(i) == ']') {
                    bCount--;
                    if (bCount == 0) {
                        to = i;
                        arrayList.add(s.substring(from + 1, to));
                    }
                } else if (s.charAt(i) == ',') {
                    if (bCount == 1) {
                        to = i;
                        arrayList.add(s.substring(from + 1, to));
                        from = to;
                    }
                } else if (s.charAt(i) == '\\') {
                    escapeNext = true;
                }
            } else {
                escapeNext = false;
            }
        }
        return arrayList;
    }

    private static boolean comparePacket(String packet1, String packet2) {
        final var strings1 = unWrap(packet1);
        final var strings2 = unWrap(packet2);
        for (int i = 0; i < Math.max(strings1.size(), strings2.size()); i++) {
            if (i >= strings1.size()) return true;
            if (i >= strings2.size()) return false;
            final var leftSide = strings1.get(i);
            final var rightSide = strings2.get(i);
            if (leftSide.contains("[") || rightSide.contains("[")) {
                try {
                    return comparePacket(leftSide, rightSide);
                } catch (RuntimeException ignored) {

                }
            } else {
                if (!leftSide.equals(rightSide)) {
                    if (leftSide.isEmpty()) return true;
                    if (rightSide.isEmpty()) return false;
                    if (Integer.parseInt(leftSide) < Integer.parseInt(rightSide)) {
                        return true;
                    } else if (Integer.parseInt(leftSide) > Integer.parseInt(rightSide)) {
                        return false;
                    }
                }
            }
        }
        throw new RuntimeException("wtf");

    }


}

