package com.numberengineer.aoc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static com.numberengineer.aoc.Utils.*;


public class AocPostCompetition {
    public static void day1() {
        TikTok tikTok = new TikTok(true);
        final var day = Utils.getDay();
        String data = Utils.getData(day);
        int[] ints = Utils.asInts(data);
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;

            boolean valid() {
                return part2 != 0 && part1 != 0;
            }
        };
        Arrays.stream(ints).anyMatch((i1) -> Arrays.stream(ints).anyMatch((i2) -> Arrays.stream(ints).anyMatch((i3) -> {
            if (i1 + i2 + i3 == 2020) {
                o.part2 = i1 * i2 * i3;
            } else if (i3 + i2 == 2020) {
                o.part1 = i3 * i2;
            }
            return o.valid();
        })));
        endOfWork(tikTok, day, false, o.part1, o.part2);
    }

    public static void day2() {
        TikTok tikTok = new TikTok(true);
        final var day = Utils.getDay();
        String data = Utils.getData(day);
        String[] s = Utils.asLines(data);
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;
        };
        Arrays.stream(s).forEach(string -> {
            final var split = string.split(":");
            final var password = split[1].trim();
            final var conditions = split[0].split(" ");
            final var qty = conditions[0].split("-");
            final var minCnt = Integer.parseInt(qty[0]);
            final var maxCnt = Integer.parseInt(qty[1]);
            final var letter = conditions[1];
            final var letterAsChar = conditions[1].charAt(0);
            final var realCount = password.length() - password.replace(letter, "").length();
            if (realCount >= minCnt && realCount <= maxCnt) {
                o.part1++;
            }
            if ((password.charAt(minCnt - 1) == letterAsChar) ^ password.charAt(maxCnt - 1) == letterAsChar) {
                o.part2++;
            }
        });
        endOfWork(tikTok, day, false, o.part1, o.part2);
    }

    public static void day3() {
        TikTok tikTok = new TikTok(true);
        final var day = Utils.getDay();
        String data = Utils.getData(day);
        String[] strings = Utils.asLines(data);
        var o = new Object() {
            int part1 = 0;
            long part2 = 0;
            int trees[] = new int[5];
            int xs[] = new int[5];
            boolean alternator = false;
        };
        final var length = strings[0].length();
        Arrays.stream(strings).skip(1).forEach(s -> {
            o.xs[0] += 1;
            o.xs[1] += 3;
            o.xs[2] += 5;
            o.xs[3] += 7;
            if (s.charAt(o.xs[0] % length) == '#') o.trees[0]++;
            if (s.charAt(o.xs[1] % length) == '#') o.trees[1]++;
            if (s.charAt(o.xs[2] % length) == '#') o.trees[2]++;
            if (s.charAt(o.xs[3] % length) == '#') o.trees[3]++;
            if (o.alternator) {
                o.xs[4] += 1;
                if (s.charAt(o.xs[4] % length) == '#') o.trees[4]++;
            }
            o.alternator = !o.alternator;
        });
        o.part1 = o.trees[1];
        o.part2 = o.trees[0];
        for (int i = 1; i < o.trees.length; i++) {
            o.part2 = Math.multiplyExact(o.part2, o.trees[i]);
        }
        endOfWork(tikTok, day, false, o.part1, o.part2);
    }

    public static void day4() {
        TikTok tikTok = new TikTok(true);
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;
        };
        final var day = Utils.getDay();
        String data = Utils.getData(day);
        String[] fields = new String[]{"byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:",};
//        String data = "byr:1971\necl:hzl pid:112040163\neyr:2023 iyr:2019\nhcl:#b6652a hgt:167cm\n\npid:108667812 eyr:2023 hcl:#623a2f hgt:171cm iyr:2018 ecl:amb byr:1993\n\nhcl:#cfa07d iyr:2014 ecl:blu eyr:2023 cid:304 hgt:70in byr:1961\n\nbyr:1977\nhcl:#b6652a\niyr:2017 ecl:oth pid:703877876 hgt:185cm\n\nbyr:1972\ncid:271\niyr:2016 pid:876104259 hgt:173cm eyr:2028 ecl:brn hcl:#733820\n\nhgt:174cm ecl:gry iyr:2014 eyr:2029 hcl:#c0946f\nbyr:1967 pid:406306240\n\nhcl:#6b5442\niyr:2011\npid:040592028 eyr:2026\necl:amb\nbyr:1923\n\npid:293598838 byr:1960 cid:87\niyr:2018\necl:blu eyr:2029\nhcl:#7d3b0c\nhgt:62in\n\niyr:2018 cid:137\nhcl:1c7db1 ecl:#38812e byr:2006 eyr:2038 pid:1239811353 hgt:84\n\nhcl:#888785 pid:308480529\niyr:2010 byr:1988\neyr:2025 hgt:176cm ecl:amb\n\ncid:79 ecl:lzr\niyr:2013 byr:1991 hcl:2f49ef\nhgt:191cm\npid:378428551\n\niyr:2005\nhgt:64in hcl:89c369\necl:gry byr:1932\neyr:2029 pid:753055776\n\necl:amb iyr:2017\nbyr:1969 hcl:#fffffd\npid:305746470\nhgt:173cm\n\npid:081972188 iyr:2011\nhcl:9bb154\neyr:2024 byr:1966 ecl:oth cid:185 hgt:171cm\n\npid:522553186 hgt:171cm ecl:grn hcl:#7d3b0c\nbyr:1955\neyr:2025 iyr:1999\n\niyr:2015\nbyr:1941 pid:140123640 ecl:amb hgt:153cm hcl:#ceb3a1 eyr:2020\n\necl:grn\ncid:202 hcl:#602927\neyr:2029\nhgt:180cm byr:1974\npid:658341964\niyr:2017\n\npid:2037156813 eyr:1978 ecl:grn hcl:519b45 iyr:2011 byr:2017\n\nhcl:#fffffd ecl:hzl\npid:658716289 byr:2001 hgt:154cm cid:234 eyr:2031 iyr:2010\n\nbyr:2013 pid:#eb519e eyr:2026\nhgt:157cm iyr:2030 hcl:7e9d5a ecl:oth\n\nbyr:2002\necl:brn iyr:1998 hgt:60cm\nhcl:#7d3b0c pid:#90286d\neyr:1938\n\nbyr:1956 hcl:#efcc98\nhgt:190cm\niyr:2010 eyr:2023\necl:amb\ncid:342 pid:278521396\n\nhgt:67cm\ncid:98 eyr:2036 byr:2028 ecl:grt hcl:08b5ad iyr:2029 pid:187cm\n\necl:dne hcl:fca461 hgt:129 iyr:2020 eyr:2027 byr:2022 pid:5014208295\n\nhgt:169cm ecl:gry iyr:2015 eyr:2025 hcl:#733820 pid:240085824 byr:1920\n\niyr:2020 eyr:2033\npid:#3f8e9d hgt:190in ecl:brn hcl:#efcc98 byr:2004\n\niyr:2018 hcl:#18171d ecl:brn byr:1933\npid:514517439 hgt:171cm eyr:2028\n\neyr:2030 pid:053251865\nbyr:2028 hgt:174cm iyr:2015 hcl:5a0da9 ecl:hzl\n\nhgt:169cm iyr:2014 ecl:oth eyr:2029 pid:348737413 hcl:#b6652a byr:1997\n\nhgt:181cm cid:315\neyr:2021 iyr:2016 byr:1966 ecl:oth pid:779435812 hcl:#733820\n\npid:5052579 cid:268 hgt:193in\nhcl:z\niyr:1942 eyr:1977\n\neyr:2039 hgt:69cm cid:337\niyr:2023 pid:568948965\nbyr:2018 hcl:z ecl:amb\n\nbyr:2014 eyr:2028\ncid:311\npid:158cm ecl:#946399 hgt:99\nhcl:z\niyr:1978\n\npid:474742310 iyr:2015 eyr:2021 hcl:#14f5da\nhgt:163cm ecl:oth\n\nhcl:#efcc98\necl:blu\nhgt:178cm pid:815309025 byr:2024\niyr:2008 eyr:1922\n\nbyr:1946 eyr:2028 pid:364439229 iyr:2011 hgt:186cm cid:79 ecl:blu\n\neyr:2028 hgt:157cm\ncid:59 iyr:2010 byr:1927\necl:brn\npid:893074368\n\nhcl:#18171d ecl:#2defe4 hgt:128 byr:1940\npid:181904523 iyr:2022 eyr:1937\n\neyr:2023 hgt:172cm iyr:2012 hcl:#a97842 ecl:hzl byr:1982 pid:638759541\n\ncid:91 hcl:#623a2f\nbyr:1996 eyr:2028 pid:181384347 hgt:175cm\niyr:2020\n\niyr:2017 eyr:2021 ecl:gry\nbyr:1979 hgt:168cm hcl:#6b5442 pid:950995084\n\necl:blu iyr:2012 byr:1972\nhcl:#888785 eyr:2022 hgt:179cm pid:293827532\n\nhgt:179cm\necl:hzl iyr:2011\nbyr:1982 eyr:2020 hcl:#efcc98 cid:209 pid:626732917\n\nbyr:1989\nhcl:#6b5442 pid:679850983 iyr:2020\nhgt:192cm ecl:blu\n\npid:333485773 hgt:167in ecl:zzz iyr:1945\neyr:2035 cid:319 hcl:#341e13\n\nhgt:64in\ncid:202 eyr:2023 ecl:gry hcl:#c0946f pid:212611149 byr:1928 iyr:2010\n\nhgt:183cm hcl:#e8fa30 ecl:oth eyr:2021\nbyr:1943 pid:667658434\niyr:2010\n\ncid:117\nbyr:2022 hcl:z ecl:#c6ae1f iyr:2028\nhgt:188cm\npid:0883366415\neyr:2030\n\nhcl:z\npid:99347800 iyr:2030 eyr:2032 ecl:#cd1fd7 hgt:192cm byr:2019\n\nhgt:178cm byr:2013\niyr:2026 hcl:ad3da1\neyr:2020 pid:1626818790\n\nhgt:63cm\niyr:1964\neyr:2032\ncid:135 byr:2017 hcl:#a97842 pid:#1b83f5 ecl:gmt\n\nhcl:c352d2 byr:1927 ecl:gmt hgt:187cm\neyr:2031 pid:170cm\n\nbyr:2022 eyr:1958 ecl:zzz pid:3692521800 hcl:8b2b50 iyr:1946 hgt:155in\n\necl:#43f305 hcl:z byr:2028\npid:63518738 cid:243 eyr:2037\nhgt:67cm iyr:1929\n\necl:brn hcl:#888785\npid:495215177 byr:1962 eyr:2021\ncid:192\nhgt:151cm iyr:2012\n\necl:#dcca8e cid:64 eyr:2030 pid:380057616\nhcl:z iyr:2026 byr:1962\n\nhcl:z\necl:hzl eyr:2027 byr:2015 pid:302526406 hgt:175cm iyr:2017\n\nbyr:1966\ncid:133 pid:9953651821 ecl:gry iyr:2020 hgt:152cm\nhcl:#fffffd eyr:2026\n\nhgt:191cm byr:1960 pid:752655640 hcl:#888785\ncid:249 ecl:blu\niyr:2012 eyr:2028\n\npid:#c8c988 eyr:2027 hgt:157in hcl:z iyr:2025 byr:2019 ecl:zzz cid:195\n\nhgt:96 pid:95381813 iyr:1950\nhcl:#fffffd eyr:2026\nbyr:2010 cid:318\necl:#48a819\n\neyr:2020\necl:oth byr:1951 pid:080392492\niyr:2015 hcl:#6b5442 hgt:176cm\n\nhgt:162cm pid:897792747 byr:1968\nhcl:#ceb3a1 ecl:grn eyr:2026 iyr:2014\n\neyr:2038 hcl:cc324a byr:1983 ecl:brn\nhgt:161 pid:#adf47f cid:208\n\niyr:2013 ecl:blu hcl:#866857 byr:1981 hgt:157cm eyr:2025 pid:216910202\n\nhgt:152in byr:1990\niyr:2027 hcl:a4a3ae\necl:#058ae2 eyr:2037 pid:646420120\n\necl:oth byr:1982 eyr:2027 hgt:65in iyr:2019\nhcl:#efcc98 cid:224\npid:854228141\n\npid:772612093\niyr:2027\nhgt:175in byr:1981 hcl:c0b5a9 ecl:utc\n\nhcl:#888785 iyr:2014 byr:1975\necl:blu\npid:461319017 cid:229 eyr:2030 hgt:154cm\n\nhgt:179cm eyr:2024\npid:192cm\niyr:2017 ecl:grt byr:1934 hcl:z cid:92\n\nhcl:9c9409 iyr:2020 eyr:2030 hgt:156in\ncid:189 pid:732321495\nbyr:1937 ecl:xry\n\neyr:2026 pid:092259220 byr:1943\niyr:2010 hgt:153cm hcl:#602927\n\nbyr:1925 hgt:180cm hcl:#888785 iyr:2014\npid:402548656 eyr:2023 ecl:hzl\ncid:188\n\neyr:2020 pid:874307939 hcl:#3f85a4\necl:gry hgt:167cm byr:1959 iyr:2014\n\neyr:2026 hgt:183cm iyr:2011 byr:1940 ecl:blu pid:810026000\ncid:226 hcl:#866857\n\ncid:292 ecl:grt hgt:72cm\nbyr:2009\niyr:2000 eyr:1946 hcl:7be409 pid:996363336\n\neyr:2027\niyr:2021\npid:632405666\nbyr:2027\necl:#d83a36 hcl:z hgt:190in\n\ncid:80\nhgt:173cm\npid:735853952 ecl:gry hcl:#fffffd eyr:2025 iyr:2020 byr:1923\n\nbyr:1977\nhcl:#733820\niyr:2020 ecl:#698d72 hgt:186cm pid:678869986 cid:67\neyr:2021\n\nhgt:61cm iyr:2022 eyr:1972 hcl:979bcf byr:2023 pid:44037388 ecl:xry\n\neyr:2032 pid:193cm hcl:z\nhgt:68cm byr:2016\n\nbyr:2008 cid:239\nhcl:ddc745 eyr:2033 ecl:#6858b5 hgt:64cm iyr:2023\npid:89867524\n\niyr:2016 hgt:74in hcl:#18171d\nbyr:1959\necl:blu\npid:848487392\neyr:2027\n\nhgt:165in ecl:grn\nbyr:1960 eyr:2029\niyr:2017\nhcl:#b6652a pid:096349067\n\neyr:2025 ecl:brn\npid:634481064 iyr:2015\nhcl:#7d3b0c\nbyr:1943\n\necl:grn eyr:2021\npid:34753212 cid:51 hgt:184 iyr:1970 byr:2012\n\neyr:1973 iyr:2014 cid:225\nbyr:2028 ecl:gmt\nhgt:158cm\npid:#74f9b8 hcl:f6932a\n\nhgt:168cm\nhcl:#602927\npid:622067991 ecl:amb eyr:2025 iyr:2018\n\npid:791399958 byr:1956 eyr:2027 hcl:#602927\necl:brn\niyr:2016 hgt:192cm\n\nhgt:168cm iyr:2015 cid:115 ecl:#3fa48b eyr:2037 hcl:#1bf77b byr:1980 pid:947370470\n\niyr:2008\nbyr:2021 ecl:zzz\nhcl:z hgt:109 pid:#fc2a91 cid:268 eyr:1957\n\nbyr:2018 hcl:fef19c iyr:2014 ecl:blu eyr:2023 cid:259 pid:193cm hgt:156\n\nhcl:#b6652a\niyr:2023 byr:2021 hgt:153cm pid:934391984 eyr:2021 ecl:brn\n\npid:168cm hcl:b13f1e eyr:2038 iyr:2020 ecl:#7c0a6d hgt:169in\n\necl:amb cid:170\npid:300188824 eyr:2024 byr:1954 hcl:#b6652a hgt:166cm\niyr:2013\n\necl:brn\neyr:2023\nhcl:#b6652a byr:1948 hgt:71in iyr:2015\npid:575973478\n\neyr:2026 hgt:180cm hcl:#866857 ecl:grn iyr:2013\nbyr:1997 pid:864648034\n\necl:hzl\niyr:2013 eyr:2024 hcl:#02e17f byr:1960\nhgt:163cm cid:338 pid:972201795\n\niyr:1994 eyr:2035 ecl:xry\nhcl:z hgt:167in pid:159cm\n\necl:hzl\nbyr:1952\neyr:2024 hgt:191cm pid:229400637 iyr:2011 hcl:#122db6\n\neyr:2022\npid:467667316 iyr:2019 hcl:#623a2f\nhgt:161cm\necl:oth\n\necl:hzl eyr:2030 hcl:#733820 byr:1944\nhgt:193cm pid:819137596\n\ncid:321 hgt:184in ecl:hzl iyr:2018 byr:2010 eyr:2020 pid:171cm\n\necl:amb eyr:2025 hcl:#c0946f pid:360891963 byr:1925\niyr:2017\nhgt:180cm\n\nhcl:#cfa07d byr:1949\neyr:1931 cid:350\necl:#ff9943\npid:7550350393 hgt:75\n\neyr:2026 ecl:amb hcl:z pid:746919391 iyr:2014 hgt:179cm byr:1997\n\npid:157cm iyr:2030\nhgt:152cm\nhcl:ce8aa7 eyr:1976 ecl:grt cid:160 byr:2011\n\neyr:2022\nhgt:183cm\nbyr:2000 iyr:2016 hcl:#a97842 ecl:blu pid:500935725\n\ncid:245 eyr:2026 iyr:2015 ecl:gry hcl:#cfa07d\nbyr:1946\n\neyr:2022 hgt:168cm\npid:786361311 iyr:2013 hcl:#c0946f byr:1988 cid:244 ecl:hzl\n\nbyr:2014 hgt:176in iyr:2021\nhcl:z pid:6361650130\neyr:2039 cid:300\necl:#76310d\n\necl:amb hgt:170in byr:2013\niyr:2024 eyr:2033 hcl:#888785\n\neyr:2025\niyr:1957 cid:182\necl:blu pid:253552114\nhgt:188cm hcl:z\n\ncid:83 ecl:amb\neyr:2022 byr:1947\niyr:2013 hcl:#cfa07d\nhgt:188cm pid:447734900\n\niyr:2013 hcl:#602927 byr:1979 hgt:167cm cid:321 pid:978238277 eyr:2020\necl:grn\n\nhgt:73cm\ncid:199 ecl:amb iyr:2019\nhcl:#733820 eyr:2021\nbyr:1939 pid:364966395\n\nhgt:168in ecl:lzr eyr:2031\npid:#ff10ac byr:2014 iyr:2006\n\nhgt:164cm eyr:1994 iyr:2010\necl:amb hcl:#7d3b0c cid:240 pid:191cm\nbyr:2025\n\necl:grn\neyr:2029\nhcl:#7d3b0c hgt:158cm\nbyr:1939 iyr:2012 pid:855145518\n\niyr:2013 hcl:#ceb3a1\nhgt:163cm eyr:2023 pid:761215570\n\nhgt:154cm ecl:grn\niyr:2019 byr:1981 eyr:2021 hcl:#602927\ncid:80 pid:427938374\n\neyr:2026 hgt:154cm cid:102 iyr:2012 pid:6632346648 ecl:amb\nbyr:2010 hcl:z\n\ncid:302 iyr:2014\npid:161cm eyr:2037 byr:2026 ecl:gry hgt:60 hcl:9fb9e0\n\necl:brn iyr:2015 pid:041582949 cid:180 byr:1938\nhgt:158cm\nhcl:#602927 eyr:2026\n\necl:xry pid:#546891 hcl:#18171d hgt:71cm byr:1974\niyr:2018 eyr:2026\n\niyr:2015 eyr:2025 ecl:brn hgt:180cm hcl:#b6652a\nbyr:1938\npid:752379523\n\niyr:2020 ecl:grn hgt:179cm byr:1929\ncid:103 hcl:#602927\npid:212212232\n\npid:262917603 ecl:gry iyr:2012 hcl:#fffffd hgt:165cm eyr:2022 byr:1965\n\nbyr:1960\neyr:2031 hgt:184in\npid:#ac1606 iyr:2013 hcl:#888785\ncid:260 ecl:#7b2c3b\n\nbyr:1987\neyr:2025 cid:102\nhgt:74in ecl:brn hcl:#4a6c75 pid:20220733 iyr:2028\n\neyr:2031 pid:823539963\niyr:1957\nhgt:159cm byr:1953 ecl:oth cid:186 hcl:26d85f\n\necl:gry iyr:2011\nhgt:167cm hcl:#fffffd pid:001642707 eyr:2030 byr:1952\n\niyr:2029 ecl:grt\nhcl:z byr:2011 hgt:64cm pid:37027672\neyr:1923\n\npid:021102096\neyr:2024 hgt:66 hcl:#a97842 byr:1922 ecl:gry iyr:2013\n\npid:166477382 ecl:oth byr:1982 iyr:2010 eyr:2020\nhcl:#866857 hgt:60in\n\nhcl:#7d3b0c\niyr:2018 pid:065652921 byr:1939\necl:blu\nhgt:180cm eyr:2028\n\necl:amb iyr:2020 byr:1967 hcl:#fffffd eyr:2028 hgt:157cm\n\neyr:2029 hgt:185cm cid:85 hcl:z iyr:2014 pid:#1f4787 ecl:grn byr:2010\n\nbyr:1987 hcl:d397d9 iyr:2028\nhgt:158cm pid:686994921 ecl:hzl\n\necl:oth\nbyr:2008\npid:#db73d9 hgt:174cm hcl:#6b5442 iyr:1955 eyr:2028\n\neyr:2020 ecl:amb pid:490866828 hcl:#cfa07d cid:113\nhgt:165cm\n\niyr:2011\npid:320518492\neyr:2028 byr:1940 hgt:164cm cid:84\nhcl:#341e13 ecl:grn\n\nhgt:142\nhcl:z pid:152cm iyr:1953 eyr:2040 ecl:#e44f11 byr:2024\n\necl:gmt hcl:be7483 eyr:2027\niyr:2026\npid:396722617 hgt:153cm\n\necl:dne byr:2015\npid:330208482\nhcl:#7d3b0c iyr:2014 eyr:2022 hgt:95\n\nbyr:1925 hcl:#7d3b0c\necl:gry\neyr:2024\npid:694714722 hgt:158cm iyr:2015 cid:283\n\neyr:2023\nhgt:183cm cid:345\nhcl:#6b5442 ecl:hzl iyr:2019 byr:1971 pid:458416257\n\necl:#dcae8b\niyr:2027 eyr:1940 byr:2009 hcl:f024de pid:20713584\nhgt:169in\n\nhcl:#888785 eyr:2026\nbyr:1984 iyr:2013 pid:935837461\nhgt:193cm\necl:gry\n\npid:7343429 byr:2002\nhgt:191cm\necl:lzr iyr:1983\neyr:1966 hcl:#623a2f\ncid:302\n\nhcl:#888785 iyr:2014 hgt:173cm\nbyr:2002 pid:005350165 eyr:2022\n\nbyr:2013 iyr:2028\necl:lzr pid:5426915565 eyr:2018 hcl:z hgt:70cm cid:142\n\neyr:2021 hgt:157cm ecl:utc iyr:2014\nbyr:1934 cid:348 hcl:#623a2f pid:607329117\n\niyr:2015 hgt:167cm ecl:hzl\npid:088516395 hcl:#efcc98 byr:1968 eyr:2029\n\neyr:2028\niyr:2019\ncid:199\necl:amb\nhgt:152cm byr:1928 pid:547112666 hcl:#623a2f\n\npid:406202463\nbyr:1950 cid:214\neyr:2021 hcl:#fffffd hgt:177cm\necl:brn\n\neyr:2029\ncid:210 byr:1982 pid:578085789 ecl:brn\nhgt:187cm iyr:2010 hcl:#c0946f\n\nbyr:1980 hcl:#c0946f hgt:159cm pid:177650318 eyr:2024 ecl:amb iyr:2019\n\npid:923359071 byr:1997 ecl:#faa530\neyr:2028 iyr:2013 hcl:e6c902 hgt:177cm\n\neyr:2040\ncid:98 hgt:156in\necl:oth\niyr:1996 pid:81500971\nhcl:#6b5442\nbyr:2017\n\nbyr:2004 iyr:1941\nhcl:e1e4bb hgt:67cm pid:1143915351 ecl:#0d3e5d eyr:1972\n\nhgt:184cm hcl:#623a2f\neyr:2028 pid:680951513 ecl:grn iyr:2014 byr:2001\n\nhcl:#866857 hgt:156cm\neyr:2020\necl:grn iyr:2010 pid:589945116\n\npid:599795227 iyr:2016 ecl:grn\nhcl:#cfa07d hgt:157cm byr:1967 eyr:2029\n\nhcl:#b6652a\nbyr:1966 iyr:2017 pid:117232314 ecl:oth hgt:186cm eyr:2029\n\npid:605019880\niyr:2020\nhgt:169cm byr:1980 hcl:#623a2f\necl:hzl eyr:2030\n\neyr:2019 hcl:#ceb3a1 pid:988269284\niyr:2015 byr:1989 hgt:171cm ecl:oth\n\ncid:311 byr:1998 ecl:hzl\neyr:2027 hgt:152cm pid:734870801 hcl:#7d3b0c\niyr:2013\n\nhcl:#efcc98\nhgt:180cm iyr:2020\npid:202682423 byr:2027 ecl:grn eyr:2030\n\nhcl:f0701f pid:161cm cid:291 hgt:160in iyr:2030\necl:#e12345\n\ncid:248 byr:1943 eyr:2024 hgt:181cm ecl:brn iyr:2010 hcl:#bf813e\n\nbyr:2005 hgt:187in eyr:2034 iyr:2025 hcl:z ecl:gmt\npid:78691465\n\nbyr:2000\nhcl:#574f4e eyr:2024 iyr:2017 pid:#fec795 hgt:185cm ecl:gry\n\nhcl:#a97842 byr:1959\niyr:2019 pid:690444949\nhgt:160in eyr:1978\n\ncid:236\niyr:2010 eyr:2025 byr:1976 pid:398376853\nhcl:#341e13\nhgt:150cm\n\nhgt:182cm iyr:2019 hcl:#866857\necl:grn\nbyr:1926 eyr:2029 pid:307880154 cid:94\n\necl:blu\nhgt:182cm pid:178cm byr:2019 eyr:2025\niyr:2022 hcl:#a2117d\n\neyr:2020 hcl:#c0946f ecl:amb pid:135511825 byr:1954 hgt:68in iyr:2017\n\nhgt:188cm ecl:amb iyr:2011\npid:949021029 eyr:2028 hcl:#fffffd byr:1986\n\niyr:1949 pid:#8a8d94 ecl:#922a92 byr:1925 hcl:#63c4a5\n\nhcl:#c0946f\necl:grn iyr:2013 eyr:2024 pid:420295283 hgt:181cm\nbyr:1977\n\nbyr:1941 pid:299186098 hcl:#f1fa72\niyr:2013 ecl:amb eyr:2022 hgt:152cm\ncid:150\n\necl:blu eyr:2021 hgt:60in hcl:#623a2f\nbyr:1930 iyr:2018\n\neyr:2028 pid:663108638\nhgt:75in cid:217\nbyr:1962 ecl:brn hcl:#733820\n\nhcl:#341e13 hgt:188cm ecl:blu\npid:868930517\neyr:2029\niyr:2010 byr:1938\n\npid:194376910 byr:1956\nhcl:#cd4ab4\neyr:1940 iyr:2012 ecl:#396cc3\n\npid:#c5da2a hgt:162cm\nhcl:#866857\ncid:95 ecl:#fa1f85\niyr:1965 byr:1963 eyr:2039\n\npid:44063430 hcl:289b20\necl:#77ddd9 eyr:1953\niyr:1924 byr:2026 cid:267 hgt:180in\n\necl:brn pid:990171473\neyr:2028 byr:1937\nhgt:165cm iyr:2015\nhcl:#fffffd cid:68\n\niyr:1968 ecl:lzr pid:#05a4ab eyr:1944 hcl:z\n\nhgt:185cm hcl:#7d3b0c eyr:2029 ecl:oth\niyr:2016 byr:1997 pid:349316183\n\nhcl:z\necl:gry\nhgt:192in pid:542996841 iyr:2019 cid:144 eyr:2028\nbyr:2026\n\neyr:2024\nhcl:#18171d\necl:grn hgt:160cm pid:399767457 byr:1979 iyr:2015\n\necl:#924147 pid:665314 cid:216 iyr:2026 hcl:z\nbyr:2023 hgt:157\neyr:1987\n\neyr:1989 hcl:4f8779 ecl:#05ff52 iyr:1943 pid:3693010880 hgt:72cm\nbyr:2009\n\nhcl:#c0946f eyr:2022\niyr:2015 hgt:157cm byr:1928 ecl:grn pid:243566446\n\neyr:2030\nhcl:#733820 byr:1988 iyr:2017 cid:125 hgt:193cm ecl:amb pid:939550667\n\ncid:161 hgt:157in\nhcl:#cfa07d eyr:2036 ecl:#4efa35\niyr:2012 pid:3943280550 byr:1979\n\necl:lzr hcl:#341e13 hgt:69cm eyr:2026 cid:322 byr:2006 pid:827964469\n\necl:amb iyr:2012\neyr:2020 hgt:178cm pid:590705772 cid:218\nhcl:#c0946f byr:1922\n\nhcl:632b01 cid:252 byr:1933 ecl:hzl\niyr:2025 eyr:2040 hgt:191cm\npid:406010613\n\npid:711656819 ecl:blu eyr:2030 hgt:151cm\nbyr:1999 cid:319\nhcl:#efcc98\n\npid:294223216 iyr:2012\nhgt:171cm\neyr:2027\nhcl:#ceb3a1 ecl:oth\nbyr:1952 cid:58\n\nhcl:#888785 pid:457433756 eyr:2022 hgt:186cm\ncid:336\nbyr:1923 iyr:2013 ecl:oth\n\nbyr:2014 hcl:6ce7d6 eyr:2030 pid:190cm iyr:2018 hgt:63cm ecl:#5063b9\n\ncid:267 hgt:189cm\neyr:2020 hcl:#ffeffd iyr:2014 byr:1989\necl:grn\npid:571696542\n\niyr:1953 hgt:160in\necl:grt cid:188 eyr:2034\npid:179cm byr:2007\nhcl:6895eb\n\nhgt:165cm ecl:oth\niyr:2020\neyr:2028\nhcl:#18171d pid:111506895\n\neyr:1957 cid:133 ecl:hzl pid:#e56ca2 byr:2003 hcl:8a9d65\n\nhcl:6c4ecd byr:1930 hgt:179cm\neyr:2007 iyr:2028 ecl:#3d8705\npid:#dbfeec\n\neyr:2036\nbyr:1991 ecl:#2202d0 hcl:#341e13 pid:85636989 hgt:61cm\niyr:1930\n\nbyr:1996 iyr:2027 hcl:z\npid:780164868 ecl:zzz eyr:2026 hgt:73cm\n\nbyr:1940\niyr:1992 pid:132016954 eyr:2021\ncid:147 hcl:#d78bfd ecl:xry\n\nhgt:174cm\nbyr:1970\neyr:2021 hcl:#341e13 pid:086579106 iyr:2017 ecl:oth\n\necl:oth cid:207 byr:1998 pid:479696359\nhgt:174cm iyr:2017 eyr:2020 hcl:#6b5442\n\necl:hzl iyr:2014\nhcl:#cfa07d hgt:163cm eyr:2025\nbyr:1951 pid:563337128\n\necl:gry hgt:172cm iyr:2013 hcl:#efcc98\nbyr:1970\npid:848996674\neyr:2027\n\nhgt:163cm pid:583600660 iyr:2015 hcl:#18171d byr:1959 ecl:brn\n\nhcl:#efcc98 pid:353178375 cid:145\niyr:2018 byr:1988 ecl:oth eyr:2029\n\nhgt:62in\nbyr:1921 pid:125944934 hcl:#b6652a\neyr:2025 cid:71 iyr:2018 ecl:blu\n\niyr:2017 ecl:brn hcl:#602927 hgt:172cm pid:932690969 byr:1957 eyr:2026\n\nhcl:#efcc98 pid:709772213 cid:146 ecl:oth byr:1998 iyr:2010 hgt:74in\neyr:2029\n\nbyr:1965\niyr:2011 hcl:#6b5442 cid:325 hgt:68in eyr:2028 pid:813272708 ecl:hzl\n\npid:57223084 hcl:#602927 ecl:grn\nhgt:156cm eyr:1972 iyr:2017\n\npid:21573000 byr:2030 cid:168\nhcl:baee61 eyr:2021 hgt:150cm\niyr:1950 ecl:#acdd7e\n\necl:gry hgt:150cm hcl:#6b5442\nbyr:1927\niyr:2018 pid:161cm eyr:2021\n\nhgt:153cm\niyr:2030 ecl:grn pid:575037626 byr:1921 eyr:2021 hcl:#866857\n\nhgt:175cm iyr:2014\nbyr:1946 eyr:2025\ncid:159 hcl:#18171d\necl:oth pid:129913905\n\npid:566885568\nhgt:157cm eyr:2021 ecl:gry byr:1933\nhcl:#623a2f cid:223\n\necl:blu byr:1981 cid:160\niyr:2014\nhcl:#a97842 eyr:2021 hgt:172cm pid:714902414\n\nhcl:#b6652a eyr:2021\nhgt:168cm byr:1921 iyr:2018 ecl:oth pid:021318713\n\nhgt:168 pid:222439573\ncid:209\nhcl:z byr:2016 ecl:#26a0fb\neyr:2031\n\nhgt:181cm\nbyr:1970 eyr:2024\npid:476171876 ecl:hzl\nhcl:#efcc98\niyr:2019\n\nhcl:#18171d ecl:oth iyr:2018 byr:1949 hgt:165cm\neyr:2029 pid:078204562\n\nbyr:2021 ecl:blu iyr:1963\npid:2911597977 hcl:#ceb3a1 eyr:2020\nhgt:154cm\n\npid:159642237\nhcl:#81e94d ecl:gry eyr:2028 byr:1958\n\nhgt:90 hcl:#a97842 pid:#db1158\niyr:1928 ecl:#c82a43 byr:1971 eyr:2036\n\neyr:2020\nhgt:177cm iyr:2013\ncid:347 ecl:grn\nbyr:1998 pid:455369144\n\nbyr:1936\npid:444305229 iyr:2013 eyr:2025 hcl:#733820\necl:gry\nhgt:175cm\n\nbyr:2027 hcl:z\nhgt:61cm ecl:brn pid:836686228 eyr:2023 iyr:2030\n\nbyr:1931\necl:hzl hgt:168cm eyr:2023 pid:956562488 hcl:#fffffd\n\necl:#4126e5 pid:182cm iyr:2021\nhgt:144 eyr:2039 hcl:z\n\npid:321400085 hcl:#733820 hgt:189cm\necl:hzl byr:1923 eyr:2023 iyr:2016\n\niyr:2011 hgt:192cm hcl:#b6652a byr:1988 pid:998875769\necl:#e612d9 eyr:2015\n\neyr:2021 iyr:2011 pid:265966660\nbyr:1934 hgt:180cm\nhcl:#7d3b0c\necl:gry cid:225\n\npid:550612542 ecl:oth byr:1931\niyr:2014 cid:99\nhcl:#cfa07d hgt:163cm eyr:2026\n\necl:gry hgt:156cm iyr:2018 hcl:#5d9d64 pid:295386055 byr:1996\neyr:2025\n\necl:gry iyr:2013 pid:855457285 cid:309 eyr:2030\nhcl:#733820 byr:1973\n\neyr:2030 pid:86472746 ecl:blu\nhgt:192cm\niyr:2013 byr:1939 hcl:#b6652a\n\nhcl:#888785\nbyr:1935\niyr:2018\nhgt:155cm ecl:grn\npid:612879095 cid:108 eyr:2027\n\neyr:2016 hcl:z pid:025915371 iyr:2010 hgt:183cm ecl:gry\nbyr:2010\ncid:228\n\nhcl:#38dbf4\nbyr:1925 ecl:amb eyr:2020 pid:065102805 iyr:2018\n\ncid:244 hgt:171cm\nhcl:#cfa07d pid:466737179 eyr:2025\nbyr:1937 iyr:2020 ecl:oth\n\necl:brn byr:1993 hgt:179cm hcl:#341e13 pid:855375268 eyr:2028\niyr:2018\n\npid:809135189 iyr:2020 hgt:162cm eyr:2027\nhcl:#888785 byr:1988 ecl:grn\n\nbyr:2003 pid:4446708453\nhgt:188cm iyr:2013 hcl:#888785 ecl:blu eyr:2008\n\nhgt:165in ecl:#db642f iyr:2014\neyr:2020\nbyr:1955 hcl:371f72 pid:756089060\n\necl:lzr\nhgt:177in eyr:2037 pid:175cm\nbyr:2023 hcl:03b398 iyr:2026\n\niyr:2017 ecl:blu byr:1942 hcl:#733820 eyr:2023 hgt:151cm pid:289923625";

        String[] passPorts = data.split("\n\n");
        Arrays.stream(passPorts).forEach(s -> {
            if (Arrays.stream(fields).allMatch(s::contains)) {
                o.part1++;
                final var keyvalue = s.split("[ \n]");
                if (Arrays.stream(keyvalue).allMatch(kv -> {
                    final var split = kv.split(":");
                    final var key = split[0];
                    final var value = split[1];
                    switch (key) {
                        case "byr":
                            if (value.length() != 4) return false;
                            try {
                                int year = Integer.parseInt(value);
                                return (year >= 1920 && year <= 2002);
                            } catch (Exception e) {
                                return false;
                            }
                        case "iyr":
                            if (value.length() != 4) return false;
                            try {
                                int year = Integer.parseInt(value);
                                return (year >= 2010 && year <= 2020);
                            } catch (Exception e) {
                                return false;
                            }
                        case "eyr":
                            if (value.length() != 4) return false;
                            try {
                                int year = Integer.parseInt(value);
                                return (year >= 2020 && year <= 2030);
                            } catch (Exception e) {
                                return false;
                            }
                        case "hgt":
                            if (value.endsWith("in")) {
                                try {
                                    int size = Integer.parseInt(value.replace("in", ""));
                                    return (size >= 59 && size <= 76);
                                } catch (Exception e) {
                                    return false;
                                }
                            } else if (value.endsWith("cm")) {
                                try {
                                    int size = Integer.parseInt(value.replace("cm", ""));
                                    return (size >= 150 && size <= 193);
                                } catch (Exception e) {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        case "hcl":
                            return Pattern.compile("^#[0-9a-f]{6}$").matcher(value).matches();
                        case "ecl":
                            String[] colors = "amb blu brn gry grn hzl oth".split(" ");
                            return Arrays.stream(colors).filter(value::contains).count() == 1;
                        case "pid":
                            return Pattern.compile("^[0-9]{9}$").matcher(value).matches();
                        default:
                            return true;
                    }
                })) {
                    o.part2++;
                }
            }

        });
        endOfWork(tikTok, day, false, o.part1, o.part2);
    }

    public static void day5() {
        TikTok tikTok = new TikTok(true);
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;
            int lastSeat = -1;
        };
        final var day = Utils.getDay();
        String data = Utils.getData(day);
        final var split = Utils.asLines(data);
        Arrays.stream(split).mapToInt(s -> {
            s = s.replace('F', '0')
                    .replace('B', '1')
                    .replace('L', '0')
                    .replace('R', '1');
            int row = Integer.parseInt(s.substring(0, 6), 2);
            int seatId = Integer.parseInt(s, 2);
            o.part1 = Math.max(o.part1, seatId);
            if (row == 127f || row == 0) {
                return -1;
            }
            return seatId;

        }).sorted().anyMatch(seatId -> {
            if (o.lastSeat == -1) {
                o.lastSeat = seatId;
            } else {
                if (o.lastSeat + 1 != seatId) {
                    o.part2 = o.lastSeat + 1;
                    return true;
                }
                o.lastSeat = seatId;
            }
            return false;
        });
        //insert magic right here
        endOfWork(tikTok, day, false, o.part1, o.part2);
    }

    public static void day6() {
        TikTok tikTok = new TikTok(true);
        final var day = Utils.getDay();
        String data = Utils.getData(day);
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;
        };
        final var split = data.split("\n\n");
        Arrays.stream(split).forEach(s -> {
            s = s.replace("\n", "");
            o.part1 += s.chars().distinct().count();
        });
        Arrays.stream(split).forEach(s -> {
            final var split1 = s.split("\n");
            for (char j = 'a'; j <= 'z'; j++) {
                char finalJ = j;
                o.part2 += Arrays.stream(split1).allMatch(sss -> sss.contains("" + finalJ)) ? 1 : 0;
            }
        });
        endOfWork(tikTok, day, false, o.part1, o.part2);
    }

    public static void day7() {
        TikTok tikTok = new TikTok(true);
        record Content(String name, int qty) {

        }
        final var day = Utils.getDay();
        String data = Utils.getData(day);
        final var split = Utils.asLines(data);
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;

        };

        HashMap<String, ArrayList<Content>> bagToContent = new HashMap<>();
        Arrays.stream(split).map(s -> s.split(" bags contain ")).forEach(rule -> {
            ArrayList<Content> arrayList = new ArrayList<>();
            final var contents = rule[1].split(", ");
            for (int i = 0; i < contents.length; i++) {
                final var descriptor = contents[i].split(" ", 2);
                if (descriptor[0].equals("no")) {
                    continue;
                }
                final var qty = Integer.parseInt(descriptor[0]);
                final var name = descriptor[1].split(" bag")[0];
                arrayList.add(new Content(name, qty));
            }
            bagToContent.put(rule[0], arrayList);
        });
        o.part1 = (int) bagToContent.entrySet().stream().filter(entry -> {
            boolean valid = false;
            ArrayList<Content> bagToSearch = new ArrayList<>();
            bagToSearch.add(new Content(entry.getKey(), 1));
            if (entry.getKey().equals("shiny gold")) {
                return false;
            }
            while (bagToSearch.size() != 0) {
                final var content = bagToSearch.remove(bagToSearch.size() - 1);
                if (content.name().equals("shiny gold")) {
                    valid = true;
                }
                final var contents = bagToContent.get(content.name);
                contents.forEach(c -> {
                    bagToSearch.add(new Content(c.name(), content.qty * c.qty()));
                });

            }
            return valid;
        }).count();
        bagToContent.get("shiny gold").stream().forEach(goldContent -> {
            ArrayList<Content> bagToSearch = new ArrayList<>();
            bagToSearch.add(goldContent);
            while (bagToSearch.size() != 0) {
                final var content = bagToSearch.remove(bagToSearch.size() - 1);
                o.part2 += content.qty;
                final var contents = bagToContent.get(content.name);
                contents.forEach(c -> {
                    bagToSearch.add(new Content(c.name(), content.qty * c.qty()));
                });

            }
        });
        endOfWork(tikTok, day, false, o.part1, o.part2);
    }

    public static void day8() {
        TikTok tikTok = new TikTok(true);
        final var day = Utils.getDay();
        String data = Utils.getData(day);
        final var split = Utils.asLines(data);
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
        endOfWork(tikTok, day, false, o.part1, o.part2);
    }

    public static void day9() {
        TikTok tikTok = new TikTok(true);
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var day = Utils.getDay();
        String data = Utils.getData(day);
        final var split = Utils.asLines(data);
        final var numb = Arrays.stream(split).mapToLong(Long::parseLong).toArray();
        for (int i = 25; i < numb.length; i++) {
            final var ints = Arrays.copyOfRange(numb, i - 25, i);
            final var l = numb[i];
            final var b = Arrays.stream(ints).anyMatch((i2) -> Arrays.stream(ints).anyMatch((i3) -> i2 + i3 == l));
            if (!b) {
                o.part1 = l;
                break;
            }
        }
        long sum = 0;
        int from = 0;
        int to = 0;
        while (sum != o.part1) {
            while (sum > o.part1) {
                sum -= numb[from++];
            }
            while (sum < o.part1) {
                sum += numb[to++];
            }
        }
        final var arr = Arrays.copyOfRange(numb, from, to + 1);
        final var longSummaryStatistics = Arrays.stream(arr).summaryStatistics();
        o.part2 = longSummaryStatistics.getMax() + longSummaryStatistics.getMin();
        endOfWork(tikTok, day, false, o.part1, o.part2);
    }

    public static void day10() {
        TikTok tikTok = new TikTok(true);
        var o = new Object() {
            int part1 = 0;
            long part2 = 0;
            int one = 0;
            int three = 0;
            int previous = 0;
        };
        final var day = Utils.getDay();
        String data = Utils.getData(day);
        final var split = Utils.asLines(data);
        var ints = Arrays.stream(split).mapToInt(Integer::parseInt).sorted().toArray();
//        var ints = new int[]{1, 4, 5, 6, 7, 10, 11, 12, 15, 16, 19};
//        var ints = new int[]{1, 2, 3, 4, 7, 8, 9, 10, 11, 14, 17, 18, 19, 20, 23, 24, 25, 28, 31, 32, 33, 34, 35, 38, 39, 42, 45, 46, 47, 48, 49};

        o.previous = 0;
        o.three++;
        Arrays.stream(ints).filter(i -> {
//            if (o.previous + 3 >= ins) {
            switch (i - o.previous) {
                case 1:
                    o.one++;
                    break;
                case 3:
                    o.three++;
                    break;
            }
            o.previous = i;
            return true;
//            }
//            return false;
        }).count();

        int arr[] = new int[ints.length + 1];
        arr[0] = ints[0];
        for (int i = 0; i < ints.length - 1; i++) {
            arr[i + 1] = ints[i + 1] - ints[i];
        }
        arr[ints.length] = 3;
        int current = 0;
        int[] sums = new int[10];
        int seq = 0;
        for (int i = 0; i < arr.length; i++) {
            if (current == 1) {
                seq++;
            } else {
                sums[seq]++;
                seq = 0;
            }
            current = arr[i];
        }
        sums[seq]++;
        seq = 0;
        int[] multi = new int[]{0, 0, 2, 4, 7};
        o.part2 = 1;
//        BigInteger bigInteger = new BigInteger("1");
        for (int i = 2; i < multi.length; i++) {
//            bigInteger = bigInteger.multiply(new BigInteger(String.valueOf(multi[ins])).pow(sums[ins]));
            o.part2 *= Math.pow(multi[i], sums[i]);
        }
//        o.part2/=2;
//        o.part2*=3;
//        System.out.println(bigInteger.toString());
        o.part1 = o.one * o.three;
        endOfWork(tikTok, day, false, o.part1, o.part2);
    }

    public static void day11() {
        TikTok tikTok = new TikTok(true);
        enum State {
            EMPTY('L'), OCCUPIED('#'), FLOOR('.'), AIR('~');
            final char c;

            State(char c) {
                this.c = c;
            }

            static State of(int i) {
                return switch (i) {
                    case 'L' -> EMPTY;
                    case '.' -> FLOOR;
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

            State[] visibleAdjacentOf(int x, int y) {
                State[] states = new State[8];
                int index = 0;
                record Vector(int dx, int dy) {

                }

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }
                        final var vector = new Vector(i, j);
                        int increment = 0;
                        State of;
                        do {
                            increment++;
                            of = of(x + increment * vector.dx, y + increment * vector.dy);
                        } while (of.equals(State.FLOOR));
                        states[index++] = of;
                    }
                }
                return states;
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

            State becomes(int x, int y, int tolerance, boolean nearbor) {
                final State[] states;
                if (nearbor) {
                    states = adjacentsOf(x, y);
                } else {
                    states = visibleAdjacentOf(x, y);
                }
                final var of = of(x, y);
                switch (of) {
                    case EMPTY -> {
                        final var count = Arrays.stream(states).filter(state -> state == State.OCCUPIED).count();
                        if (count == 0) {
                            return State.OCCUPIED;
                        } else {
                            return State.EMPTY;
                        }
                    }
                    case OCCUPIED -> {
                        final var count = Arrays.stream(states).filter(state -> state == State.OCCUPIED).count();
                        if (count >= tolerance) {
                            return State.EMPTY;
                        } else {
                            return State.OCCUPIED;
                        }
                    }
                    case FLOOR -> {
                        return State.FLOOR;
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
        }
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;
        };
        final var day = Utils.getDay();
        String data = Utils.getData(day);
//        String data = "L.LL.LL.LL\n" +
//                "LLLLLLL.LL\n" +
//                "L.L.L..L..\n" +
//                "LLLL.LL.LL\n" +
//                "L.LL.LL.LL\n" +
//                "L.LLLLL.LL\n" +
//                "..L.L.....\n" +
//                "LLLLLLLLLL\n" +
//                "L.LLLLLL.L\n" +
//                "L.LLLLL.LL";
        final var split = Arrays.stream(Utils.asLines(data)).map(String::chars)
                .map(intStream -> intStream.mapToObj(State::of).toArray(State[]::new)).toArray(State[][]::new);
        Grid grid = new Grid(split, true);

        boolean changed;
        do {
            State[][] states = new State[grid.width][grid.heigth];
            changed = false;
            for (int i = 0; i < grid.width; i++) {
                for (int j = 0; j < grid.heigth; j++) {
                    final var becomes = grid.becomes(i, j, 4, true);
                    if (grid.of(i, j) != becomes) {
                        changed = true;
                    }
                    states[i][j] = becomes;
                }
            }
            grid = new Grid(states, false);
        } while (changed);
        grid.forEach(state -> {
            if (state == State.OCCUPIED) {
                o.part1++;
            }
        });

        grid = new Grid(split, true);
        do {
            State[][] states = new State[grid.width][grid.heigth];
            changed = false;
            for (int i = 0; i < grid.width; i++) {
                for (int j = 0; j < grid.heigth; j++) {
                    final var becomes = grid.becomes(i, j, 5, false);
                    if (grid.of(i, j) != becomes) {
                        changed = true;
                    }
                    states[i][j] = becomes;
                }
            }
            grid = new Grid(states, false);
        } while (changed);
        grid.forEach(state -> {
            if (state == State.OCCUPIED) {
                o.part2++;
            }
        });
        endOfWork(tikTok, day, false, o.part1, o.part2);
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
                        x += waypoint.x * instruction.qty;
                        y += waypoint.y * instruction.qty;
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
        final var day = Utils.getDay();
        String data = Utils.getData(day);
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
        endOfWork(tikTok, day, false, o.part1, o.part2);
    }

    public static void day13() {
        TikTok tikTok = new TikTok(true);
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var day = Utils.getDay();
        String data = Utils.getData(day);
//        String data = "939\n" +
//                "13,x,x,41,x,x,x,x,x,x,x,x,x,467,x,x,x,x,x,x,x,x,x,x,x,19,x,x,x,x,17,x,x,x,x,x,x,x,x,x,x,x,29,x,353,x,x,x,x,x,37,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,23";
//                "67,7,x,59,61";
//                "19,x,x,x,x,x,x,x,x,41,x,x,x,37,x,x,x,x,x,787,x,x,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,x,x,x,x,x,23,x,x,x,x,x,29,x,571,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,17";
//        String data = "F10\n" +
//                "N3\n" +
//                "F7\n" +
//                "R90\n" +
//                "F11";
        final var split = Utils.asLines(data);
        final var goal = Integer.parseInt(split[0]);
        final var busses = Arrays.stream(split[1].split(",")).mapToLong((s) -> {
            if (s.equals("x")) {
                return -1;
            } else {
                return Integer.parseInt(s);
            }
        }).toArray();
        int minVal = 999999999;
        int indexmin = -1;
        for (int i = 0; i < busses.length; i++) {
            final var buss = busses[i];
            if (buss == -1) {
                continue;
            }
            final var l1 = buss * (1 + i / buss);
            final var i1 = l1 - (goal % buss);
            if (minVal > i1) {
                minVal = (int) i1;
                indexmin = i;
            }
        }
        o.part1 = minVal * busses[indexmin];
        o.part2 = 0;
        long root = busses[0];
        for (int i = 1; i < busses.length; i++) {
            final var buss = busses[i];
            if (buss == -1) {
                continue;
            }
            final var l1 = buss * (1 + i / buss);
            while (true) {
                final var l = l1 - (o.part2 % buss);
                if (l == i) {
                    break;
                }
                o.part2 += root;
            }
            root *= buss;
        }

//        final var ints = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
//        o.part1;
//        o.part2;
        endOfWork(tikTok, day, false, o.part1, o.part2);
    }

    public static void day14() {
        TikTok tikTok = new TikTok(true);

        final var day = Utils.getDay();
        String data = Utils.getData(day);

//        boolean testMode = true;
        boolean testMode = false;
        if (testMode) {
            data = "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
                    "mem[8] = 11\n" +
                    "mem[7] = 101\n" +
                    "mem[8] = 0";
            data = "mask = 000000000000000000000000000000X1001X\n" +
                    "mem[42] = 100\n" +
                    "mask = 00000000000000000000000000000000X0XX\n" +
                    "mem[26] = 1";
        } else {
            data = getData(day);
        }

        record MemoryAddress(long index) {

        }
        record MemoryValue(long value) {

        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
            long andMask = 0;
            long orMask = 0;
            long[] andMasks = {0};
            long[] orMasks = {0};
        };
        HashMap<MemoryAddress, MemoryValue> addresses = new HashMap<>();
        final var split = data.split("\n");

        final var memExtractor = Pattern.compile("mem\\[([0-9]*)] = ([0-9]*)");
        Arrays.stream(split).forEach(s -> {
            if (s.startsWith("mask")) {
                final var mask = s.replace("mask = ", "");
                o.andMask = Long.parseLong(mask.replace("X", "1"), 2);
                o.orMask = Long.parseLong(mask.replace("X", "0"), 2);

            } else {
                final var matcher = memExtractor.matcher(s);
                final var matches = matcher.matches();
                final var address = Long.parseLong(matcher.group(1));
                final var value = Long.parseLong(matcher.group(2));
                final var newValue = (value | o.orMask) & o.andMask;
                addresses.put(new MemoryAddress(address), new MemoryValue(newValue));
                if (testMode) {
                    System.out.println(newValue);
                }
            }
        });
        o.part1 = addresses.values().stream().mapToLong(MemoryValue::value).sum();
        addresses.clear();
        Arrays.stream(split).forEach(s -> {
            if (s.startsWith("mask")) {
                final var mask = s.replace("mask = ", "");
                final var x = mask.length() - mask.replace("X", "").length();
                long max = (long) Math.pow(2, x);
                o.andMasks = new long[(int) max];
                o.orMasks = new long[(int) max];
                for (int i = 0; i < max; i++) {
                    var ref = new Object() {
                        int j = 0;
                        int xss = 0;
                    };
                    int finalI = i;
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < mask.length(); j++) {
                        char c = mask.charAt(j);
                        if (c == 'X') {
                            final var offset = 1 << ref.xss;
                            if ((offset & finalI) == 0) {
                                c = '0';
                            } else {
                                c = '1';
                            }
                            ref.xss++;
                        } else if (c == '0') {
                            c = 'X';
                        }
                        stringBuilder.append(c);
                    }
                    String newMask = stringBuilder.toString();
                    if (testMode) {
                        System.out.println(newMask);
                    }
                    o.andMasks[i] = Long.parseLong(newMask.replace("X", "1"), 2);
                    o.orMasks[i] = Long.parseLong(newMask.replace("X", "0"), 2);
                }
            } else {
                final var matcher = memExtractor.matcher(s);
                final var matches = matcher.matches();
                final var address = Long.parseLong(matcher.group(1));
                final var value = Long.parseLong(matcher.group(2));
                for (int i = 0; i < o.andMasks.length; i++) {
                    final var newAddress = (address | o.orMasks[i]) & o.andMasks[i];
                    addresses.put(new MemoryAddress(newAddress), new MemoryValue(value));
                    if (testMode) {
                        System.out.println(newAddress);
                    }
                }
            }
        });
        o.part2 = addresses.values().stream().mapToLong(MemoryValue::value).sum();

        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day15() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "1,3,2";
        if (!testMode) {
            data = "8,11,0,19,1,2";
        }
        record Number(int i) {

        }
        record Numbers(int i, int j) {

        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };

        HashMap<Number, Numbers> map = new HashMap<>();

        final var ints = Arrays.stream(data.split(",")).mapToInt(Integer::parseInt).toArray();
        int lastNumber = 0;
        for (int i = 0; i < ints.length; i++) {
            map.put(new Number(ints[i]), new Numbers(-1, i));
            lastNumber = ints[i];
        }
        for (int i = ints.length; i < 2020; i++) {
            final var numbers = map.get(new Number(lastNumber));
            final int nextNumber;
            if (numbers.i == -1) {
                nextNumber = 0;
            } else {
                nextNumber = numbers.j - numbers.i;
            }
            Numbers numbs;
            if ((numbs = map.get(new Number(nextNumber))) == null) {
                numbs = new Numbers(-1, i);
            } else {
                numbs = new Numbers(numbs.j, i);
            }
            map.put(new Number(nextNumber), numbs);
            if (testMode) {
                System.out.println((i + 1) + ": " + nextNumber);
            }
            lastNumber = nextNumber;
        }
        o.part1 = lastNumber;
        for (int i = 2020; i < 30000000; i++) {
            final var numbers = map.get(new Number(lastNumber));
            final int nextNumber;
            if (numbers.i == -1) {
                nextNumber = 0;
            } else {
                nextNumber = numbers.j - numbers.i;
            }
            Numbers numbs;
            if ((numbs = map.get(new Number(nextNumber))) == null) {
                numbs = new Numbers(-1, i);
            } else {
                numbs = new Numbers(numbs.j, i);
            }
            map.put(new Number(nextNumber), numbs);
            if (testMode) {
                System.out.println((i + 1) + ": " + nextNumber);
            }
            lastNumber = nextNumber;
        }
        o.part2 = lastNumber;
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
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
                System.out.println(namedRanges[i].s + "=" + myTicket.numbers[trueIndexes[i]]);
                o.part2 *= myTicket.numbers[trueIndexes[i]];
            }
            if (testMode) {
                if (namedRanges[i].s.startsWith("class")) {
                    System.out.println("class=" + myTicket.numbers[trueIndexes[i]]);
                }
                if (namedRanges[i].s.startsWith("row")) {
                    System.out.println("row=" + myTicket.numbers[trueIndexes[i]]);
                }
                if (namedRanges[i].s.startsWith("seat")) {
                    System.out.println("seat=" + myTicket.numbers[trueIndexes[i]]);
                }
            }
        }


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day17() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = ".#.\n" +
                "..#\n" +
                "###";
        if (!testMode) {
            data = getData(day);
        }
        record Coordinate(int... ints) {
            @Override
            public int hashCode() {
                return Arrays.hashCode(ints);
            }

            @Override
            public String toString() {
                return Arrays.toString(ints);
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Coordinate that = (Coordinate) o;
                return Arrays.equals(ints, that.ints);
            }
        }
        class Grid {
            HashSet<Coordinate> states = new HashSet<>();

            public Grid() {

            }

            boolean exist(int... ints) {
                return states.contains(new Coordinate(ints));
            }

            private int countAdjacent(int index, Coordinate test, Coordinate ref, HashSet<Coordinate> coords) {
                if (index == 0) {

                    if (test.equals(ref)) {
                        return 0;
                    }
//                    if (exist(test.ints)) {
//                        System.out.println(test);
//                    }
                    coords.add(test);
                    return exist(test.ints) ? 1 : 0;
                } else {
                    int sum = 0;
                    for (int i = -1; i <= 1; i++) {
                        final var ints = Arrays.copyOf(test.ints, test.ints.length);
                        ints[index - 1] = ref.ints[index - 1] + i;
                        Coordinate newCoord = new Coordinate(ints);
                        sum += countAdjacent(index - 1, newCoord, ref, coords);
                    }
                    return sum;
                }
            }

            public Grid evolve() {
                HashSet<Coordinate> coords = new HashSet<>();
                Grid grid = new Grid();
                for (Coordinate state : states) {
//                    System.out.println("Next to " + state);
                    final var actives = countAdjacent(state.ints.length, state, state, coords);
//                    System.out.println("Sum: " + actives);
                    if (actives == 3 || actives == 2) {
                        grid.states.add(state);
                    }
                }
//                for (Coordinate state : states) {
//                    coords.remove(state);
//                }
                for (Coordinate state : coords) {
                    if (states.contains(state)) {
                        continue;
                    }
                    final var actives = countAdjacent(state.ints.length, state, state, new HashSet<>());
                    if (actives == 3) {
                        grid.states.add(state);
                    }
                }
                return grid;
            }
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var strings = asLines(data);
//        final var longs = asLongs(data);

        var grid = new Grid();
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length(); j++) {
                if (strings[i].charAt(j) == '#') {
                    grid.states.add(new Coordinate(j, i, 0));
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            grid = grid.evolve();
        }
        o.part1 = grid.states.size();
        grid = new Grid();
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length(); j++) {
                if (strings[i].charAt(j) == '#') {
                    grid.states.add(new Coordinate(j, i, 0, 0));
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            grid = grid.evolve();
        }
        o.part2 = grid.states.size();
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    public static void day18() {
        String tab = "\t";
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        boolean testMode = true;
//        boolean testMode = false;
        String data = """
                1 + (2 * 3) + (4 * (5 + 6))
                2 * 3 + (4 * 5)
                5 + (8 * 3 + 9 + 3 * 4 * 3)
                5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))
                ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
                """;
//        String data = "(9 + (8 * 9 * 4 * 5 + 4) * 9 * 4 * 9 * 4) * ((6 * 9) + 3) * (6 * (3 * 5 + 6 + 9 + 2) * 6 + (4 + 5 + 3 + 2 + 2 * 4)) + 8 * 4 + 9";
        if (!testMode) {
            data = getData(day);
        }


        var o = new Object() {
            long part1 = 0;
            long part2 = 0;

            long crunch(String s) {
                s = s + "  ";
                long currentNumber = 0;
                long nextNumber = 0;
                BiFunction<Long, Long, Long> add = (integer, integer2) -> integer + integer2;
                BiFunction<Long, Long, Long> multi = (integer, integer2) -> integer * integer2;
                BiFunction<Long, Long, Long> none = (integer, integer2) -> integer2;
                BiFunction<Long, Long, Long> current = none;
                HardFabricator hardFabricator = new HardFabricator(s);
                while (true) {
                    final var c = hardFabricator.peek();
                    if (c == 0) {
                        break;
                    }
                    switch (c) {
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case '0':
                            nextNumber = Long.parseLong(hardFabricator.readTextUntil(' '));
                            break;
                        case '(':
                            hardFabricator.traverseUpToChar('(');
                            nextNumber = crunch(hardFabricator.readUntilClosed());
                            break;
                        case '+':
                            current = add;
                            hardFabricator.traverseUpToChar(' ');
                            continue;
                        case '*':
                            current = multi;
                            hardFabricator.traverseUpToChar(' ');
                            continue;
                        case ')':
                            System.out.println("help");
                            break;
                        case ' ':
                            hardFabricator.skip(1);
                            continue;
                    }
                    currentNumber = current.apply(currentNumber, nextNumber);
                }
                return currentNumber;
            }

            long crunchV2(String s, int indent) {

                System.out.println(tab.repeat(indent) + s);
                while (s.contains("(")) {

                    HardFabricator hardFabricator = new HardFabricator(s);
                    hardFabricator.traverseUpToChar('(');
                    final var s1 = hardFabricator.readUntilClosed();
//            System.out.println("break (" + s1 + ")");
                    long res = crunchV2(s1, indent + 1);
                    s = s.replace("(" + s1 + ")", res + "");
                    System.out.println(tab.repeat(indent) + s);
                }
                while (s.contains("+")) {
                    final var i = s.indexOf("+");
                    int kR = 2;
                    long rightVal = 0;
                    while (true) {
                        final var index = i + kR++;
                        if (index == s.length()) {
                            break;
                        }
                        final var c = s.charAt(index);
                        if (c >= '0' && c <= '9') {
                            rightVal *= 10;
                            rightVal += c - '0';
                        } else {
                            break;
                        }
                    }
                    long leftVal = 0;
                    int kL = -2;
                    while (true) {
                        final var index = i + kL--;
                        if (index == -1) {
                            break;
                        }
                        final var c = s.charAt(index);
                        if (c >= '0' && c <= '9') {
                            leftVal += (c - '0') * Math.pow(10, -3 - kL);
                        } else {
                            break;
                        }
                    }
//            System.out.println("adding "+ leftVal+ " + "+ rightVal);
                    s = s.replaceFirst(s.substring(i + kL + 2, i + kR - 1).replace("+", "\\+"), "" + (Math.addExact(leftVal, rightVal)));
                    System.out.println(tab.repeat(indent) + s);
                }
                while (s.contains("*")) {
                    final var i = s.indexOf("*");
                    int kR = 2;
                    long rightVal = 0;
                    while (true) {
                        final var index = i + kR++;
                        if (index == s.length()) {
                            break;
                        }
                        final var c = s.charAt(index);
                        if (c >= '0' && c <= '9') {
                            rightVal *= 10;
                            rightVal += c - '0';
                        } else {
                            break;
                        }
                    }
                    long leftVal = 0;
                    int kL = -2;
                    while (true) {
                        final var index = i + kL--;
                        if (index == -1) {
                            break;
                        }
                        final var c = s.charAt(index);
                        if (c >= '0' && c <= '9') {
                            leftVal += (c - '0') * Math.pow(10, -3 - kL);
                        } else {
                            break;
                        }
                    }
//            System.out.println("multiplying "+ leftVal+ " * "+ rightVal);
                    s = s.replaceFirst(s.substring(i + kL + 2, i + kR - 1).replace("*", "\\*"), "" + (Math.multiplyExact(leftVal, rightVal)));
                    System.out.println(tab.repeat(indent) + s);
                }
                return Long.parseLong(s);
            }

        };
        final var strings = asLines(data);

        for (int i = 0; i < strings.length; i++) {
            o.part1 += o.crunch(strings[i]);
        }
        for (int i = 0; i < strings.length; i++) {

            final var part2 = o.crunchV2(strings[i], 0);

            if (testMode) {
                System.out.println(part2);
            }
            o.part2 = Math.addExact(o.part2, part2);
        }


        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    private static int indexOfTruth(boolean[] booleans1) {
        int answer = -1;
        for (int k = 0; k < booleans1.length; k++) {
            if (booleans1[k]) {
                if (answer != -1) {
                    throw new RuntimeException("not only one boolean");
                }
                answer = k;
            }
        }
        return answer;
    }

    public static String readFile(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        int i = 1;
        LocalDateTime time = LocalDateTime.now(ZoneId.of("America/New_York"));
        int dayOfMonth = Math.min(25, time.getDayOfMonth());
        Method method;
        ArrayList<Method> objects = new ArrayList<>();
        while (i <= dayOfMonth) {
            method = AocPostCompetition.class.getMethod("day" + i++);
            objects.add(method);
        }
        objects.forEach((day) -> {
            try {
                day.invoke(null);
            } catch (Exception ignored) {
            }
        });
    }

    public static void day5competition() {
        TikTok tikTok = new TikTok(true);
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;
            int lastI = -1;
            int lastII = -1;
        };
        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\input.txt");
        final var split = data.split("\n");
//        final var split = new String[]{"BBFFBBFRLL"};
        final var pattern = Pattern.compile("pattern");
        o.part1 = Arrays.stream(split).mapToInt(s -> {
            float i = 32;
            float row = 127 / 2f;
            for (int b = 0; b < 7; b++) {
                if (s.charAt(b) == 'F') {
                    row -= i;
                } else {
                    row += i;
                }
                i /= 2;
            }
            float seat = 3.5f;
            i = 2;
            for (int b = 7; b < 10; b++) {
                if (s.charAt(b) == 'R') {
                    seat += i;
                } else {
                    seat -= i;
                }
                i /= 2;
            }
            return (int) (row * 8 + seat);

        }).max().getAsInt();

        Arrays.stream(split).mapToInt(s -> {
            float i = 32;
            float row = 127 / 2f;
            for (int b = 0; b < 7; b++) {
                if (s.charAt(b) == 'F') {
                    row -= i;
                } else {
                    row += i;
                }
                i /= 2;
            }
            float seat = 3.5f;
            i = 2;
            for (int b = 7; b < 10; b++) {
                if (s.charAt(b) == 'R') {
                    seat += i;
                } else {
                    seat -= i;
                }
                i /= 2;
            }
            if (row == 127f || row == 0) {
                return -1;
            }
            return (int) (row * 8 + seat);

        }).filter(ii -> ii != -1).sorted().forEach(iii -> {
            if (o.lastII == -1) {
                o.lastII = iii;
            } else if (o.lastI == -1) {
                o.lastI = iii;
            } else {
                if (o.lastII + 1 != o.lastI) {
                    System.out.println(o.lastI);//debug here

//                    if (o.lastI+1==iii){
//                        System.out.println(o.lastI);
//                    }
                }
                o.lastII = o.lastI;
                o.lastI = iii;
            }

        });
        //insert magic right here
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }
}

