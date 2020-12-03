package com.numberengineer.aoc;

import java.io.PrintStream;

public class TicToc {
    long start;
    long end;
    long comp;
    long firstTic;

    public TicToc() {
        super();
    }

    public static void main(String[] args) {
        TicToc ticToc=new TicToc();
        ticToc.tic();
        ticToc.toc();
        ticToc.toc();
        ticToc.toc();

    }
    public TicToc(boolean init) {
        super();
        if (init) {
            tic();
        }
    }

    public TicToc tic() {
        start = System.nanoTime();
        firstTic = start;
        return this;
    }

    public void toc(PrintStream out, boolean print, String s) {
        end = System.nanoTime();

        if (print) {
            if (firstTic == start) {
                out.println(TimeVar.fromNs(end - firstTic - comp) + s);
            } else {
                out.println(TimeVar.fromNs(end - firstTic - comp) + "(+" + TimeVar.fromNs(end - start - comp) + ")" + s);
            }
        }
        start=end;
    }

    public void toc(PrintStream out, String s) {
        toc(out, true, s);
    }

    public void toc(boolean print) {
        toc(System.out, print, "");
    }

    public void toc() {
        toc(System.out, true, "");
    }

    public void toc(PrintStream out) {
        toc(out, true, "");
    }

    public void setComp() {
        end = System.nanoTime();
        this.comp = end - start;
    }
}