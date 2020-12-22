package com.numberengineer.aoc.days;

import com.numberengineer.aoc.TikTok;

import java.util.Arrays;
import java.util.HashMap;

import static com.numberengineer.aoc.Utils.*;

public class Day19 {
    public static void day19() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = """
                42: 9 14 | 10 1
                9: 14 27 | 1 26
                10: 23 14 | 28 1
                1: "a"
                11: 42 31
                5: 1 14 | 15 1
                19: 14 1 | 14 14
                12: 24 14 | 19 1
                16: 15 1 | 14 14
                31: 14 17 | 1 13
                6: 14 14 | 1 14
                2: 1 24 | 14 4
                0: 8 11
                13: 14 3 | 1 12
                15: 1 | 14
                17: 14 2 | 1 7
                23: 25 1 | 22 14
                28: 16 1
                4: 1 1
                20: 14 14 | 1 15
                3: 5 14 | 16 1
                27: 1 6 | 14 18
                14: "b"
                21: 14 1 | 1 14
                25: 1 1 | 1 14
                22: 14 14
                8: 42
                26: 14 22 | 1 20
                18: 15 15
                7: 14 5 | 1 21
                24: 14 1
                                        
                abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
                bbabbbbaabaabba
                babbbbaabbbbbabbbbbbaabaaabaaa
                aaabbbbbbaaaabaababaabababbabaaabbababababaaa
                bbbbbbbaaaabbbbaaabbabaaa
                bbbababbbbaaaaaaaabbababaaababaabab
                ababaaaaaabaaab
                ababaaaaabbbaba
                baabbaaaabbaaaababbaababb
                abbbbabbbbaaaababbbbbbaaaababb
                aaaaabbaabaaaaababaa
                aaaabbaaaabbaaa
                aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
                babaaabbbaaabaababbaabababaaab
                aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba
                """;
        if (!testMode) {
            data = getData(day);
        }
        record RuleNo(int i) {

        }

        abstract class Rule {

            abstract int[] tests(String s, int[] atIndexes);
        }
        class RawRule extends Rule {
            char match;

            @Override
            int[] tests(String s, int[] atIndexes) {
                int[] ints = Arrays.copyOf(atIndexes, atIndexes.length);
                for (int i = 0; i < atIndexes.length; i++) {
                    final var atIndex = atIndexes[i];
                    ints[i] = (atIndex >= s.length() || atIndex == -1 || s.charAt(atIndex) != match) ? -1 : atIndex + 1;
                }
                return ints;
            }
        }
        class SimpleRule extends Rule {
            int[] _rules;
            Rule[] rules;

            @Override
            int[] tests(String s, int[] atIndexes) {
                atIndexes = clean(atIndexes);
                if (atIndexes.length == 0) {
                    return atIndexes;
                }
                int[] tests = Arrays.copyOf(atIndexes, atIndexes.length);
                for (int j = 0; j < rules.length; j++) {
                    tests = rules[j].tests(s, tests);
                }
                tests = clean(tests);
                return tests;
            }

            private int[] clean(int[] atIndexes) {
                return Arrays.stream(atIndexes).filter(i -> i != -1).distinct().toArray();
            }
        }
        class OrRules extends Rule {
            int[] _rules;
            int[] _orRules;
            Rule[] rules;
            Rule[] orRules;

            @Override
            int[] tests(String s, int[] atIndexes) {
                atIndexes = clean(atIndexes);
                if (atIndexes.length == 0) {
                    return atIndexes;
                }
                int[] tests = Arrays.copyOf(atIndexes, atIndexes.length);
                int[] tests2 = Arrays.copyOf(atIndexes, atIndexes.length);
                for (int j = 0; j < rules.length; j++) {
                    tests = rules[j].tests(s, tests);
                }
                for (int j = 0; j < orRules.length; j++) {
                    tests2 = orRules[j].tests(s, tests2);
                }
                atIndexes = new int[tests.length + tests2.length];
                System.arraycopy(tests, 0, atIndexes, 0, tests.length);
                System.arraycopy(tests2, 0, atIndexes, tests.length, tests2.length);
                atIndexes = clean(atIndexes);
                return atIndexes;
            }

            private int[] clean(int[] atIndexes) {
                return Arrays.stream(atIndexes).filter(i -> i != -1).distinct().toArray();
            }
        }
        HashMap<RuleNo, Rule> map = new HashMap<>();

        var o = new Object() {
            long part1 = 0;
            long part2 = 0;

            void bindMap() {
                map.values().stream().forEach(rule -> {
                    if (rule instanceof SimpleRule simpleRule) {
                        simpleRule.rules = Arrays.stream(simpleRule._rules).mapToObj(RuleNo::new)
                                .map(map::get)
                                .toArray(Rule[]::new);
                    } else if (rule instanceof OrRules orRules) {
                        orRules.rules = Arrays.stream(orRules._rules).mapToObj(RuleNo::new)
                                .map(map::get)
                                .toArray(Rule[]::new);
                        orRules.orRules = Arrays.stream(orRules._orRules).mapToObj(RuleNo::new)
                                .map(map::get)
                                .toArray(Rule[]::new);
                    }
                });
            }

            Rule makeRule(String s) {
                Rule rule;
                if (s.contains("|")) {
                    final var tRule = new OrRules();
                    rule = tRule;
                    final var s1 = s.split("\\|");
                    tRule._rules = Arrays.stream(s1[0].trim().split(" "))
                            .mapToInt(Integer::parseInt).toArray();
                    tRule._orRules = Arrays.stream(s1[1].trim().split(" "))
                            .mapToInt(Integer::parseInt).toArray();
                } else if (s.contains("\"")) {
                    final var tRule = new RawRule();
                    rule = tRule;
                    tRule.match = s.replace("\"", "").charAt(0);
                } else {
                    final var tRule = new SimpleRule();
                    rule = tRule;
                    final var s1 = s.split("\\|");
                    tRule._rules = Arrays.stream(s1[0].trim().split(" "))
                            .mapToInt(Integer::parseInt).toArray();
                }
                return rule;
            }
        };

        final var split = data.split("\n\n");
        final var rules = asLines(split[0]);
        Arrays.stream(rules).forEach(s -> {
            if (testMode) {
                System.out.println(s);
            }
            final var values = s.split(": ");
            final var key = new RuleNo(Integer.parseInt(values[0]));
            final var rule = o.makeRule(values[1]);
            map.put(key, rule);
        });

        final var messages = asLines(split[1]);
        Rule zero = map.get(new RuleNo(0));
        o.bindMap();
        for (int i = 0; i < messages.length; i++) {
            int finalI = i;
            final var test = Arrays.stream(zero.tests(messages[i], new int[]{0})).filter(value -> value == messages[finalI].length()).count() > 0;
            o.part1 += test ? 1 : 0;
        }
        map.put(new RuleNo(8), o.makeRule("42 | 42 8"));
        map.put(new RuleNo(11), o.makeRule("42 31 | 42 11 31"));
        o.bindMap();
        for (int i = 0; i < messages.length; i++) {
            int finalI = i;
            final var test = Arrays.stream(zero.tests(messages[i], new int[]{0})).filter(value -> value == messages[finalI].length()).count() > 0;
            o.part2 += test ? 1 : 0;
        }

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }
}

