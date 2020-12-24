package com.numberengineer.aoc.days;

import com.numberengineer.aoc.TikTok;

import java.util.*;

import static com.numberengineer.aoc.Utils.*;

public class Day22 {
    public static void day22() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean testMode = true;
//        boolean testMode = false;
        String data = "Player 1:\n" +
                "43\n" +
                "19\n" +
                "\n" +
                "Player 2:\n" +
                "2\n" +
                "29\n" +
                "14";
        if (!testMode) {
            data = getData(day);
        }
        class Player {
            final String name;
            Deque<Integer> cards = new ArrayDeque<>();


            Player(String name, int[] cards) {
                this.name = name;
                Arrays.stream(cards).boxed().forEach(this.cards::add);
            }

            boolean alive() {
                return cards.size() != 0;
            }

            public long result() {
                long i = 0;
                long score = 0;
                while (cards.size() != 0) {
                    score += cards.pollLast() * ++i;
                }
                return score;
            }

            @Override
            public String toString() {
                return "Player{" +
                        "name='" + name + '\'' +
                        ", cards=" + cards +
                        '}';
            }
        }
        class Combat {
            Player fightStd(Player me, Player crab) {
                while (me.alive() && crab.alive()) {
                    final var _me = me.cards.pollFirst();
                    final var _aoc = crab.cards.pollFirst();
                    if (_me > _aoc) {
                        me.cards.addLast(_me);
                        me.cards.addLast(_aoc);
                    } else {
                        crab.cards.addLast(_aoc);
                        crab.cards.addLast(_me);
                    }
//                    System.out.println(_me + " vs " + _aoc);
                }
                return me.alive() ? me : crab;
            }

            Player fightRecursive(Player me, Player crab) {
                final var recursionLevel = Arrays.stream(Thread.currentThread().getStackTrace()).filter(stackTraceElement -> stackTraceElement.getMethodName().equals("fightRecursive")).count();
                System.out.println("Recursion " + recursionLevel);
                HashSet<String> gameStates = new HashSet<>();
                while (me.alive() && crab.alive()) {
                    String state = me + "" + crab;
                    if (gameStates.contains(state)) {
                        System.out.println("Instant death");
                        return me;
                    } else {
                        gameStates.add(state);
                    }
                    final var _me = me.cards.pollFirst();
                    final var _aoc = crab.cards.pollFirst();
                    if (me.cards.size() != 0 && crab.cards.size() != 0 && _me <= me.cards.size() && _aoc <= crab.cards.size()) {
                        Deque<Integer> meCards = new ArrayDeque<>(me.cards);
                        Deque<Integer> aocCards = new ArrayDeque<>(crab.cards);
                        while (me.cards.size() > _me) {
                            me.cards.pollLast();
                        }
                        while (crab.cards.size() > _aoc) {
                            crab.cards.pollLast();
                        }
                        var subWinner = fightRecursive(me, crab);
                        me.cards=meCards;
                        crab.cards=aocCards;
                        if (subWinner == me) {
                            me.cards.addLast(_me);
                            me.cards.addLast(_aoc);
                        } else {
                            crab.cards.addLast(_aoc);
                            crab.cards.addLast(_me);
                        }

                    } else {
                        if (_me > _aoc) {
                            me.cards.addLast(_me);
                            me.cards.addLast(_aoc);
                        } else {
                            crab.cards.addLast(_aoc);
                            crab.cards.addLast(_me);
                        }
                        System.out.println(_me + " vs " + _aoc);
                    }
                }
                return me.alive() ? me : crab;
            }
        }
        Player me;
        Player crab;
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;


        };
        {
            Combat combat = new Combat();
            final var players = Arrays.stream(data.split("\n\n")).map(s -> {
                final var values = s.split("\n");
                return new Player(values[0], Arrays.stream(values).skip(1).mapToInt(Integer::parseInt).toArray());
            }).toArray(Player[]::new);
            me = players[0];
            crab = players[1];
            Player won = combat.fightStd(me, crab);
            System.out.println(won == me ? "I won :)" : "I lost :(");
            o.part1 = won.result();
        }
        {
            Combat combat = new Combat();
            final var players = Arrays.stream(data.split("\n\n")).map(s -> {
                final var values = s.split("\n");
                return new Player(values[0], Arrays.stream(values).skip(1).mapToInt(Integer::parseInt).toArray());
            }).toArray(Player[]::new);
            me = players[0];
            crab = players[1];
            Player won = combat.fightRecursive(me, crab);
            System.out.println(won == me ? "I won :)" : "I lost :(");
            o.part2 = won.result();
        }


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

}

