package com.numberengineer.general;

public abstract class Day {
    public Day() {
    }

    public void run() {
        part1();
        part2();
    }

    protected abstract void part1();

    protected abstract void part2();

}

