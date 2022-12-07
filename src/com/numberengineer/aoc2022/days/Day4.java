package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import java.util.Arrays;

import static com.numberengineer.aoc.Utils.*;

public class Day4 {
    public static void day4() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "2-4,6-8\n" +
                "2-3,4-5\n" +
                "5-7,7-9\n" +
                "2-8,3-7\n" +
                "6-6,4-6\n" +
                "2-6,4-8";
        if (!testMode) {
            data = getData(day, "2022");
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var strings = asLines(data);
        for (String string : strings) {
            final var parts = string.split(",");
            final var left = Arrays.stream(parts[0].split("-")).map(Integer::parseInt).mapToInt(Integer::intValue).toArray();
            final var right = Arrays.stream(parts[1].split("-")).map(Integer::parseInt).mapToInt(Integer::intValue).toArray();
            if (left[0] <= right[0] && left[1] >= right[1]) {
                o.part1++;
            } else if (left[0] >= right[0] && left[1] <= right[1]) {
                o.part1++;
            }

            if (left[0] < right[0] && left[1] < right[0]) {

            } else if (right[0] < left[0] && right[1] < left[0]) {

            } else if (left[0] > right[1] && left[1] > right[1]) {

            } else if (right[0] > left[1] && right[1] > left[1]) {

            } else {
                o.part2++;
            }
        }


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

