package com.numberengineer.aoc;

import java.util.Arrays;
import java.util.HashSet;

import static com.numberengineer.aoc.Utils.*;

public class AocExperiment {
    public static void main(String[] args) {
        day17();
    }

    public static void day17() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean testMode = true;
//        boolean testMode = false;
        String data = ".#.\n" +
                "..#\n" +
                "###";
        if (!testMode) {
            data = getData(day);
        }
        record Coordinate(int... ints) {
            @Override
            public int hashCode() {
                return Arrays.hashCode(ints);
            }

            @Override
            public String toString() {
                return Arrays.toString(ints);
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Coordinate that = (Coordinate) o;
                return Arrays.equals(ints, that.ints);
            }
        }
        class Grid {


            HashSet<Coordinate> states = new HashSet<>();

            public Grid() {

            }

            boolean exist(int... ints) {
                return states.contains(new Coordinate(ints));
            }

            private int countAdjacent(int index, Coordinate test, Coordinate ref, HashSet<Coordinate> coords) {
                if (index == 0) {

                    if (test.equals(ref)) {
                        return 0;
                    }
//                    if (exist(test.ints)) {
//                        System.out.println(test);
//                    }
                    coords.add(test);
                    return exist(test.ints) ? 1 : 0;
                } else {
                    int sum = 0;
                    for (int i = -1; i <= 1; i++) {
                        final var ints = Arrays.copyOf(test.ints, test.ints.length);
                        ints[index - 1] = ref.ints[index - 1] + i;
                        Coordinate newCoord = new Coordinate(ints);
                        sum += countAdjacent(index - 1, newCoord, ref, coords);
                    }
                    return sum;
                }
            }

            public Grid evolve() {
                HashSet<Coordinate> coords = new HashSet<>();
                Grid grid = new Grid();
                for (Coordinate state : states) {
//                    System.out.println("Next to " + state);
                    final var actives = countAdjacent(state.ints.length, state, state, coords);
//                    System.out.println("Sum: " + actives);
                    if (actives == 3 || actives == 2) {
                        grid.states.add(state);
                    }
                }
//                for (Coordinate state : states) {
//                    coords.remove(state);
//                }
                for (Coordinate state : coords) {
                    if (states.contains(state)) {
                        continue;
                    }
                    final var actives = countAdjacent(state.ints.length, state, state, new HashSet<>());
                    if (actives == 3) {
                        grid.states.add(state);
                    }
                }
                return grid;
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var strings = asLines(data);
//        final var longs = asLongs(data);

        int[] series = new int[]{6,7};
        System.out.println("Compute time, Dim, Gen, Actives");
        Arrays.stream(series).parallel().forEach(value -> {
            TikTok tikTok1 = new TikTok(true);
            Grid protoGrid = new Grid();
            for (int i = 0; i < strings.length; i++) {
                for (int j = 0; j < strings[i].length(); j++) {
                    if (strings[i].charAt(j) == '#') {
                        int[] ints = new int[value];
                        ints[0] = j;
                        ints[1] = i;
                        protoGrid.states.add(new Coordinate(ints));
                    }
                }
            }
            int i = 0;
            while (true) {
                tikTok1.tic();
                protoGrid = protoGrid.evolve();
                tikTok1.toc(System.out, ", " + value + ", " + ++i + ", " + protoGrid.states.size());
            }
        });
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

