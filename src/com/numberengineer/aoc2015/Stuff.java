package com.numberengineer.aoc2015;

import com.numberengineer.aoc.TikTok;
import com.numberengineer.aoc2015.utils.TravelingSalesman;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.numberengineer.aoc.AocPostCompetition.readFile;

public class Stuff {
    public Stuff() {
    }

    public static void main(String[] args) {

        day9();

    }

    public static void day1() {
        TikTok tikTok = new TikTok(true);

        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
            int i = 0;
        };
        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc2015\\data\\day1.txt");
//        String data = "()())";

        data.chars().forEach(c -> {
            o.i++;
            if (c == '(') {
                o.part1++;
            } else {
                o.part1--;
            }
            if (o.part1 == -1) {
                if (o.part2 == 0) {
                    o.part2 = o.i;
                }
            }
        });
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

    public static void day2() {
        TikTok tikTok = new TikTok(true);

        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
            int i = 0;
        };
        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc2015\\data\\day2.txt");
//        String data = "1x1x10\n" +
//                "2x3x4";
        final var split = data.split("\n");
        Arrays.stream(split).forEach(s -> {
            final var xes = Arrays.stream(s.split("x")).mapToLong(Long::parseLong).sorted().toArray();
            o.part2 += Arrays.stream(xes).reduce((left, right) -> left * right).getAsLong();
            o.part2 += 2 * (xes[0] + xes[1]);
            o.part1 += xes[0] * xes[1];
            o.part1 += 2 * (xes[0] * xes[1] + xes[2] * xes[1] + xes[0] * xes[2]);
        });


        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

    public static void day3() {
        TikTok tikTok = new TikTok(true);
        record Postion(int x, int y) {

        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
            int x = 0;
            int y = 0;
            int x2 = 0;
            int y2 = 0;
            boolean flipper = false;
        };
        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc2015\\data\\day3.txt");
//        String data = "^v" ;
//                "2x3x4";

        HashSet<Postion> postions = new HashSet<>();


        postions.add(new Postion(o.x, o.y));
        data.chars().forEach(c -> {

            switch (c) {
                case '^' -> postions.add(new Postion(o.x, ++o.y));
                case 'v' -> postions.add(new Postion(o.x, --o.y));
                case '>' -> postions.add(new Postion(--o.x, o.y));
                case '<' -> postions.add(new Postion(++o.x, o.y));
            }

        });
        o.part1 = postions.size();
        postions.clear();
        o.x = 0;
        o.y = 0;
        o.x2 = 0;
        o.y2 = 0;
        postions.add(new Postion(o.x, o.y));
        data.chars().forEach(c -> {
            if (o.flipper) {
                switch (c) {
                    case '^' -> postions.add(new Postion(o.x, ++o.y));
                    case 'v' -> postions.add(new Postion(o.x, --o.y));
                    case '>' -> postions.add(new Postion(++o.x, o.y));
                    case '<' -> postions.add(new Postion(--o.x, o.y));
                }
            } else {
                switch (c) {
                    case '^' -> postions.add(new Postion(o.x2, ++o.y2));
                    case 'v' -> postions.add(new Postion(o.x2, --o.y2));
                    case '>' -> postions.add(new Postion(++o.x2, o.y2));
                    case '<' -> postions.add(new Postion(--o.x2, o.y2));
                }
            }
            o.flipper = !o.flipper;
        });
        o.part2 = postions.size();

        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

    public static void day4() {
        TikTok tikTok = new TikTok(true);
        class Hasher {
            String getHash(String password) {
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                md.update(password.getBytes());
                byte[] digest = md.digest();
                return toHexString(digest);
            }

            String toHexString(byte[] bytes) {
                StringBuilder hexString = new StringBuilder();

                for (int i = 0; i < bytes.length; i++) {
                    String hex = Integer.toHexString(0xFF & bytes[i]);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }

                return hexString.toString();
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var hasher = new Hasher();
        String data = "ckczppom";
        while (!hasher.getHash(data + (++o.part1)).startsWith("00000")) {

        }
        while (!hasher.getHash(data + (++o.part2)).startsWith("000000")) {

        }

        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

    public static void day5() {
        TikTok tikTok = new TikTok(true);
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();


//        boolean testMode = true;
        boolean testMode = false;
        String data;
        if (testMode) {
            data = "ieodomkazucvgmuy";
        } else {
            data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc2015\\data\\" + methodName + ".txt");
        }

        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };

        final var split = data.split("\n");
        final var twochar = Pattern.compile("(.)\\1+");
        final var aVowel = Pattern.compile("[aeiou]");
        final var notAny = Pattern.compile("(xy)|(ab)|(cd)|(pq)");
        Arrays.stream(split).forEach(s -> {
            if (twochar.matcher(s).find()) {
                if (aVowel.matcher(s).results().count() >= 3) {
                    if (!notAny.matcher(s).find()) {
                        o.part1++;
                    }
                }
            }
        });
        Arrays.stream(split).forEach(s -> {
            int valids = 0;
            for (int i = 0; i < s.length() - 2; i++) {
                if (s.substring(i + 2).contains(s.substring(i, i + 2))) {
                    valids++;
                    break;
                }
            }
            for (int i = 0; i < s.length() - 2; i++) {
                if (s.charAt(i) == s.charAt(i + 2)) {
                    valids++;
                    break;
                }
            }
            if (valids == 2) {
                o.part2++;
            }
        });
        if (testMode) {
            System.out.println("#######################################");
            System.out.println("##        THIS IS JUST A TEST        ##");
            System.out.println("#######################################");
        }
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

    public static void day6() {
        TikTok tikTok = new TikTok(true);
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        final var twochar = Pattern.compile("(.)\\1+");

//        boolean testMode = true;
        boolean testMode = false;
        String data;
        if (testMode) {
            data = "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
                    "mem[8] = 11\n" +
                    "mem[7] = 101\n" +
                    "mem[8] = 0";
        } else {
            data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc2015\\data\\" + methodName + ".txt");
        }

        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };

        final var split = data.split("\n");
        final var compile = Pattern.compile("^((turn (off|on))|(toggle)) (([0-9]+),([0-9]+)) through (([0-9]+),([\\d]+))$");
        boolean[][] booleans = new boolean[1000][1000];
        int[][] ints = new int[1000][1000];
        Arrays.stream(split).forEach(s -> {
            final var matcher = compile.matcher(s);
            if (matcher.matches()) {
                String instruction = matcher.group(1);
                Predicate<Boolean> predicate = switch (instruction) {
                    case "toggle" -> Boolean.FALSE::equals;
                    case "turn on" -> a -> true;
                    case "turn off" -> a -> false;
                    default -> throw new IllegalStateException("Unexpected value: " + instruction);
                };
                int xFrom = Integer.parseInt(matcher.group(6));
                int yFrom = Integer.parseInt(matcher.group(7));
                int xTo = Integer.parseInt(matcher.group(9));
                int yTo = Integer.parseInt(matcher.group(10));
                for (int x = xFrom; x <= xTo; x++) {
                    for (int y = yFrom; y <= yTo; y++) {
                        booleans[x][y] = predicate.test(booleans[x][y]);
                    }
                }
                System.out.println(matcher.groupCount());
            }
        });
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (booleans[i][j]) {
                    o.part1++;
                }
            }
        }
        Arrays.stream(split).forEach(s -> {
            final var matcher = compile.matcher(s);
            if (matcher.matches()) {
                String instruction = matcher.group(1);
                Function<Integer, Integer> functor = switch (instruction) {
                    case "toggle" -> aInt -> aInt + 2;
                    case "turn on" -> aInt -> aInt + 1;
                    case "turn off" -> aInt -> aInt == 0 ? aInt : aInt - 1;
                    default -> throw new IllegalStateException("Unexpected value: " + instruction);
                };
                int xFrom = Integer.parseInt(matcher.group(6));
                int yFrom = Integer.parseInt(matcher.group(7));
                int xTo = Integer.parseInt(matcher.group(9));
                int yTo = Integer.parseInt(matcher.group(10));
                for (int x = xFrom; x <= xTo; x++) {
                    for (int y = yFrom; y <= yTo; y++) {
                        ints[x][y] = functor.apply(ints[x][y]);
                    }
                }
            }
        });
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                o.part2 += ints[i][j];
            }
        }

        if (testMode) {
            System.out.println("#######################################");
            System.out.println("##        THIS IS JUST A TEST        ##");
            System.out.println("#######################################");
        }
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

    public static void day9() {
        TikTok tikTok = new TikTok(true);
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();


//        boolean testMode = true;
        boolean testMode = false;
        String data;
        if (testMode) {
            data = "London to Dublin = 464\n" +
                    "London to Belfast = 518\n" +
                    "Dublin to Belfast = 141";
        } else {
            data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc2015\\data\\" + methodName + ".txt");
        }
        Pattern extractor = Pattern.compile("(\\w+) to (\\w+) = (\\d+)");
        record Line(String from, String to, int distance) {

        }
        var o = new Object() {
            long part1 = Long.MAX_VALUE;
            long part2 = 0;
        };

        final var datas = Arrays.stream(data.split("\n")).map(extractor::matcher).toArray(Matcher[]::new);

        HashMap<String, Integer> cities = new HashMap<>();
        ArrayList<Line> lines = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < datas.length; i++) {
            datas[i].matches();
            final var from = datas[i].group(1);
            final var to = datas[i].group(2);
            final var distance = Integer.parseInt(datas[i].group(3));
            if (!cities.containsKey(from)) {
                cities.put(from, index++);
            }
            if (!cities.containsKey(to)) {
                cities.put(to, index++);
            }
            lines.add(new Line(from, to, distance));
        }
        double[][] graph = new double[cities.size()][cities.size()];
        lines.stream().forEach(l -> {
            final var f = cities.get(l.from);
            final var t = cities.get(l.to);
            graph[f][t] = l.distance;
            graph[t][f] = l.distance;
        });

        for (int i = 0; i < cities.size(); i++) {
            double[][] tGraph = new double[cities.size()][cities.size()];
            for (int j = 0; j < cities.size(); j++) {
                for (int k = 0; k < cities.size(); k++) {
                    if (j == i && k != j) {
                        tGraph[j][k] = 0;
                    } else {
                        tGraph[j][k] = graph[j][k];
                    }
                }
            }

            final var tspDynamicProgrammingIterative = new TravelingSalesman(i,tGraph);
            double tsp = tspDynamicProgrammingIterative.getTourCost();
            System.out.println(tsp);
            o.part1 = (long) Math.min(o.part1, tsp);
        }
        for (int i = 0; i < cities.size(); i++) {
            double[][] tGraph = new double[cities.size()][cities.size()];
            for (int j = 0; j < cities.size(); j++) {
                for (int k = 0; k < cities.size(); k++) {
                    if (j == i && k != j) {
                        tGraph[j][k] = 0;
                    } else {
                        tGraph[j][k] = -graph[j][k];
                    }
                }
            }

            final var tspDynamicProgrammingIterative = new TravelingSalesman(i,tGraph);
            double tsp = tspDynamicProgrammingIterative.getTourCost();

            System.out.println(tsp);
            o.part2 = (long) Math.min(o.part2, tsp);
        }
        if (testMode) {
            System.out.println("#######################################");
            System.out.println("##        THIS IS JUST A TEST        ##");
            System.out.println("#######################################");
        }
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

    public static void dayN() {
        TikTok tikTok = new TikTok(true);
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();


//        boolean testMode = true;
        boolean testMode = false;
        String data;
        if (testMode) {
            data = "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
                    "mem[8] = 11\n" +
                    "mem[7] = 101\n" +
                    "mem[8] = 0";
        } else {
            data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc2015\\data\\" + methodName + ".txt");
        }

        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };

        final var split = data.split("\n");


        if (testMode) {
            System.out.println("#######################################");
            System.out.println("##        THIS IS JUST A TEST        ##");
            System.out.println("#######################################");
        }
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }


}

