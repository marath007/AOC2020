package com.numberengineer.aoc.days;

import com.numberengineer.aoc.TikTok;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import static com.numberengineer.aoc.Utils.*;

public class Day24 {
    public static void day24() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "sesenwnenenewseeswwswswwnenewsewsw\n" +
                "neeenesenwnwwswnenewnwwsewnenwseswesw\n" +
                "seswneswswsenwwnwse\n" +
                "nwnwneseeswswnenewneswwnewseswneseene\n" +
                "swweswneswnenwsewnwneneseenw\n" +
                "eesenwseswswnenwswnwnwsewwnwsene\n" +
                "sewnenenenesenwsewnenwwwse\n" +
                "wenwwweseeeweswwwnwwe\n" +
                "wsweesenenewnwwnwsenewsenwwsesesenwne\n" +
                "neeswseenwwswnwswswnw\n" +
                "nenwswwsewswnenenewsenwsenwnesesenew\n" +
                "enewnwewneswsewnwswenweswnenwsenwsw\n" +
                "sweneswneswneneenwnewenewwneswswnese\n" +
                "swwesenesewenwneswnwwneseswwne\n" +
                "enesenwswwswneneswsenwnewswseenwsese\n" +
                "wnwnesenesenenwwnenwsewesewsesesew\n" +
                "nenewswnwewswnenesenwnesewesw\n" +
                "eneswnwswnwsenenwnwnwwseeswneewsenese\n" +
                "neswnwewnwnwseenwseesewsenwsweewe\n" +
                "wseweeenwnesenwwwswnew";
//        String data = "nwwswee";

        if (!testMode) {
            data = getData(day);
        }


        enum Direction {
            SE(new Coordinate(0.5, -1).normalize()), E(new Coordinate(1, 0).normalize()), SW(new Coordinate(-0.5, -1).normalize()), W(new Coordinate(-1, 0).normalize()), NW(new Coordinate(-0.5, 1).normalize()), NE(new Coordinate(0.5, 1).normalize());
            final Coordinate coordinate;

            Direction(Coordinate coordinate) {
                this.coordinate = coordinate;
            }
        }


        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };

        final var strings = asLines(data);

        HashMap<Coordinate, Coordinate> coordinates = new HashMap<>();
        for (int i = 0; i < strings.length; i++) {
            Coordinate coord = new Coordinate(0, 0);
            final var string = strings[i];
            String direction = "";
            for (int j = 0; j < string.length(); j++) {
                final var c = string.charAt(j);
                if (c == 'n' || c == 's') {
                    direction += c;
                } else {
                    direction += c;
                    final var direction1 = Direction.valueOf(direction.toUpperCase(Locale.ROOT));
                    coord.x += direction1.coordinate.x;
                    coord.y += direction1.coordinate.y;
                    direction = "";
                }
            }
            final var clean = coord.clean();
            System.out.println(clean);
            if (coordinates.containsKey(clean)) {
                coordinates.remove(coord.clean());
            } else {
                coordinates.put(coord.clean(), coord.clean());
            }

        }
        o.part1 = coordinates.size();
        coordinates.values().forEach(coordinate -> coordinate.black = true);
        for (int i = 0; i < 100; i++) {
            HashMap<Coordinate, Coordinate> tempMap = new HashMap<>();
            coordinates.values().forEach(coordinate -> {
                tempMap.put(coordinate, coordinate);
                Arrays.stream(Direction.values()).forEach(direction -> {
                    final var e = new Coordinate(coordinate.x + direction.coordinate.x, coordinate.y + direction.coordinate.y).clean();
                    if (!tempMap.containsKey(e)) {
                        tempMap.put(e, e);
                    }
                });
            });
            coordinates = new HashMap<>();
            HashMap<Coordinate, Coordinate> finalCoordinates = coordinates;
            tempMap.values().forEach(coordinate -> {
                final var actives = Arrays.stream(Direction.values()).mapToInt(direction -> {
                    final var e = new Coordinate(coordinate.x + direction.coordinate.x, coordinate.y + direction.coordinate.y).clean();
                    if (!tempMap.containsKey(e)) {
                        return 0;
                    } else {
                        return tempMap.get(e).black ? 1 : 0;
                    }
                }).sum();
                final var value = new Coordinate(coordinate.x, coordinate.y);
                if (coordinate.black) {
                    if (actives == 0 || actives > 2) {
                        value.black = false;
                    } else {
                        value.black = true;
                    }
                } else {
                    if (actives == 2) {
                        value.black = true;
                    }else {
                        value.black=false;
                    }
                }
                if (value.black){
                    finalCoordinates.put(coordinate, value);
                }
            });
            System.out.println(coordinates.size());
        }

        o.part2 = coordinates.size();
//        final var longs = asLongs(data);


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    static class Coordinate {
        boolean black = false;
        private double x;
        private double y;

        Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double x() {
            return x;
        }

        public double y() {
            return y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Coordinate) obj;
            return Double.doubleToLongBits(this.x) == Double.doubleToLongBits(that.x) &&
                    Double.doubleToLongBits(this.y) == Double.doubleToLongBits(that.y);
        }

        Coordinate normalize() {
//            double l = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            double x1 = x;
            double y1 = y;
//            x1 /= l;
//            y1 /= l;
            return new Coordinate(x1, y1);
        }

        Coordinate clean() {
            double x1 = Math.round(x * 10000d) / 10000d;
            double y1 = Math.round(y * 10000d) / 10000d;
            return new Coordinate(x1, y1);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Coordinate[" +
                    "x=" + x + ", " +
                    "y=" + y + ']';
        }


    }

}

