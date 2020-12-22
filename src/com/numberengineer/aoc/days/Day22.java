package com.numberengineer.aoc.days;

import com.numberengineer.aoc.TikTok;

import java.util.*;
import java.util.regex.Pattern;

import static com.numberengineer.aoc.Utils.*;

public class Day22 {
    public static void day22() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "Player 1:\n" +
                "9\n" +
                "2\n" +
                "6\n" +
                "3\n" +
                "1\n" +
                "\n" +
                "Player 2:\n" +
                "5\n" +
                "8\n" +
                "4\n" +
                "7\n" +
                "10";
        if (!testMode) {
            data = getData(day);
        }
        class Player {
            final String name;
            final Deque<Integer> cards = new ArrayDeque<>();
            boolean instantDeath = false;

            Player(String name, int[] cards) {
                this.name = name;
                Arrays.stream(cards).boxed().forEach(this.cards::add);
            }

            boolean alive() {
                return !instantDeath && cards.size() != 0;
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
        class CrabGame {
            Player fightStd(Player me, Player aoc) {
                while (me.alive() && aoc.alive()) {
                    final var _me = me.cards.pollFirst();
                    final var _aoc = aoc.cards.pollFirst();
                    if (_me > _aoc) {
                        me.cards.addLast(_me);
                        me.cards.addLast(_aoc);
                    } else {
                        aoc.cards.addLast(_aoc);
                        aoc.cards.addLast(_me);
                    }
//                    System.out.println(_me + " vs " + _aoc);
                }
                return me.alive() ? me : aoc;
            }

            Player fightRecursive(Player me, Player aoc) {
                final var recursionLevel = Arrays.stream(Thread.currentThread().getStackTrace()).filter(stackTraceElement -> stackTraceElement.getMethodName().equals("fightRecursive")).count();
//                System.out.println("Recursion " + recursionLevel);
                HashSet<String> gameStates = new HashSet<>();
                while (me.alive() && aoc.alive()) {
                    String state = me + "" + aoc;
                    if (gameStates.contains(state)) {
                        aoc.instantDeath = true;
//                        System.out.println("instant Death");
                        continue;
                    } else {
                        gameStates.add(state);
                    }
                    final var _me = me.cards.pollFirst();
                    final var _aoc = aoc.cards.pollFirst();
                    if (me.cards.size() != 0 && aoc.cards.size() != 0 && _me <= me.cards.size() && _aoc <= aoc.cards.size()) {
                        Deque<Integer> meCards = new ArrayDeque<>(me.cards);
                        Deque<Integer> aocCards = new ArrayDeque<>(aoc.cards);
                        while (me.cards.size() > _me) {
                            me.cards.pollLast();
                        }
                        while (aoc.cards.size() > _aoc) {
                            aoc.cards.pollLast();
                        }
                        var subWinner = fightRecursive(me, aoc);
                        me.instantDeath = false;
                        aoc.instantDeath = false;
                        me.cards.clear();
                        aoc.cards.clear();
                        me.cards.addAll(meCards);
                        aoc.cards.addAll(aocCards);
                        if (subWinner == me) {
                            me.cards.addLast(_me);
                            me.cards.addLast(_aoc);
                        } else {
                            aoc.cards.addLast(_aoc);
                            aoc.cards.addLast(_me);
                        }

                    } else {
                        if (_me > _aoc) {
                            me.cards.addLast(_me);
                            me.cards.addLast(_aoc);
                        } else {
                            aoc.cards.addLast(_aoc);
                            aoc.cards.addLast(_me);
                        }
//                        System.out.println(_me + " vs " + _aoc);
                    }
                }
                return me.alive() ? me : aoc;
            }
        }
        Player me;
        Player aoc;
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;


        };
        {
            CrabGame crabGame = new CrabGame();
            final var players = Arrays.stream(data.split("\n\n")).map(s -> {
                final var values = s.split("\n");
                return new Player(values[0], Arrays.stream(values).skip(1).mapToInt(Integer::parseInt).toArray());
            }).toArray(Player[]::new);
            me = players[0];
            aoc = players[1];
            Player won = crabGame.fightStd(me, aoc);
//            System.out.println(won == me ? "I won :)" : "I lost :(");
            o.part1 = won.result();
        }
        {
            CrabGame crabGame = new CrabGame();
            final var players = Arrays.stream(data.split("\n\n")).map(s -> {
                final var values = s.split("\n");
                return new Player(values[0], Arrays.stream(values).skip(1).mapToInt(Integer::parseInt).toArray());
            }).toArray(Player[]::new);
            me = players[0];
            aoc = players[1];
            Player won = crabGame.fightRecursive(me, aoc);
//            System.out.println(won == me ? "I won :)" : "I lost :(");
            o.part2 = won.result();
        }


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

}

