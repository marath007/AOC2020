package com.numberengineer.aoc2019.days;

import com.numberengineer.aoc.TikTok;

import java.util.Arrays;
import java.util.regex.Pattern;

import static com.numberengineer.aoc.Utils.*;

public class Day22 {
    public static void day22() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "deal into new stack\n" +
                "cut -2\n" +
                "deal with increment 7\n" +
                "cut 8\n" +
                "cut -4\n" +
                "deal with increment 7\n" +
                "cut 3\n" +
                "deal with increment 9\n" +
                "deal with increment 3\n" +
                "cut -1";
        if (!testMode) {
            data = getData(day, "2019");
        }
        abstract class Instruction {
            abstract int[] process(int[] integers);

        }
        class DealIncrement extends Instruction {
            final int increment;

            DealIncrement(int increment) {
                this.increment = increment;
            }


            @Override
            int[] process(int[] integers) {
                int[] tInt = new int[integers.length];
                int k = 0;
                for (int integer : integers) {
                    tInt[k] = integer;
                    k = (k + increment) % integers.length;
                }
                return tInt;
            }


        }
        class CutNCards extends Instruction {
            final int increment;

            CutNCards(int increment) {
                this.increment = increment;
            }

            @Override
            int[] process(int[] integers) {
                int[] tInt = new int[integers.length];
                for (int i = 0; i < integers.length; i++) {
                    var i1 = (i + increment) % integers.length;
                    if (i1 < 0) {
                        i1 += integers.length;
                    }
                    tInt[i] = integers[i1];
                }
                return tInt;
            }


        }
        class DealIntoNewStack extends Instruction {
            DealIntoNewStack() {

            }

            @Override
            int[] process(int[] integers) {
                int[] tInt = new int[integers.length];
                for (int i = 0; i < integers.length; i++) {
                    tInt[integers.length - 1 - i] = integers[i];
                }
                return tInt;
            }


        }


        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
            int[] cardDeck;
            long cardPosition;
        };
        if (testMode) {
            o.cardDeck = new int[10];
        } else {
            o.cardDeck = new int[10007];
        }
        for (int i = 0; i < o.cardDeck.length; i++) {
            o.cardDeck[i] = i;
        }
        final var strings = asLines(data);
        Pattern extractor = Pattern.compile("(?:deal with increment ([\\d]+))|(?:cut ([-]?[\\d]+))|(deal into new stack)");
        var instructions = Arrays.stream(strings).map(s -> {
            final var matcher = extractor.matcher(s);
            matcher.find();
            Instruction instruction;
            if (matcher.group(1) != null) {
                instruction = new DealIncrement(Integer.parseInt(matcher.group(1)));
            } else if (matcher.group(2) != null) {
                instruction = new CutNCards(Integer.parseInt(matcher.group(2)));
            } else if (matcher.group(3) != null) {
                instruction = new DealIntoNewStack();
            } else {
                throw new RuntimeException("Greg?");
            }
            return instruction;
        }).toArray(Instruction[]::new);

////        cardDeck;

//        System.out.println(Arrays.toString(new DealIntoNewStack().process(o.cardDeck)));
//        System.out.println(Arrays.toString(new DealIncrement(9).process(o.cardDeck)));
//        System.out.println(Arrays.toString(new DealIncrement(3).process(new DealIncrement(10).process(o.cardDeck))));
//        System.out.println(Arrays.toString(new DealIncrement(7).process(new DealIncrement(10).process(new DealIntoNewStack().process(o.cardDeck)))));
////        System.out.println(Arrays.toString(new CutNCards(-4).process(o.cardDeck)));
////        System.out.println(Arrays.toString(new DealIncrement(3).process(o.cardDeck)));

//        Arrays.stream(instructions).forEach(instruction -> {
//            o.cardDeck = instruction.process(o.cardDeck);
//        });
        final var reduce = Arrays.stream(instructions).filter(i -> i instanceof DealIncrement).mapToLong(i -> ((DealIncrement) i).increment).reduce((left, right) -> Math.multiplyExact(left % o.cardDeck.length, right % o.cardDeck.length)% o.cardDeck.length);
        System.out.println( reduce.getAsLong());
        System.out.println(Arrays.toString(Arrays.copyOf(o.cardDeck,Math.min(25,o.cardDeck.length))));
        instructions= Arrays.stream(instructions).filter(instruction -> instruction instanceof DealIncrement).toArray(Instruction[]::new);
        {
            for (int i = 0; i < o.cardDeck.length; i++) {
                o.cardDeck[i] = i;
            }
//            instructions = new Instruction[]{new DealIntoNewStack(), new DealIncrement(10), new DealIncrement(3)};
            Arrays.stream(instructions).forEach(instruction -> {
                o.cardDeck = instruction.process(o.cardDeck);
            });
            System.out.println(Arrays.toString(Arrays.copyOf(o.cardDeck,Math.min(25,o.cardDeck.length))));
        }
        {
            for (int i = 0; i < o.cardDeck.length; i++) {
                o.cardDeck[i] = i;
            }
            instructions = new Instruction[]{ new DealIncrement(1000)};
            for (int i = 0; i < 1000; i++) {
                Arrays.stream(instructions).forEach(instruction -> {
                    o.cardDeck = instruction.process(o.cardDeck);
                });

            }
            System.out.println(Arrays.toString(Arrays.copyOf(o.cardDeck,Math.min(25,o.cardDeck.length))));
        }
        {
            for (int i = 0; i < o.cardDeck.length; i++) {
                o.cardDeck[i] = i;
            }
            long cat=1;
            for (int i = 0; i < 1000; i++) {
                cat*=1000;
                cat%=o.cardDeck.length;
            }
            instructions = new Instruction[]{ new DealIncrement((int) cat)};
                Arrays.stream(instructions).forEach(instruction -> {
                    o.cardDeck = instruction.process(o.cardDeck);
                });

            System.out.println(Arrays.toString(Arrays.copyOf(o.cardDeck,Math.min(25,o.cardDeck.length))));
        }
//        {
//            for (int i = 0; i < o.cardDeck.length; i++) {
//                o.cardDeck[i] = i;
//            }
//           instructions[0]=new DealIncrement(9);
//            Arrays.stream(instructions).forEach(instruction -> {
//                o.cardDeck = instruction.process(o.cardDeck);
//            });
//            System.out.println(Arrays.toString(o.cardDeck));
//        }
        for (int i = 0; i < o.cardDeck.length; i++) {
            if (o.cardDeck[i] == 2019) {
                o.part1 = i;
                break;
            }
        }
//        final var reduce = Arrays.stream(instructions).filter(i -> i instanceof DealIncrement).mapToLong(i -> ((DealIncrement) i).increment).reduce((left, right) -> Math.multiplyExact(left % o.cardDeck.length, right % o.cardDeck.length)% o.cardDeck.length);
//        System.out.println( reduce.getAsLong());

//        long[] sizes = new long[]{10007,
//                10009,
//                10033,
//                10037,
//                10039,
//                10049,
//                10057,
//                10061,
//                10067,
//                10069,
//                10079,
//                10091,
//                119315717514047L
//        };
//        for (int i = 0; i < sizes.length; i++) {
//            final var size = sizes[i];
//            final var reduce = Arrays.stream(instructions).filter(k -> k instanceof DealIncrement).mapToLong(k -> ((DealIncrement) k).increment).reduce((left, right) -> Math.multiplyExact(left % size, right % size) % size);
//            System.out.println(reduce.getAsLong());
//            if (size > Integer.MAX_VALUE) {
//                break;
//            }
//
//            o.cardDeck = new int[(int) size];
//            for (int j = 0; j < o.cardDeck.length; j++) {
//                o.cardDeck[j] = j;
//            }
////            o.cardPosition = findCardPosition(o.cardDeck);
//
//            Arrays.stream(instructions).forEach(instruction -> {
//                o.cardDeck = instruction.process(o.cardDeck);
////                o.cardPosition = findCardPosition(o.cardDeck);
//            });
////            System.out.println("Card at 2020 for size " + sizes[i] + ": " + o.cardDeck[2020]);
//        }

//        System.out.println(Arrays.toString(o.cardDeck));

//        final var longs = asLongs(data);


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
//
//    private static int findCardPosition(int[] cardDeck) {
//        for (int j = 0; j < cardDeck.length; j++) {
//            if (cardDeck[j] == 2020) {
//                return j;
//            }
//        }
//    }

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

