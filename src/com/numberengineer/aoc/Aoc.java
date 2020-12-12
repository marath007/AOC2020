package com.numberengineer.aoc;


import java.util.Arrays;

import static com.numberengineer.aoc.AocPostCompetition.readFile;

class Aoc {
    public static void main(String[] args) {
        day12();
    }

    public static void day12() {
        TikTok tikTok = new TikTok(true);
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
        enum Action {
            N, E, S, W, L, R, F;
        }

        record Instruction(Action a, int qty) {

        }
        class Waypoint {
            int x = 10;
            int y = 1;
            void rotate(int deg) {
                final var x = this.x;
                final var y = this.y;
                deg=(deg+360)%360;
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

            void processWaypointInstruction(Instruction instruction) {
                switch (instruction.a) {
                    case N -> waypoint.y += instruction.qty;
                    case E -> waypoint.x += instruction.qty;
                    case S -> waypoint.y -= instruction.qty;
                    case W -> waypoint.x -= instruction.qty;
                    case L -> waypoint.rotate(-instruction.qty);
                    case R -> waypoint.rotate(instruction.qty);
                    case F -> {
                        x+=waypoint.x*instruction.qty;
                        y+=waypoint.y*instruction.qty;
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
        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\day12.txt");
//        String data = "F10\n" +
//                "N3\n" +
//                "F7\n" +
//                "R90\n" +
//                "F11";
        final var split = data.split("\n");
        final var instructions = Arrays.stream(split).map(s -> new Instruction(Action.valueOf(s.substring(0, 1)), Integer.parseInt(s.substring(1)))).toArray(Instruction[]::new);
        Ship ship = new Ship();
        Arrays.stream(instructions).forEach(ship::processInstruction);
        o.part1 = ship.getManateeDistance();
        ship = new Ship();
        Arrays.stream(instructions).forEach(ship::processWaypointInstruction);
        o.part2 = ship.getManateeDistance();
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }


}