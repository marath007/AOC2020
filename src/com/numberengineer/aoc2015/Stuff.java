package com.numberengineer.aoc2015;

import com.numberengineer.aoc.TikTok;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;

import static com.numberengineer.aoc.AocPostCompetition.readFile;

public class Stuff {
    public Stuff() {
    }

    public static void main(String[] args) {

        day4();

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
        while ( !hasher.getHash(data+(++o.part1)).startsWith("00000")){

        }
        while ( !hasher.getHash(data+(++o.part2)).startsWith("000000")){

        }

        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }
}

