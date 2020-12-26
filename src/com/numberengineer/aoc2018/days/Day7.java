package com.numberengineer.aoc2018.days;

import com.numberengineer.aoc.TikTok;

import java.util.*;
import java.util.regex.Pattern;

import static com.numberengineer.aoc.Utils.*;

public class Day7 {
    public static void day7() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "Step C must be finished before step A can begin.\n" +
                "Step C must be finished before step F can begin.\n" +
                "Step A must be finished before step B can begin.\n" +
                "Step A must be finished before step D can begin.\n" +
                "Step B must be finished before step E can begin.\n" +
                "Step D must be finished before step E can begin.\n" +
                "Step F must be finished before step E can begin.";
        if (!testMode) {
            data = getData(day, "2018");
        }
        record Instruction(String a, String b) {
        }
        class Task {
            int timeLeft;
            ArrayList<Task> tasks = new ArrayList<>();
            private String name;

            public Task() {
            }

            public Task(String name, int timeLeft) {
                this.name = name;
                this.timeLeft = timeLeft;
            }
        }
        var o = new Object() {
            String part1;
            long part2;
        };


        final var strings = asLines(data);
        Pattern extractor = Pattern.compile("Step ([A-Z]) must be finished before step ([A-Z]) can begin.");
        final var instructions = Arrays.stream(strings).map(s -> {
            final var matcher = extractor.matcher(s);
            matcher.find();
            return new Instruction(matcher.group(1), matcher.group(2));
        }).toArray(Instruction[]::new);
        HashMap<String, Task> map = new HashMap<>();
        Arrays.stream(instructions).forEach(instruction -> {
            map.put(instruction.a, new Task());
            map.put(instruction.b, new Task());
        });
        Arrays.stream(instructions).forEach(instruction -> {
            map.get(instruction.b).tasks.add(map.get(instruction.a));
        });
        StringBuilder stringBuilder = new StringBuilder();
        while (map.size() > 0) {
            final var next = map.entrySet().stream().filter(e -> e.getValue().tasks.size() == 0).map(Map.Entry::getKey).sorted().findFirst().get();
            final var remove = map.remove(next);
            stringBuilder.append(next);
            map.values().forEach(v -> v.tasks.remove(remove));
        }
        o.part1 = stringBuilder.toString();

        Arrays.stream(instructions).forEach(instruction -> {
            map.put(instruction.a, new Task(instruction.a, 60+instruction.a.charAt(0) - 'A' + 1));
            map.put(instruction.b, new Task(instruction.b, 60+instruction.b.charAt(0) - 'A' + 1));
        });
        Arrays.stream(instructions).forEach(instruction -> {
            map.get(instruction.b).tasks.add(map.get(instruction.a));
        });
        HashSet<Task> runningTasks = new HashSet<>();
        long duration = 0;
        while (map.size() > 0) {

            final var next = map.entrySet().stream().filter(e -> e.getValue().tasks.size() == 0).map(Map.Entry::getKey).sorted().toArray(String[]::new);
            int k = 0;
            for (int i = runningTasks.size(); i < 6 && k < next.length; i++, k++) {
                runningTasks.add(map.get(next[k]));
            }
            System.out.println(runningTasks.size() + " worker active");
            final var size = map.size();
            while (size == map.size()) {
                duration++;
                final var tasks = runningTasks.stream().peek(task -> task.timeLeft--).filter(task -> {
                    if (task.timeLeft == 0) {
                        return true;
                    } else {
                        return false;
                    }
                }).toArray(Task[]::new);
                for (int i = 0; i < tasks.length; i++) {
                    final var task = tasks[i];
                    runningTasks.remove(task);
                    map.values().forEach(v -> v.tasks.remove(task));
                    map.remove(task.name);
                }

            }
        }
        o.part2 = duration;

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

