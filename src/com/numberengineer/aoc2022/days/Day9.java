package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import java.util.HashSet;

import static com.numberengineer.aoc.Utils.*;

public class Day9 {
    public static void day9() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "R 5\n" + "U 8\n" + "L 8\n" + "D 3\n" + "R 17\n" + "D 10\n" + "L 25\n" + "U 20";
        if (!testMode) {
            data = getData(day, "2022");
        }
        record Pos(int x, int y) {
        }

        class HeadTail {
            HeadTail childTail = null;
            int xH = 0;
            int yH = 0;
            int xT = 0;
            int yT = 0;

            void updateH(HashSet<Pos> pos, int tailLen) {
                final var dx = xH - xT;
                final var dy = yH - yT;

                switch (dx) {
                    case -2 -> {
                        switch (dy) {
                            case -2, -1 -> {
                                xT--;
                                yT--;
                            }
                            case 0 -> xT--;
                            case 1, 2 -> {
                                xT--;
                                yT++;
                            }
                        }
                    }
                    case -1 -> {
                        switch (dy) {
                            case -2 -> {
                                xT--;
                                yT--;
                            }
                            case 2 -> {
                                xT--;
                                yT++;
                            }
                        }
                    }
                    case -0 -> {
                        switch (dy) {
                            case -2 -> yT--;
                            case 2 -> yT++;
                        }
                    }
                    case 1 -> {
                        switch (dy) {
                            case -2 -> {
                                xT++;
                                yT--;
                            }
                            case 2 -> {
                                xT++;
                                yT++;
                            }
                        }
                    }
                    case 2 -> {
                        switch (dy) {
                            case -2, -1 -> {
                                xT++;
                                yT--;
                            }
                            case 0 -> xT++;
                            case 1, 2 -> {
                                xT++;
                                yT++;
                            }
                        }
                    }
                }

                if (tailLen == 0) {
                    pos.add(new Pos(xT, yT));

                } else {
                    if (childTail == null) {
                        childTail = new HeadTail();
                    }
                    childTail.xH = xT;
                    childTail.yH = yT;
                    childTail.updateH(pos, tailLen - 1);

                }
            }
        }
        var o = new Object() {
            HashSet<Pos> pos1 = new HashSet<>();
            HashSet<Pos> pos2 = new HashSet<>();
            long part1 = 0;
            long part2 = 0;
        };
        HeadTail headTail1 = new HeadTail();
        HeadTail headTail2 = new HeadTail();
        final var strings = asLines(data);
        headTail1.updateH(o.pos1, 0);
        headTail2.updateH(o.pos2, 8);
        for (String string : strings) {
            final var s = string.split(" ");
            var dist = Integer.parseInt(s[1]);
            switch (s[0]) {
                case "R" -> {
                    for (int i = 0; i < dist; i++) {
                        headTail1.xH++;
                        headTail2.xH++;
                        headTail1.updateH(o.pos1, 0);
                        headTail2.updateH(o.pos2, 8);
                    }
                }
                case "L" -> {
                    for (int i = 0; i < dist; i++) {
                        headTail1.xH--;
                        headTail2.xH--;
                        headTail1.updateH(o.pos1, 0);
                        headTail2.updateH(o.pos2, 8);
                    }
                }
                case "U" -> {
                    for (int i = 0; i < dist; i++) {
                        headTail1.yH++;
                        headTail2.yH++;
                        headTail1.updateH(o.pos1, 0);
                        headTail2.updateH(o.pos2, 8);
                    }
                }
                case "D" -> {
                    for (int i = 0; i < dist; i++) {
                        headTail1.yH--;
                        headTail2.yH--;
                        headTail1.updateH(o.pos1, 0);
                        headTail2.updateH(o.pos2, 8);
                    }
                }
            }
        }
        o.part1 = o.pos1.size();
        o.part2 = o.pos2.size();

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

