package com.numberengineer.aoc;


import java.util.Arrays;
import java.util.HashMap;

import static com.numberengineer.aoc.AocPostCompetition.readFile;

class Aoc {
    public static void main(String[] args) {
        day8();
    }

    public static void day8() {
        TicToc ticToc = new TicToc(true);
        record Result(int value, boolean terminated) {

        }
        record Instruction(String opCode, int value, int line_no) {
            private Result getProgramResult(Instruction[] instructions) {
                int counter = 0;
                HashMap<Instruction, Integer> safetyMap = new HashMap<>();
                for (int i = 0; i < instructions.length; i++) {
                    final var instruction = instructions[i];
                    if (safetyMap.containsKey(instruction)) {
                        return new Result(counter, false);

                    } else {
                        safetyMap.put(instruction, 0);
                    }
                    switch (instruction.opCode()) {
                        case "acc" -> counter += instruction.value();
                        case "jmp" -> i += instruction.value() - 1;
                    }
                }
                return new Result(counter, true);
            }
        }
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;
            int line_no = 0;

        };


        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\input.txt");
        final var split = data.split("\n");

        final var instructions = Arrays.stream(split).map(s -> {
            final var values = s.split(" ");
            return new Instruction(values[0], Integer.valueOf(values[1]), o.line_no++);
        }).toArray(Instruction[]::new);

        Result result = instructions[0].getProgramResult(instructions);
        o.part1 = result.value();

        for (int i = 0; i < instructions.length; i++) {
            final var instructions1 = Arrays.copyOf(instructions, instructions.length);
            final var curInstruction = instructions1[i];
            if (curInstruction.opCode().equals("jmp")) {
                instructions1[i] = new Instruction("nop", curInstruction.value(), curInstruction.line_no());
            } else if (curInstruction.opCode().equals("nop")) {
                if (curInstruction.value() == 0) {
                    continue;
                }
                instructions1[i] = new Instruction("jmp", curInstruction.value(), curInstruction.line_no());
            } else {
                continue;
            }
            Result programResult = curInstruction.getProgramResult(instructions1);
            if (programResult.terminated()) {
                o.part2 = programResult.value();
                break;
            }
        }
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        ticToc.toc(System.out, " " + methodName);
    }


}