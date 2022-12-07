package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import static com.numberengineer.aoc.Utils.*;

public class Day6 {
    public static void day6() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "mjqjpqmgbljsphdztnvjfqwrcgsmlb";
        if (!testMode) {
            data = getData(day, "2022");
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        for (int i = 0; i < data.length() - 4; i++) {
            if (data.substring(i, i+4).chars().distinct().count() == 4) {
                o.part1 = i+4;
                break;
            }

        }
        for (int i = 0; i < data.length() - 14; i++) {
            if (data.substring(i, i+14).chars().distinct().count() == 14) {
                o.part2 = i+14;
                break;
            }

        }
//        final var strings = asLines(data);
//        final var longs = asLongs(data);


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

