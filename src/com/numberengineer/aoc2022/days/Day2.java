package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import java.util.Arrays;

import static com.numberengineer.aoc.Utils.*;

public class Day2 {
    public static void day2() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "A Y\n" +
                "B X\n" +
                "C Z";
        if (!testMode) {
            data = getData(day, "2022");
        }


        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var strings = asLines(data);
        Arrays.stream(strings).forEach(s -> {
            final var s1 = s.split(" ");
            switch (s1[1]){
                case "X"-> {
                    if (s1[0].equals("C")) o.part1 += 6;
                    if (s1[0].equals("A")) o.part1 += 3;
                    o.part1+=1;
                }
                case "Y"-> {
                    if (s1[0].equals("A")) o.part1 += 6;
                    if (s1[0].equals("B")) o.part1 += 3;
                    o.part1+=2;
                }
                case "Z"-> {
                    if (s1[0].equals("B")) o.part1 += 6;
                    if (s1[0].equals("C")) o.part1 += 3;
                    o.part1+=3;
                }
            }
            switch (s1[1]){
                case "X"-> {
                    if (s1[0].equals("A")) o.part2 += 3;
                    if (s1[0].equals("B")) o.part2 += 1;
                    if (s1[0].equals("C")) o.part2 += 2;
                    o.part2+=0;
                }
                case "Y"-> {
                    if (s1[0].equals("A")) o.part2 += 1;
                    if (s1[0].equals("B")) o.part2 += 2;
                    if (s1[0].equals("C")) o.part2 += 3;
                    o.part2+=3;
                }
                case "Z"-> {
                    if (s1[0].equals("A")) o.part2 += 2;
                    if (s1[0].equals("B")) o.part2 += 3;
                    if (s1[0].equals("C")) o.part2 += 1;
                    o.part2+=6;
                }
            }
        });
//        final var longs = asLongs(data);
//        o.part1 = 0;
//        o.part2 = 0;


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

