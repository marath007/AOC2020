package com.numberengineer.aoc2019.days;

import com.numberengineer.aoc.TikTok;

import static com.numberengineer.aoc.Utils.*;

public class Day22 {
    public static void dayN() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean testMode = true;
//        boolean testMode = false;
        String data = "";
        if (!testMode) {
            data = getData(day);
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var strings = asLines(data);
        final var longs = asLongs(data);


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

