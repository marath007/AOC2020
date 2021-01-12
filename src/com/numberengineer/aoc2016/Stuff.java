package com.numberengineer.aoc2016;

import com.numberengineer.aoc.TikTok;

import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "";
        if (!testMode) {
            data = getData(day, "2016");
        }
        enum Action {
            N, E, S, W, L, R, F;
        }
        enum Cardinal {
            N, E, S, W;

            Cardinal right(int deg) {
                final var values = values();
                return values[(this.ordinal() + deg / 90) % values.length];
            }

            Cardinal left(int deg) {
                final var values = values();
                return values[(this.ordinal() + values.length - deg / 90) % values.length];
            }
        }

        record Instruction(Action a, int qty) {

        }

        record Position(int x, int y) {
        }

        class Waypoint {
            int x = 10;
            int y = 1;

            void rotate(int deg) {
                final var x = this.x;
                final var y = this.y;
                deg = (deg + 360) % 360;
                switch (deg) {
                    case 90 -> {
                        this.y = -x;
                        this.x = y;
                    }
                    case 180 -> {
                        this.y = -y;
                        this.x = -x;
                    }
                    case 270 -> {
                        this.y = x;
                        this.x = -y;
                    }
                }
            }
        }

        class Ship {
            int x;
            int y;
            Cardinal direction = Cardinal.E;
            Waypoint waypoint = new Waypoint();
            HashSet<Position> set = new HashSet<>();
            private boolean freeze;

            void processInstruction(Instruction instruction) {
                switch (instruction.a) {
                    case N -> y += instruction.qty;
                    case E -> x += instruction.qty;
                    case S -> y -= instruction.qty;
                    case W -> x -= instruction.qty;
                    case L -> direction = direction.left(instruction.qty);
                    case R -> direction = direction.right(instruction.qty);
                    case F -> {
                        switch (direction) {
                            case N -> y += instruction.qty;
                            case E -> x += instruction.qty;
                            case S -> y -= instruction.qty;
                            case W -> x -= instruction.qty;
                        }
                    }
                }
            }

            void processSafeInstruction(Instruction instruction) {
                if (freeze) return;
                switch (instruction.a) {
                    case N -> y += instruction.qty;
                    case E -> x += instruction.qty;
                    case S -> y -= instruction.qty;
                    case W -> x -= instruction.qty;
                    case L -> direction = direction.left(instruction.qty);
                    case R -> direction = direction.right(instruction.qty);
                    case F -> {
                        for (int i = 0; i < instruction.qty; i++) {
                            switch (direction) {
                                case N -> y++;
                                case E -> x++;
                                case S -> y--;
                                case W -> x--;
                            }
                            if (!set.add(new Position(x, y))) {
                                freeze = true;
                                return;
                            }
                        }

                    }
                }
            }

            int getManateeDistance() {
                return Math.abs(x) + Math.abs(y);
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        Ship ship = new Ship();
        ArrayList<Instruction> instructions = new ArrayList<>();
        Arrays.stream(data.split(",")).map(String::trim).forEach(s -> {
            Instruction instruction;
            final var i = Integer.parseInt(s.substring(1));
            if (s.charAt(0) == 'R') {
                instructions.add(new Instruction(Action.R, 90));
                instructions.add(new Instruction(Action.F, i));
            } else {
                instructions.add(new Instruction(Action.L, 90));
                instructions.add(new Instruction(Action.F, i));
            }
        });
        instructions.forEach(ship::processInstruction);
        o.part1 = ship.getManateeDistance();
        ship = new Ship();
        instructions.forEach(ship::processSafeInstruction);
        o.part2 = ship.getManateeDistance();
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day2() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "ULL\n" +
                "RRDDD\n" +
                "LURDL\n" +
                "UUUUD";
        if (!testMode) {
            data = getData(day, "2016");
        }
        final class Position {
            private int x;
            private int y;

            Position(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public int x() {
                return x;
            }

            public int y() {
                return y;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this) return true;
                if (obj == null || obj.getClass() != this.getClass()) return false;
                var that = (Position) obj;
                return this.x == that.x &&
                        this.y == that.y;
            }

            @Override
            public int hashCode() {
                return Objects.hash(x, y);
            }

            @Override
            public String toString() {
                return "Position[" +
                        "x=" + x + ", " +
                        "y=" + y + ']';
            }

        }
        class Keypad {
            char[][] keys;
            Position cursor = new Position(1, 1);

            public Keypad(String s) {
                final var pad = s.split("\n");
                keys = new char[pad.length][];
                for (int i = 0; i < pad.length; i++) {
                    final var s1 = pad[i];
                    char[] chars = new char[(s1.length() + 1) / 2];
                    for (int j = 0, k = 0; j < s1.length(); j += 2, k++) {
                        chars[k] = s1.charAt(j);
                    }
                    keys[i] = chars;
                }
                for (int i = 0; i < keys.length; i++) {
                    for (int j = 0; j < keys[i].length; j++) {
                        if ('5' == keys[i][j]) {
                            cursor.x = j;
                            cursor.y = i;
                            return;
                        }
                    }
                }
            }

            char getCurrentKey() throws IndexOutOfBoundsException {
                return keys[cursor.y][cursor.x];
            }

            void readChar(int a) {
                readChar((char) a);
            }

            void readChar(char a) {
                final var x = cursor.x;
                final var y = cursor.y;
                switch (a) {
                    case 'U' -> cursor.y--;
                    case 'R' -> cursor.x++;
                    case 'D' -> cursor.y++;
                    case 'L' -> cursor.x--;
                }
                try {
                    if (getCurrentKey() == ' ') {
                        cursor.x = x;
                        cursor.y = y;
                    }
                } catch (IndexOutOfBoundsException e) {
                    cursor.x = x;
                    cursor.y = y;
                }
            }

        }
        var o = new Object() {
            String part1 = "";
            String part2 = "";
        };
        final var strings = asLines(data);
        Keypad keypad = new Keypad("1 2 3\n" +
                "4 5 6\n" +
                "7 8 9");
        for (String string : strings) {
            string.chars().forEach(keypad::readChar);
            o.part1 += keypad.getCurrentKey();
        }
        keypad = new Keypad(
                "    1\n" +
                        "  2 3 4\n" +
                        "5 6 7 8 9\n" +
                        "  A B C\n" +
                        "    D");
        for (String string : strings) {
            string.chars().forEach(keypad::readChar);
            o.part2 += keypad.getCurrentKey();
        }
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day3() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "5 10 25\n" +
                "25 10 5\n" +
                "823  909    9";
        if (!testMode) {
            data = getData(day, "2016");
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var strings = asLines(data);
        Arrays.stream(strings).forEach(s -> {
            s = s.trim();
            final var ints = Arrays.stream(s.split("[ ]+")).mapToInt(Integer::parseInt).toArray();
            final var intSummaryStatistics = Arrays.stream(ints).summaryStatistics();
            final var sum = intSummaryStatistics.getSum();
            for (int anInt : ints) {
                if (sum <= anInt * 2L) {
                    return;
                }
            }
            o.part1++;
        });
        final var ls = Arrays.stream(data.replace("\n", " ").split("[ ]+")).filter(s -> !s.isEmpty()).mapToLong(Long::parseLong).toArray();
        for (int i = 0; i < ls.length; i += 9) {
            for (int j = 0; j < 3; j++) {
                long[] ints = new long[]{ls[j + i], ls[j + i + 3], ls[j + i + 6]};
                final var intSummaryStatistics = Arrays.stream(ints).summaryStatistics();
                final var sum = intSummaryStatistics.getSum();
                boolean valid = true;
                for (long anInt : ints) {
                    if (sum <= anInt * 2L) {
                        valid = false;
                        break;
                    }
                }
                o.part2 += valid ? 1 : 0;
            }
        }

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day4() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "aaaaa-bbb-z-y-x-123[abxyz]\n" +
                "a-b-c-d-e-f-g-h-987[abcde]\n" +
                "not-a-real-room-404[oarel]\n" +
                "totally-real-room-200[decoy]";
        if (!testMode) {
            data = getData(day, "2016");
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        Pattern extract = Pattern.compile("([a-z]+)([0-9]+)\\[([a-z]+)]");
        final var strings = asLines(data);

//        final var longs = asLongs(data);
        final var matchers = Arrays.stream(strings).map(s -> {
            s = s.replace("-", "");
            final var matcher = extract.matcher(s);
            matcher.find();

            return matcher;
        }).toArray(Matcher[]::new);
        Arrays.stream(matchers).forEach(m -> {
            String name = m.group(1);
            long roomID = Long.parseLong(m.group(2));
            String hash = m.group(3);
            final var collect = name.chars().boxed().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
            final var computedHash = collect.entrySet().stream().sorted(Comparator.comparingInt(e -> e.getKey())).sorted(Comparator.comparingInt(e -> -e.getValue().intValue())).map(i -> "" + (char) i.getKey().intValue()).limit(5).reduce((l, r) -> l + r).get();
            if (computedHash.equals(hash)) {
                o.part1 += roomID;
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < name.length(); i++) {
                    final var c = name.charAt(i);
                    stringBuilder.append((char) ((((c - 'a') + roomID) % 26) + 'a'));
                }
                if (stringBuilder.toString().contains("north")) {
                    o.part2 = roomID;
                }
//                System.out.println(stringBuilder+ " id be: "+ roomID);
            }
        });

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day5() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "abc";
        if (!testMode) {
            data = "ffykfhsq";
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        class Hasher {
            MessageDigest md = null;

            public Hasher() {
                try {
                    md = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }

            String getHash(String password) {
                md.update(password.getBytes());
                byte[] digest = md.digest();
                return toHexString(digest);
            }

            String getHashIfRight(String password, int zeroCountsAtStart) {
                md.update(password.getBytes());
                byte[] digest = md.digest();
                int zeroCnt = 0;
                for (final byte b : digest) {
                    if (b >> 4 == 0) {
                        zeroCnt++;
                    } else {
                        break;
                    }
                    if ((b & 0xF) == 0) {
                        zeroCnt++;
                    } else {
                        break;
                    }
                }
                if (zeroCnt >= zeroCountsAtStart) {
                    return toHexString(digest);
                } else {
                    return "";
                }
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
            String part1 = "";
            String part2 = "";
        };
        final var hasher = new Hasher();

        int cnt = 0;
        while (o.part1.length() != 8) {
            String hash;
            while ((hash = hasher.getHashIfRight(data + (++cnt), 5)).isEmpty()) {

            }
            cnt++;
            o.part1 += hash.charAt(5);
            System.out.println(o.part1);

        }
        cnt = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(" ");
        }
        while (sb.toString().contains(" ")) {
            String hash;
            while ((hash = hasher.getHashIfRight(data + (++cnt), 5)).isEmpty()) {
            }
            cnt++;
            final var c = hash.charAt(5);
            if (c < '0' || c > '7') {
                continue;
            }
            final var index = c - '0';
            if (sb.charAt(index) == ' ') {
                sb.setCharAt(index, hash.charAt(6));
            }
            System.out.println(sb);

        }
        o.part2 = sb.toString();
//        final var strings = asLines(data);
//        final var longs = asLongs(data);
//        Pattern extract = Pattern.compile("");
//        final var matchers = Arrays.stream(strings).map(s -> {
//            final var matcher = extract.matcher(s);
//            matcher.find();
//
//            return matcher;
//        }).toArray(Matcher[]::new);

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day6() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "eedadn\n" +
                "drvtee\n" +
                "eandsr\n" +
                "raavrd\n" +
                "atevrs\n" +
                "tsrnev\n" +
                "sdttsa\n" +
                "rasrtv\n" +
                "nssdts\n" +
                "ntnada\n" +
                "svetve\n" +
                "tesnvt\n" +
                "vntsnd\n" +
                "vrdear\n" +
                "dvrsen\n" +
                "enarar";
        if (!testMode) {
            data = getData(day, "2016");
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        var o = new Object() {
            String part1 = "";
            String part2 = "";
        };
        final var strings = asLines(data);
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        for (int i = 0; i < strings[0].length(); i++) {
            int finalI = i;
            final var collect = Arrays.stream(strings).map(s -> (Integer) (int) (s.charAt(finalI))).collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
            final var c = (char) collect.entrySet().stream().max(Comparator.comparingInt(e -> e.getValue().intValue())).get().getKey().intValue();
            final var c2 = (char) collect.entrySet().stream().min(Comparator.comparingInt(e -> e.getValue().intValue())).get().getKey().intValue();
            stringBuilder.append(c);
            stringBuilder2.append(c2);
            System.out.println(stringBuilder + "\t\t" + stringBuilder2);
        }
        o.part1 = stringBuilder.toString();
        o.part2 = stringBuilder2.toString();


//        final var longs = asLongs(data);
//        Pattern extract = Pattern.compile("");
//        final var matchers = Arrays.stream(strings).map(s -> {
//            final var matcher = extract.matcher(s);
//            matcher.find();
//
//            return matcher;
//        }).toArray(Matcher[]::new);

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day7() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "aba[bab]xyz\n" +
                "xyx[xyx]xyx\n" +
                "aaa[kek]eke\n" +
                "zazbz[bzb]cdb";
        if (!testMode) {
            data = getData(day, "2016");
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;

            String[] getABA(String s) {
                ArrayList<String> strings = new ArrayList<>();
                char lastChar = 0;
                for (int i = 0; i < s.length(); i++) {
                    final var c = s.charAt(i);
                    if (c != 0) {
                        if (lastChar != c) {
                            if (s.length() - i > 1) {
                                if (s.charAt(i + 1) == lastChar) {
                                    strings.add("" + lastChar + c + lastChar);
                                }
                            }
                        }
                    }
                    lastChar = c;
                }
                return strings.toArray(String[]::new);
            }

            String[] getBAB(String s) {
                final var aba = getABA(s);
                String[] bab = new String[aba.length];
                for (int i = 0; i < aba.length; i++) {
                    final var bab1 = aba[i];
                    char a = bab1.charAt(1);
                    char b = bab1.charAt(0);
                    bab[i] = "" + a + b + a;
                }
                return bab;
            }
        };
        final var strings = asLines(data);
        Arrays.stream(strings).forEach(s -> {
            boolean interiorValid1 = true;
            boolean exteriorValid1 = false;
            boolean insideBracket = false;
            char lastChar = 0;
            for (int i = 0; i < s.length(); i++) {
                final var c = s.charAt(i);
                switch (c) {
                    case '[' -> {
                        insideBracket = true;
                        lastChar = 0;
                    }
                    case ']' -> {
                        insideBracket = false;
                        lastChar = 0;
                    }
                    case 0 -> lastChar = c;
                    default -> {
                        if (lastChar != c) {
                            if (s.length() - i > 2) {
                                if (s.charAt(i + 1) == c) {
                                    if (s.charAt(i + 2) == lastChar) {
                                        if (insideBracket) {
                                            interiorValid1 = false;
                                        } else {
                                            exteriorValid1 = true;
                                        }
                                    }
                                }
                            }
                        }
                        lastChar = c;
                    }
                }
            }
            if (interiorValid1 && exteriorValid1) {
                o.part1++;
            }
        });
//        final var longs = asLongs(data);
        Pattern extract = Pattern.compile("(?:\\[([\\w]+)])|(?:(?<![^]])([\\w]+))");
        Arrays.stream(strings).forEach(s -> {
            final var m = extract.matcher(s);
            final var aba = new ArrayList<String>();
            final var bab = new ArrayList<String>();
            m.results().forEach(matchResult -> {
                if (matchResult.group(1) != null) {
                    aba.addAll(Arrays.asList(o.getABA(matchResult.group(1))));
                } else {
                    bab.addAll(Arrays.asList(o.getBAB(matchResult.group(2))));
                }
            });
            if (aba.stream().anyMatch(s1 -> bab.stream().anyMatch(s2 -> s2.equals(s1)))) {
                o.part2++;
            }
        });

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day8() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "rect 3x2\n" +
                "rotate column x=1 by 1\n" +
                "rotate row y=0 by 4\n" +
                "rotate column x=1 by 1";
        if (!testMode) {
            data = getData(day, "2016");
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        class Screen {
            final boolean[][] lights;

            public Screen(int height, int width) {
                this.lights = new boolean[height][width];
            }

            public void lightUp(int width, int height) {
                for (int i = 0; i < height; i++) {
                    final var light = lights[i];
                    for (int j = 0; j < width; j++) {
                        light[j] = true;
                    }
                }
            }

            public void rotateAtX(int x, int by) {
                boolean[] newBool = new boolean[lights.length];
                for (int i = 0; i < lights.length; i++) {
                    newBool[(i + by) % newBool.length] = lights[i][x];
                }
                for (int i = 0; i < lights.length; i++) {
                    lights[i][x] = newBool[i];
                }
            }

            public void rotateAtY(int y, int by) {
                boolean[] newBool = new boolean[lights[y].length];
                for (int j = 0; j < lights[y].length; j++) {
                    newBool[(j + by) % newBool.length] = lights[y][j];
                }
                System.arraycopy(newBool, 0, lights[y], 0, lights[y].length);
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < lights.length; i++) {
                    final var light = lights[i];
                    for (int j = 0; j < light.length; j++) {
                        sb.append(light[j] ? '#' : '.');
                    }
                    sb.append('\n');
                }
                return sb.toString();
            }

            public int brightness() {
                int cnt = 0;
                for (int i = 0; i < lights.length; i++) {
                    final var light = lights[i];
                    for (int j = 0; j < light.length; j++) {
                        cnt += light[j] ? 1 : 0;
                    }
                }
                return cnt;
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        Screen screen = new Screen(6, 50);
//        System.out.println(screen);
//        screen.lightUp(3, 2);
//        System.out.println(screen);
//        screen.rotateAtX(1,1);
//        System.out.println(screen);
//        screen.rotateAtY(0,4);
//        System.out.println(screen);
//        screen.rotateAtX(1,1);
//        System.out.println(screen);
        final var strings = asLines(data);
//        final var longs = asLongs(data);
        Pattern extract = Pattern.compile("(?:rect (\\d+)x(\\d+))|(?:rotate column x=(\\d+) by (\\d+))|(?:rotate row y=(\\d+) by (\\d+))");
        System.out.println(screen);
        final var matchers = Arrays.stream(strings).map(s -> {
            final var matcher = extract.matcher(s);
            matcher.find();
            if (matcher.group(1) != null) {
                screen.lightUp(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
            } else if (matcher.group(3) != null) {
                screen.rotateAtX(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
            } else if (matcher.group(5) != null) {
                screen.rotateAtY(Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
            }
            System.out.println(screen);

            return matcher;
        }).toArray(Matcher[]::new);
        o.part1 = screen.brightness();
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day9() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "(6x1)(1x3)A";
        if (!testMode) {
            data = getData(day, "2016");
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        data = data.replace("\n", "");
        StringBuilder stringBuilder = new StringBuilder();
        int compressionIndex = -1;
        for (int i = 0; i < data.length(); i++) {
            final var c = data.charAt(i);
            switch (c) {
                case '(' -> compressionIndex = i;
                case ')' -> {
                    if (compressionIndex != -1) {
                        final var xoxox = data.substring(compressionIndex + 1, i).split("x");
                        int length = Integer.parseInt(xoxox[0]);
                        int cnt = Integer.parseInt(xoxox[1]);
                        final var substring = data.substring(i + 1, i + 1 + length).repeat(cnt);
                        stringBuilder.append(substring);
                        compressionIndex = -1;
                        i += length;
                    }
                }
                default -> {
                    if (compressionIndex == -1) {
                        stringBuilder.append(c);
                    }
                }
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;

            long decompressedLength(String s) {
                long size = 0;
                int compressionIndex = -1;
                for (int i = 0; i < s.length(); i++) {
                    final var c = s.charAt(i);
                    switch (c) {
                        case '(' -> compressionIndex = i;
                        case ')' -> {
                            if (compressionIndex != -1) {
                                final var xoxox = s.substring(compressionIndex + 1, i).split("x");
                                int length = Integer.parseInt(xoxox[0]);
                                int cnt = Integer.parseInt(xoxox[1]);
                                final var substring1 = s.substring(i + 1, i + 1 + length);
                                size += decompressedLength(substring1) * cnt;
                                compressionIndex = -1;
                                i += length;
                            }
                        }
                        default -> {
                            if (compressionIndex == -1) {
                                size++;
                            }
                        }
                    }
                }
                return size;
            }

        };

        o.part1 = stringBuilder.toString().length();
        o.part2 = o.decompressedLength(data);
//        final var strings = asLines(data);
//        final var longs = asLongs(data);
//        Pattern extract = Pattern.compile("");
//        final var matchers = Arrays.stream(strings).map(s -> {
//            final var matcher = extract.matcher(s);
//            matcher.find();
//
//            return matcher;
//        }).toArray(Matcher[]::new);

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day10() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "value 5 goes to bot 2\n" +
                "bot 2 gives low to bot 1 and high to bot 0\n" +
                "value 3 goes to bot 1\n" +
                "bot 1 gives low to output 1 and high to bot 0\n" +
                "bot 0 gives low to output 2 and high to output 0\n" +
                "value 2 goes to bot 2";
        if (!testMode) {
            data = getData(day, "2016");
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        abstract class Entity {
            final int id;

            protected Entity(int id) {
                this.id = id;
            }

            @Override
            public String toString() {
                return
                        "id=" + id;
            }
        }
        class Bot extends Entity {
            Entity low;
            Entity high;

            Bot(int id) {
                super(id);
            }
        }
        class Output extends Entity {
            int value;

            Output(int id) {
                super(id);
            }
        }
        class Value extends Entity {
            Entity entity;

            Value(int id) {
                super(id);
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var strings = asLines(data);
//        final var longs = asLongs(data);
        HashMap<Integer, Bot> botHashMap = new HashMap<>();
        HashMap<Integer, Output> outputHashMap = new HashMap<>();
        HashMap<Integer, Value> valueHashMap = new HashMap<>();
        Pattern extract = Pattern.compile("(?:value (\\d+) goes to bot (\\d+))|(?:bot (\\d+) gives low to ((?:bot)|(?:output)) (\\d+) and high to ((?:bot)|(?:output)) (\\d+))");
        final var matchers = Arrays.stream(strings).map(s -> {
            final var matcher = extract.matcher(s);
            matcher.find();
            if (matcher.group(1) != null) {
                final var id = Integer.parseInt(matcher.group(1));
                valueHashMap.put(id, new Value(id));
                final var i = Integer.parseInt(matcher.group(2));
                botHashMap.put(i, new Bot(i));
            } else if (matcher.group(3) != null) {
                var i = Integer.parseInt(matcher.group(3));
                botHashMap.put(i, new Bot(i));
                if (matcher.group(4).equals("bot")) {
                    i = Integer.parseInt(matcher.group(5));
                    botHashMap.put(i, new Bot(i));
                } else {
                    i = Integer.parseInt(matcher.group(5));
                    outputHashMap.put(i, new Output(i));
                }
                if (matcher.group(6).equals("bot")) {
                    i = Integer.parseInt(matcher.group(7));
                    botHashMap.put(i, new Bot(i));
                } else {
                    i = Integer.parseInt(matcher.group(7));
                    outputHashMap.put(i, new Output(i));
                }
            }
            return matcher;
        }).toArray(Matcher[]::new);
        Arrays.stream(matchers).forEach(matcher -> {
            if (matcher.group(1) != null) {
                final var id = Integer.parseInt(matcher.group(1));
                final var i = Integer.parseInt(matcher.group(2));
                valueHashMap.get(id).entity = botHashMap.get(i);
            } else if (matcher.group(3) != null) {
                var i = Integer.parseInt(matcher.group(3));
                final var bot = botHashMap.get(i);

                if (matcher.group(4).equals("bot")) {

                    i = Integer.parseInt(matcher.group(5));
                    bot.low = botHashMap.get(i);

                } else {
                    i = Integer.parseInt(matcher.group(5));
                    bot.low = outputHashMap.get(i);
                }
                if (matcher.group(6).equals("bot")) {
                    i = Integer.parseInt(matcher.group(7));
                    bot.high = botHashMap.get(i);
                } else {
                    i = Integer.parseInt(matcher.group(7));
                    bot.high = outputHashMap.get(i);
                }
            }
        });

        HashMap<Bot, ArrayList<Entity>> botToValue = new HashMap<>();
        botHashMap.values().forEach(bot -> botToValue.put(bot, new ArrayList<>()));
        valueHashMap.values().forEach(value -> {
            final var values = botToValue.get(value.entity);
//            if (values != null) {
            values.add(value);
//            } else {
//                final var value1 = new ArrayList<Entity>();
//                value1.add(value);
//                entityToValues.put(value.entity, value1);
//            }
        });
        while (true) {
            HashMap<Bot, ArrayList<Entity>> entityToProcess = new HashMap<>();
            botToValue.forEach((key, value) -> {
                if (value.size() == 2) {
                    entityToProcess.put(key, value);
                }
            });
            entityToProcess.forEach((key, value) -> {
                final var e1 = value.get(0);
                final var e2 = value.get(1);

                value.clear();
                if (e1.id > e2.id) {
                    if (e1.id == 61 && e2.id == 17) {
                        o.part1 = key.id;
                    }
                    if (key.low instanceof Bot) {
                        botToValue.get(key.low).add(e2);
                    } else {
                        ((Output) key.low).value = e2.id;
                    }
                    if (key.high instanceof Bot) {
                        botToValue.get(key.high).add(e1);
                    } else {
                        ((Output) key.high).value = e1.id;
                    }
                } else {
                    if (e1.id == 17 && e2.id == 61) {
                        o.part1 = key.id;
                    }
                    if (key.low instanceof Bot) {
                        botToValue.get(key.low).add(e1);
                    } else {
                        ((Output) key.low).value = e1.id;
                    }
                    if (key.high instanceof Bot) {
                        botToValue.get(key.high).add(e2);
                    } else {
                        ((Output) key.high).value = e2.id;
                    }
                }
            });
            if (entityToProcess.size() == 0) {
                break;
            }
        }
        o.part2 = outputHashMap.get(0).value * outputHashMap.get(1).value * outputHashMap.get(2).value;
//        entityToValues.get(valueHashMap.get(61).entity)
//        valueHashMap.get(61);
//        valueHashMap.get(67);
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day11() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "F4 .  .  .  .  .  \n" +
                "F3 .  .  .  LG .  \n" +
                "F2 .  HG .  .  .  \n" +
                "F1 E  .  HM .  LM ";
        if (!testMode) {
            data = getData(day, "2016");
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        class Machine {
            final String name;
            final int hash;

            Machine(String name, int hash) {
                this.name = name;
                this.hash = hash;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null) return false;
                Machine machine = (Machine) o;
                return Objects.equals(name, machine.name);
            }

            @Override
            public int hashCode() {
                return hash;
            }
        }
        class MicroChip extends Machine {

            MicroChip(String name, int hash) {
                super(name, hash);
            }
        }
        class Generator extends Machine {

            Generator(String name, int hash) {
                super(name, hash);
            }
        }
        class Floor {
            ArrayList<Generator> generators = new ArrayList<>();
            ArrayList<MicroChip> microChips = new ArrayList<>();

            void remove(Machine machine) {
                if (machine instanceof Generator) {
                    generators.remove(machine);
                } else {
                    microChips.remove(machine);
                }
            }

            void add(Machine machine) {
                if (machine instanceof Generator g) {
                    generators.add((Generator) machine);
                } else {
                    microChips.add((MicroChip) machine);
                }
            }

            boolean safe() {
                if (generators.size() != 0) {
                    return generators.containsAll(microChips);
                } else {
                    return true;
                }
            }

            @Override
            protected Floor clone() {
                Floor floor = new Floor();
                floor.generators = new ArrayList<>(generators);
                floor.microChips = new ArrayList<>(microChips);
                return floor;
            }

            @Override
            public String toString() {

                return "G".repeat(generators.size()) + "M".repeat(microChips.size());
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                return o.hashCode() == hashCode();
            }

            @Override
            public int hashCode() {
                int code = 0;
                for (int i = 0; i < generators.size(); i++) {
                    code += generators.get(i).hashCode();
                }
                for (int i = 0; i < microChips.size(); i++) {
                    code += microChips.get(i).hashCode();
                }
                return code;
            }
        }
        class Building {
            int elevatorIndex = 0;
            transient boolean evaluated = false;
            int todos = 0;
            boolean safe = false;
            Floor[] floors;

            public Building(int size) {
                this.floors = new Floor[size];
                Arrays.setAll(floors, i -> new Floor());
            }

            public int todos() {
                evaluate();
                return todos;
            }

            private void evaluate() {
                if (evaluated) {
                    return;
                }
                safe = Arrays.stream(floors).allMatch(Floor::safe);
                todos = 0;
                if (safe) {
                    for (int i = 0; i < floors.length - 1; i++) {
                        final var floor = floors[i];
                        todos += (floors.length - i - 1) * floor.microChips.size();
                        todos += (floors.length - i - 1) * floor.generators.size();
                    }
                }
            }

            public Floor currentFloor() {
                return floors[elevatorIndex];
            }

            public Floor floorOver() {
                if (elevatorIndex + 1 >= floors.length) {
                    return null;
                }
                return floors[elevatorIndex + 1];
            }

            public Floor floorUnder() {
                if (elevatorIndex <= 0) {
                    return null;
                }
                return floors[elevatorIndex - 1];
            }

            public boolean safe() {
                evaluate();
                return safe;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Building building = (Building) o;
                return elevatorIndex == building.elevatorIndex && Arrays.equals(floors, building.floors);
            }

            @Override
            public int hashCode() {
                int result = Objects.hash(elevatorIndex);
                result = 31 * result + Arrays.hashCode(floors);
                return result;
            }

            @Override
            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("###########\n");
                if (!safe()) {
                    stringBuilder.append("## DEATH ##\n");
                } else {
                    stringBuilder.append("todos " + todos() + "\n");
                }
                for (int i = floors.length - 1; i >= 0; i--) {
                    stringBuilder.append(elevatorIndex == i ? "E " : ". ");
                    stringBuilder.append(floors[i].toString());
                    stringBuilder.append("\n");
                }
                stringBuilder.append("###########\n");
                return stringBuilder.toString();
            }

            @Override
            protected Building clone() {
                Building building = new Building(floors.length);
                Arrays.setAll(building.floors, i -> new Floor());
                for (int i = 0; i < floors.length; i++) {
                    building.floors[i] = floors[i].clone();
                }
                building.elevatorIndex = elevatorIndex;
                return building;
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var strings = asLines(data);
        Building building = new Building(strings.length);
        Pattern extract = Pattern.compile("(?:(\\w+) (?:generator))|(?:(\\w+)(?:-compatible microchip))");
        int hash = 1;
        for (int i = 0; i < strings.length; i++) {
            final var matcher = extract.matcher(strings[i]);
            final var floor = building.floors[i];
            while (matcher.find()) {
                if (matcher.group(1) != null) {
                    floor.generators.add(new Generator(matcher.group(1), hash <<= 1));
                } else {
                    floor.microChips.add(new MicroChip(matcher.group(2), hash <<= 1));
                }
            }
        }

        HashSet<Building> buildings = new HashSet<>();
        var recursor = new Object() {
            void scan(Building building, ConcurrentHashMap<Building, Building> workSet) {
                if (buildings.contains(building)) {
                    return;
                } else {
                    buildings.add(building);
                    if (!building.safe()) {
                        return;
                    }
                    final var floor = building.currentFloor();
                    for (int i = 0; i < floor.generators.size(); i++) {
                        final var clone = building.clone();
                        final var generator = floor.generators.get(i);
                        clone.currentFloor().generators.remove(generator);
                        final var clone1 = clone.clone();
                        final var floorUnder = clone.floorUnder();
                        if (floorUnder != null) {
                            floorUnder.generators.add(generator);
                            clone.elevatorIndex--;
                            workSet.put(clone, building);
                        }
                        final var floorOver = clone1.floorOver();
                        if (floorOver != null) {
                            floorOver.generators.add(generator);
                            clone1.elevatorIndex++;
                            workSet.put(clone1, building);

                        }
                    }
                    for (int j = 0; j < floor.microChips.size(); j++) {
                        final var clone = building.clone();
                        final var microChip = floor.microChips.get(j);
                        clone.currentFloor().microChips.remove(microChip);
                        final var clone1 = clone.clone();
                        final var floorUnder = clone.floorUnder();
                        if (floorUnder != null) {
                            floorUnder.microChips.add(microChip);
                            clone.elevatorIndex--;
                            workSet.put(clone, building);
                        }
                        final var floorOver = clone1.floorOver();
                        if (floorOver != null) {
                            floorOver.microChips.add(microChip);
                            clone1.elevatorIndex++;
                            workSet.put(clone1, building);
                        }
                    }
                    ArrayList<Machine> machines = new ArrayList<>();
                    machines.addAll(floor.microChips);
                    machines.addAll(floor.generators);
                    for (int i = 0; i < machines.size(); i++) {
                        for (int j = i; j < machines.size(); j++) {
                            if (j == i) {
                                continue;
                            } else {
                                final var clone = building.clone();
                                clone.currentFloor().remove(machines.get(j));
                                clone.currentFloor().remove(machines.get(i));
                                final var clone1 = clone.clone();
                                if (clone.floorUnder() != null) {
                                    clone.floorUnder().add(machines.get(i));
                                    clone.floorUnder().add(machines.get(j));
                                    clone.elevatorIndex--;
                                    workSet.put(clone, building);
                                }
                                if (clone1.floorOver() != null) {
                                    clone1.floorOver().add(machines.get(i));
                                    clone1.floorOver().add(machines.get(j));
                                    clone1.elevatorIndex++;
                                    workSet.put(clone1, building);
                                }
                            }
                        }
                    }
                }
            }
        };

//        HashSet<Building> workset = new HashSet<>();
//        recursor.scan(building, workset);
        int step = 0;
        ConcurrentHashMap<Building, Building> newWorkSet = new ConcurrentHashMap<>();
        newWorkSet.put(building, building);
        ArrayList<ConcurrentHashMap<Building, Building>> maps = new ArrayList<>();

        while (o.part1 == 0) {
            int finalStep = step;
            ConcurrentHashMap<Building, Building> nextSet = new ConcurrentHashMap<>();
            maps.add(newWorkSet);
            System.out.println("Step " + step);
            System.out.println(newWorkSet.keySet().stream().filter(Building::safe).min(Comparator.comparingInt(Building::todos)).get().todos);
            System.out.println("Processing " + newWorkSet.size());
            final var buildings1 = newWorkSet.keySet().stream().filter(Building::safe).toArray(Building[]::new);
            Arrays.stream(buildings1).parallel().forEach(building1 -> {
                if (building1.todos() == 0) {
                    o.part1 = finalStep;
                    Building building2 = building1;
                    System.out.println(building2);
                    for (int i = maps.size() - 1; i >= 0; i--) {
                        building2 = maps.get(i).get(building2);
                        System.out.println(building2);
                    }
                }
                recursor.scan(building1, nextSet);
            });
            newWorkSet = nextSet;
            System.out.println("Processed " + newWorkSet.size());
            step++;
        }


        newWorkSet.clear();
        newWorkSet.put(building, building);
        building.currentFloor().add(new MicroChip("Elerium", hash <<= 1));
        building.currentFloor().add(new Generator("Elerium", hash <<= 1));
        building.currentFloor().add(new MicroChip("dilithium", hash <<= 1));
        building.currentFloor().add(new Generator("dilithium", hash <<= 1));
        step = 0;
        while (o.part2 == 0) {
            int finalStep = step;
            ConcurrentHashMap<Building, Building> nextSet = new ConcurrentHashMap<>();
            System.out.println("Step " + step);
            final var minToDo = newWorkSet.keySet().stream().filter(Building::safe).min(Comparator.comparingInt(Building::todos)).get().todos;
            System.out.print(minToDo);
            System.out.print("->");
            System.out.println(newWorkSet.keySet().stream().filter(Building::safe).max(Comparator.comparingInt(Building::todos)).get().todos);
            System.out.println("Processing " + newWorkSet.size());
            final var buildings1 = newWorkSet.keySet().stream().filter(Building::safe)
                    .filter(b -> b.todos() < minToDo + 8)
//                    .sorted(Comparator.comparingInt(Building::todos))
                    .toArray(Building[]::new);
            Arrays.stream(buildings1).parallel().forEach(building1 -> {
                if (building1.todos() == 0) {
                    o.part2 = finalStep;
                }
                recursor.scan(building1, nextSet);
            });
            newWorkSet = nextSet;
            System.out.println("Processed " + newWorkSet.size());
            step++;
        }

//        final var longs = asLongs(data);
//        final var matchers = Arrays.stream(strings).map(s -> {
//            final var matcher = extract.matcher(s);
//            matcher.find();
//
//            return matcher;
//        }).toArray(Matcher[]::new);

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day12() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "cpy 41 a\n" +
                "inc a\n" +
                "inc a\n" +
                "dec a\n" +
                "jnz a 2\n" +
                "dec a";
        if (!testMode) {
            data = getData(day, "2016");
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        record Result(int value, boolean terminated) {

        }


        record Instruction(String opCode, int line_no, String... values) {

            private Result getProgramResult(Instruction[] instructions, HashMap<String, Integer> register) {
                Predicate<String> digit = Pattern.compile("^[\\d]+$").asMatchPredicate();
                int counter = 0;
                for (int i = 0; i < instructions.length; i++, counter++) {
                    final var instruction = instructions[i];
                    switch (instruction.opCode()) {
                        case "cpy" -> {
                            if (digit.test(instruction.values[0])) {
                                register.put(instruction.values[1], Integer.parseInt(instruction.values[0]));
                            } else {
                                register.put(instruction.values[1], register.getOrDefault(instruction.values[0], 0));
                            }
                        }
                        case "inc" -> register.put(instruction.values[0], register.getOrDefault(instruction.values[0], 0) + 1);
                        case "dec" -> register.put(instruction.values[0], register.getOrDefault(instruction.values[0], 0) - 1);
                        case "jnz" -> {
                            boolean nonZero = false;
                            if (digit.test(instruction.values[0])) {
                                nonZero = Integer.parseInt(instruction.values[0]) != 0;
                            } else {
                                nonZero = register.getOrDefault(instruction.values[0], 0) != 0;
                            }
                            if (nonZero) {
                                i += Integer.parseInt(instruction.values[1]);
                                i--;
                            }
                        }
                    }
                }
                return new Result(counter, true);
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
            int line_no = 0;
        };
        final var split = asLines(data);
        final var instructions = Arrays.stream(split).map(s -> {
            final var values = s.split(" ");
            if (values.length == 2) {
                return new Instruction(values[0], o.line_no++, values[1]);
            } else {
                return new Instruction(values[0], o.line_no++, values[1], values[2]);
            }
        }).toArray(Instruction[]::new);
        HashMap<String, Integer> register = new HashMap<>();
        System.out.println("part 1 in " + instructions[0].getProgramResult(instructions, register) + " operations");
        o.part1 = register.get("a");
        register = new HashMap<>();
        register.put("c", 1);
        System.out.println("part 2 in " + instructions[0].getProgramResult(instructions, register) + " operations");

        o.part2 = register.get("a");
//        final var longs = asLongs(data);
//        Pattern extract = Pattern.compile("");
//        final var matchers = Arrays.stream(strings).map(s -> {
//            final var matcher = extract.matcher(s);
//            matcher.find();
//
//            return matcher;
//        }).toArray(Matcher[]::new);

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day13() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;

        String data = "10";
        if (!testMode) {
            data = "1362";
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        int designerFavoriteNumber = Integer.parseInt(data);
        final class Location {
            private final int x;
            private final int y;
            private final boolean wall;

            Location(int x, int y) {
                this.x = x;
                this.y = y;
                wall = Integer.bitCount(x * x + 3 * x + 2 * x * y + y + y * y + designerFavoriteNumber) % 2 == 1;
            }

            boolean walkable() {
                return !wall;
            }

            public Location[] sides() {
                ArrayList<Location> locations = new ArrayList<>();
                if (x != 0) {
                    locations.add(new Location(x - 1, y));
                }
                locations.add(new Location(x + 1, y));

                if (y != 0) {
                    locations.add(new Location(x, y - 1));
                }
                locations.add(new Location(x, y + 1));
                final var locations1 = locations.stream().filter(Location::walkable).toArray(Location[]::new);
                return locations1;
            }

            public int x() {
                return x;
            }

            public int y() {
                return y;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this) return true;
                if (obj == null || obj.getClass() != this.getClass()) return false;
                var that = (Location) obj;
                return this.x == that.x &&
                        this.y == that.y;
            }

            @Override
            public int hashCode() {
                return Objects.hash(x, y);
            }

            @Override
            public String toString() {
                return "Location[" +
                        "x=" + x + ", " +
                        "y=" + y + ']';
            }


        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };


        HashMap<Location, Integer> weight = new HashMap<>();
        final var start = new Location(1, 1);
        weight.put(start, 0);
        final var goal = new Location(31, 39);
        HashSet<Location> workSet = new HashSet<>();
        workSet.add(start);
        int cnt = 1;
        while (!weight.containsKey(goal)) {
            HashSet<Location> nextSet = new HashSet<>();
            int finalCnt = cnt;
            workSet.forEach(location -> {
                Arrays.stream(location.sides()).filter(l -> !weight.containsKey(l)).forEach(l -> {
                    weight.put(l, finalCnt);
                    nextSet.add(l);
                });
            });
            workSet = nextSet;
            cnt++;
        }
        o.part1 = weight.get(goal);
        weight.forEach((location, integer) -> {
            if (integer <= 50) {
                o.part2++;
            }
        });
//        StringBuilder builder = new StringBuilder();
//        builder.append("  ");
//        for (int i = 0; i < 10; i++) {
//            builder.append(i);
//
//        }
//        builder.append("\n");
//
//        for (int y = 0; y < 10; y++) {
//            builder.append(y);
//            builder.append(" ");
//            for (int x = 0; x < 10; x++) {
//                long res = x * x + 3 * x + 2 * x * y + y + y * y + designerFavoriteNumber;
//                boolean even = Long.bitCount(res) % 2 == 0;
//                if (even) {
//                    builder.append(".");
//                } else {
//                    builder.append("#");
//                }
//            }
//            builder.append("\n");
//        }
//        System.out.println(builder);
//        final var strings = asLines(data);
//        final var longs = asLongs(data);
//        Pattern extract = Pattern.compile("");
//        final var matchers = Arrays.stream(strings).map(s -> {
//            final var matcher = extract.matcher(s);
//            matcher.find();
//
//            return matcher;
//        }).toArray(Matcher[]::new);

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day14() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "abc";
        if (!testMode) {
            data = "jlmsuwbz";
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        record HashCache(String password, int cnt) {

        }
        class Hasher {
            MessageDigest md = null;
            HashMap<HashCache, String> cacheStringHashMap = new HashMap<>();

            public Hasher() {
                try {
                    md = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }

            String getHash(String password) {
                md.update(password.getBytes());
                byte[] digest = md.digest();
                return toHexString(digest);
            }

            String getReHash(String password, int cnt) {
                final var hashCache = new HashCache(password, cnt);
                final var s = cacheStringHashMap.get(hashCache);
                if (s != null) {
                    return s;
                }
                for (int i = 0; i < cnt; i++) {
                    md.update(password.getBytes());
                    byte[] digest = md.digest();
                    password = toHexString(digest);
                }
                cacheStringHashMap.put(hashCache, password);
                return password;
            }

            String getHashIfRight(String password, int zeroCountsAtStart) {
                md.update(password.getBytes());
                byte[] digest = md.digest();
                int zeroCnt = 0;
                for (final byte b : digest) {
                    if (b >> 4 == 0) {
                        zeroCnt++;
                    } else {
                        break;
                    }
                    if ((b & 0xF) == 0) {
                        zeroCnt++;
                    } else {
                        break;
                    }
                }
                if (zeroCnt >= zeroCountsAtStart) {
                    return toHexString(digest);
                } else {
                    return "";
                }
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


        Hasher hasher = new Hasher();
        String hash;
        int cnt = -1;
        int keyCount = 0;
        while (keyCount < 64) {
            cnt++;
            hash = hasher.getHash(data + cnt);
            char lastChar = hash.charAt(0);
            int inARow = 1;
            for (int i = 1; i < hash.length(); i++) {
                if (lastChar == hash.charAt(i)) {
                    inARow++;
                } else {
                    lastChar = hash.charAt(i);
                    inARow = 1;
                }
                if (inARow == 3) {
                    break;
                }
            }
            if (inARow == 3) {
                for (int i = 1; i < 1001; i++) {
                    String nextHash = hasher.getHash(data + (cnt + i));
                    inARow = 0;
                    for (int j = 0; j < nextHash.length(); j++) {
                        if (lastChar == nextHash.charAt(j)) {
                            inARow++;
                        } else {
                            inARow = 0;
                        }
                        if (inARow == 5) {
                            break;
                        }
                    }
                    if (inARow == 5) {
                        break;
                    }
                }
                if (inARow == 5) {
                    keyCount++;
                    System.out.println(keyCount + " " + cnt);
                }
            }
        }
        o.part1 = cnt;
        cnt = -1;
        keyCount = 0;
        while (keyCount < 64) {
            cnt++;
            hash = hasher.getReHash(data + cnt, 2017);
            char lastChar = hash.charAt(0);
            int inARow = 1;
            for (int i = 1; i < hash.length(); i++) {
                if (lastChar == hash.charAt(i)) {
                    inARow++;
                } else {
                    lastChar = hash.charAt(i);
                    inARow = 1;
                }
                if (inARow == 3) {
                    break;
                }
            }
            if (inARow == 3) {
                for (int i = 1; i < 1001; i++) {
                    String nextHash = hasher.getReHash(data + (cnt + i), 2017);
                    inARow = 0;
                    for (int j = 0; j < nextHash.length(); j++) {
                        if (lastChar == nextHash.charAt(j)) {
                            inARow++;
                        } else {
                            inARow = 0;
                        }
                        if (inARow == 5) {
                            break;
                        }
                    }
                    if (inARow == 5) {
                        break;
                    }
                }
                if (inARow == 5) {
                    keyCount++;
                    System.out.println(keyCount + " " + cnt);
                }
            }
        }
        o.part2 = cnt;
//        final var strings = asLines(data);
//        final var longs = asLongs(data);
//        Pattern extract = Pattern.compile("");
//        final var matchers = Arrays.stream(strings).map(s -> {
//            final var matcher = extract.matcher(s);
//            matcher.find();
//
//            return matcher;
//        }).toArray(Matcher[]::new);

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day15() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "Disc #1 has 5 positions; at time=0, it is at position 4.\n" +
                "Disc #2 has 2 positions; at time=0, it is at position 1.";
        if (!testMode) {
            data = getData(day, "2016");
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        class Disk {
            final long size;
            long position;

            Disk(long size) {
                this.size = size;
            }

            public long getPosition(long i) {
                return (position + i) % size;
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };

        final var strings = asLines(data);
//        final var longs = asLongs(data);
        Pattern extract = Pattern.compile("Disc #(\\d+) has (\\d+) positions; at time=0, it is at position (\\d+).");
        Disk[] disks = new Disk[strings.length];
        final var matchers = Arrays.stream(strings).map(s -> {
            final var matcher = extract.matcher(s);
            matcher.find();
            int i = Integer.parseInt(matcher.group(1)) - 1;
            int siz = Integer.parseInt(matcher.group(2));
            int p = Integer.parseInt(matcher.group(3));
            disks[i] = new Disk(siz);
            disks[i].position = p;
            return matcher;
        }).toArray(Matcher[]::new);
        long ticker = 0;
        long augmenter = 1;
        for (int i = 0; i < disks.length; i++) {
            while (disks[i].getPosition(ticker + i + 1) != 0) {
                ticker += augmenter;
            }
            augmenter *= disks[i].size;
        }
        o.part1 = ticker;
        final var disks1 = new Disk[strings.length + 1];
        for (int i = 0; i < disks.length; i++) {
            disks1[i] = disks[i];
        }
        disks1[6] = new Disk(11);
        ticker = 0;
        augmenter = 1;
        for (int i = 0; i < disks1.length; i++) {
            while (disks1[i].getPosition(ticker + i + 1) != 0) {
                ticker += augmenter;
            }
            augmenter *= disks1[i].size;
        }
        o.part2 = ticker;
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day16() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean verbose = true;
//        boolean verbose = false;
//        boolean testMode = true;
        boolean testMode = false;
        String data = "10000";
        if (!testMode) {
            data = "11100010111110100";
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        var o = new Object() {
            String part1 = "";
            String part2 = "";

            String expand(String s) {
                String a = s;
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    final var c = s.charAt(i);
                    if (c == '1') {
                        stringBuilder.append('0');
                    } else {
                        stringBuilder.append('1');
                    }
                }
                String b = stringBuilder.reverse().toString();
                return a + "0" + b;
            }

            String checkSum(String s) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < s.length(); i += 2) {
                    final var c1 = s.charAt(i);
                    final var c2 = s.charAt(i + 1);
                    if (c1 == c2) {
                        stringBuilder.append('1');
                    } else {
                        stringBuilder.append('0');
                    }
                }
                return stringBuilder.toString();
            }

        };
        String fill = data;
        var size = 272;
        while ((fill = o.expand(fill)).length() <= size) {

        }
        fill = fill.substring(0, size);
        while ((fill = o.checkSum(fill)).length() % 2 == 0) {

        }
        o.part1 = fill;
        fill = data;
        size = 35651584;
        while ((fill = o.expand(fill)).length() <= size) {

        }
        fill = fill.substring(0, size);
        while ((fill = o.checkSum(fill)).length() % 2 == 0) {

        }
        o.part2 = fill;
//        final var strings = asLines(data);
//        final var longs = asLongs(data);
//        Pattern extract = Pattern.compile("");
//        final var matchers = Arrays.stream(strings).map(s -> {
//            final var matcher = extract.matcher(s);
//            matcher.find();
//
//            return matcher;
//        }).toArray(Matcher[]::new);

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
            data = getData(day, "2016");
        } else {
            if (data.isEmpty()) {
                throw new OutOfMemoryError("empty data");
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
//        final var strings = asLines(data);
//        final var longs = asLongs(data);
//        Pattern extract = Pattern.compile("");
//        final var matchers = Arrays.stream(strings).map(s -> {
//            final var matcher = extract.matcher(s);
//            matcher.find();
//
//            return matcher;
//        }).toArray(Matcher[]::new);

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

