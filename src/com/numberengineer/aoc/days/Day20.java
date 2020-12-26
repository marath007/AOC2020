package com.numberengineer.aoc.days;

import com.numberengineer.aoc.TikTok;

import java.util.*;
import java.util.function.LongBinaryOperator;
import java.util.regex.Pattern;

import static com.numberengineer.aoc.Utils.*;

public class Day20 {
    public static void day20() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = """
                Tile 2311:
                ..##.#..#.
                ##..#.....
                #...##..#.
                ####.#...#
                ##.##.###.
                ##...#.###
                .#.#.#..##
                ..#....#..
                ###...#.#.
                ..###..###
                                
                Tile 1951:
                #.##...##.
                #.####...#
                .....#..##
                #...######
                .##.#....#
                .###.#####
                ###.##.##.
                .###....#.
                ..#.#..#.#
                #...##.#..
                                
                Tile 1171:
                ####...##.
                #..##.#..#
                ##.#..#.#.
                .###.####.
                ..###.####
                .##....##.
                .#...####.
                #.##.####.
                ####..#...
                .....##...
                                
                Tile 1427:
                ###.##.#..
                .#..#.##..
                .#.##.#..#
                #.#.#.##.#
                ....#...##
                ...##..##.
                ...#.#####
                .#.####.#.
                ..#..###.#
                ..##.#..#.
                                
                Tile 1489:
                ##.#.#....
                ..##...#..
                .##..##...
                ..#...#...
                #####...#.
                #..#.#.#.#
                ...#.#.#..
                ##.#...##.
                ..##.##.##
                ###.##.#..
                                
                Tile 2473:
                #....####.
                #..#.##...
                #.##..#...
                ######.#.#
                .#...#.#.#
                .#########
                .###.#..#.
                ########.#
                ##...##.#.
                ..###.#.#.
                                
                Tile 2971:
                ..#.#....#
                #...###...
                #.#.###...
                ##.##..#..
                .#####..##
                .#..####.#
                #..#.#..#.
                ..####.###
                ..#.#.###.
                ...#.#.#.#
                                
                Tile 2729:
                ...#.#.#.#
                ####.#....
                ..#.#.....
                ....#..#.#
                .##..##.#.
                .#.####...
                ####.#.#..
                ##.####...
                ##..#.##..
                #.##...##.
                                
                Tile 3079:
                #.#.#####.
                .#..######
                ..#.......
                ######....
                ####.#..#.
                .#...#.##.
                #.#####.##
                ..#.###...
                ..#.......
                ..#.###...""";
        String seaMonster = """
                Monster: 1337
                                  #\s
                #    ##    ##    ###
                 #  #  #  #  #  #  \s""";
        if (!testMode) {
            data = getData(day);
        }
        record Counter() {
            static int rotateCnt = 0;
            static int flip = 0;
        }
        record Side(boolean... state) {
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Side side = (Side) o;
                return Arrays.equals(state, side.state);
            }

            @Override
            public int hashCode() {
                return Arrays.hashCode(state);
            }
        }
        class Tile {
            final long tileID;
            final int size;
            boolean[][] states;

            Tile(String s) {
                final var split = s.split("\n");
                Pattern pattern = Pattern.compile("([\\d]+)");
                final var matcher = pattern.matcher(split[0]);
                matcher.find();
                tileID = Integer.parseInt(matcher.group(1));
                size = split.length - 1;
                states = new boolean[size][split[1].length()];
                for (int i = 1; i < split.length; i++) {
                    final var s1 = split[i];
                    for (int j = 0; j < s1.length(); j++) {
                        states[i - 1][j] = s1.charAt(j) == '#';
                    }
                }
            }

            Tile(Tile[][] tiles) {
                size = Arrays.stream(tiles).mapToInt(a -> a[0].size - 2).sum();
                states = new boolean[size][size];
                tileID = -1;
                final var trimmedSize = tiles[0][0].states.length - 2;
                for (int i = 0; i < tiles.length; i++) {
                    for (int j = 0; j < tiles.length; j++) {
                        final var tile = tiles[i][j];
                        for (int k = 0; k < trimmedSize; k++) {
                            for (int l = 0; l < trimmedSize; l++) {
                                states[trimmedSize * j + k][trimmedSize * i + l] = tile.states[k + 1][l + 1];
                            }
                        }
                    }
                }

            }

            int activeTiles() {
                return Arrays.stream(states).mapToInt(b -> {
                    int cnt = 0;
                    for (int i = 0; i < b.length; i++) {
                        cnt += b[i] ? 1 : 0;
                    }
                    return cnt;
                }).sum();
            }

            boolean[] rightSide() {
                boolean[] right = new boolean[size];
                for (int i = 0; i < size; i++) {
                    right[i] = states[i][size - 1];
                }
                return right;
            }

            boolean[] leftSide() {
                boolean[] left = new boolean[size];
                for (int i = 0; i < size; i++) {
                    left[i] = states[i][0];
                }
                return left;
            }

            boolean[] topSide() {
                boolean[] top = new boolean[size];
                for (int i = 0; i < size; i++) {
                    top[i] = states[0][i];
                }
                return top;
            }

            boolean[] bottomSide() {
                boolean[] bottom = new boolean[size];
                for (int i = 0; i < size; i++) {
                    bottom[i] = states[size - 1][i];
                }
                return bottom;
            }

            void rotate() {
                final var booleans = new boolean[size][size];
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        booleans[j][i] = states[size - 1 - i][j];
                    }
                }
                this.states = booleans;
                Counter.rotateCnt++;
            }

            void flip() {
                final var booleans = new boolean[size][size];
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        booleans[i][size - 1 - j] = states[i][j];
                    }
                }
                this.states = booleans;
                Counter.flip++;
            }

            @Override
            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Tile no: ").append(tileID).append('\n');
                for (int i = 0; i < states.length; i++) {
                    for (int j = 0; j < states[i].length; j++) {
                        stringBuilder.append(states[i][j] ? '#' : '.');
                    }
                    stringBuilder.append('\n');
                }
                return stringBuilder.toString();
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Tile tile = (Tile) o;
                return tileID == tile.tileID;
            }

            @Override
            public int hashCode() {
                return Objects.hash(tileID);
            }
        }
        class Picture {
            Tile[][] tileSquare;
            HashSet<Tile> tiles = new HashSet<>();
            ArrayList<Tile> usedTiles = new ArrayList<>();
            int size;
            Tile pictureTile;
            private HashMap<Side, Tile> tileHashMap;
            private HashMap<Tile, HashSet<Tile>> friends;
            private Tile[] corners;
            private Tile[] edges;
            private Tile[] centerpieces;

            public Picture(Tile[] tiles) {

                size = (int) Math.round(Math.sqrt(tiles.length));
                tileSquare = new Tile[size][size];
                this.tiles.addAll(Arrays.asList(tiles));
            }

            public Picture(HashMap<Tile, HashSet<Tile>> friends, HashMap<Side, Tile> tileHashMap, Tile[] corners, Tile[] edges, Tile[] centerpieces) {
                this.tileHashMap = tileHashMap;
                this.friends = friends;
                this.corners = corners;
                this.edges = edges;
                this.centerpieces = centerpieces;

            }

            void solveFaster() {
                tileSquare[0][0] = corners[0];
                int fail=0;
                while (!tileHashMap.containsKey(new Side(corners[0].rightSide())) && !tileHashMap.containsKey(new Side(corners[0].bottomSide()))) {
                    fail++;
                    if (fail==4){
                        tileSquare[0][0].flip();
                    }else {
                        tileSquare[0][0].rotate();
                    }
                }
            }

            void solveRightLeft() {

            }

            void solve() {
                int index = 0;
                int zeroIndex = 0;
                ArrayList<HashSet<Tile>> denied = new ArrayList<>();
                while (tiles.size() != 0) {
                    int finalX = index % size;
                    int finalY = index / size;
                    if (denied.size() <= index) {
                        denied.add(new HashSet<>());
                    }
                    final var deniedSet = denied.get(index);
                    final var any = this.tiles.stream().filter(tile -> !deniedSet.contains(tile)).filter(tile -> {
                        for (int i = 0; i < 2; i++) {
                            if (i != 0) {
                                tile.flip();
                            }
                            for (int j = 0; j < 4; j++) {
                                if (j != 0) {
                                    tile.rotate();
                                }
                                if (finalX != 0) {
                                    final var onTheLeft = tileSquare[finalX - 1][finalY];
                                    if (onTheLeft != null && !Arrays.equals(tile.leftSide(), onTheLeft.rightSide())) {
                                        continue;
                                    }
                                }
                                if (finalY != 0) {
                                    final var onTheTop = tileSquare[finalX][finalY - 1];
                                    if (onTheTop != null && !Arrays.equals(tile.topSide(), onTheTop.bottomSide())) {
                                        continue;
                                    }
                                }
                                if (finalX != size - 1) {
                                    final var onTheRight = tileSquare[finalX + 1][finalY];
                                    if (onTheRight != null && !Arrays.equals(tile.rightSide(), onTheRight.leftSide())) {
                                        continue;
                                    }
                                }
                                if (finalY != size - 1) {
                                    final var onTheBottom = tileSquare[finalX][finalY + 1];
                                    if (onTheBottom != null && !Arrays.equals(tile.bottomSide(), onTheBottom.topSide())) {
                                        continue;
                                    }
                                }
                                return true;
                            }
                        }
                        return false;
                    }).findAny();
                    if (any.isPresent()) {
                        final var o = any.get();
                        usedTiles.add(o);
                        this.tiles.remove(o);
                        tileSquare[finalX][finalY] = o;
                        index++;
                    } else {
                        denied.get(index).clear();
                        if (index == 1) {
                            final var remove = usedTiles.get(usedTiles.size() - 1);
                            switch (zeroIndex) {
                                case 0, 1, 2, 4, 5, 6 -> remove.rotate();
                                case 3 -> remove.flip();
                                case 7 -> {
                                    denied.get(index).clear();
                                    denied.get(--index).add(remove);
                                    zeroIndex = -1;
                                    tileSquare[index % size][index / size] = null;
                                    usedTiles.remove(remove);
                                    this.tiles.add(remove);
                                }
                            }
                            zeroIndex++;
                        } else {
                            final var remove = usedTiles.remove(usedTiles.size() - 1);
                            this.tiles.add(remove);
                            denied.get(--index).add(remove);
                            tileSquare[index % size][index / size] = null;
                        }
                    }
                }
                assemble();
            }

            private void assemble() {
                pictureTile = new Tile(tileSquare);
            }

            public long value() {
                return tileSquare[0][0].tileID * tileSquare[0][size - 1].tileID * tileSquare[size - 1][0].tileID * tileSquare[size - 1][size - 1].tileID;
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var tiles = Arrays.stream(data.split("\n\n")).map(Tile::new).toArray(Tile[]::new);
        HashMap<Side, Tile> tileHashMap = new HashMap<>();
        HashMap<Tile, HashSet<Tile>> friends = new HashMap<>();
        Arrays.stream(tiles).forEach(t -> friends.put(t, new HashSet<>()));
        Arrays.stream(tiles).forEach(tile -> {
            Tile[] tilez = new Tile[8];
            tilez[0] = tileHashMap.put(new Side(tile.leftSide()), tile);
            tilez[1] = tileHashMap.put(new Side(tile.rightSide()), tile);
            tilez[2] = tileHashMap.put(new Side(tile.topSide()), tile);
            tilez[3] = tileHashMap.put(new Side(tile.bottomSide()), tile);
            tile.rotate();
            tile.rotate();
            tilez[4] = tileHashMap.put(new Side(tile.leftSide()), tile);
            tilez[5] = tileHashMap.put(new Side(tile.rightSide()), tile);
            tilez[6] = tileHashMap.put(new Side(tile.topSide()), tile);
            tilez[7] = tileHashMap.put(new Side(tile.bottomSide()), tile);
            final var myList = friends.get(tile);
            Arrays.stream(tilez).filter(Objects::nonNull).distinct().forEach(t -> {
                myList.add(t);
                friends.get(t).add(tile);
//                System.out.println(tile.tileID + "->" + t.tileID);
            });
        });
        final var corners = friends.entrySet().stream().filter(entry -> entry.getValue().size() == 2).map(Map.Entry::getKey).toArray(Tile[]::new);
        final var edges = friends.entrySet().stream().filter(entry -> entry.getValue().size() == 3).map(Map.Entry::getKey).toArray(Tile[]::new);
        final var centerpieces = friends.entrySet().stream().filter(entry -> entry.getValue().size() == 4).map(Map.Entry::getKey).toArray(Tile[]::new);
        o.part1=Arrays.stream(corners).mapToLong(t->t.tileID).reduce((left, right) -> left*right).getAsLong();
        tikTok.toc(System.out," part 1");
//        System.out.println("fast part 1 "+ Arrays.stream(corners).mapToLong(t->t.tileID).reduce((left, right) -> left*right).getAsLong());
//        System.out.println(corners.length + " corners founds");
//        System.out.println(edges.length + " edges founds");
//        System.out.println(centerpieces.length + " centerpieces founds");
//        Picture picture = new Picture(friends, tileHashMap, corners, edges, centerpieces);
        Picture picture = new Picture(tiles);
//        picture.solveFaster();
        tikTok.toc(System.out, "before Solve");
        picture.solve();
        tikTok.toc(System.out, "after Solve");
//        o.part1 = picture.value();
        final var pictureTile = picture.pictureTile;
        Tile seaM = new Tile(seaMonster);
//        System.out.println(seaM.toString());

        int monsterCount = 0;
        for (int i = 0; i < 2; i++) {
            if (i != 0) {
                pictureTile.flip();
            }
            for (int j = 0; j < 4; j++) {
                if (j != 0) {
                    pictureTile.rotate();
                }
//                System.out.println(seaM);
                var seaMHeight = seaM.states.length;
                var seaMWidth = seaM.states[0].length;
                for (int m = 0; m < pictureTile.states.length - seaMHeight; m++) {
                    for (int n = 0; n < pictureTile.states[0].length - seaMWidth; n++) {
                        boolean validMonster = true;
                        for (int k = 0; k < seaMHeight; k++) {
                            for (int l = 0; l < seaMWidth; l++) {
                                validMonster &= !seaM.states[k][l] || (pictureTile.states[m + k][n + l] == seaM.states[k][l]);
                            }
                        }
                        if (validMonster) {
                            monsterCount++;
//                            System.out.println("Found a monster");
                        }
                    }

                }
//                if (monsterCount != 0) {
//                    break;
//                }
            }
//            if (monsterCount != 0) {
//                break;
//            }
        }
        System.out.println("flip " + Counter.flip);
        System.out.println("rotateCnt " + Counter.rotateCnt);
        o.part2 = pictureTile.activeTiles() - (long) seaM.activeTiles() * monsterCount;
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

}

