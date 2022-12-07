package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Pattern;

import static com.numberengineer.aoc.Utils.*;

public class Day5 {
    public static void day5() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "    [D]    \n" +
                "[N] [C]    \n" +
                "[Z] [M] [P]\n" +
                " 1   2   3 \n" +
                "\n" +
                "move 1 from 2 to 1\n" +
                "move 3 from 1 to 3\n" +
                "move 2 from 2 to 1\n" +
                "move 1 from 1 to 2";
        if (!testMode) {
            data = getData(day, "2022");
        }
        var o = new Object() {
            ArrayList<Stack<String>> world = new ArrayList<>();
            String part1 = "";
            String part2 = "";

            void printTower(ArrayList<Stack<String>> stacks) {
                String s = "";
                s += " ";
                for (int i = 0; i < stacks.size(); i++) {
                    s += (i + 1);
                    s += "   ";
                }
                s = s.substring(0, s.length() - 2);

                final var towerCnt = stacks.stream().mapToInt(Vector::size).max().getAsInt();
                for (int i = 0; i < towerCnt; i++) {
                    String row = "";
                    for (int j = 0; j < stacks.size(); j++) {
                        final var strings = stacks.get(j);
                        if (strings.size() > i) {
                            row += "[" + strings.get(i) + "] ";
                        } else {
                            row += "    ";
                        }
                    }
                    row += "\n";
                    s = row + s;
                }
                System.out.println(s);
                System.out.println();
            }
        };

        final var split = data.split("\n\n");
        final var crates = split[0];
        final var cratesRows = crates.split("\n");
        final var indexes = Arrays.stream(cratesRows[cratesRows.length - 1].split(" ")).filter(s -> s.length() > 0).toArray(String[]::new);


        o.world = new ArrayList<Stack<String>>();

        for (int i = 0; i < indexes.length; i++) {
            o.world.add(new Stack<>());
        }
        for (int i = cratesRows.length - 2; i >= 0; i--) {
            final var length = cratesRows[i].length();
            int w = 0;
            for (int j = 1; j < length; j += 4) {
                final var e = String.valueOf(cratesRows[i].charAt(j));
                if (!e.equals(" ")) {
                    o.world.get(w).add(e);
                }
                w++;
            }
        }
        o.printTower(o.world);

        final var compile = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");


        final var strings = asLines(split[1]);
        Arrays.stream(strings).forEach(s -> {
            final var matcher = compile.matcher(s);
            final var b = matcher.find();
            if (!b) {
                System.out.println("Error");
            }
            final var move = Integer.parseInt(matcher.group(1));
            final var from = Integer.parseInt(matcher.group(2));
            final var to = Integer.parseInt(matcher.group(3));
            for (int i = 0; i < move; i++) {
                o.world.get(to - 1).add(o.world.get(from - 1).pop());
            }
            o.printTower(o.world);
        });

        for (int i = 0; i < o.world.size(); i++) {
            o.part1 += o.world.get(i).pop();
        }

        o.world = new ArrayList<Stack<String>>();

        for (int i = 0; i < indexes.length; i++) {
            o.world.add(new Stack<>());
        }
        for (int i = cratesRows.length - 2; i >= 0; i--) {
            final var length = cratesRows[i].length();
            int w = 0;
            for (int j = 1; j < length; j += 4) {
                final var e = String.valueOf(cratesRows[i].charAt(j));
                if (!e.equals(" ")) {
                    o.world.get(w).add(e);
                }
                w++;
            }
        }
        o.printTower(o.world);


        Arrays.stream(strings).forEach(s -> {
            final var matcher = compile.matcher(s);
            final var b = matcher.find();
            if (!b) {
                System.out.println("Error");
            }
            final var botStack = new Stack<String>();
            final var move = Integer.parseInt(matcher.group(1));
            final var from = Integer.parseInt(matcher.group(2));
            final var to = Integer.parseInt(matcher.group(3));
            for (int i = 0; i < move; i++) {
                botStack.add(o.world.get(from - 1).pop());
            }

            while (!botStack.isEmpty()) {
                o.world.get(to - 1).add(botStack.pop());
            }
            o.printTower(o.world);
        });

        for (int i = 0; i < o.world.size(); i++) {
            o.part2 += o.world.get(i).pop();
        }


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

