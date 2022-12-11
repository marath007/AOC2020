package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.function.Function;

import static com.numberengineer.aoc.Utils.*;

public class Day11 {
    public static void day11() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "Monkey 0:\n" +
                "  Starting items: 79, 98\n" +
                "  Operation: new = old * 19\n" +
                "  Test: divisible by 23\n" +
                "    If true: throw to monkey 2\n" +
                "    If false: throw to monkey 3\n" +
                "\n" +
                "Monkey 1:\n" +
                "  Starting items: 54, 65, 75, 74\n" +
                "  Operation: new = old + 6\n" +
                "  Test: divisible by 19\n" +
                "    If true: throw to monkey 2\n" +
                "    If false: throw to monkey 0\n" +
                "\n" +
                "Monkey 2:\n" +
                "  Starting items: 79, 60, 97\n" +
                "  Operation: new = old * old\n" +
                "  Test: divisible by 13\n" +
                "    If true: throw to monkey 1\n" +
                "    If false: throw to monkey 3\n" +
                "\n" +
                "Monkey 3:\n" +
                "  Starting items: 74\n" +
                "  Operation: new = old + 3\n" +
                "  Test: divisible by 17\n" +
                "    If true: throw to monkey 0\n" +
                "    If false: throw to monkey 1";
        if (!testMode) {
            data = getData(day, "2022");
        }
        var o = new Object() {
            int x = 1;
            long part1 = 0;
            long part2 = 0;
        };
        final var split = data.split("\n\n");
        ArrayList<Monkey> monkeys = new ArrayList<>();
        for (String s : split) {
            monkeys.add(new Monkey(s));
        } final var longs1 = monkeys.stream().mapToLong(m -> m.modulator).toArray();
        long otherModulator=1;
        for (long l : longs1) {
            otherModulator*=l;
        }
        for (int i = 0; i < 20; i++) {
            for (Monkey monkey : monkeys) {
                monkey.monkeyAround(monkeys, 3, otherModulator);
            }
        }
        var longs = monkeys.stream().mapToLong(m -> m.itemsInspecitons).sorted().toArray();
        o.part1 = longs[longs.length - 2] * longs[longs.length - 1];
        monkeys.clear();
        for (String s : split) {
            monkeys.add(new Monkey(s));
        }


        for (int i = 0; i < 10000; i++) {
            for (Monkey monkey : monkeys) {
                monkey.monkeyAround(monkeys, 1,otherModulator);
            }
        }

        longs = monkeys.stream().mapToLong(m -> m.itemsInspecitons).sorted().toArray();
        o.part2 = longs[longs.length - 2] * longs[longs.length - 1];
        final var strings = asLines(data);

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    static class Monkey {
        int itemsInspecitons = 0;
        ArrayDeque<Long> items = new ArrayDeque<>();
        Function<Long, Long> operation;
        long modulator;
        int trueMonkey;
        int falseMonkey;

        public Monkey(String data) {
            final var split = data.trim().split("\n");
            final var items = split[1].split(":")[1].split(",");
            for (String item : items) {
                this.items.add(Long.parseLong(item.trim()));
            }
            final var ops = split[2].split("=")[1];
            if (ops.contains("+")) {
                final var old = Long.parseLong(ops.replace("+", "").replace("old", "").trim());
                operation = (i) -> i + old;
            } else if (ops.contains("*")) {
                final var split1 = ops.split("\\*");

                if (split1[0].contains("old") && split1[1].contains("old")) {
                    operation = (i) -> i * i;
                } else {
                    final var old = Long.parseLong(ops.replace("*", "").replace("old", "").trim());
                    operation = (i) -> i * old;
                }
            }
            modulator = Long.parseLong(split[3].split("by")[1].trim());
            trueMonkey = Integer.parseInt(split[4].split("monkey ")[1].trim());
            falseMonkey = Integer.parseInt(split[5].split("monkey ")[1].trim());
        }

        public void monkeyAround(ArrayList<Monkey> monkeys, int i, long otherModulator) {
            while (!items.isEmpty()) {
                itemsInspecitons++;
                final var poll = items.poll();
                long nowIs = operation.apply(poll) / i;
                nowIs%=otherModulator;
                if (nowIs % modulator == 0) {
                    monkeys.get(trueMonkey).items.add(nowIs);
                } else {
                    monkeys.get(falseMonkey).items.add(nowIs);
                }
            }
        }
    }
}

