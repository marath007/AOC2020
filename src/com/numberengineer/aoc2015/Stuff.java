package com.numberengineer.aoc2015;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.numberengineer.aoc.TikTok;
import com.numberengineer.aoc2015.utils.TravelingSalesman;

import java.lang.reflect.InvocationTargetException;
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
import static com.numberengineer.aoc.Utils.*;

public class Stuff {
    public Stuff() {
    }

    public static void main(String[] args) {
        for (int i = 25; i > 0; i--) {
            try {
                Stuff.class.getMethod("day" + i).invoke(null);
                break;
            } catch (IllegalAccessException | NoSuchMethodException ignored) {
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

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

    public static void day7() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//boolean verbose =false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "123 -> x\n" +
                "456 -> y\n" +
                "x AND y -> d\n" +
                "x OR y -> e\n" +
                "x LSHIFT 2 -> f\n" +
                "y RSHIFT 2 -> g\n" +
                "NOT x -> h\n" +
                "NOT y -> i";
        if (!testMode) {
            data = getData(day, "2015");
        }
        abstract class Instruction {
            String name = "raw";
            int raw = -1;

            int cachedProcess() {
                if (raw == -1) {
                    raw = process();
                }
                return raw;
            }

            abstract int process();

            abstract void map(HashMap<String, Instruction> map);

            @Override
            public String toString() {
                return name;
            }
        }
        Predicate<String> digit = Pattern.compile("^[\\d]+$").asMatchPredicate();
        class DataInstruction extends Instruction {
            int value;

            public DataInstruction(int value) {
                this.value = value;
            }

            @Override
            int process() {
//                System.out.println("<-" + value);
                return value;
            }

            @Override
            void map(HashMap<String, Instruction> map) {

            }


        }
        class NotInstruction extends Instruction {
            Instruction instruction;
            transient String _instruction;

            public NotInstruction(String _instruction) {
                this._instruction = _instruction;
            }

            @Override
            int process() {
                final var process = instruction.cachedProcess();
                final var i = 65535 - process;
                // System.out.println(i + " <- Not " + process + " :" + name);
                return i;
            }

            @Override
            void map(HashMap<String, Instruction> map) {
                instruction = map.get(_instruction);
            }
        }
        class RShiftInstruction extends Instruction {
            Instruction instruction;
            transient String _instruction;
            private int qty;

            public RShiftInstruction(String _instruction, int qty) {
                this._instruction = _instruction;
                this.qty = qty;
            }

            @Override
            int process() {
                final var process = instruction.cachedProcess();
                final var i = process >> qty;
                // System.out.println(i + " <- " + process + " RShift " + qty + " :" + name);
                return i;
            }

            @Override
            void map(HashMap<String, Instruction> map) {
                instruction = map.get(_instruction);
            }
        }
        class LShiftInstruction extends Instruction {
            Instruction instruction;
            transient String _instruction;
            private int qty;

            public LShiftInstruction(String _instruction, int qty) {
                this._instruction = _instruction;
                this.qty = qty;
            }

            @Override
            int process() {
                final var process = instruction.cachedProcess();
                final var i = process << qty;
                if (verbose) {
                    System.out.println(i + "<-" + process + " LShift " + qty + " :" + name);
                }
                return i;
            }

            @Override
            void map(HashMap<String, Instruction> map) {
                instruction = map.get(_instruction);
            }
        }
        class AndInstruction extends Instruction {
            Instruction[] instructions;
            transient String[] _instructions;

            public AndInstruction(String _instruction1, String _instruction2) {
                _instructions = new String[]{_instruction1, _instruction2};
            }

            @Override
            int process() {
                final var process = instructions[0].cachedProcess();
                final var process1 = instructions[1].cachedProcess();
                final var i = process & process1;
                // System.out.println(i + " <- " + process + " AND " + process1 + " :" + name);
                return i;
            }

            @Override
            void map(HashMap<String, Instruction> map) {
                instructions = new Instruction[_instructions.length];
                for (int i = 0; i < _instructions.length; i++) {
                    if (digit.test(_instructions[i])) {
                        instructions[i] = new DataInstruction(Integer.parseInt(_instructions[i]));
                    } else {
                        instructions[i] = map.get(_instructions[i]);
                    }
                }
            }
        }
        class OrInstruction extends Instruction {
            Instruction[] instructions;
            transient String[] _instructions;

            public OrInstruction(String _instruction1, String _instruction2) {
                _instructions = new String[]{_instruction1, _instruction2};
            }

            @Override
            int process() {
                final var process = instructions[0].cachedProcess();
                final var process1 = instructions[1].cachedProcess();
                final var i = process | process1;
//               \\ System.out.println(i + " <- " + process + " OR " + process1 + " :" + name);
                return i;
            }

            @Override
            void map(HashMap<String, Instruction> map) {
                instructions = new Instruction[_instructions.length];
                for (int i = 0; i < _instructions.length; i++) {
                    if (digit.test(_instructions[i])) {
                        instructions[i] = new DataInstruction(Integer.parseInt(_instructions[i]));
                    } else {
                        instructions[i] = map.get(_instructions[i]);
                    }
                }
            }
        }
        class MapInstruction extends Instruction {
            Instruction instruction;
            transient String _instruction;

            public MapInstruction(String _instruction) {
                this._instruction = _instruction;
            }

            @Override
            int process() {
                return instruction.cachedProcess();
            }

            @Override
            void map(HashMap<String, Instruction> map) {
                instruction = map.get(_instruction);
            }
        }

        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var split = data.split("\n");
        HashMap<String, Instruction> map = new HashMap<>();

        final var orAndFinder = Pattern.compile("([\\w]+) ((OR)|(AND)) ([\\w]+)");
        final var shiftFinder = Pattern.compile("([\\w]+) ((LSHIFT)|(RSHIFT)) ([\\d]+)");
        final var notFinder = Pattern.compile("NOT ([\\w]+)");
        Arrays.stream(split).forEach(s -> {
            final var line = s.split(" -> ");
//            System.out.println(line);
            Instruction instruction;
            final var leftSide = line[0];
            if (digit.test(leftSide)) {
                instruction = new DataInstruction(Integer.parseInt(leftSide));
            } else {
                final var orAnd = orAndFinder.matcher(leftSide);
                final var shift = shiftFinder.matcher(leftSide);
                final var not = notFinder.matcher(leftSide);
                if (orAnd.find()) {
                    if (orAnd.group(3) == null) {
                        instruction = new AndInstruction(orAnd.group(1), orAnd.group(5));
                    } else {
                        instruction = new OrInstruction(orAnd.group(1), orAnd.group(5));
                    }
                } else if (shift.find()) {
                    if (shift.group(3) == null) {
                        instruction = new RShiftInstruction(shift.group(1), Integer.parseInt(shift.group(5)));
                    } else {
                        instruction = new LShiftInstruction(shift.group(1), Integer.parseInt(shift.group(5)));
                    }
                } else if (not.find()) {
                    instruction = new NotInstruction(not.group(1));
                } else {
                    instruction = new MapInstruction(leftSide);
                }
            }
            instruction.name = line[1];
            map.put(line[1], instruction);
        });
        map.values().forEach(instruction -> instruction.map(map));
        if (testMode) {
            System.out.println(map.get("d").cachedProcess() + "=" + 72);
            System.out.println(map.get("e").cachedProcess() + "=" + 507);
            System.out.println(map.get("f").cachedProcess() + "=" + 492);
            System.out.println(map.get("g").cachedProcess() + "=" + 114);
            System.out.println(map.get("h").cachedProcess() + "=" + 65412);
            System.out.println(map.get("i").cachedProcess() + "=" + 65079);
            System.out.println(map.get("x").cachedProcess() + "=" + 123);
        } else {
            o.part1 = map.get("a").cachedProcess();
            map.put("b", new DataInstruction((int) o.part1));
            map.values().forEach(instruction -> instruction.raw = -1);
            map.values().forEach(instruction -> instruction.map(map));
            o.part2 = map.get("a").cachedProcess();
        }


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day8() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean verbose = true;
        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = """
                "\"
                "abc"
                "aaa\\"aaa"
                "\\x27"
                """;
        if (!testMode) {
            data = getData(day, "2015");
        }

        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var strings = asLines(data);
        final var rawSize = Arrays.stream(strings).mapToInt(String::length).sum();
        final var realSize = Arrays.stream(strings).map(s -> {
            var s1 = s;
            s = s.substring(1, s.length() - 1);
            s = s.replace("\\\\", "\\").replace("\\\"", "\"").replaceAll("\\\\x[0-9a-f]{2}", "~" +
                    "");
            if (verbose) {
                System.out.println(s1 + "\t->\t" + s);
            }
            return s;
        }).mapToInt(String::length).sum();
        if (verbose) {
            System.out.println();
        }
        final var retardSize = Arrays.stream(strings).map(s -> {
            var s1 = s;
            s = "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
            if (verbose) {
                System.out.println(s1 + "\t->\t" + s);
                System.out.println(s1.length() + "\t->\t" + s.length());
            }
            return s;
        }).mapToInt(String::length).sum();

        o.part1 = rawSize - realSize;
        o.part2 = retardSize - rawSize;

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day9() {
        TikTok tikTok = new TikTok(true);
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        boolean verbose = true;
//        boolean verbose = false;
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

            final var tspDynamicProgrammingIterative = new TravelingSalesman(i, tGraph);
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

            final var tspDynamicProgrammingIterative = new TravelingSalesman(i, tGraph);
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

    public static void day10() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "1";
        if (!testMode) {
            data = "1113122113";
        }

        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        if (testMode) {
            System.out.println(data);
        }
        final var i1 = testMode ? 5 : 50;

        for (int i = 0; i < i1; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            char lastChar = 0;
            int cnt = 0;
            for (int j = 0; j < data.length(); j++) {
                if (lastChar == 0) {
                    lastChar = data.charAt(j);
                    cnt++;
                } else {
                    if (lastChar == data.charAt(j)) {
                        cnt++;
                    } else {
                        stringBuilder.append(cnt);
                        stringBuilder.append(lastChar);
                        cnt = 1;
                        lastChar = data.charAt(j);
                    }
                }
            }
            stringBuilder.append(cnt);
            stringBuilder.append(lastChar);
            data = stringBuilder.toString();
            if (i == 39) {
                o.part1 = data.length();
            }
            if (i == 49) {
                o.part2 = data.length();
            }
//            System.out.println(data);
        }


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day11() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "ghijklmn";
        if (!testMode) {
            data = "vzbxkghb";
        }
        Predicate<String> rule1 = s -> {
            char lastChar = 0;
            lastChar = s.charAt(0);
            int maxCnt = 0;
            int cnt = 0;
            for (int i = 0; i < s.length(); i++) {
                final var c = s.charAt(i);
                if (lastChar + 1 == c) {
                    cnt++;
                } else {
                    cnt = 1;
                }
                maxCnt = Math.max(maxCnt, cnt);
                lastChar = c;
            }
            return maxCnt >= 3;
        };
        Predicate<String> rule3 = s -> {
            char lastChar = 0;
            lastChar = s.charAt(0);
            int maxCnt = 0;
            int cnt = 0;
            HashSet<String> strings = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                final var c = s.charAt(i);
                if (lastChar == c) {
                    cnt++;
                } else {
                    cnt = 1;
                }
                if (cnt == 2) {
                    strings.add(lastChar + "" + lastChar);
                }
                lastChar = c;
            }
            return strings.size() >= 2;
        };
        Predicate<String> rule2 = s -> !(s.contains("i") || s.contains("o") || s.contains("l"));
        class Password {
            String pass;

            public Password(String pass) {
                this.pass = pass;
            }

            boolean valid() {
                return rule1.test(pass) && rule2.test(pass) && rule3.test(pass);
            }

            String toRadix26(String s) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    stringBuilder.append((char) switch (s.charAt(i)) {
                        case 'a' -> '0';
                        case 'b' -> '1';
                        case 'c' -> '2';
                        case 'd' -> '3';
                        case 'e' -> '4';
                        case 'f' -> '5';
                        case 'g' -> '6';
                        case 'h' -> '7';
                        case 'i' -> '8';
                        case 'j' -> '9';
                        default -> s.charAt(i) - 10;
                    });
                }
                return stringBuilder.toString();
            }

            String fromRadix26(String s) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    stringBuilder.append((char) switch (s.charAt(i)) {
                        case '0' -> 'a';
                        case '1' -> 'b';
                        case '2' -> 'c';
                        case '3' -> 'd';
                        case '4' -> 'e';
                        case '5' -> 'f';
                        case '6' -> 'g';
                        case '7' -> 'h';
                        case '8' -> 'i';
                        case '9' -> 'j';
                        default -> s.charAt(i) + 10;
                    });
                }
                return stringBuilder.toString();
            }

            void increment() {
                long l = Long.parseLong(toRadix26(pass), 26);
                l += 1;
                final var s = fromRadix26(Long.toString(l, 26));
//                System.out.println(pass + " -> " + s);
                pass = s;
            }
        }
        Password password = new Password(data);
        System.out.println(password.valid());
        var o = new Object() {
            String part1 = "";
            String part2 = "";
        };
        while (!password.valid()) {
            password.increment();
        }
        o.part1 = password.pass;
        password.increment();
        while (!password.valid()) {
            password.increment();
        }
        o.part2 = password.pass;
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day12() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "[1,2,3]";
        if (!testMode) {
            data = getData(day, "2015");
        }
        Pattern pattern = Pattern.compile("([-]?\\d+)");
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;

            long read(Object object) {
                if (object instanceof LinkedTreeMap map) {
                    if (map.containsValue("red")) {
                        return 0;
                    } else {
                        return map.values().stream().mapToLong(this::read).sum();
                    }
                } else if (object instanceof ArrayList arrayList) {
                    return arrayList.stream().mapToLong(this::read).sum();
                } else {
                    if (object instanceof Number n){
                        return n.longValue();
                    }
//                    System.out.println("egg");
                    return 0;
                }
            }
        };
        o.part1 = pattern.matcher(data).results().mapToLong(m -> Integer.parseInt(m.group(1))).sum();
        final var objects = new Gson().fromJson(data, Object[].class);
        o.part2 = Arrays.stream(objects).mapToLong(o::read).sum();
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void dayN() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
        boolean testMode = true;
//        boolean testMode = false;
        String data = "";
        if (!testMode) {
            data = getData(day, "2015");
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

