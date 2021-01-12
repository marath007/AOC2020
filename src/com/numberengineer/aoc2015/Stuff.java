package com.numberengineer.aoc2015;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.numberengineer.aoc.TikTok;
import com.numberengineer.aoc.Utils;
import com.numberengineer.aoc2015.utils.TravelingSalesman;

import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.numberengineer.aoc.AocPostCompetition.readFile;
import static com.numberengineer.aoc.Utils.*;
import static com.numberengineer.aoc2015.utils.Utilities.permutations;

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
                    if (object instanceof Number n) {
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

    public static void day13() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "Alice would gain 54 happiness units by sitting next to Bob.\n" +
                "Alice would lose 79 happiness units by sitting next to Carol.\n" +
                "Alice would lose 2 happiness units by sitting next to David.\n" +
                "Bob would gain 83 happiness units by sitting next to Alice.\n" +
                "Bob would lose 7 happiness units by sitting next to Carol.\n" +
                "Bob would lose 63 happiness units by sitting next to David.\n" +
                "Carol would lose 62 happiness units by sitting next to Alice.\n" +
                "Carol would gain 60 happiness units by sitting next to Bob.\n" +
                "Carol would gain 55 happiness units by sitting next to David.\n" +
                "David would gain 46 happiness units by sitting next to Alice.\n" +
                "David would lose 7 happiness units by sitting next to Bob.\n" +
                "David would gain 41 happiness units by sitting next to Carol.";
        if (!testMode) {
            data = getData(day, "2015");
        }
        class Person {
            String name;
            HashMap<Person, Integer> friendBond = new HashMap<>();

            public Person(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return name;
            }
        }
        class Node {
            Person data;
            Node left;
            Node right;

            public Node(Person data) {
                this.data = data;
            }

            Node removeFromCircle() {
                final var right = this.right;
                right.left = left;
                left.right = right;
                left = null;
                this.right = null;
                return this;
            }

            public void insertToTheRight(Node... set) {
                Node n = this;
                for (Node node : set) {
                    n.insertToTheRight(node);
                    n = node;
                }
            }

            public void insertToTheRight(Node set) {
                final var right = this.right;
                set.left = this;
                set.right = this.right;
                this.right = set;
                right.left = set;
            }

        }
        class NodeCircle {
            Node currentNode = null;
            int size = 0;
            int fullSize = 0;


            void sow() {
                fullSize = size + 1;
                Node rightEnd = currentNode;
                while (currentNode.left != null) {
                    currentNode = currentNode.left;
                }
                rightEnd.right = currentNode;
                currentNode.left = rightEnd;
            }


            void putNode(Node n) {
                size++;
                if (currentNode != null) {
                    currentNode.right = n;
                    n.left = currentNode;
                }
                currentNode = n;
            }

            int clean(int i) {
                i %= fullSize;
                if (i < 0)
                    i += fullSize;
                return i;
            }

            long sum() {
                long sum = 0;
                var currentNode = this.currentNode;
                for (int i = 0; i < size; i++) {
                    sum += currentNode.data.friendBond.get(currentNode.left.data);
                    sum += currentNode.data.friendBond.get(currentNode.right.data);
                    currentNode = currentNode.right;
                }
                return sum;
            }

            @Override
            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                var currentNode = this.currentNode;
                for (int i = 0; i < Math.min(size, 100); i++) {
                    stringBuilder.append(currentNode.data);
                    currentNode = currentNode.right;
                }
                return stringBuilder.toString();
            }
        }
        Pattern extract = Pattern.compile("([\\w]+) would ((?:gain)|(?:lose)) ([\\d]+) happiness units by sitting next to ([\\w]+)\\.");
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
            String number = "0";
        };
        final var strings = asLines(data);
        HashMap<String, Person> map = new HashMap<>();
        Arrays.stream(Arrays.stream(strings).map(s -> {
            final var matcher = extract.matcher(s);
            matcher.find();
            map.put(matcher.group(1), new Person(matcher.group(1)));
            map.put(matcher.group(4), new Person(matcher.group(4)));
            return matcher;
        }).toArray(Matcher[]::new)).forEach(matcher -> {
            Integer power = Integer.parseInt(matcher.group(3));
            if (matcher.group(2).equals("lose")) {
                power = -power;
            }
            map.get(matcher.group(1)).friendBond.put(map.get(matcher.group(4)), power);
        });
        final var people = map.values().toArray(Person[]::new);
        final var permutations = permutations(people.length);
        o.part1 = 0;
        for (int i = 0; i < permutations.size(); i++) {
            final var ints = permutations.get(i);
//            var tPeople=new Person[ints.length];
            NodeCircle nodeCircle = new NodeCircle();
            for (int j = 0; j < ints.length; j++) {
                nodeCircle.putNode(new Node(people[ints[j]]));
            }
            nodeCircle.sow();
            final var sum = nodeCircle.sum();
            o.part1 = Math.max(o.part1, sum);

        }
        Person me = new Person("me");
        final var peoples = new Person[people.length + 1];
        for (int i = 0; i < people.length; i++) {
            peoples[i] = people[i];
            peoples[i].friendBond.put(me, 0);
            me.friendBond.put(peoples[i], 0);
        }
        peoples[people.length] = me;
        final var permutations2 = permutations(peoples.length);
        o.part2 = 0;
        for (int i = 0; i < permutations2.size(); i++) {
            final var ints = permutations2.get(i);
//            var tPeople=new Person[ints.length];
            NodeCircle nodeCircle = new NodeCircle();
            for (int j = 0; j < ints.length; j++) {
                nodeCircle.putNode(new Node(peoples[ints[j]]));
            }
            nodeCircle.sow();
            final var sum = nodeCircle.sum();
            o.part2 = Math.max(o.part2, sum);

        }

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day14() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.\n" +
                "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.";
        if (!testMode) {
            data = getData(day, "2015");
        }
        class Reindeer {
            String name;
            long velocity;
            long runTime;
            long pauseTime;

        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var strings = asLines(data);

        Pattern extract = Pattern.compile("([\\w]+) can fly ([\\d]+) km\\/s for ([\\d]+) seconds, but then must rest for ([\\d]+) seconds\\.");
        final var reindeers = Arrays.stream(strings).map(s -> {
            final var matcher = extract.matcher(s);
            matcher.find();
            final var reindeer = new Reindeer();
            reindeer.name = matcher.group(1);
            reindeer.velocity = Long.parseLong(matcher.group(2));
            reindeer.runTime = Long.parseLong(matcher.group(3));
            reindeer.pauseTime = Long.parseLong(matcher.group(4));
            return reindeer;
        }).toArray(Reindeer[]::new);
        System.out.println(reindeers.length);
        Reindeer fastestReindeer = null;
        long fastestReindeerDistance = 0;
        int allotedTime = 2503;
        int[][] distances = new int[reindeers.length][(allotedTime + 1)];
        for (int i = 0; i < reindeers.length; i++) {
            final var reindeer = reindeers[i];
            final var myDistance = distances[i];
            long distance = 0;
            int currentTime = 0;
            int maxTime = (int) allotedTime;
            while (maxTime > 0) {
                if (maxTime > reindeer.runTime) {
                    maxTime -= reindeer.runTime;
                    for (int j = 0; j < reindeer.runTime; j++) {
                        myDistance[currentTime++] = (int) (distance + reindeer.velocity * j);
                    }
                    distance += reindeer.velocity * reindeer.runTime;
                    if (maxTime > reindeer.pauseTime) {
                        maxTime -= reindeer.pauseTime;
                        for (int j = 0; j < reindeer.pauseTime; j++) {
                            myDistance[currentTime++] = (int) distance;
                        }
                    } else {
                        for (int j = 0; j <= maxTime; j++) {
                            myDistance[currentTime++] = (int) distance;
                        }
                        maxTime = 0;
                    }
                } else {
                    for (int j = 0; j <= maxTime; j++) {
                        myDistance[currentTime++] = (int) (distance + reindeer.velocity * j);
                    }
                    distance += reindeer.velocity * maxTime;
                    maxTime = 0;
                }
            }
            if (distance > fastestReindeerDistance) {
                fastestReindeer = reindeer;
                fastestReindeerDistance = distance;
            }
            System.out.println(reindeer.name + " travelled " + distance);
        }
        int[] scores = new int[distances.length];
        for (int j = 1; j <= allotedTime; j++) {
            int maxValue = 0;
            ArrayList<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < distances.length; i++) {
                if (distances[i][j] > maxValue) {
                    maxValue = distances[i][j];
                    indexes.clear();
                    indexes.add(i);
                } else if (distances[i][j] == maxValue) {
                    indexes.add(i);
                }
            }
            indexes.forEach(i -> scores[i]++);
        }
        System.out.println("The fastest reindeer is " + fastestReindeer.name + " travelled " + fastestReindeerDistance);
        o.part1 = fastestReindeerDistance;
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day15() {
        //using excel
    }

    public static void day16() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "";
        if (!testMode) {
            data = getData(day, "2015");
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        String[] inputs = new String[]{
                "children:",
                "cats:",
                "samoyeds:",
                "pomeranians:",
                "akitas:",
                "vizslas:",
                "goldfish:",
                "trees:",
                "cars:",
                "perfumes:",
        };
        String[] result = new String[]{
                "children: 3",
                "cats: 7",
                "samoyeds: 2",
                "pomeranians: 3",
                "akitas: 0",
                "vizslas: 0",
                "goldfish: 5",
                "trees: 3",
                "cars: 2",
                "perfumes: 1",
        };
        final var strings = asLines(data);
        String[] finalInputs = inputs;
        String[] finalResult = result;
        var count = Arrays.stream(strings).filter(s -> {
            for (int i = 0; i < finalInputs.length; i++) {
                if (s.contains(finalInputs[i])) {
                    if (!s.contains(finalResult[i])) {
                        return false;
                    }
                }
            }
            return true;
        }).findAny().get();
        o.part1 = Long.parseLong(count.replace("Sue ", "").split(":")[0]);

        inputs = new String[]{
                "children:",
                "samoyeds:",
                "akitas:",
                "vizslas:",
                "cars:",
                "perfumes:",
        };
        result = new String[]{
                "children: 3",
                "samoyeds: 2",
                "akitas: 0",
                "vizslas: 0",
                "cars: 2",
                "perfumes: 1",
        };

        String[] finalInputs1 = inputs;
        String[] finalResult1 = result;
        Pattern ext = Pattern.compile("(?=cats: ([\\d]+))|(?=trees: ([\\d]+))|(?=pomeranians: ([\\d]+))|(?=goldfish: ([\\d]+))");
        count = Arrays.stream(strings).filter(s -> {
            for (int i = 0; i < finalInputs1.length; i++) {
                if (s.contains(finalInputs1[i])) {
                    if (!s.contains(finalResult1[i])) {
                        return false;
                    }
                }
            }
            final var matcher = ext.matcher(s);
            while (matcher.find()) {
                if (matcher.group(1) != null) {
                    if (Integer.parseInt(matcher.group(1)) <= 7) {
                        return false;
                    }
                }
                if (matcher.group(2) != null) {
                    if (Integer.parseInt(matcher.group(2)) <= 3) {
                        return false;
                    }
                }
                if (matcher.group(3) != null) {
                    if (Integer.parseInt(matcher.group(3)) >= 3) {
                        return false;
                    }
                }
                if (matcher.group(4) != null) {
                    if (Integer.parseInt(matcher.group(4)) >= 5) {
                        return false;
                    }
                }
            }
            return true;
        }).findAny().get();
        o.part2 = Long.parseLong(count.replace("Sue ", "").split(":")[0]);
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day17() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "";
        if (!testMode) {
            data = getData(day, "2015");
        }
        class RecurseHell {
            int minRecurse = Integer.MAX_VALUE;
            int recurse = 0;
            ArrayList<Integer> integers = new ArrayList<>();

            int countWith(long[] values, long bank) {
                recurse++;
                if (integers.size() < recurse) {
                    integers.add(0);
                }
                int counted = 0;
                for (int i = 0; i < values.length; i++) {
                    if (bank > values[i]) {
                        counted += countWith(Arrays.copyOfRange(values, i + 1, values.length), bank - values[i]);
                    } else if (bank == values[i]) {
                        minRecurse = Math.min(recurse, minRecurse);
                        integers.set(recurse - 1, integers.get(recurse - 1) + 1);
                        counted++;
                    }
                }
                recurse--;
                return counted;
            }
        }

        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var longs = Arrays.stream(asLongs(data)).sorted().toArray();
        final var longs2 = new long[longs.length];
        for (int i = 0; i < longs.length; i++) {
            longs2[longs.length - 1 - i] = longs[i];
        }
        final var recurseHell = new RecurseHell();
        o.part1 = recurseHell.countWith(longs2, 150);
        o.part2 = recurseHell.integers.get(recurseHell.minRecurse - 1);
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day18() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        enum State {
            EMPTY('.'), OCCUPIED('#'), AIR('~');
            final char c;

            State(char c) {
                this.c = c;
            }

            static State of(int i) {
                return switch (i) {
                    case '.' -> EMPTY;
                    case '#' -> OCCUPIED;
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                };
            }
        }
        class Grid implements Iterable<State> {
            final State[][] states;
            final int width;
            final int heigth;
            private final boolean transposed;

            public Grid(State[][] states, boolean transposed) {
                this.states = states;
                if (transposed) {
                    heigth = states.length;
                    width = states[1].length;
                } else {
                    width = states.length;
                    heigth = states[1].length;
                }
                this.transposed = transposed;
            }

            State of(int x, int y) {
                if (x < 0 || y < 0 || y >= heigth || x >= width) {
                    return State.AIR;
                }
                if (transposed) {
                    return states[y][x];
                } else {
                    return states[x][y];
                }
            }


            State[] adjacentsOf(int x, int y) {
                State[] states = new State[8];
                int index = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }
                        states[index++] = of(x + i, y + j);
                    }
                }
                return states;
            }

            State becomes(int x, int y) {
                final State[] states;
                states = adjacentsOf(x, y);
                final var of = of(x, y);
                switch (of) {
                    case EMPTY -> {
                        final var count = Arrays.stream(states).filter(state -> state == State.OCCUPIED).count();
                        if (count == 3) {
                            return State.OCCUPIED;
                        } else {
                            return State.EMPTY;
                        }
                    }
                    case OCCUPIED -> {
                        final var count = Arrays.stream(states).filter(state -> state == State.OCCUPIED).count();
                        if (count == 2 || count == 3) {
                            return State.OCCUPIED;
                        } else {
                            return State.EMPTY;
                        }
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + of);
                }
            }

            @Override
            public Iterator<State> iterator() {
                return null;
            }

            @Override
            public void forEach(Consumer<? super State> action) {
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < heigth; j++) {
                        action.accept(of(i, j));
                    }
                }
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < heigth; i++) {
                    for (int j = 0; j < width; j++) {
                        sb.append(of(j, i).c);
                    }
                    sb.append('\n');
                }
                return sb.toString();
            }

            public void turnOnCorner() {
                states[0][0] = State.OCCUPIED;
                states[0][states[0].length - 1] = State.OCCUPIED;
                states[states.length - 1][0] = State.OCCUPIED;
                states[states.length - 1][states[0].length - 1] = State.OCCUPIED;
            }
        }
        String data = ".#.#.#\n" +
                "...##.\n" +
                "#....#\n" +
                "..#...\n" +
                "#.#..#\n" +
                "####..\n";
        if (!testMode) {
            data = getData(day, "2015");
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };


        final var split = Arrays.stream(Utils.asLines(data)).map(String::chars)
                .map(intStream -> intStream.mapToObj(State::of).toArray(State[]::new)).toArray(State[][]::new);
        Grid grid = new Grid(split, true);
        for (int z = 0; z < 100; z++) {
            State[][] states = new State[grid.width][grid.heigth];
            for (int i = 0; i < grid.width; i++) {
                for (int j = 0; j < grid.heigth; j++) {
                    final var becomes = grid.becomes(i, j);
                    states[i][j] = becomes;
                }
            }
            grid = new Grid(states, false);
        }
        grid.forEach(state -> {
            if (state == State.OCCUPIED) {
                o.part1++;
            }
        });
        grid = new Grid(split, true);
        grid.turnOnCorner();
        for (int z = 0; z < 100; z++) {
            State[][] states = new State[grid.width][grid.heigth];
            for (int i = 0; i < grid.width; i++) {
                for (int j = 0; j < grid.heigth; j++) {
                    final var becomes = grid.becomes(i, j);
                    states[i][j] = becomes;
                }
            }
            grid = new Grid(states, false);
            grid.turnOnCorner();
        }
        grid.forEach(state -> {
            if (state == State.OCCUPIED) {
                o.part2++;
            }
        });
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day19() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "e => H\n" +
                "e => O\n" +
                "H => HO\n" +
                "H => OH\n" +
                "O => HH\n\nHOH";
        if (!testMode) {
            data = getData(day, "2015");
        }
        record KeyValue(String key, String value) {

            void feedPossibility(String s, HashSet<String> strings) {
                int from = 0;
                while (true) {
                    final var i = s.indexOf(key, from);
                    if (i == -1) {
                        break;
                    }
                    strings.add(s.substring(0, i) + value + s.substring(i + key.length()));
                    from = i + 1;
                }
            }

            void feedInvert(String s, HashSet<String> strings) {
                int from = 0;
                while (true) {
                    final var i = s.indexOf(value, from);
                    if (i == -1) {
                        break;
                    }
                    strings.add(s.substring(0, i) + key + s.substring(i + value.length()));
                    from = i + 1;
                }
            }

            String clean(String s) {
                return s.replace(key, "");
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var datass = data.split("\n\n");
        final var molecule = datass[1];
        HashSet<String> strings = new HashSet<>();
        final var keyValues = Arrays.stream(datass[0].split("\n")).map(s -> {
            final var keyValue = s.split(" => ");
            final var key = keyValue[0];
            final var value = keyValue[1];
            final var keyValue2 = new KeyValue(key, value);
            return keyValue2;
        }).toArray(KeyValue[]::new);
        Arrays.stream(keyValues).forEach(keyValue -> keyValue.feedPossibility(molecule, strings));
        o.part1 = strings.size();
        String tMolecule = molecule;

        int offset = 0;
        o.part1 = 0;
        while (!tMolecule.equals("e\n")) {
            boolean changed = false;
            for (int i = offset; i < keyValues.length + offset; i++) {
                final var keyValue = keyValues[i % keyValues.length];
                if (tMolecule.contains(keyValue.value)) {
                    tMolecule = tMolecule.replaceFirst(keyValue.value, keyValue.key);
                    changed = true;
                    break;
                }
            }
            o.part2++;
            if (!changed) {
                offset++;
                System.out.println(tMolecule);
                if (tMolecule.equals("e\n")) {
                    break;
                } else {
                    o.part2 = 0;
                }
                tMolecule = molecule;
            }
        }

//        HashMap<String, String> atomMapping = new HashMap<>();
//        StringBuilder reData = new StringBuilder();
//        String atom = "";
//        for (int i = 0; i < data.length(); i++) {
//            final var c = data.charAt(i);
//            if (c >= 'A' && c <= 'Z') {
//                if (atom.length() == 1) {
//                    if (!atomMapping.containsKey(atom)) {
//                        atomMapping.put(atom, String.valueOf((char) (atomMapping.size() + 'A')));
//                    }
//                    reData.append(atomMapping.get(atom));
//                }
//                atom = "" + c;
//            } else if (c >= 'a' && c <= 'z') {
//                atom += c;
//                if (!atomMapping.containsKey(atom)) {
//                    atomMapping.put(atom, String.valueOf((char) (atomMapping.size() + 'A')));
//                }
//                reData.append(atomMapping.get(atom));
//                atom = "";
//            } else {
//                if (atom.length() == 1) {
//                    if (!atomMapping.containsKey(atom)) {
//                        atomMapping.put(atom, String.valueOf((char) (atomMapping.size() + 'A')));
//                    }
//                    reData.append(atomMapping.get(atom));
//                    atom = "";
//                }
//                reData.append(c);
//            }
//        }
//        if (atom.length() == 1) {
//            if (!atomMapping.containsKey(atom)) {
//                atomMapping.put(atom, String.valueOf((char) (atomMapping.size() + 'A')));
//            }
//            reData.append(atomMapping.get(atom));
//        }
//        System.out.println(reData);
//        final var dataz = reData.toString().split("\n\n");


//        final var molecule2 = dataz[1];
//        final var keyValues2 = Arrays.stream(dataz[0].split("\n")).map(s -> {
//            final var keyValue = s.split(" => ");
//            final var key = keyValue[0];
//            final var value = keyValue[1];
//            final var keyValue2 = new KeyValue(key, value);
//            return keyValue2;
//        }).toArray(KeyValue[]::new);
//        var molecule3 = molecule2;

//        for (int i = 0; i < keyValues2.length; i++) {
//            molecule3 = keyValues2[i].clean(molecule3);
////            System.out.println(molecule3);
//        }

//        final var ints = molecule3.chars().distinct().toArray();
//        var molecule4 = reData.toString();
//        for (int i = 0; i < ints.length; i++) {
//            final var i1 = ((char) ints[i]) + "";
//            molecule4 = molecule4.replace(i1, i1.toLowerCase(Locale.ROOT));
//            System.out.println(i1);
//        }
//        System.out.println(molecule4);
////
//        HashSet<String> nextString = new HashSet<>();
//        nextString.add(atomMapping.get("e"));
//        while (!nextString.contains(molecule2)) {
//            o.part2++;
//            HashSet<String> tStrings = new HashSet<>();
//
//            nextString.forEach(s -> {
//                Arrays.stream(keyValues2).forEach(keyValue -> keyValue.feedPossibility(s, tStrings));
//            });
//
//            System.out.println("Step " + o.part2 + " size " + tStrings.size());
//            if (tStrings.size() > 10000) {
//                final var max = tStrings.stream().mapToInt(s -> measure(molecule2, s)).max().getAsInt() - 3;
//                tStrings.removeIf(s -> measure(molecule2, s) < max);
//                System.out.println("Step " + o.part2 + " size " + tStrings.size() + "[cutoff at " + max + "]");
//                System.out.println(tStrings.stream().iterator().next());
//
//            }
//            nextString = tStrings;
//        }
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day20() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "";
        if (!testMode) {
            data = "33100000";
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;

            public long getSillyDivisorsSum(long n) {
                long sum = 0;
                for (int i = 1; i <= 50; i++) {
                    if (n % i == 0) {
                        // If divisors are equal, print only one

                        sum += n / i;
                    }
                }
                return sum * 11;
            }

            long getDivisorsSum(long n) {
                long sum = 0;
                for (int i = 1; i <= Math.sqrt(n); i++) {
                    if (n % i == 0) {
                        // If divisors are equal, print only one
                        sum += i;
                        if (n / i != i) {
                            sum += n / i;
                        }
                    }
                }
                return sum * 10;
            }
        };

        if (testMode) {
            for (int i = 1; i < 10; i++) {
                System.out.println(o.getDivisorsSum(i));
            }
        }
        long goal = Long.parseLong(data);
        long i = 0;
        long max = 0;
        long val;
        while ((val = o.getDivisorsSum(++i)) <= goal) {
            if (val > max) {
                System.out.println(max = val);
            }
        }
        o.part1 = i;

        i = 0;
        max = 0;
        while ((val = o.getSillyDivisorsSum(++i)) <= goal) {
            if (val > max) {
                System.out.println((max = val) + " at index " + i);
            }
        }
        o.part2 = i;
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }


    public static void day21() {
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
        record Dude(String name, int hitPoint, int dmg, int armor) {

        }
        record Armor(String name, int cost, int armor) {

        }
        record Sword(String name, int cost, int dmg) {

        }
        record Ring(String name, int cost, int dmg, int armor) {

        }
        record Kit(Sword sword, Armor armor, Ring ring1, Ring Ring2) {

        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };

        Sword[] swords = new Sword[]{
                new Sword("Dagger", 8, 4),
                new Sword("Shortsword", 10, 5),
                new Sword("Warhammer", 25, 6),
                new Sword("Longsword", 40, 7),
                new Sword("Greataxe", 74, 8)
        };
        Armor[] armors = new Armor[]{
                new Armor("None", 0, 0),
                new Armor("Leather", 13, 1),
                new Armor("Chainmail", 31, 2),
                new Armor("Splintmail", 53, 3),
                new Armor("Bandedmail", 75, 4),
                new Armor("Platemail", 102, 5),

        };
        Ring[] rings = new Ring[]{
                new Ring("None 1", 0, 0, 0),
                new Ring("None 2", 0, 0, 0),
                new Ring("Damage +1", 25, 1, 0),
                new Ring("Damage +2", 50, 2, 0),
                new Ring("Damage +3", 100, 3, 0),
                new Ring("Defense +1", 20, 0, 1),
                new Ring("Defense +2", 40, 0, 2),
                new Ring("Defense +3", 80, 0, 3),
        };
        long cheapest = Long.MAX_VALUE;
        long mostExpensive = 0;
        for (int i = 0; i < swords.length; i++) {
            for (int j = 0; j < armors.length; j++) {
                for (int k = 0; k < rings.length; k++) {
                    for (int l = 0; l < rings.length; l++) {
                        if (k == l) {
                            continue;
                        }
                        float dmg = swords[i].dmg + rings[k].dmg + rings[l].dmg;
                        float armor = armors[j].armor + rings[k].armor + rings[l].armor;
                        float hp = 100;
                        float bossHp = 109;
                        float bossDmg = 8;
                        float bossArmor = 2;
                        dmg = Math.max(1, dmg - bossArmor);
                        bossDmg = Math.max(1, bossDmg - armor);
                        int cost = swords[i].cost + armors[j].cost + rings[k].cost + rings[l].cost;
                        if (Math.ceil(bossHp / dmg) < Math.ceil(hp / bossDmg)) {
                            if (cost < cheapest) {
                                cheapest = cost;
                            }
                        } else {
                            if (mostExpensive < cost) {
                                mostExpensive = cost;
                            }
                        }
                    }
                }
            }
        }
        o.part1 = cheapest;
        o.part2 = mostExpensive;
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

//    public static void day22() {
//        TikTok tikTok = new TikTok(true);
//        final var day = getDay();
//        boolean verbose = true;
////        boolean verbose = false;
//        boolean testMode = true;
////        boolean testMode = false;
//        String data = "";
//        if (!testMode) {
//            data = getData(day, "2015");
//        }
//        var o = new Object() {
//            long part1 = 0;
//            long part2 = 0;
//        };
//        while (true){
//            int bossDmg = 10;
//            int bossHp = 71;
//
//            int mana = 500;
//            int hp = 50;
//            int armor=0;
//
//            break;
//        }
//
//        endOfWork(tikTok, day, testMode, o.part1, o.part2);
//    }

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
        Pattern extract = Pattern.compile("");
        final var matchers = Arrays.stream(strings).map(s -> {
            final var matcher = extract.matcher(s);
            matcher.find();

            return matcher;
        }).toArray(Matcher[]::new);

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }


}

