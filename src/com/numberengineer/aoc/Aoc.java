package com.numberengineer.aoc;


import java.lang.reflect.InvocationTargetException;

import static com.numberengineer.aoc.Utils.*;


class Aoc {


    public static void main(String[] args) {
        if (args[0].equals("--Speed")) {
            for (int i = 25; i > 0; i--) {
                try {
                    Class.forName("com.numberengineer.aoc.days.Day" + i).getMethod("day" + i).invoke(null);
                    break;
                } catch (IllegalAccessException | NoSuchMethodException | ClassNotFoundException ignored) {
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 1; i <= 25; i++) {
                try {
                    Class.forName("com.numberengineer.aoc.days.Day" + i).getMethod("day" + i).invoke(null);
                    System.out.println();
                } catch (IllegalAccessException | NoSuchMethodException | ClassNotFoundException ignored) {
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static void dayN() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean testMode = true;
//        boolean testMode = false;
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