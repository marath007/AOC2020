package com.numberengineer.aoc;


import java.util.Arrays;
import java.util.regex.Pattern;

import static com.numberengineer.aoc.Utils.*;

class Aoc {

    public static void main(String[] args) {


        day16();

    }

    public static void day16() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "class: 0-1 or 4-19\n" +
                "row: 0-5 or 8-19\n" +
                "seat: 0-13 or 16-19\n" +
                "\n" +
                "your ticket:\n" +
                "11,12,13\n" +
                "\n" +
                "nearby tickets:\n" +
                "3,9,18\n" +
                "15,1,5\n" +
                "5,14,9";
        if (!testMode) {
            data = getData(day);
        }

        final var blocks = data.split("\n\n");
        record NamedRange(String s, int from, int to, int from2, int to2) {

            boolean contains(int i) {
                return (i <= to && i >= from) || (i <= to2 && i >= from2);
            }
        }
        record Ticket(int... numbers) {

        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };

        Pattern extractor = Pattern.compile("([a-z ]*): ([\\d]+)-([\\d]+) or ([\\d]+)-([\\d]+)");
        final var namedRanges = Arrays.stream(blocks[0].split("\n")).map(s -> {
            final var matcher = extractor.matcher(s);
            matcher.matches();
            return new NamedRange(matcher.group(1),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4)),
                    Integer.parseInt(matcher.group(5)));
        }).toArray(NamedRange[]::new);
        final var myTicket = new Ticket(Arrays.stream(blocks[1].split("\n")[1].split(",")).mapToInt(Integer::parseInt).toArray());
        final var nearbyTickets = Arrays.stream(blocks[2].split("\n")).skip(1).map(s -> new Ticket(Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray())).toArray(Ticket[]::new);

        Arrays.stream(nearbyTickets).forEach(t -> {
            Arrays.stream(t.numbers).asLongStream()
                    .filter(l -> !Arrays.stream(namedRanges).anyMatch(n -> n.contains((int) l))).forEach(l -> o.part1 += l);
        });
        final var validTickets = Arrays.stream(nearbyTickets).filter(t -> Arrays.stream(t.numbers).asLongStream().allMatch(l -> Arrays.stream(namedRanges).anyMatch(n -> n.contains((int) l)))).toArray(Ticket[]::new);
        boolean[][] booleans = new boolean[namedRanges.length][namedRanges.length];//get [range][field]
        for (int i = 0; i < booleans.length; i++) {
            final var namedRange = namedRanges[i];
            for (int j = 0; j < booleans[0].length; j++) {
                int finalJ = j;
                booleans[i][j] = Arrays.stream(validTickets).allMatch(t -> namedRange.contains(t.numbers[finalJ]));
            }
        }
        int[] chainOfSolve = new int[booleans.length];
        for (int i = 0; i < booleans.length; i++) {
            final var aBoolean = booleans[i];
            for (int j = 0; j < aBoolean.length; j++) {
                chainOfSolve[i] += (aBoolean[j] ? 1 : 0);
            }
        }
        for (int i = 0; i < chainOfSolve.length; i++) {
            int j = 0;
            while (chainOfSolve[j++] != i + 1) {

            }
            j--;
            int indexOfTruth = indexOfTruth(booleans[j]);
            for (int k = 0; k < chainOfSolve.length; k++) {
                if (k != j) {
                    booleans[k][indexOfTruth] = false;
                }
            }

        }
        int[] trueIndexes = new int[booleans.length];
        for (int i = 0; i < booleans.length; i++) {
            trueIndexes[i] = indexOfTruth(booleans[i]);
        }
        o.part2 = 1;
        for (int i = 0; i < namedRanges.length; i++) {
            if (namedRanges[i].s.startsWith("departure")) {
                System.out.println(namedRanges[i].s+"="+myTicket.numbers[trueIndexes[i]]);
                o.part2 *= myTicket.numbers[trueIndexes[i]];
            }
            if (testMode){
                if (namedRanges[i].s.startsWith("class")){
                    System.out.println("class=" + myTicket.numbers[trueIndexes[i]]);
                }
                if (namedRanges[i].s.startsWith("row")){
                    System.out.println("row=" + myTicket.numbers[trueIndexes[i]]);
                }
                if (namedRanges[i].s.startsWith("seat")){
                    System.out.println("seat=" + myTicket.numbers[trueIndexes[i]]);
                }
            }
        }
//        for (int i = 0; i < namedRanges1.length; i++) {
//            if (namedRanges1[i].s.startsWith("departure")) {
//                System.out.println(namedRanges1[i].s+"="+myTicket.numbers[i]);
//                o.part2 *= myTicket.numbers[i];
//            }
//            if (testMode){
//                if (namedRanges1[i].s.startsWith("class")){
//                    System.out.println("class=" + myTicket.numbers[i]);
//                }
//                if (namedRanges1[i].s.startsWith("row")){
//                    System.out.println("row=" + myTicket.numbers[i]);
//                }
//                if (namedRanges1[i].s.startsWith("seat")){
//                    System.out.println("seat=" + myTicket.numbers[i]);
//                }
//            }
//        }

        if (o.part2<=2016493457861L||o.part2>=2972476609733L){
            System.out.println("######invalid########");
        }
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    private static int indexOfTruth(boolean[] booleans1) {
        int answer=-1;
        for (int k = 0; k < booleans1.length; k++) {
            if (booleans1[k]) {
                if (answer!=-1){
                    throw new RuntimeException("not only one boolean");
                }
                answer= k;
            }
        }
        return answer;
    }


    public static void dayN() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
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