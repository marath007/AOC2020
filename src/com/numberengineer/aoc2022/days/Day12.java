package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import java.awt.*;

import static com.numberengineer.aoc.Utils.*;

public class Day12 {


    public static void day12() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "Sabqponm\n" +
                "abcryxxl\n" +
                "accszExk\n" +
                "acctuvwj\n" +
                "abdefghi";
        if (!testMode) {
            data = getData(day, "2022");
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };


        final var strings = asLines(data);
        final var trailPath = new TrailPath();
        int[][] mountainMap = new int[strings.length][strings[0].length()];
        int[][] wMap = new int[strings.length][strings[0].length()];
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length(); j++) {
                final var c = strings[i].charAt(j);
                if (c == 'S') {
                    mountainMap[i][j] = 1;
                    wMap[i][j] = 1;
                } else if (c == 'E') {
                    mountainMap[i][j] = 26;
                    trailPath.end = new Point(i, j);
                }else {
                    mountainMap[i][j] = (c - 'a') + 1;
                }
            }
        }
        int dist=0;
        while (wMap[trailPath.end.x][trailPath.end.y] == 0) {
            for (int i = 0; i < wMap.length; i++) {
                for (int j = 0; j < wMap[i].length; j++) {
                    if (wMap[i][j] != 0) {
                        int h = mountainMap[i][j];
                        if (i != 0) {
                            if (wMap[i - 1][j] == 0) {
                                if (mountainMap[i - 1][j]  <= h+ 1) {
                                    wMap[i - 1][j] = wMap[i][j] + 1;
                                }
                            }
                        }
                        if (i != wMap.length - 1) {
                            if (wMap[i + 1][j] == 0) {
                                if (mountainMap[i + 1][j]  <= h+ 1) {
                                    wMap[i + 1][j] = wMap[i][j] + 1;
                                }
                            }
                        }
                        if (j != 0) {
                            if (wMap[i][j - 1] == 0) {
                                if (mountainMap[i][j - 1]  <= h+ 1) {
                                    wMap[i][j - 1] = wMap[i][j] + 1;
                                }
                            }
                        }
                        if (j != wMap[i].length - 1) {
                            if (wMap[i][j + 1] == 0) {
                                if (mountainMap[i][j + 1]  <= h+ 1) {
                                    wMap[i][j + 1] = wMap[i][j] + 1;
                                }
                            }
                        }
                    }
                }
            }
            dist++;
            System.out.println("iterations: "+dist);
        }

        o.part1 = wMap[trailPath.end.x][trailPath.end.y]-1;

        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length(); j++) {
                final var c = strings[i].charAt(j);
                if (c == 'S') {
                    mountainMap[i][j] = 1;
                    wMap[i][j] = 1;
                } else if (c == 'E') {
                    mountainMap[i][j] = 26;
                    trailPath.end = new Point(i, j);
                } else if (c == 'a') {
                    mountainMap[i][j] = 1;
                    wMap[i][j] = 1;
                } else {
                    mountainMap[i][j] = (c - 'a') + 1;
                }
            }
        }


        dist = 0;
        while (wMap[trailPath.end.x][trailPath.end.y] == 0 || dist<wMap[trailPath.end.x][trailPath.end.y]) {
            for (int i = 0; i < wMap.length; i++) {
                for (int j = 0; j < wMap[i].length; j++) {
                    if (wMap[i][j] != 0) {
                        int h = mountainMap[i][j];
                        if (i != 0) {
                            if (wMap[i - 1][j] == 0) {
                                if (mountainMap[i - 1][j] <= h + 1) {
                                    wMap[i - 1][j] = wMap[i][j] + 1;
                                }
                            } else {
                                if (mountainMap[i - 1][j] <= h + 1) {
                                    wMap[i - 1][j] = Math.min(wMap[i][j] + 1, wMap[i - 1][j]);
                                }
                            }
                        }
                        if (i != wMap.length - 1) {
                            if (wMap[i + 1][j] == 0) {
                                if (mountainMap[i + 1][j] <= h + 1) {
                                    wMap[i + 1][j] = wMap[i][j] + 1;
                                }
                            } else {
                                if (mountainMap[i + 1][j] <= h + 1) {
                                    wMap[i + 1][j] = Math.min(wMap[i][j] + 1, wMap[i + 1][j]);
                                }
                            }
                        }
                        if (j != 0) {
                            if (wMap[i][j - 1] == 0) {
                                if (mountainMap[i][j - 1] <= h + 1) {
                                    wMap[i][j - 1] = wMap[i][j] + 1;
                                }
                            } else {
                                if (mountainMap[i][j - 1] <= h + 1) {
                                    wMap[i][j - 1] = Math.min(wMap[i][j] + 1, wMap[i][j - 1]);
                                }
                            }
                        }
                        if (j != wMap[i].length - 1) {
                            if (wMap[i][j + 1] == 0) {
                                if (mountainMap[i][j + 1] <= h + 1) {
                                    wMap[i][j + 1] = wMap[i][j] + 1;
                                }
                            } else {
                                if (mountainMap[i][j + 1] <= h + 1) {
                                    wMap[i][j + 1] = Math.min(wMap[i][j] + 1,wMap[i][j + 1]);
                                }
                            }
                        }
                    }
                }
            }
            dist++;
            System.out.println("iterations: " + dist);
        }
        o.part2 = wMap[trailPath.end.x][trailPath.end.y] - 1;
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static class TrailPath {

        Point end;
    }

    public record Point(int x, int y) {

    }


}

