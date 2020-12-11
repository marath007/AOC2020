package com.numberengineer.aoc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;


public class AocPostCompetition {
    public static void day1() {
        TikTok tikTok = new TikTok(true);
        int[] ints = new int[]{267, 307, 321, 523, 551, 789, 917, 946, 1154, 1324, 1374, 1383, 1388, 1447, 1461, 1466, 1530, 1534, 1539, 1544, 1548, 1553, 1558, 1567, 1602, 1617, 1639, 1644, 1645, 1659, 1662, 1667, 1672, 1677, 1681, 1691, 1694, 1695, 1700, 1705, 1706, 1709, 1710, 1715, 1716, 1717, 1719, 1720, 1721, 1724, 1725, 1726, 1728, 1732, 1733, 1735, 1736, 1737, 1739, 1741, 1742, 1743, 1744, 1749, 1750, 1751, 1753, 1754, 1757, 1758, 1761, 1762, 1763, 1765, 1766, 1767, 1769, 1770, 1772, 1773, 1775, 1777, 1782, 1785, 1786, 1787, 1789, 1794, 1797, 1798, 1799, 1800, 1801, 1803, 1804, 1806, 1807, 1812, 1813, 1815, 1816, 1819, 1821, 1824, 1827, 1828, 1829, 1830, 1831, 1832, 1833, 1837, 1838, 1839, 1843, 1845, 1847, 1848, 1849, 1850, 1851, 1852, 1853, 1854, 1858, 1859, 1861, 1864, 1866, 1870, 1871, 1875, 1877, 1878, 1879, 1880, 1881, 1883, 1887, 1891, 1894, 1896, 1899, 1900, 1902, 1903, 1906, 1907, 1909, 1911, 1912, 1916, 1919, 1920, 1921, 1922, 1923, 1924, 1926, 1927, 1929, 1930, 1934, 1935, 1936, 1942, 1944, 1945, 1946, 1948, 1950, 1951, 1952, 1957, 1959, 1964, 1965, 1966, 1968, 1972, 1975, 1976, 1978, 1979, 1980, 1981, 1982, 1985, 1986, 1989, 1991, 1993, 1997, 2000, 2003, 2004, 2005, 2006, 2008, 2010};
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
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }
    public static void day9() {
        TikTok tikTok = new TikTok(true);
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\day9.txt");
        final var split = data.split("\n");
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
        int from=0;
        int to=0;
        while (sum!=o.part1){
            while (sum>o.part1){
                sum-=numb[from++];
            }
            while (sum<o.part1){
                sum+=numb[to++];
            }
        }
        final var arr = Arrays.copyOfRange(numb, from, to + 1);
        final var longSummaryStatistics = Arrays.stream(arr).summaryStatistics();
        o.part2 = longSummaryStatistics.getMax() + longSummaryStatistics.getMin();
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }
    public static void day2() {
        TikTok tikTok = new TikTok(true);
        String[] s = new String[]{"3-4 l: vdcv", "6-9 d: dddddkzdl", "6-13 f: mfswqfrqffrvfvf", "10-12 l: sfzlnwcptlnlflq", "2-4 m: qbwcmt", "15-16 v: vvvvvvvvnvvvvcvvvvgv", "1-4 n: wnnzfln", "1-3 x: xxgx", "7-8 j: jjjjjfvh", "6-8 x: xxzxxnnwx", "3-8 t: djtdznbtgwtrhxf", "7-9 w: glwwwwxtxwlwwwcp", "4-5 g: jggjtgggg", "9-10 c: trgjrcbfcwf", "9-10 v: vvvbvmvcvvvv", "3-14 p: pptzpppppppppsfrsppc", "3-5 n: nnznmkn", "1-13 t: fxtrpgtzztsfnmzmmtg", "13-15 d: dcztmqjdmclgdhdcd", "3-4 j: mjjjjc", "13-18 x: fxxgxxxxxxxxxxxxxcx", "2-4 f: fxqs", "3-4 w: ffmbwwnn", "12-13 k: dlpdzkhbkkkftkb", "5-11 r: vrdxrrrrrtrqz", "7-8 b: bbbbbbrcb", "13-16 c: wzccvzccclccczcc", "10-11 m: lmmmmmmmmwqmm", "6-10 f: fffqjfznvfhz", "8-9 h: hvfhhvgklhhpfmh", "4-17 p: hpprnpsppcppppppfp", "9-13 z: zzzzrgtzzzzzzzzzzl", "6-10 l: lllllqlllplll", "3-10 j: vjjjwjjjjjjjj", "4-8 j: jjjjjjjjjjc", "2-5 s: jsgtsfsw", "1-2 b: brwpbbbbb", "2-5 m: tbkpfbvnqmpjbbx", "7-8 m: xxbgmbmnmmwmdhmm", "9-12 v: phsslrznvrsvtxgv", "10-14 v: rnnvvrvvvzbhmcx", "13-15 b: bbbbbbbbbbgbwbbb", "2-5 w: bwldjwwz", "5-7 d: dddjthk", "10-12 c: pckcccccccccl", "2-11 b: rdjqkdbzxzqfgn", "3-8 s: dsssmshs", "8-10 d: dddfdddsdj", "2-4 l: slgltjzwzrnlllrh", "6-10 z: vrcvgrgzphxd", "10-13 b: mnblbbbbbbbfvbbbbx", "8-16 n: wznnnbfhndnnnnngnn", "6-8 v: fvvvzqks", "3-6 n: znqnbnzwnn", "15-16 g: wggzxsgcgjwgxhgggg", "10-13 x: xlvxxxxxkxxxfxx", "2-6 z: jpznbgszmj", "13-19 j: jjzjjjdjwxlnjhwsrtj", "5-6 p: bvppqqppcp", "19-20 r: rrrrrrrrbrrrrrrrrrvd", "4-7 j: jjjjjjljj", "5-6 k: kkkkkck", "1-2 k: zbkk", "11-13 r: rrsrrrwrrrrrr", "6-11 z: zzgzznzzzzwzzrzpzgc", "7-11 l: llvlllllllllllll", "7-8 f: fffffjffnp", "3-4 j: pxcw", "9-11 k: zkkkkkkkdkzx", "12-13 g: fgglgvggxhgks", "6-7 d: kbdddftdddl", "14-15 m: dmmjwnxxfdfhwvcdd", "3-4 b: bbbsbt", "2-9 d: tgsvznmnnrgvtldjbj", "4-5 l: ljxtc", "5-8 j: jnvsjlfw", "3-4 v: zrklbf", "1-15 v: vvvvvvvvvvvvvvtv", "5-6 n: ssnnnngnsxkphtnwrk", "6-7 k: kkkkkkdk", "2-4 d: xchmdk", "12-13 v: vfvvmvvvpvvwvpvvvdm", "3-14 d: dzcddwdddddfdfdwd", "1-6 b: dbbbbsb", "12-14 b: bbbxbbbbbbbjbb", "1-8 s: sssssssgn", "5-10 d: ddddwddddvdd", "10-12 s: sssmssssskswssss", "3-13 q: cdqfcqvszbzqnhvxlw", "8-10 z: tmgzkvxzzvz", "8-10 n: nnnnntnqnhrsnn", "2-7 r: vjrctrhprhc", "6-8 w: bzfxrwwfmx", "3-5 x: xxxwxb", "5-9 z: vbspkzfsrrpdcfl", "8-9 z: jgqzzzzzwq", "5-12 x: dvqvjsdfjxgtxkd", "5-6 r: rrrrrr", "1-4 f: xdzngd", "15-18 x: xxxxxxxxxxxxxxvvxx", "1-4 g: qcwjpxgtvgkf", "13-15 m: mhmmmmmmmhmmqmmmmmmv", "5-11 n: nnnndnnnnnnnnnnnn", "4-9 n: tjgnpqsnpxpzfw", "10-13 q: qqlqdqqqqqqqnqq", "8-14 g: gdcggnknjsgdskq", "3-5 l: vlllll", "16-18 t: ttttttttttttttttmt", "9-14 b: kbhbcbbbbbqbsbr", "3-9 f: jzrnxffljfmhffftdng", "1-4 z: pbzs", "10-11 h: lrrgpmhshtfg", "1-5 n: mnqjwjpfnnzn", "1-8 r: zrrrprrkr", "8-9 g: gkbtggcpf", "3-8 q: qqsqqgqqqqm", "17-18 j: jjjjjjjjjjjjjjjjgkj", "13-14 t: tttttttttmtttfttt", "2-6 c: cvcccqc", "4-17 c: sfnckrcmlcpmcpcgbkct", "10-12 d: dddddddqvddd", "3-5 d: ddddtd", "10-11 h: hchhhhhhhvg", "3-11 j: mmlcjsjhjbpjjjxsk", "11-12 w: wwwwwwwwwwwww", "9-10 c: ccccccccccc", "5-7 x: ckhdnfgjrdkxqphvjtvs", "10-12 s: ssssjsssshstd", "7-8 m: mmmmmmmg", "3-4 v: vjvvt", "2-3 f: hmknf", "3-5 g: bgsglggg", "12-13 x: vxxxdcgxxxfxx", "3-20 h: hhhtkvrmfswvmkzmbvmh", "2-3 v: vvvv", "3-14 g: zgpggzgqxjzggg", "15-16 c: ggcjccnccccccccc", "11-12 d: wrdrdxsfdzddkl", "6-7 q: vqqknqq", "2-6 w: wwwwwwwkck", "4-6 l: dplldljlvxrrm", "2-18 m: pmwfkqlknsxzjmdzkmxd", "5-11 q: rqkzqxqmqtqnzqqqxk", "6-7 s: sssssskss", "9-10 w: wwwwwwwwsw", "5-7 x: jxxxhxwxxxxxxxx", "4-5 t: ttbttfm", "2-4 h: mlhhhkhc", "13-14 l: llllllllllllfjl", "8-10 k: kllkkkjkkkk", "1-3 s: sssbdss", "2-3 z: zlbzzzzzz", "9-10 r: rrvrrrfrdfrr", "4-12 k: vkkkbkhkzkkk", "11-12 l: llllllllllcgc", "5-6 k: hkzkkkkgdsghdk", "3-5 n: nzsxk", "12-14 r: rwrrrrrrrrrprxrrr", "4-7 k: kkxkkkkkkk", "12-16 b: bbbbbbbbbbbbbbbbb", "13-16 p: ppppppppppppzppfp", "15-19 j: tjjjjjdjjjjmjjccbjm", "1-3 l: llll", "3-4 x: hxkl", "9-15 n: ktnpmnnnlnxnnft", "6-7 q: qqdtcqs", "13-14 g: lgghndddgllnfg", "7-14 k: cdzkjkkksksbwkjb", "7-11 w: cwcsvqwfwhw", "14-18 m: mmmmmmmmmmmmmhmmmjmm", "4-5 c: cvfctcsncscbmj", "4-5 r: zrnrrnxct", "2-3 b: ggdlb", "8-9 x: cpxskxxxxbxn", "12-14 v: vvvvvwvvvvvbvw", "13-16 n: pmhnhpnntncxqrmd", "2-4 m: mmxm", "7-10 m: mmmrqwsmnkgbdbrmmjjm", "7-8 z: zzzzzzzzzz", "2-3 p: pppnfltzpd", "1-10 h: hhslhfsgtxphhncg", "5-6 m: txmhxhtmgm", "4-5 v: wsnvrsvx", "8-12 p: pfbdlnrphdfplvpqx", "10-13 b: nbpjvgghsbplb", "9-15 h: ghcxbptxsgvvvptqx", "4-5 t: tttgf", "2-3 l: lsnl", "1-11 v: vvvvvvvvvvvvvvvv", "6-7 r: brnrrrr", "3-4 k: bkkvkrtmzj", "17-18 p: pppppwphpkpppppppp", "8-11 f: fqfffdffffpfffffffff", "14-16 z: zznzzzbzzzzdzzzsz", "10-11 v: xtvfnxdrvqnxg", "8-13 c: tcwtbcpfvdccs", "5-6 b: btbnbdb", "2-4 d: ddxmcb", "6-9 p: qpppzppbpsplpppppxp", "7-12 z: zzzzzztzqzzqzz", "4-5 h: hhhmb", "7-10 f: frftffhfff", "4-5 c: xccmv", "8-11 x: rfglxxxhxwvxxfcxx", "7-10 k: kkqnjxzckmmbkkk", "6-7 h: hhxsdhhwh", "15-18 r: rmrrlrrrrrrrrtncrcr", "10-11 x: srnxxbbhxxx", "13-14 c: cccccccccccchx", "5-8 r: rrrrfrrhrrr", "11-15 v: vvvvvvvvvvvvwvvvsvv", "6-8 q: qqkqlnqzmqqx", "2-3 h: hhzq", "10-12 l: lllflllllpqt", "7-9 m: mlmmmmmmm", "4-12 b: pbbhbrrhzxvrgcsfmqq", "8-10 g: tggrgggbgdggmg", "9-10 s: sssssksspsss", "4-8 k: dtkkkkkkkkkkmk", "19-20 j: jjjjjjjjjjjjjjjjjjjj", "3-8 r: rrrrxrrrrrrr", "9-10 p: qmgzfbqpnp", "1-4 w: wwwsw", "1-15 f: fffffffftzftxhfwfbx", "1-5 t: rtttttlxtjtttsq", "3-4 f: fmffkt", "16-18 n: kfnnnlnnnsgscnzmrv", "3-4 w: wwmk", "7-9 l: lllkbllllllllljtjll", "7-13 l: pqzbtxmqslllqkx", "6-18 j: qlnjjvrtzqkfrnjxjk", "6-8 q: qtpqqpqg", "6-8 c: cccfccxd", "3-4 f: fhff", "4-5 x: xjxfhqkhx", "16-18 k: xvbktrmqkckwkrkkjkk", "9-13 v: vvvbvvvvvvvvnvvlmvv", "1-7 k: wkkkkkqkk", "2-4 z: jpzlxfjbh", "5-8 l: lllllpllfllllr", "7-8 r: rrrrrrwnr", "5-6 d: ddddftd", "4-5 q: vqlqn", "3-13 p: pppsppmpbppppppppn", "1-3 w: wwww", "4-5 d: tfrpfcdlft", "5-10 r: rrrrppxrrvrm", "9-10 t: dtttttttgvtt", "8-12 j: jjjhjjjljjjjjpjj", "6-7 b: lpfpzljhbl", "3-6 k: kzkqkrkckkv", "3-7 q: rvqdtsqqjfftzllflnx", "2-3 l: glsjcsrlmn", "5-8 g: gggxhggpgg", "12-14 q: zqqsqqqqqqqqxqqq", "9-11 q: qqqqsqjtqqq", "9-10 g: gggggcggggg", "12-14 z: zzzzzzzzzzzlzzzzzz", "1-6 s: gvqttcgrqqsbndv", "8-19 z: mzzzpkzdzzzskfhzzlj", "4-6 h: kmlbhwthhp", "8-9 x: xxxxxxxvk", "1-2 k: drjkkkk", "10-12 z: zdzsgzzzwvzrzszzzzzz", "13-14 f: lffdffjfbfdxjd", "11-12 b: rppkbnjgcqrr", "1-2 l: lllq", "5-6 m: mmmmzr", "6-14 l: lllbldllpllllblllcl", "4-9 d: rdtdcwnbqjbthddr", "13-15 v: vvcwvvqvzvvvfvg", "9-10 g: gpxpwxhmgg", "13-14 n: njpndvsvfgrrhnb", "3-5 r: rrrrr", "7-13 h: hhdcgscdxmhwhg", "3-4 l: tnhf", "10-13 c: mfcvbcmcccqhc", "6-7 x: cdwfdxxxxxx", "5-7 b: wbbzwbcx", "4-6 h: vhhzpqjhk", "5-8 r: hffprngr", "14-15 p: pppppppppppspjm", "8-12 z: pzqdhzzkzzzzxzzc", "1-8 b: kbbfdvvcsdjlbw", "16-18 k: rqwtgtwzgsnvlkdkkk", "8-10 w: xllfslwwsf", "2-5 m: qmbdm", "10-12 b: bhbbbbbwtbbbbbhbb", "8-14 c: cscdccccccccxcp", "3-5 c: rcdpz", "16-18 q: qqqqqqqqqqnqqqqbql", "3-4 x: xjxx", "6-7 n: gnnnnxwqn", "16-17 f: tffrffffffffffffff", "5-6 d: ddddgm", "2-7 r: prrrssrrkrjrrr", "12-16 g: ggxbxggtgbggccwgggtv", "10-14 n: nnkntpnvncnnnznnr", "3-5 c: qccwcx", "7-17 v: jvvvvhvvvpvvqvgvv", "3-6 c: cclccs", "8-10 m: nwtmfcnmmmmm", "1-2 p: hrpp", "2-3 j: hdnjgt", "1-3 h: jhgvhjhh", "9-10 j: jjjjjjjjvl", "12-15 h: hhshhhhthhhhhnhn", "5-8 k: qkvmkwckvlkl", "12-15 j: jjjjjjjkjjjwjjlj", "11-16 v: tvmrmvvvwwqrxwvg", "5-7 b: dbhtpjvxqzbhbz", "3-4 b: mqbg", "4-7 t: tttbtvt", "8-13 h: hhhhchhhhqfhhhhhthr", "1-6 w: lhwqgrbbwbv", "1-11 t: ttttpttttttt", "2-5 l: rlwllvfhw", "7-8 m: jmmrmmmmlmmm", "3-5 s: smsss", "15-19 p: mfpzcghppcgpgqpfvxfw", "6-7 n: jnldsnb", "7-9 x: xwbwdxjhcxxvxx", "1-2 c: cbqprgfjfpc", "3-4 g: gngf", "7-10 w: twvvwwtwwtwwsgwws", "2-9 g: ggngggglgrzk", "7-9 p: xpphpkppppp", "11-12 p: pppppxpppppppppp", "3-4 g: fzqrl", "6-7 x: xxxxxmw", "3-4 r: rrrn", "8-11 b: bbhbbbbrbbwbkb", "3-11 d: xxvfmvrdmzhfmvmhhp", "1-12 z: mzzzkzwzzzxfzzzzzzzz", "5-11 g: gzbjgxghddggkgq", "5-8 g: grpggrgg", "1-6 f: lfffffsfffff", "8-10 x: slxxxgxxqxxx", "4-13 l: lllllllllllllvll", "8-9 z: zzzzdhzzz", "13-15 l: rglrmcqvgjzvlklx", "2-4 q: nsxs", "2-3 v: vvqcv", "13-14 b: bbbbbbbbbbbbbb", "13-14 x: rxxdxnxxtxxxqq", "1-4 s: jmsssssgssvppss", "6-7 j: pgvjkxcvjslmj", "2-5 w: jwwswgl", "5-7 m: mmnmmxp", "2-6 k: tkkkxwtkrk", "4-15 n: nnnnngnnnnnnnnnnnt", "7-11 x: dxxtxxxvwtnx", "11-14 s: ssrvphskdsstzsszrnsz", "11-12 l: rclllgbllblb", "6-7 x: fxhbmnj", "15-17 q: kqvhwsqhqqqhmqqtqqlq", "12-14 k: kksktkkkkkkkkfk", "3-4 h: hshk", "14-17 v: vvvqvvkvvvvvvvjkvxv", "2-6 f: vfvfrp", "4-12 z: bzzqszhwffrhr", "2-9 b: sbdjfbndbhcclrmblccn", "11-13 j: jjjjjjjjjjgjkjjjjj", "15-17 f: fpffffsfffffffdfjfff", "6-7 v: tlvvvvv", "11-19 l: llllllllllllllllllll", "1-6 w: wwbwwwwww", "3-7 s: cpsvmss", "2-11 r: rrpnkchfzljwxmrrvvw", "7-12 n: msgpplgpfnkmxlm", "4-13 f: sjpfqpnzqzznfftfhgfs", "13-14 q: qqqqqqqqqqqqqqq", "13-14 j: jjjmjjjjjjjjgq", "2-4 q: qdrds", "5-10 s: ssssqssssbs", "6-8 c: ccdcchchcc", "18-20 z: xztgqzzckzgcpzzzjzdz", "5-8 z: ctpdzzzzzqzmvzxn", "6-8 b: bbmkgbfkb", "2-10 x: xxxxxxxxxxx", "5-6 z: gzzzqk", "4-7 c: cccvccj", "7-8 d: dddddddd", "9-11 c: ccdccfccxcccc", "1-4 z: zjzzzz", "6-8 z: zlmjbzvz", "8-10 v: gvtjvgvzvjphscn", "8-10 d: dcdddddddddddddd", "7-8 p: mjpwppppp", "1-11 w: wwwwwnwwwwwww", "7-9 j: jjjjjjjjj", "6-8 q: dmqdqqzt", "3-10 p: tsqcpgmzgh", "11-15 s: ssslsxrlbfqsssssss", "5-10 m: dpmtmhbdmk", "3-5 p: wpjqv", "10-13 p: lhvkzxprppmtprj", "4-6 w: nlfjwz", "19-20 c: kzckcfsqpdvssgcgktcm", "1-2 p: ppgltbv", "3-4 w: ngwwfttwqg", "10-11 p: ppppppnpllgp", "2-4 t: tkttptt", "3-6 x: xwrjpcf", "14-17 q: qcqbqfqqkqdqrqqqq", "4-8 n: qsncdnbknpn", "8-10 s: wzscpmssrshh", "3-8 p: pfppppgpp", "11-15 b: tqdbgfbbbgjbbbg", "12-15 h: lwhhjzhpmhzhhnhmhh", "4-5 w: nwccwlpwdvtlwwr", "11-16 w: wwwwwwwwwwswwwwxw", "6-9 q: qqqqqqqqqqqqqqqq", "8-12 t: tqttjtttpgbtlt", "14-15 v: vvvvkvgvvvvvvvg", "2-12 f: fpfflxzxffwxfvfff", "7-12 m: hmmmmwmmbmlm", "2-3 z: mmlzzz", "12-16 h: hhbhhhkhhfhpbghm", "15-18 s: ssssxsssssmsssqssx", "8-9 s: sqsqdtmssqscxxs", "1-10 n: nnbnnngnnn", "10-14 c: ccbcvscncrccng", "7-8 k: kkhkxrkkctkpjkkck", "10-11 g: bgpgggkcwzs", "9-17 l: llllllllckwllllllwl", "7-12 q: gqqqjbxtqvqxzjhvjrjj", "1-3 s: ssnsc", "3-10 n: nnnnnnnnnnnnnnnn", "9-12 l: blfnqllsllll", "5-8 s: sgsgstssl", "1-4 c: cccwcc", "7-9 h: hhhhhhhhhhh", "2-4 s: sssss", "8-10 f: vznpfffrsffscgfzft", "4-5 r: hnrns", "4-5 z: zzzlgz", "10-18 w: wwwwwwwwwwwwwwwwwwww", "3-12 b: bfmbbbbfbbltbg", "14-15 z: zzjxrhrjsbrrzgd", "1-2 z: zzzzz", "4-11 x: pxgxwphxmgxpt", "1-16 c: cbgnxcccxsrcbmcvqc", "3-6 n: cnppnnjnxnnjss", "8-9 r: zltfbjwrrlmtrzqrh", "1-3 z: zzqzzzzzzz", "4-7 n: vgndgdndbxcnznxc", "2-9 w: wwwwwcfnww", "1-4 b: tbbrbbbbbcbbbbbbbbdk", "7-8 h: hhhwhzhhhhtf", "12-13 j: jjjjjjjjjjjjj", "2-8 m: rthcgnxmm", "2-7 l: glzctbjh", "5-7 d: dddddddx", "12-13 x: xxxxxxxxxxxxs", "6-11 t: ltnfnnqttvtkjnlk", "6-7 x: qxxxxpfxxx", "1-3 d: kdndzd", "6-11 f: zfxfmffptsffmffvfz", "1-4 x: xxxxxxx", "4-5 l: lllllllll", "4-8 c: cjccccgckrdpvv", "13-14 z: zzzwzzzzzzzzkkzp", "1-3 r: lgtr", "3-17 g: ggtgjgmggggggggggggg", "4-5 q: qqqqqq", "2-6 v: vvvlvvv", "4-17 c: nwqcprgftnqgcrjpcjb", "7-10 x: xlxfrjxxxxgnxx", "2-14 q: qqqqqqqqqqqqqsqqq", "4-8 x: xxxxxxxxxxx", "1-16 z: bzzzzzzzzmzzzzldzzzz", "2-4 v: bffhzd", "3-4 r: rrrr", "5-8 x: xxxxxnsxd", "1-17 g: gbngjggxgggjgzvjk", "2-3 b: sgbb", "11-14 p: ppppppppppppppp", "3-9 q: vhsjtqppqgdqdqlf", "1-3 w: wcwzsfzwwcwdwncwp", "7-11 f: ffffffdfffff", "3-8 g: ksgdcqqgtkmgjbzsf", "5-8 s: tsssccswss", "1-7 r: brrrrrhrrrrr", "5-7 g: ggxmgggcwgz", "2-4 h: hhwh", "9-18 w: wwwwwwwwhwwwwwwwwbw", "1-7 m: ndmmbmmmmmmm", "5-7 j: jjvjvjt", "3-8 m: plnmcmkfmmcm", "4-5 r: rrrjzr", "4-8 k: kkkgkjknkjkk", "17-18 s: srngdqpwxcrbqrprss", "4-9 t: mbkjrtntztrttt", "1-10 w: hwwwwwwwwwwtx", "5-9 f: lfvhfxndfffmxhf", "7-8 n: pnnnnnbvnnnnnnnnnhnn", "1-10 b: jjtptpmmqv", "1-3 p: rqppqt", "1-11 t: pbtwdttkttmtxj", "5-6 g: jpkfggtcmj", "6-8 f: fffffpfvf", "9-13 t: ttttttttdtttg", "3-5 r: prrjr", "18-19 b: bbbbbbbbbbbbbbbbbbb", "5-7 v: vjvjxjhvrv", "8-10 z: zzzsznzxttgspzzzz", "5-13 m: mngmcbmqbxmzjfm", "1-3 b: xbhb", "12-14 m: mfmmbgmmmmmcmz", "3-6 n: jpnpnnn", "3-14 j: xmgjqkzfctctxwgjms", "7-14 k: lbkfcbctkgkjkqvjdwkc", "6-8 l: lhthfgllfvbpxchct", "5-6 h: hhhhhk", "6-9 c: qkxtbqltkzcrdcgckncc", "13-15 w: fhsvgcxwfcgxwvwwwkb", "3-4 c: cccmcj", "3-4 m: mhmcmm", "4-5 k: skkqg", "4-16 m: mgmcmmmmmmmmmmmtmmm", "3-5 g: gggbsg", "9-15 l: dnslgllwlflmlllllll", "3-4 h: mhhh", "3-5 z: vkjzlzzvrztwg", "11-12 q: qqqqqqqqqqqq", "5-10 p: xpxprppppgzppp", "1-4 z: kzzhzszzzzzzzz", "2-7 j: nsjfszt", "8-13 j: qtrjfmbjfswrj", "2-10 s: ssscwcsnsssrt", "1-12 w: zwwwwwwwwwwx", "2-4 p: ppgvf", "2-3 n: hnnxmgtchqwgnfx", "11-12 z: bfzlvgdzpfzzzgvhz", "1-6 d: czqqdjrxs", "6-7 r: rrrrrkz", "4-7 d: hxdzddvq", "2-4 g: slwgs", "13-14 f: mqfhrlftvvfkff", "12-14 d: ddddddddddhqdc", "1-3 f: ffzff", "4-5 r: mrrrkrrrrrrkr", "3-4 m: mmwl", "2-5 j: jljjsjdj", "4-5 f: fkfdf", "16-17 s: dzdsrsgmsjffsnssls", "10-17 f: fffffhffffhffffff", "8-10 v: fvvvvvvvvz", "4-9 n: nnnpsnjstl", "8-11 x: xxzdxfjczxx", "4-10 k: nrkxhfzspmfpzcl", "7-10 x: pxxjlcbtsjxdc", "1-14 s: fsszssssssfsssss", "5-7 d: ddgdgdvddd", "2-11 t: jttpshfcmlt", "1-3 v: pvdv", "5-7 x: xxkxxxx", "13-14 d: ddddxdvdppdcrdhk", "2-6 h: hhmhpc", "6-11 g: jxhsggmgcrp", "13-16 r: krrrnccjrrtrtrrlprrr", "3-4 f: ffff", "5-7 t: ttttttttttttt", "5-7 d: ndddjdw", "7-12 w: wgjwwbwcxfww", "2-6 s: sxsssbs", "12-14 l: xlxslmmjpvmzll", "4-6 h: hdhhhhdhkch", "9-11 j: jjjjjjjjjjj", "6-8 d: dddddddn", "5-6 b: bbbbsnbbbbb", "1-8 h: hhhhhhhhhh", "5-11 p: ppppppppppp", "11-12 d: dddddddddddk", "1-2 b: bbpbb", "2-8 j: jjhhjzjjt", "5-6 k: kfkjkk", "3-5 j: kgjjjjqnjcfmjjrc", "2-5 l: jtmzhlcs", "3-6 v: vvvvvvv", "15-16 v: vpvvvvvvvvjvvsvvvvvv", "15-16 h: hhhhhhhhmhhhdhhh", "2-3 k: ksdg", "7-11 b: dbhbbbbbhbbrb", "4-14 k: kkkknkkkkkbtkkk", "5-6 j: smdndsgfm", "2-3 c: sccc", "10-15 w: bwwwbwwwcwwlwwpwwwww", "3-6 w: wwwwwww", "14-19 s: jqdgjszsssssdrssjszs", "5-10 p: vpppbppppp", "4-7 p: zcpltzbsjmcgpv", "2-8 j: tfjjjjjnprj", "14-15 m: mmmmmmmmmmmmmhqm", "1-6 t: pttttct", "9-10 n: qnbnzxjfnnkb", "5-6 v: vvvvjvv", "7-8 v: qvvbvpvvvf", "2-15 s: sjsssssssssssstss", "7-10 b: bvbbbbnbbbbb", "13-14 b: bqbbbjqbbbbbtq", "3-7 k: cltbmkkb", "8-9 c: mlscsmqccfmccszsslck", "3-4 k: kklckk", "5-7 c: cccmclg", "5-7 d: dnddddd", "1-5 f: xfffbffffffffff", "13-15 n: nnnnnnnnnnnnnnqfnn", "3-6 t: ttttqztttcttt", "3-4 s: ssnws", "9-11 f: bcvkzrdrcffgzjgvf", "3-10 r: rrrdrrqrrrrrr", "3-6 h: bbqhkh", "3-8 n: nngnwthw", "1-11 x: ffqbbnxcpkrlzhm", "3-4 b: bbbbnnb", "5-6 f: lfdwqjffmj", "7-16 v: vvvvvvvvvvvvvvvvv", "13-15 m: zmmmzmmmmmmmmkmrmmmm", "1-2 r: rrrr", "12-14 d: dkdddddddddddcddd", "14-15 r: crlrtjkrrnhqrrr", "5-9 w: xjjwpcpfffchxtww", "15-16 d: jdcchsgdjtjdcxdrlwx", "9-15 p: cpmpppprpnpppsp", "6-7 w: jlqkwww", "1-12 p: wpfqzppptpflp", "4-14 x: xxxxxxxxxxxxxxxxxx", "1-4 z: xzzjz", "2-10 t: zvbnlwltbkvvcf", "5-9 v: vpvvvvfvvvvv", "2-12 l: lllllllllllllllll", "2-6 w: wmcwzpvxqsbxrrw", "11-12 l: lldlllllllrllgll", "9-13 h: hhhhhhhhdhxhrhh", "6-9 x: xxxzxxxxxxxx", "1-15 x: vcsxxxgxxxxwxxbbxx", "7-12 d: fmddcdfndddddkdnrgt", "12-14 x: wlmbpdxxrxbxdxg", "5-9 l: gnllscnsz", "4-5 j: jjjjj", "6-10 q: kvctnxpqhqfhjb", "6-8 q: vqqnqcqpqq", "2-9 k: mvxsbrxzr", "10-11 c: lbcgbpsxhcc", "9-17 x: xkdpklsvxqlkvrlvx", "3-14 j: trjcmjzjqkbjjj", "7-8 k: kkkkkkhfg", "1-3 f: pfdf", "9-12 s: glcjzdmllprrrxmdds", "9-11 b: bwbbbbbbbbqb", "2-14 f: kzfffbzfhfffflf", "5-7 n: ngnnnnn", "6-10 t: tttttrtttjtt", "7-8 f: fvffzfpff", "3-5 h: jbhkx", "9-11 b: qckbnjtbcbb", "12-15 m: mmmmkmmmmqmmmmjxm", "9-11 c: cdckczpcccc", "1-3 v: wzxq", "8-11 q: qhqqqqqlqqnqq", "4-9 j: mpjjqsdsj", "13-17 s: sssssssssssssssss", "4-7 x: knbxqrxxzl", "15-17 m: mmmmmmmmmmmmmmmmmmm", "6-10 w: wxnwfhwqdd", "9-11 r: crrrrcrnrrwrrmrrrm", "9-15 l: klflxlcllldllllll", "1-4 n: vpnznn", "2-4 k: cjkg", "7-8 m: xqmmrmmjjshmfm", "4-6 f: sfffff", "2-7 l: mltmmnldsnnl", "5-6 j: tjjdjxjjbs", "4-5 p: ppmnp", "2-3 b: bzrb", "5-17 h: dmpdgxthrcppfznbhchw", "3-4 p: pppp", "3-5 v: vvhvvr", "9-10 g: gflzqwbvgsgnbzngmgr", "8-17 z: xpvzsrhgzqtdcfjzl", "1-4 x: khxjxxvd", "3-4 z: czzz", "10-12 m: zjzcmmpxmjxmxzm", "7-9 k: kkkkkkkktkzkkk", "16-17 d: ddrdgdxdddddgddwmd", "2-16 f: xmsflgsvfwnfmxwnnmg", "4-12 b: ltbkbqbbbbbc", "12-14 t: tctlnvtjttsgztthtxtt", "9-14 x: xxxgpxvlkxdxdrxxrxwx", "15-17 s: xsssssssssssssvgw", "10-16 q: hqqpqxjqbsxqfqbm", "4-5 v: vvvnvvvttv", "15-16 v: rwshvltmvgvcvpvv", "4-5 j: jjjjjjjj", "3-5 w: jwfwp", "4-5 m: nddmm", "10-13 t: rtttnttttttttj", "14-17 l: llllllllllllltlcnl", "7-16 p: crjhtmpppgkpvgnpt", "6-9 b: bbfbbhbfgdrb", "1-2 q: qkqq", "17-20 f: hwcftkmtzhftmnfwfsdf", "2-4 w: wjtww", "10-11 p: pppppppppppp", "5-12 l: dntvlnljwkkldgp", "3-4 s: gmkvlqxsx", "8-10 m: qmmmmmmmmm", "12-14 p: gtbnfmlvtkppwp", "7-8 t: ttttttvs", "12-18 j: jjjjjjjjjjjjjjjjjb", "5-7 p: tfrppwpcpppp", "11-13 h: hthhhhzfbhhhdhhph", "1-7 n: ncgsvwvlvwlhbtmnnnpx", "10-11 j: djjbsjljjxf", "5-12 n: cnjqkknnnnmjnq", "18-20 z: zzzzzzzzzzzzzzzzzbzp", "2-5 m: kphbm", "12-13 g: tzrqwrjgzkxgg", "8-9 w: mwwwkwwwww", "2-6 j: jbxjjzjjjjj", "9-10 j: jjjjjjjjznj", "5-6 j: jjjspjj", "8-18 g: jggmgpfggbvggvgggggl", "3-5 n: nnxnh", "3-18 n: nnlnnnnnngnnnnnnnhnn", "6-10 r: rcrctghspmrrbrcrrrrq", "18-19 s: sfxqssssgssscxsdrss", "7-10 b: pbbbbbbgvbzbbz", "15-17 m: mmmmmmmmmmmmmmhmmm", "4-11 s: sssgssssssssssssxfss", "7-9 g: ggtgggggg", "4-8 w: wwwwwwwt", "17-20 p: pppppppppppppppppppn", "2-5 w: hdxrw", "9-18 c: cccccccccwcccccccc", "1-9 l: qllllllllllllll", "15-16 g: ggggggggggggggnj", "8-9 s: sjszkrhsss", "12-18 g: ggggggggggggggggggg", "5-6 s: ksnsxspsw", "4-8 p: pppbpppcppppp", "4-10 f: fffffffffff", "1-2 d: dqdd", "7-8 w: wwjwvhvcwbwww", "3-5 h: xghfhzhqw", "4-5 q: qqqzpw", "5-9 q: fxkrhqrjq", "2-4 m: mmjm", "1-4 p: wpmg", "7-13 n: nnnqfdtnnnnftnlnnn", "2-11 w: vwphpjskljxgqsgqzph", "2-4 w: wwwwgfkmpcf", "6-10 w: htgtkwwrmwvgrdmkzt", "3-4 s: sssstss", "11-20 l: dqlqmkllqclbmzklllrt", "1-6 g: gfgcwgvzkrhgjslg", "2-13 j: jjjszjxjljjjjqjjvjjh", "4-19 q: qqqlqqqqqqqqqqqqqqfq", "11-14 j: jbjjjjjjjjrjjp", "10-11 f: fpffvfffspvkf", "4-5 v: vhvlvhgh", "6-8 h: nsfklqhtxg", "2-3 z: czwnzzp", "1-10 c: wcmvcrcmjmcmbcckfcdc", "2-4 f: hzbrjqqntbbffbfl", "2-5 b: bzmbwztrb", "7-9 z: bscfzxzxz", "13-14 z: zzzzznzzzzzzbn", "3-7 t: rlxttrcmfttlt", "4-6 h: rldcbhx", "11-12 g: vfgfdgffbrrg", "1-10 s: ssssssssjsss", "7-13 k: dqbklkhvlgphjqhvgc", "8-18 x: xxxxdxxxxxxxxxxxxzxl", "1-3 x: xxxx", "11-12 z: ttzhvzzzxkzzzzgz", "5-7 h: hjmhhhhh", "10-12 l: lllllllpllllwmwwb", "2-6 j: hfjqlm", "6-9 n: mnnqfnpkw", "7-8 c: cckcqchscc", "4-5 q: qqqqq", "11-13 d: dddddddjddddgd", "3-11 q: qvqlqqqqlknnqhq", "1-4 h: hnphh", "9-15 f: ffffffffffffffffffff", "4-7 p: pxpvbpcz", "9-14 g: ggggggggwgggggg", "1-2 w: wwwwwwwwww", "4-5 g: ggcgggvg", "11-15 k: kkkgtkcjmwkrkkkkkk", "3-4 w: wzww", "8-10 k: kkkkkkkwjqtkkkkkkkk", "5-6 n: djnfkmzzbc", "12-18 x: jxxnxhqxxxxsxxxbxxxx", "13-14 p: hbmspppspppkgp", "15-16 d: dtddddddddddddpkd", "4-6 q: bvqqcpq", "2-4 h: dqhsjpp", "10-11 l: llllllklwll", "4-5 h: dhhcdh", "3-5 t: mvntxdsxftt", "5-9 w: wxpqwwqwjf", "14-15 c: cccccccccccccccc", "6-8 b: bbtbbdbwb", "4-7 g: gdpqgrj", "1-6 p: qppppppp", "1-14 s: sqssssbrszsskmss", "7-9 n: nnnnnnvsn", "4-7 r: nrltrrhndcbrr", "8-18 d: dddddddpdddddddddfdd", "1-3 b: bbbbb", "6-7 f: fpfplqf", "2-8 j: jjjjjjjsj", "3-5 h: ndhwxnb", "4-5 z: zzcztzv", "5-6 c: ccccck", "3-4 q: qjqqqqq", "4-19 q: nsqqgqpgmjsmbkpzwvl", "13-15 r: rrrrrrrrrrrrrrrrr", "6-12 k: kckvkkhkfbkkkkkkr", "3-5 x: xxxxsxx", "3-4 s: ssff", "2-7 l: pgwllllhlxwl", "8-18 z: zzzzzzzzzzzzzzzzzz", "1-11 c: fhhcjcchrlxcpcc", "3-4 w: zhcjwjnwgwg", "1-7 x: zxlpxmxt", "12-13 k: jskdrkmhkkkndk", "1-8 x: xmpwwvrx", "10-12 r: kprvkppwczjt", "1-12 b: sbbbvgbbzgbtbklsbb", "1-2 z: zzgqpwhzrhnrgjrgrstz", "9-11 s: ssssxssssss", "3-10 k: sbksdgcrkkxfgc", "7-8 n: nnnnnnzf", "13-14 j: jjjjjjjjjjjjjj", "3-4 f: fmff", "13-14 k: nkkkkkkkkkkkkk", "2-3 w: zbtww", "1-5 k: kgmkkrckdp", "4-5 p: xphlp", "3-4 k: tzzh", "2-6 s: jssvsxdsszsthspslztk", "1-3 z: mzzzqzzz", "5-7 h: hkhhwgwlchnh", "8-14 v: dvvvhggtsvdwgx", "3-8 d: dxdrkjcd", "2-4 d: pkmb", "8-11 b: bbbbbbbmbbkbbb", "3-12 j: wxljjrjjjjhzj", "5-17 c: wbnpcnngzcgcgbqwvkh", "2-6 q: sqlbhq", "1-3 n: nlbqgnhcnnznj", "2-3 k: gkncgkn", "4-6 g: gwxglggzhnq", "3-14 j: dgjcfxrjcgwjmjdmggk", "2-3 z: qvnrlzs", "11-12 k: kdtwlzkjfdkkfkn", "15-18 b: bqpxdxbbhkbnfdbfpb", "5-15 j: jjjjgjjrjjjjjmmjjjj", "8-13 t: tthtpttttmtktttt", "6-8 f: wxbpffcf", "8-9 b: bbbbbbbwr", "8-14 l: xmlrtbphkjrxrg", "15-18 x: qxxxlpxxmxxhmmxxxx", "7-11 c: cccccsclcrc", "1-4 k: kkkkkk", "3-4 j: jdqq", "11-15 b: vgfqbwsrkhgjhjv", "13-16 k: kkkkkfkkkkckkkkkkk", "18-20 b: lwwgrrbtwbcvdhbwbmbf", "7-12 m: mmmmmmmmmmmmmm", "2-12 l: pllndrxbzqzlmzstmbvw", "3-14 r: rrrrrrrrrrrrrrrrrrr", "1-16 p: ppppppppppppppppppp", "5-9 x: mtbxxxjxxxxd", "2-4 r: rrrrrrrrrr", "3-4 k: kfkz", "3-5 m: mgmmmm", "11-12 l: blcpjbfvzjll", "2-4 z: tzzzd", "5-9 z: zzzzqzzzrzjzzzzz", "10-14 m: mmmmmmmmmmmmvcmmm", "2-7 w: jwchkdws", "12-14 p: mdpppjpppppppppppr", "15-18 v: vvvvvvvvvvvvvvtvvcv", "4-10 p: pppfmbkhqppkvzpp", "3-17 p: ppppppjppppppppppppp", "3-6 r: cjvklrqrnhkk", "1-3 s: sssss", "2-6 d: dqddctdjd", "12-16 k: kkprkzdksmkbkfxd", "8-13 g: pggggggzwgggwgggmg", "13-16 b: sbwbbpbhbbbbdbbgb", "10-14 c: ccccccccccccccccc", "4-8 j: cxkxmzcrsnvbkfvmlk", "5-13 l: lmdnlnkbtspvll", "3-4 g: ggmgmmhwfmg", "5-7 d: ddddjdp", "11-15 v: vplvcvvdvhvmvtv", "10-12 r: rhrrrjrslrrnrqrrrzrn", "2-6 x: xhxxxxxx", "5-10 s: zmrkslzcdsrssfsssss", "5-7 r: rrrrrrr", "3-4 g: ggpx", "14-15 s: sssssssssssssmb", "1-5 w: smclhnwxff", "4-9 l: lllcllllqvlllll", "1-4 s: lhpd", "12-14 b: cwbdzbbbbdbfqc", "5-13 b: qtbsqbvjbcxbkznrlb", "10-12 f: fffpfffffxfqffffff", "15-16 x: xkxdqrxlwxxvxtxx", "14-15 p: ppjpgdppppppppgpp", "10-16 n: nnwnnnnnnnrqnnnn", "5-7 k: kwwzqdn", "2-3 m: mchvnq", "6-10 h: vhhxzphthw", "5-6 r: slpcrr", "17-18 b: bbbbcmbbbbbbbbbbpzbb", "3-5 s: wfscss", "9-12 r: rfjrrtrrrrkrr", "1-8 t: tstztmttgtttfvt", "8-9 f: fffffffjnff", "7-13 l: lkxclrlgldxllcll", "10-12 s: bssssdssbsxc", "8-12 t: tttttttwtttttt", "3-11 d: ndrndqqzqhdnjf", "1-10 g: xgqgggqggfmgggggggmg", "8-9 x: kttxbdnxxxcfjxxrxgxx", "4-10 n: ndjrqmcxnh", "12-13 c: txpwlvzhxhcwbpg", "8-9 z: crsqsztzz", "5-6 b: vbwbjb", "4-5 v: vvvvv", "2-6 d: tdsddddx", "4-10 r: hzqzrrnwrrhrrfrfwrr", "11-18 g: ggggggggggggggggggg", "1-7 z: zhcppxz", "7-9 r: rrwrrrrrr", "1-9 l: hlllllllqllpllllllvd", "1-4 g: jklqjgvtxsggfjggg", "2-18 m: mmqmmmjkmmmlkmmmlvmm", "1-8 w: kwgwxzshwwwc", "6-7 f: fffzfgk", "6-8 t: ttttthtcttk", "3-14 s: hsszwzdxkhsphrtrcbs", "4-10 l: lllllbllllll", "3-8 g: gggdgggkj", "1-4 x: xxxcx", "3-7 k: tqhknrkxjxqv", "11-19 p: pwwppgsztplgssgtpsp", "11-12 k: kkkkkkkkkklgkkk", "4-16 h: tnhhcgldlnzngvhb", "8-11 w: znwwwwwwwwwwww", "8-9 m: mbtmmmccjvmtm", "14-15 l: lllllllllltllfw", "11-16 z: kfslzzwszdmsnptcz", "5-6 g: qgggggk", "3-8 k: kkmdkwkd", "9-14 v: vvvvvvcvnvvvxwvfvv", "3-7 v: vvtvvvcmvvvvxvvvv", "6-7 l: dnfjfzfhhn", "9-10 g: mtzwcwgfjgshqg", "2-9 s: bsgcgjrqs", "3-4 c: ccpc", "9-14 n: nnnnnnnnnnnnnnknnnn", "4-5 p: tppxsp", "4-6 x: xmxxnxxw", "3-4 m: mmmm", "15-16 s: swshctdsksqgcsqsr", "2-4 x: xxxxxx", "6-7 h: hhvhhhh", "8-15 n: nnmvfnhnlndnwgjthmb", "4-5 z: fvzzzw", "1-12 m: wmjwgmmlmmmm", "7-16 n: xhnxjkjprznthqmdnwnl", "6-9 b: bbbbbbbbb", "17-19 d: ddwdlddkrgdkdddpqdj", "6-9 b: bbbbbbbbbb", "7-19 l: llvllllllclllflllll", "7-9 h: lhhhhhfhhhh", "7-10 m: hsdgdmmqqncdcgfxpb", "7-8 h: hqzcmvhw", "3-7 l: clkllltlwlp", "1-2 g: gsgggggqgg", "6-8 j: qjjqqcjjjjtjs", "11-14 r: nrlhdrrlbrrxgg", "3-10 s: tsxfscsfsws", "6-7 t: tstttdtbt", "6-7 r: rrcxrplkrnrrrbrpl", "3-11 b: pbwvpbkbzdbwbvlb", "5-11 d: wgwhfxtjmdfd", "7-12 m: chmmmrmrmxmqjcpmb", "1-2 n: nntnnn", "4-12 l: lllllllllllnllll", "4-5 c: ccchc",};
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
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

    public static void day3() {
        TikTok tikTok = new TikTok(true);
        var o = new Object() {
            int part1 = 0;
            long part2 = 0;
            int trees[] = new int[5];
            int xs[] = new int[5];
            boolean alternator = false;
        };
        String[] strings = new String[]{"..#.#...#.#.#.##.....###.#....#", "...........##.#...#.#..........", "....#.....#..#.............#...", ".#....###..##...#...##...#.#..#", "#.......#.........#..#.......#.", "...#.##..##...#.#......#.##.#..", "#.#..##.....#.....#..##........", "...#.####...#.##...#...........", ".#...#..#..#....#.#.#.#.##.....", "##.#..#.##..#......#..##.#.#..#", ".#.##.....#.#...............#.#", "..##.#.....#.....##..##.#....#.", "#..#..........#...##........#..", "#..##.#.#...............#..#...", "..#....#...#.......#.......#...", ".........#.#.##.#........#.....", "#...##....#..#.........#.#...##", "...#.#...#...........#..#...#..", "...#..#........#...#...........", ".#....##.#...#.#....#....##....", "...#...#......#.#.......#...##.", "####..........##....#..........", "#..#...........................", "#....#...####..##.#......#.#...", "..#..#.....##.....#...#....#..#", "#.##......#..##........#.......", "..........#.....#...#.#.#....##", "....##...##..#........#...#..#.", "#..#..#...##..............##...", "###.##..##.###...#....##.#..#..", ".#......#.................#.#..", "#.#..#.##.#.#.#.....#.........#", "..##......#.......##........#..", "#..............#.##.#.....#....", "............................##.", "..#.##......#..........#....#..", "..##.....#..##.#....#.......##.", "..#.#.##.#.........#...........", "...........##.#.#...#......###.", "#....#...#........#.#...#.#.###", "..............#...#.....##....#", "#...#...#..............#..#...#", ".##..#.........#.##.#..#...##..", ".....#.........#..#..#.......#.", ".#......#.#.#....##..#...#..##.", "#....................#.#....#..", "......#.....##............#....", ".#.....#......####.....#....##.", "##.####.#..#..........#......#.", "##....................#..##....", ".....#...#.#.##.#.###.....#....", ".#..#...####.#.#...#.#.....#...", "#.....##.........##.##.##.....#", "....#....##.###.........#...###", ".......#........#.##.....#####.", "...#.##..#...#...####.....##...", "..#....#....#......#......#.#..", "...#.#.#.........#.......#..#..", ".....#...........#.#........##.", "..##...#.#.##.#.#.#...###.#....", "..##.............###....#.#....", "#.......#....#..#...#..##..#...", "....##..#.......####....#..#.##", "##....#...#.#.#...#...#........", "....#.#................#...#...", "...#.....#.#.......##....#.#..#", "#....##.#...#.#..#.#.........#.", "#..##.........##.....#...#.....", "....#.....#.#..#..##..##.##...#", "#.....#...#.#.#.##....#.#.##...", ".#.#........#..##.......#...#.#", "..###.....#..#.##....#...#....#", "...#..###...#...#.......#..#...", ".#....##.......#.#..........##.", "...#.#.............##.....##...", "..#..#...#.....#...#...........", ".#.#......#.##....#.....#......", "........#.#.....#.#...#..#.#..#", "#.....#.#.....#.##..#.#....#.#.", "..#..###.#.#........#.....##..#", "#.#....#......#.#....###..#...#", "...#.#....#..#.##.....#...#....", "....##....#.#...#.........#..##", ".#......#...#.............#..#.", "#........#........#.#.....##...", "..##..#.##..#........#.........", ".....#...#...#..#.....#.#.##.#.", "..#..#..#.........#...#.......#", "....#.....#.......#.##.#.##..##", "......#.......##...#......#....", "....#....##.......###.#......#.", ".....#..#.#........#....#.....#", "#...#...#....#...###........#..", "#...........####.......#.#..#.#", "..###....#..........#...#.###..", "....#.#.....#....#..#.....#.##.", "...##.#..#..#.......#......#.#.", "....#......###..#.....#.....#..", ".....#.#.#.....#.##.#....####..", ".##....#.....#.#....##..#......", "#..#.....#..#...#....#.#.......", ".##.#..####..#.##.#......#.....", "......#....#.......##.##....#..", "...#....#....#..##.......##.###", "..##..........##.............#.", ".#...#.#...##..##.....#..#.....", "....#.#.##...................#.", ".......#.#..#....#.....#.......", ".#.#..#....####...#.#.##....#..", ".#.##...#..#..#...#.#.......#..", "##.#.....##.........#.......#..", ".##...#.....#.........##.#....#", ".............#..#............##", "...##.......#.....#.......#.##.", "##..##.........................", ".##.#........#........#........", ".....#................#.#......", ".............#....#....##....#.", "#..##...##...#..#.#............", ".......#...####.#..#..#.....##.", "..#.#..#......#.....#.#.#.....#", "...#..##........#..#.#....#.#..", ".#.....#..###..#....#.##.#...#.", "#.#..#.##.#..#......#.###...#..", "##..#.#..###....##.#...#...##.#", "##..#.........#...##......#....", "#.#...#.#..#..........#.......#", ".......#.#.......#.....##..#...", "........#..##............##.#..", "........##.....#........#..#...", "#..##.#..###......##...........", "..#.....#.#.#....#...#.#..#..##", "#...............#.......#.#.##.", "#..#.....#....#............#.#.", "...#....#...#....#..#..###.....", "..#....#.#.....#..#......##.#.#", ".#.#....#..#...#....#........#.", "..##....##....#.....#.#........", ".#...#....##..##.....##.....##.", ".#...........#....##...##.#....", "...#.....#......###.##.#.......", "......#.#..##.#.#....#...#...##", "....#...###.##....#.#.....#....", ".......#.....#......#.....##..#", ".####.#...##..#....#...........", "................###...#....#..#", "...#...#.....###.#.##.......#..", "..#....#...##...#.###......#.#.", "#...#......#............#.....#", "#.........#...............#..#.", "...#.##.....#............#.....", "........#......##..#..#..#.#..#", "....#....#.....#.#.....##..#...", ".....#....#..##.....#..........", ".##....#..#...........##.......", "#......##.....#...#.....#......", "...#.....#......#.#....#.......", "...#................##...#..#..", "........#..........#....#......", "......#....#.#.#...........#.#.", ".#............#....##.......##.", "#.......#.....#...##.#..##.....", ".#.....#.##..#..#....#.#..#.#.#", "....#...............###........", "#####...........#..#.......#..#", "...#.......#...#.#............#", "#...#..#.#...#.#...#.##.....##.", ".#..#..#..#.....#....#...#.....", ".#...#......#.......#.........#", ".#....#.....#...#...#..#....#..", "#....#....#.......#.....##.....", ".#...#.#.##.#....#..##........#", "..##...#............#..........", "..........#..#..#...#....#.....", "..#.......#....#.....##..##....", ".#...#......#...#..###...#...#.", "..##...#......#...#.#.#...#....", ".....#..#.#.#.#.#...#....##..#.", "##..#..##....#.#........##.#...", ".##..#.#...##..#....#..#.......", ".....#...#...#..#.#..#......#..", ".#.....##.##..#....####..#....#", "......##.................#....#", "....##.......###...#.##...##.#.", "...#...#.................##.#..", ".#.....##...#...#.....#.....##.", "##.........####..#...#...#....#", "...##.....#......#.###..#......", ".....###..##.#.......###..##...", "#....#...#.#...#...#.#....#..#.", "#...#.........##.#.........###.", "#....#..###..........##........", ".###.....#.#.....#........##..#", "....#.........##..#..#.#.#..#..", "..#......#...........#..##...#.", "...#.#..#..#...#.##..#..#.....#", ".#...#...#....................#", "..#..##..#.............#.....#.", ".....###.#.#.#...##..#.##....#.", "..#...####..##.#....#...#...#..", ".....#..#........#.#.#..#.##...", "#.#.........####..#...#.#......", "..............#..#........#....", "....#........#......#.........#", "#..#.##......#.#.......#....#..", "....#..............#.#.#..#....", "#.#......#.....##.......#..##..", ".#.#..........#....#......#....", ".....#.......#.##.....#......##", "...#...#.##.............####...", "..#....##...#...##..#.#..##.#..", "..#.........##.......###.#.....", "..#.........#####..##...#......", "..#.#...#.......#.####......##.", "......#.#.#....#......####....#", ".###...........#...#..#..#..##.", "..#...#..##.##...#.#.##.....#..", ".....#..#....##.......#...#....", "......#.....#.........#..#..#..", "...#..#.........##.....##.#...#", "....##...#......#..#.....#.....", "....#..#....#....#........##...", "##.....#.......#.....#.#.#..#..", ".....#..##.....##.##.#.........", ".#.#..##.............#.#.......", "......#.##.#.....#.#......#..#.", "..........#.#..#....#.#.#.#..##", "...##.....#..#...#...#...##....", "........#.#......#..###..#.....", "..#.##......#.......#.......#..", "...#....##.##.........#.#......", "......#....#.#.........#......#", ".....#...#....#...#......#..#..", ".##...#......#.........#...#.#.", "..#.#.#......#....#............", "..#.....##.............#.##.##.", "#......#......#...##.......#.#.", "##........#.....#..............", ".#.###.................#.#....#", "........##.#..##........#.#....", ".......###...#...##.#..#....#..", ".#..#....#..#......##......#...", ".#...#....#..........##..##.#..", ".#..###.......#............#...", "...#.....###.#..#........#.#.#.", "...#....#..#.##..........#.#.#.", ".#..##..#.....#...........#....", "#...#...##....#..#....##.......", "#..#......#................#...", "#..##....#.#..#......#.#.#.....", "##.#..#...#.....#.#...#......##", "#....#.#.#....#.....##.....##..", "....#...##.#...####.#.#.#.#..#.", ".....#.#....#..#.....#..#......", ".........#.#...................", "........#.....####......#..#..#", ".#.#.##.#...#.#......#...##.##.", ".#......#.#.#...#..#.......#...", "..#......#.##.##.#.#....#......", "..........#.#...###............", ".##..#..#.#.#..#.....#..#.#....", "......#.......#.#..#.#....#...#", ".#.......###......#...#.#.#....", ".............##..#..#...#....#.", "....#......#.#...#.#...#...#...", "..#....#.......#.#..#..#.#..#.#", ".#..#.#...#.....#.#...#####...#", ".##............#....#..........", "#.......####...#.#.#...........", "...#.......##.#..........#....#", "..#.#......#.......##.....#..##", "#......#.###..#......#......#.#", "##....#..#....#.##....#..#.....", "...##...#.#....#.#.......#.....", "#...####....#..#.#..#.##....###", ".....#..#..........###..#......", ".#..#..#...#....#.##..#..#.....", "#..#.....#....#..#.##...##.....", ".....###.#..#.......#...###.##.", "#..#........#.#..#.#.........#.", "....##........................#", ".#....#.#.#.#.#...#......#....#", "#....#...#.##.......#.#.###....", "..........###..##....#..##.#...", "...##..###...#.#.#.......##...#", "##.#...#..#.....###....#.......", "..#..##....###........##....###", ".....##..#...#..#.....#..#....#", "#................#....#...#..##", "#....#.#....#..###.#.#...#..#.#", "........##.#...#.#.#.#...#.....", "..#..###....#......##.#...##...", "..#..##....#.##..#.....#.....#.", ".#.#...#.....#..#..#......##.#.", "........#.#...#..##....#..#....", "...##...#...#...#...##...##..#.", ".......#..#..#....#.#..#...##..", ".#.....#.##........#...#.#.....", "##.#..#....#.#....#.#....#...#.", "..#.#......#.......##...#....#.", "#.#..####..#........#.......###", "....#.......#.......##.#...#.#.", "..#..#.#.............#..#......", "........###.....##....#.......#", "...#.....#...#...#....#.###....", "#...##.#........#..#...##..#..#", "...##..#....#....#.#.#...#.#...", "#......#.....#....###......##..", ".....#.........####...##..#....", ".......#...##...#..#..#.#......", ".#.#....#.....#.......#........", "...##...#....##..#.....###.....", ".#....#........##......#....#.#", ".........#.#.#.#...........#.#.", "....#.#..##......#.#.#..##.....", ".........#.....##....#.........", "....#.............#...........#", "...#..##........#.....###......", "#....#....#......#..#..#..#.#..", "#......##.....#..#....#..#.#...", "#..............#....#.#....###.", "..##..#..#...#...##........##..", "..#.##....#..#......###..#.....",};
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
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }
    public static void day8() {
        TikTok tikTok = new TikTok(true);
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


        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\day8.txt");
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
        tikTok.toc(System.out, " " + methodName);
    }
    public static void day4() {
        TikTok tikTok = new TikTok(true);
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;
        };

        String[] fields = new String[]{"byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:",};
        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\input.txt");
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
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

    public static void day5() {
        TikTok tikTok = new TikTok(true);
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;
            int lastSeat = -1;
        };
        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\input.txt");
        final var split = data.split("\n");
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
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

    public static void day6() {
        TikTok tikTok = new TikTok(true);
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;
        };
        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\input.txt");
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
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
    }

    public static void day7() {
        TikTok tikTok = new TikTok(true);
        record Content(String name, int qty) {

        }
        var o = new Object() {
            int part1 = 0;
            int part2 = 0;

        };

        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\input.txt");
        HashMap<String, ArrayList<Content>> bagToContent = new HashMap<>();
        final var split = data.split("\n");
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
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
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
        String data = readFile("F:\\DevFolder\\IdeaProjects\\AdventOfCode\\src\\com\\numberengineer\\aoc\\input.txt");
        final var split = data.split("\n");
        var ints = Arrays.stream(split).mapToInt(Integer::parseInt).sorted().toArray();
//        var ints = new int[]{1, 4, 5, 6, 7, 10, 11, 12, 15, 16, 19};
//        var ints = new int[]{1, 2, 3, 4, 7, 8, 9, 10, 11, 14, 17, 18, 19, 20, 23, 24, 25, 28, 31, 32, 33, 34, 35, 38, 39, 42, 45, 46, 47, 48, 49};

        o.previous = 0;
        o.three++;
        Arrays.stream(ints).filter(i -> {
//            if (o.previous + 3 >= i) {
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
        for (int i = 0; i < ints.length-1 ; i++) {
            arr[i+1] = ints[i + 1] - ints[i];
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
//            bigInteger = bigInteger.multiply(new BigInteger(String.valueOf(multi[i])).pow(sums[i]));
            o.part2*= Math.pow(multi[i],sums[i]);
        }
//        o.part2/=2;
//        o.part2*=3;
//        System.out.println(bigInteger.toString());
        o.part1 = o.one * o.three;
        final var methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " part1 " + o.part1);
        System.out.println(methodName + " part2 " + o.part2);
        tikTok.toc(System.out, " " + methodName);
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

