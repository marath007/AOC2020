package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import java.util.Arrays;

import static com.numberengineer.aoc.Utils.*;

public class Day10 {
    public static void day10() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "addx 15\n" +
                "addx -11\n" +
                "addx 6\n" +
                "addx -3\n" +
                "addx 5\n" +
                "addx -1\n" +
                "addx -8\n" +
                "addx 13\n" +
                "addx 4\n" +
                "noop\n" +
                "addx -1\n" +
                "addx 5\n" +
                "addx -1\n" +
                "addx 5\n" +
                "addx -1\n" +
                "addx 5\n" +
                "addx -1\n" +
                "addx 5\n" +
                "addx -1\n" +
                "addx -35\n" +
                "addx 1\n" +
                "addx 24\n" +
                "addx -19\n" +
                "addx 1\n" +
                "addx 16\n" +
                "addx -11\n" +
                "noop\n" +
                "noop\n" +
                "addx 21\n" +
                "addx -15\n" +
                "noop\n" +
                "noop\n" +
                "addx -3\n" +
                "addx 9\n" +
                "addx 1\n" +
                "addx -3\n" +
                "addx 8\n" +
                "addx 1\n" +
                "addx 5\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx -36\n" +
                "noop\n" +
                "addx 1\n" +
                "addx 7\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx 2\n" +
                "addx 6\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx 1\n" +
                "noop\n" +
                "noop\n" +
                "addx 7\n" +
                "addx 1\n" +
                "noop\n" +
                "addx -13\n" +
                "addx 13\n" +
                "addx 7\n" +
                "noop\n" +
                "addx 1\n" +
                "addx -33\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx 2\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx 8\n" +
                "noop\n" +
                "addx -1\n" +
                "addx 2\n" +
                "addx 1\n" +
                "noop\n" +
                "addx 17\n" +
                "addx -9\n" +
                "addx 1\n" +
                "addx 1\n" +
                "addx -3\n" +
                "addx 11\n" +
                "noop\n" +
                "noop\n" +
                "addx 1\n" +
                "noop\n" +
                "addx 1\n" +
                "noop\n" +
                "noop\n" +
                "addx -13\n" +
                "addx -19\n" +
                "addx 1\n" +
                "addx 3\n" +
                "addx 26\n" +
                "addx -30\n" +
                "addx 12\n" +
                "addx -1\n" +
                "addx 3\n" +
                "addx 1\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx -9\n" +
                "addx 18\n" +
                "addx 1\n" +
                "addx 2\n" +
                "noop\n" +
                "noop\n" +
                "addx 9\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx -1\n" +
                "addx 2\n" +
                "addx -37\n" +
                "addx 1\n" +
                "addx 3\n" +
                "noop\n" +
                "addx 15\n" +
                "addx -21\n" +
                "addx 22\n" +
                "addx -6\n" +
                "addx 1\n" +
                "noop\n" +
                "addx 2\n" +
                "addx 1\n" +
                "noop\n" +
                "addx -10\n" +
                "noop\n" +
                "noop\n" +
                "addx 20\n" +
                "addx 1\n" +
                "addx 2\n" +
                "addx 2\n" +
                "addx -6\n" +
                "addx -11\n" +
                "noop\n" +
                "noop\n" +
                "noop";
        if (!testMode) {
            data = getData(day, "2022");
        }
        var o = new Object() {
            int x = 1;
            long part1 = 0;
            long part2 = 0;
        };
        int[] atCycle = new int[]{20, 60, 100, 140, 180, 220};
//        int[] valueat = new int[]{20, 60, 100, 140, 180, 220};
        final var strings = asLines(data);
        int k=0;
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < strings.length; i++,k++) {
            int finalI =k;

            int cycleNo=k%40+1;
            if (Arrays.stream(atCycle).filter(value -> finalI == value-1).count() == 1) {
                o.part1 += (long) (finalI+1) * o.x;
                System.out.println(o.x);
                System.out.println(finalI);
                System.out.println();

            }
            if (k%40==0){
                stringBuilder.append("\n");
            }
            if (cycleNo>=o.x && cycleNo<o.x+3){
                stringBuilder.append("█");
            }else {
                stringBuilder.append(" ");
            }

            String string = strings[i];
            final var s = string.split(" ");
            if (s[0].equals("noop")) {
            } else if (s[0].equals("addx")) {
                final var s1 = Integer.parseInt(s[1]);
                k++;

                int finalII =k;
                if (Arrays.stream(atCycle).filter(value -> finalII == value-1).count() == 1) {
                    o.part1 += (long) (finalII+1) * o.x;

//                    System.out.println((long) finalII * o.x);
                    System.out.println(o.x);
                    System.out.println(finalII);
                    System.out.println();


                }
                cycleNo=k%40+1;
                if (k%40==0){
                    stringBuilder.append("\n");
                }
                if (cycleNo>=o.x && cycleNo<o.x+3){
                    stringBuilder.append("█");
                }else {
                    stringBuilder.append(" ");
                }
                o.x += s1;

            }
        }
        System.out.println(stringBuilder);

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

