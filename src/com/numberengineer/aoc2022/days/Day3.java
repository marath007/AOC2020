package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import java.util.Arrays;

import static com.numberengineer.aoc.Utils.*;

public class Day3 {
    public static void day3() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;/**/
        boolean testMode = false;
        String data = "vJrwpWtwJgWrhcsFMMfFFhFp\n" +
                "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\n" +
                "PmmdzqPrVvPwwTWBwg\n" +
                "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\n" +
                "ttgJtRGJQctTZtZT\n" +
                "CrZsJsPPZsGzwwsLwLmpwMDw";
        if (!testMode) {
            data = getData(day, "2022");
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var strings = asLines(data);
        Arrays.stream(strings).forEach(s -> {
            final var left = s.substring(0, s.length() / 2);
            final var right = s.substring(s.length() / 2);
            for (int i = 0; i < left.length(); i++) {
                if (right.contains("" + left.charAt(i))) {
                    if ((left.charAt(i) + "").toLowerCase().equals(left.charAt(i) + "")) {
                        o.part1 += left.charAt(i) - 'a' + 1;
                    } else {
                        o.part1 += left.charAt(i) - 'A' + 27;
                    }
                    break;
                }
            }
        });
        for (int i = 0; i < strings.length; i += 3) {
            for (int k = 0; k < strings[i].length(); k++) {
                final var c = strings[i].charAt(k);
                if (strings[i + 1].contains(c + "") && strings[i + 2].contains(c + "")) {
                    if ((c + "").toLowerCase().equals(c + "")) {
                        o.part2 += c - 'a' + 1;
                    } else {
                        o.part2 += c - 'A' + 27;
                    }
                    break;
                }
            }
        }


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

