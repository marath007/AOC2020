package com.numberengineer.aoc.days;

import com.numberengineer.aoc.TikTok;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import static com.numberengineer.aoc.Utils.*;

public class Day25 {
    public static void day25() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
        long pkey=14788856;
        long modulator = 20201227;
        long subject=7;
        long incrementor=1;
        long cnt=0;
        while (incrementor!=pkey){
            incrementor*=subject;
            cnt++; incrementor%=modulator;

        }
        System.out.println(cnt+" loop");
        subject=19316454;
        incrementor=1;
        for (int i=0;i<cnt;i++){
            incrementor*=subject;
            incrementor%=modulator;
        }
        endOfWork(tikTok, day, false, incrementor, "cake");
    }

    static class Coordinate {
        boolean black = false;
        private double x;
        private double y;

        Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double x() {
            return x;
        }

        public double y() {
            return y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Coordinate) obj;
            return Double.doubleToLongBits(this.x) == Double.doubleToLongBits(that.x) &&
                    Double.doubleToLongBits(this.y) == Double.doubleToLongBits(that.y);
        }

        Coordinate normalize() {
//            double l = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            double x1 = x;
            double y1 = y;
//            x1 /= l;
//            y1 /= l;
            return new Coordinate(x1, y1);
        }

        Coordinate clean() {
            double x1 = Math.round(x * 10000d) / 10000d;
            double y1 = Math.round(y * 10000d) / 10000d;
            return new Coordinate(x1, y1);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Coordinate[" +
                    "x=" + x + ", " +
                    "y=" + y + ']';
        }


    }

}

