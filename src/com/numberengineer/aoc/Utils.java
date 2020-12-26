package com.numberengineer.aoc;

import java.util.Arrays;

import static com.numberengineer.aoc.AocPostCompetition.readFile;

public class Utils {
    public Utils() {
    }
    public static String consoleProgressBar(double percent){
        if (percent>1){
            percent=1;
        }else if (percent<0){
            percent=0;
        }
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append('[');
        int cnt= (int) (percent/0.05);
        for (int i = 0; i < cnt; i++) {
            stringBuilder.append('|');
        }
        for (int i = cnt; cnt < 20; cnt++) {
            stringBuilder.append('-');
        }
        stringBuilder.append(']');
        stringBuilder.append(' ');
        stringBuilder.append(String.format("%.1f",percent*100f));
        stringBuilder.append('%');
        return stringBuilder.toString();
    }
    public static String getData(String day) {
        return getData(day, "");
    }

    public static String getData(String day, String year) {
        String data;
        data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc" + year + "\\data\\" + day + ".txt");
        return data;
    }

    public static String[] asLines(String data) {
        return data.split("\n");
    }

    public static long[] asLongs(String data) {
        return Arrays.stream(data.split("\n")).mapToLong(Long::parseLong).toArray();
    }

    public static int[] asInts(String data) {
        return Arrays.stream(data.split("\n")).mapToInt(Integer::parseInt).toArray();
    }


    public static void endOfWork(TikTok tikTok, String day, boolean testMode, Object part1, Object part2) {
        if (testMode) {
            System.out.println("#######################################");
            System.out.println("##        THIS IS JUST A TEST        ##");
            System.out.println("#######################################");
        }
        System.out.println(day + " part1 " + part1);
        System.out.println(day + " part2 " + part2);
        tikTok.toc(System.out, " " + day);
    }

    public static String getDay() {
        return Thread.currentThread().getStackTrace()[2].getMethodName().split("_")[0];
    }

}

