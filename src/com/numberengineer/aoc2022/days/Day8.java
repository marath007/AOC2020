package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import java.util.HashSet;

import static com.numberengineer.aoc.Utils.*;

public class Day8 {
    public static void day8() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "30373\n" +
                "25512\n" +
                "65332\n" +
                "33549\n" +
                "35390";
        if (!testMode) {
            data = getData(day, "2022");
        }
         record Coord( int x, int y){
        }


        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        HashSet<Coord> listOfTree = new HashSet<>();
        final var strings = asLines(data);
        int[][] grid = new int[strings.length][strings[0].length()];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = Integer.parseInt(strings[i].charAt(j) + "");
            }
        }

        for (int i = 0; i < grid.length; i++) {
            int height = -1;
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] > height) {
                    listOfTree.add(new Coord(i, j));
                    height = grid[i][j];
                }
            }
        }
        for (int i = 0; i < grid.length; i++) {
            int height = -1;
            for (int j = grid[i].length - 1; j >= 0; j--) {
                if (grid[i][j] > height) {
                    listOfTree.add(new Coord(i, j));
                    height = grid[i][j];
                }
            }
        }
        for (int j = 0; j < grid[0].length; j++) {
            int height = -1;
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][j] > height) {
                    listOfTree.add(new Coord(i, j));
                    height = grid[i][j];

                }
            }
        }
        for (int j = 0; j < grid[0].length; j++) {
            int height = -1;
            for (int i = grid.length - 1; i >= 0; i--) {
                if (grid[i][j] > height) {
                    listOfTree.add(new Coord(i, j));
                    height = grid[i][j];
                }
            }
        }
        for (int i = 3; i < grid.length; i++) {
            for (int j = 2; j < grid[i].length; j++) {
                final var initH = grid[i][j];
                var value = 1;
                var sightLen = 0;
                for (int k = i+1; k < grid.length; k++) {
                    if (initH > grid[k][j]) {
                        sightLen++;
                    } else if (initH == grid[k][j]) {
                        sightLen++;
                        break;
                    } else {
                        break;
                    }
                }
                value*=sightLen;
                sightLen = 0;
                for (int k = i-1; k >= 0; k--) {
                    if (initH > grid[k][j]) {
                        sightLen++;
                    } else if (initH == grid[k][j]) {
                        sightLen++;
                        break;
                    } else {
                        sightLen++;
                        break;
                    }
                }
                value*=sightLen;
                sightLen = 0;
                for (int k = j+1; k < grid[i].length; k++) {
                    if (initH > grid[i][k]) {
                        sightLen++;
                    } else if (initH == grid[i][k]) {
                        sightLen++;
                        break;
                    } else {
                        sightLen++;
                        break;
                    }
                }
                value*=sightLen;
                sightLen = 0;
                for (int k = j-1; k >=0; k--) {
                    if (initH > grid[i][k]) {
                        sightLen++;
                    } else if (initH == grid[i][k]) {
                        sightLen++;
                        break;
                    } else {
                        sightLen++;
                        break;
                    }
                }
                value*=sightLen;
                o.part2=Math.max(value,o.part2);
            }
        }


//        final var longs = asLongs(data);

        o.part1 = listOfTree.size();
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

