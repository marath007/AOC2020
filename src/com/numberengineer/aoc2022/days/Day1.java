package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import java.util.ArrayList;

import static com.numberengineer.aoc.Utils.*;
import static com.numberengineer.aoc.Utils.asLongs;

public class Day1 {
    public static void day1() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "1000\n" +
                "2000\n" +
                "3000\n" +
                "\n" +
                "4000\n" +
                "\n" +
                "5000\n" +
                "6000\n" +
                "\n" +
                "7000\n" +
                "8000\n" +
                "9000\n" +
                "\n" +
                "10000";
        if (!testMode) {
            data = getData(day, "2022");
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        ArrayList<Long> sums=new ArrayList<>();
        final var split = data.split("\n\n");
        long maxElf=0;
        for (String s : split) {
            final var longs = asLongs(s);
            var sum=0;
            for (long aLong : longs) {
                sum+=aLong;
            }
            sums.add((long) sum);
            maxElf=Math.max(sum,maxElf);

        }
        sums.sort(Long::compareTo);
        while (sums.size()>3){
            sums.remove(0);
        }
        o.part1=maxElf;
        o.part2=sums.get(0)+sums.get(1)+sums.get(2);
//        final var strings = asLines(data);
//        strings.sp
//        final var longs = asLongs(data);


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

