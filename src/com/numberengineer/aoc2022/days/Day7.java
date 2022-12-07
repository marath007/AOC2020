package com.numberengineer.aoc2022.days;

import com.numberengineer.aoc.TikTok;

import java.util.ArrayList;

import static com.numberengineer.aoc.Utils.*;

public class Day7 {
    public static void day7() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = "$ cd /\n" + "$ ls\n" + "dir a\n" + "14848514 b.txt\n" + "8504156 c.dat\n" + "dir d\n" + "$ cd a\n" + "$ ls\n" + "dir e\n" + "29116 f\n" + "2557 g\n" + "62596 h.lst\n" + "$ cd e\n" + "$ ls\n" + "584 i\n" + "$ cd ..\n" + "$ cd ..\n" + "$ cd d\n" + "$ ls\n" + "4060174 j\n" + "8033020 d.log\n" + "5626152 d.ext\n" + "7214296 k";
        if (!testMode) {
            data = getData(day, "2022");
        }
        var o = new Object() {
            long part1 = 0;
            long part2 = 0;
        };
        final var strings = asLines(data);
        NEFolder curFolder = new NEFolder("");
        for (int i = 0; i < strings.length; i++) {
            var args = strings[i].split(" ");
            if (args[0].equals("$")) {
                if (args[1].equals("cd")) {
                    final var newDir = args[2];
                    if (newDir.equals("/")) {
                        curFolder = curFolder.getRoot();
                    } else if (newDir.equals("..")) {
                        curFolder = curFolder.getParent();
                    } else {
                        curFolder = curFolder.moveInto(newDir);
                    }
                } else if (args[1].equals("ls")) {
                    while (strings.length > (i + 1) && !strings[i + 1].startsWith("$")) {
                        i++;
                        args = strings[i].split(" ");
                        if (args[0].equals("dir")) {
                            curFolder.addDir(args[1]);
                        } else {
                            curFolder.addFile(args[1], Long.parseLong(args[0]));
                        }
                    }
                }
            }
        }

        final var freeSpace = 70000000 - curFolder.getRoot().getSize();
        final var toFree = 30000000 - freeSpace;
//        System.out.println("freeSpace="+freeSpace);
//        System.out.println("toFree=" + toFree);
//        System.out.println(curFolder.getRoot().getSizes());
        o.part2 = curFolder.getRoot().getSizes().stream().filter(aLong -> aLong >= toFree).mapToLong(Long::longValue).min().getAsLong();
        o.part1 = curFolder.getRoot().getSizes().stream().filter(aLong -> aLong <= 100000).mapToLong(Long::longValue).sum();
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

    static class NEFolder {
        String name;
        ArrayList<NEFolder> folders = new ArrayList<>();
        ArrayList<NEFile> files = new ArrayList<>();
        NEFolder parent = null;

        public NEFolder(String name) {
            this.name = name;
        }

        public NEFolder(String name, NEFolder parent) {
            this.name = name;
            this.parent = parent;
        }

        public ArrayList<Long> getSizes() {
            ArrayList<Long> sizes = new ArrayList<>();
            for (NEFolder folder : folders) {
                sizes.add(folder.getSize());
                sizes.addAll(folder.getSizes());
            }
            return sizes;
        }

        public long getSize() {
            long size = 0;
            for (NEFolder folder : folders) {
                size += folder.getSize();
            }
            for (NEFile file : files) {
                size += file.size;
            }
            return size;
        }

        public NEFolder getParent() {
            return parent;
        }

        public NEFolder getRoot() {
            if (parent == null) return this;
            var parent1 = parent;
            while (parent1.getParent() != null) {
                parent1 = parent1.getParent();
            }
            return parent1;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NEFolder neFolder = (NEFolder) o;
            return name.equals(neFolder.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        public NEFolder moveInto(String newDir) {
            final var neFolder = new NEFolder(newDir, this);
            if (!folders.contains(neFolder)) {
                folders.add(neFolder);
                return neFolder;
            } else {
                return folders.get(folders.indexOf(neFolder));
            }
        }

        public void addDir(String string) {
            final var neFolder = new NEFolder(string, this);
            if (!folders.contains(neFolder)) {
                folders.add(neFolder);
            }
        }

        public void addFile(String string, long size) {
            final var neFile = new NEFile(string, size);
            if (!files.contains(neFile)) {
                files.add(neFile);
            }
        }
    }

    static class NEFile {
        String name;
        long size;

        public NEFile(String name, long size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NEFile neFile = (NEFile) o;

            return name.equals(neFile.name);
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}

