package com.numberengineer.aoc;


import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

import static com.numberengineer.aoc.AocPostCompetition.readFile;

class Aoc {
    public static void main(String[] args) {
        day11();
    }


    public static void day11() {
        TikTok tikTok = new TikTok(true);
        enum State {
            EMPTY('L'), OCCUPIED('#'), FLOOR('.'), AIR('~');
            final char c;

            State(char c) {
                this.c = c;
            }

            static State of(int i) {
                return switch (i) {
                    case 'L' -> EMPTY;
                    case '.' -> FLOOR;
                    case '#' -> OCCUPIED;
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                };
            }
        }
        class Grid implements Iterable<State> {
            final State[][] states;
            final int width;
            final int heigth;
            private final boolean transposed;

            public Grid(State[][] states, boolean transposed) {
                this.states = states;
                if (transposed) {
                    heigth = states.length;
                    width = states[1].length;
                } else {
                    width = states.length;
                    heigth = states[1].length;
                }
                this.transposed = transposed;
            }

            State of(int x, int y) {
                if (x < 0 || y < 0 || y >= heigth || x >= width) {
                    return State.AIR;
                }
                if (transposed) {
                    return states[y][x];
                } else {
                    return states[x][y];
                }
            }

            State[] visibleAdjacentOf(int x, int y) {
                State[] states = new State[8];
                int index = 0;
                record Vector(int dx, int dy) {

                }

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }
                        final var vector = new Vector(i, j);
                        int increment = 0;
                        State of;
                        do {
                            increment++;
                            of = of(x + increment * vector.dx, y + increment * vector.dy);
                        } while (of.equals(State.FLOOR));
                        states[index++] = of;
                    }
                }
                return states;
            }

            State[] adjacentsOf(int x, int y) {
                State[] states = new State[8];
                int index = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }
                        states[index++] = of(x + i, y + j);
                    }
                }
                return states;
            }

            State becomes(int x, int y, int tolerance, boolean nearbor) {
                final State[] states;
                if (nearbor) {
                    states = adjacentsOf(x, y);
                } else {
                    states = visibleAdjacentOf(x, y);
                }
                final var of = of(x, y);
                switch (of) {
                    case EMPTY -> {
                        final var count = Arrays.stream(states).filter(state -> state == State.OCCUPIED).count();
                        if (count == 0) {
                            return State.OCCUPIED;
                        } else {
                            return State.EMPTY;
                        }
                    }
                    case OCCUPIED -> {
                        final var count = Arrays.stream(states).filter(state -> state == State.OCCUPIED).count();
                        if (count >= tolerance) {
                            return State.EMPTY;
                        } else {
                            return State.OCCUPIED;
                        }
                    }
                    case FLOOR -> {
                        return State.FLOOR;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + of);
                }
            }

            @Override
            public Iterator<State> iterator() {
                return null;
            }

            @Override
            public void forEach(Consumer<? super State> action) {
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < heigth; j++) {
                        action.accept(of(i, j));
                    }
                }
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < heigth; i++) {
                    for (int j = 0; j < width; j++) {
                        sb.append(of(j, i).c);
                    }
                    sb.append('\n');
                }
                return sb.toString();
            }
        }
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;
        };
        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\day11.txt");
//        String data = "L.LL.LL.LL\n" +
//                "LLLLLLL.LL\n" +
//                "L.L.L..L..\n" +
//                "LLLL.LL.LL\n" +
//                "L.LL.LL.LL\n" +
//                "L.LLLLL.LL\n" +
//                "..L.L.....\n" +
//                "LLLLLLLLLL\n" +
//                "L.LLLLLL.L\n" +
//                "L.LLLLL.LL";
        final var split = Arrays.stream(data.split("\n")).map(String::chars)
                .map(intStream -> intStream.mapToObj(State::of).toArray(State[]::new)).toArray(State[][]::new);
        Grid grid = new Grid(split, true);

        boolean changed;
        do {
            State[][] states = new State[grid.width][grid.heigth];
            changed = false;
            for (int i = 0; i < grid.width; i++) {
                for (int j = 0; j < grid.heigth; j++) {
                    final var becomes = grid.becomes(i, j, 4, true);
                    if (grid.of(i, j) != becomes) {
                        changed = true;
                    }
                    states[i][j] = becomes;
                }
            }
            grid = new Grid(states, false);
        } while (changed);
        grid.forEach(state -> {
            if (state == State.OCCUPIED) {
                o.part1++;
            }
        });
        System.out.println("p1");
        grid = new Grid(split, true);
        do {
            State[][] states = new State[grid.width][grid.heigth];
            changed = false;
            for (int i = 0; i < grid.width; i++) {
                for (int j = 0; j < grid.heigth; j++) {
                    final var becomes = grid.becomes(i, j, 5, false);
                    if (grid.of(i, j) != becomes) {
                        changed = true;
                    }
                    states[i][j] = becomes;
                }
            }
            grid = new Grid(states, false);
        } while (changed);
        grid.forEach(state -> {
            if (state == State.OCCUPIED) {
                o.part2++;
            }
        });
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

}