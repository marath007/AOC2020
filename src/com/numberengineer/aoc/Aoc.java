package com.numberengineer.aoc;


import java.util.Arrays;
import java.util.HashSet;

import static com.numberengineer.aoc.Utils.*;

class Aoc {

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
                for (Coordinate state : states) {
                    coords.remove(state);
                }
                for (Coordinate state : coords) {
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

        var grid = new Grid();
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length(); j++) {
                if (strings[i].charAt(j) == '#') {
                    grid.states.add(new Coordinate(j, i, 0));
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            grid = grid.evolve();
        }
        o.part1 = grid.states.size();
        grid = new Grid();
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length(); j++) {
                if (strings[i].charAt(j) == '#') {
                    grid.states.add(new Coordinate(j, i, 0, 0));
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            grid = grid.evolve();
        }
        o.part2 = grid.states.size();
        int[] series = new int[]{10, 11, 12};
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
        System.out.println(grid.states.size());
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

//    public static void day17() {
//        TikTok tikTok = new TikTok(true);
//        final var day = getDay();
////        boolean testMode = true;
//        boolean testMode = false;
//        String data = ".#.\n" +
//                "..#\n" +
//                "###";
//        if (!testMode) {
//            data = getData(day);
//        }
//        enum State {
//            ACTIVE('#'), INACTIVE('.');
//            final char c;
//
//            State(char c) {
//                this.c = c;
//            }
//
//            public static State of(String s) {
//                if (s.equals(".")) {
//                    return INACTIVE;
//                } else {
//                    return ACTIVE;
//                }
//            }
//        }
//        record Coordinate(int x, int y, int z, int w) {
//        }
//        class Grid implements Iterable<State> {
//
//
//            HashMap<Coordinate, State> states = new HashMap<>();
//
//            public Grid() {
//
//            }
//
//            Coordinate getMinCoordinate() {
//                int minX = 0;
//                int minY = 0;
//                int minZ = 0;
//                int minW = 0;
//                final var entries = states.keySet();
//                final var iterator = entries.iterator();
//                while (iterator.hasNext()) {
//                    final var next = iterator.next();
//                    minX = Math.min(minX, next.x);
//                    minY = Math.min(minY, next.y);
//                    minZ = Math.min(minZ, next.z);
//                    minW = Math.min(minW, next.w);
//                }
//                return new Coordinate(minX,
//                        minY,
//                        minZ,
//                        minW);
//
//            }
//
//            Coordinate getMaxCoordinate() {
//                int maxX = 0;
//                int maxY = 0;
//                int maxZ = 0;
//                int maxW = 0;
//                final var entries = states.keySet();
//                final var iterator = entries.iterator();
//                while (iterator.hasNext()) {
//                    final var next = iterator.next();
//                    maxX = Math.max(maxX, next.x);
//                    maxY = Math.max(maxY, next.y);
//                    maxZ = Math.max(maxZ, next.z);
//                    maxW = Math.max(maxW, next.w);
//                }
//                return new Coordinate(maxX,
//                        maxY,
//                        maxZ,
//                        maxW);
//
//            }
//
//            State of(int x, int y, int z, int w) {
//                final var state = states.get(new Coordinate(x, y, z, w));
//                if (state == null) {
//                    return State.INACTIVE;
//                }
//                return state;
//            }
//
//
//            ArrayList<State> adjacentsOf(int x, int y, int z, int w) {
//                ArrayList<State> states = new ArrayList();
//                for (int i = -1; i <= 1; i++) {
//                    for (int j = -1; j <= 1; j++) {
//                        for (int k = -1; k <= 1; k++) {
//                            for (int l = -1; l <= 1; l++) {
//                                if (i == 0 && j == 0 && k == 0 && l == 0) {
//                                    continue;
//                                }
//                                states.add(of(x + i, y + j, z + k, w + l));
//                            }
//                        }
//                    }
//                }
//                return states;
//            }
//
//            State becomes(int x, int y, int z, int w) {
//                ArrayList<State> states;
////                if (nearbor) {
//                states = adjacentsOf(x, y, z, w);
////                } else {
////                    states = visibleAdjacentOf(x, y);
////                }
//                final var of = of(x, y, z, w);
//                switch (of) {
//                    case INACTIVE -> {
//                        final var count = states.stream().filter(state -> state == State.ACTIVE).count();
//                        if (count == 3) {
//                            return State.ACTIVE;
//                        } else {
//                            return State.INACTIVE;
//                        }
//                    }
//                    case ACTIVE -> {
//                        final var count = states.stream().filter(state -> state == State.ACTIVE).count();
//                        if (count == 2 || count == 3) {
//                            return State.ACTIVE;
//                        } else {
//                            return State.INACTIVE;
//                        }
//                    }
//                    default -> throw new IllegalStateException("Unexpected value: " + of);
//                }
//            }
//
//            @Override
//            public Iterator<State> iterator() {
//                return null;
//            }
//
//            @Override
//            public void forEach(Consumer<? super State> action) {
//                states.values().stream().forEach(action);
//            }
//
//            @Override
//            public String toString() {
//                StringBuilder sb = new StringBuilder();
//
//                final var minCoordinate = getMinCoordinate();
//                final var maxCoordinate = getMaxCoordinate();
//                for (int l = minCoordinate.z; l <= maxCoordinate.z; l++) {
//                    for (int m = minCoordinate.w; m <= maxCoordinate.w; m++) {
//                        sb.append("z=" + l + ", w=" + m + "\n");
//                        for (int k = minCoordinate.y; k <= maxCoordinate.y; k++) {
//                            for (int j = minCoordinate.x; j <= maxCoordinate.x; j++) {
//                                sb.append(of(j, k, l, m).c);
//                            }
//                            sb.append('\n');
//                        }
//                        sb.append('\n');
//                    }
//                }
//
//                return sb.toString();
//            }
//        }
//        var o = new Object() {
//            long part1 = 0;
//            long part2 = 0;
//        };
//        final var strings = asLines(data);
////        final var longs = asLongs(data);
//
//        var grid = new Grid();
//        for (int i = 0; i < strings.length; i++) {
//            for (int j = 0; j < strings[i].length(); j++) {
//                grid.states.put(new Coordinate(i, j, 0, 0), State.of(strings[i].charAt(j) + ""));
//            }
//        }
//        for (int i = 0; i < 6; i++) {
//            System.out.println(grid);
//            Grid newGrid = new Grid();
//            Grid finalGrid = grid;
//            final var minCoordinate = grid.getMinCoordinate();
//            final var maxCoordinate = grid.getMaxCoordinate();
//
//            for (int j = minCoordinate.x - 1; j <= maxCoordinate.x + 1; j++) {
//                for (int k = minCoordinate.y - 1; k <= maxCoordinate.y + 1; k++) {
//                    for (int l = minCoordinate.z - 1; l <= maxCoordinate.z + 1; l++) {
//                        for (int m = minCoordinate.w - 1; m <= maxCoordinate.w + 1; m++) {
//                            final var becomes = finalGrid.becomes(j, k, l, m);
//                            if (becomes != State.INACTIVE) {
//                                newGrid.states.put(new Coordinate(j, k, l, m), becomes);
//                            }
//                        }
//                    }
//                }
//            }
//            System.out.println(newGrid.states.size());
//            grid = newGrid;
////
//        }
//        grid.forEach(s -> {
//            if (s == State.ACTIVE) {
//                o.part2++;
//            }
//        });
//
//        endOfWork(tikTok, day, testMode, o.part1, o.part2);
//    }

    public static void dayN() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
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