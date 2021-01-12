package com.numberengineer.general;

import com.numberengineer.aoc.TimeVar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class TimeLeft {

    public static String timeLeft() {
        return new TimeVar(timeLeftMs()).toShortReadableString();
    }
    final static long l = 15000 ;

    //    public static double nextEventf() {
//        return (double) ((System.currentTimeMillis() + timeLeftMs()) / 86400000.0d);
//    }
//    public static double lastEventf() {
//        return (double) ((System.currentTimeMillis() + timeLeftMs()) / 86400000.0d)-1;
//    }
    public static long timeLeftMs() {
        long dayMs = l- (System.currentTimeMillis() % l);
        if (dayMs < 0) {
            dayMs += l;
        }
        return dayMs;
    }

    public static void main(String[] args) {
        System.out.println(timeLeftMs());
        schedule(()-> System.out.println("New quarter minute"));
    }


    public static void schedule(Runnable runnable) {
        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r);
            thread.setName("Event Scheduler");
            return thread;
        });
        scheduler.scheduleAtFixedRate(runnable, timeLeftMs(), l, TimeUnit.MILLISECONDS);
//        scheduler.scheduleAtFixedRate(runnable, timeLeftMs(), 1000, TimeUnit.MILLISECONDS);
    }
}