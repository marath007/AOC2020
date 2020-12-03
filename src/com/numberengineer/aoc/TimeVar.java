package com.numberengineer.aoc;

public class TimeVar {
    boolean showDays = true;
    private long micros;
    private long milli;
    private long seconds;
    private long minutes;
    private long hours;
    private long days;

    public TimeVar(long mili) {
        this.milli = mili;
    }

    public TimeVar(double nanos) {
        nanos /= 1000;
        double diff = nanos % 1000;
        this.milli = (long) ((nanos - diff) / 1000);
        this.micros = (long) diff;
    }

    public TimeVar(long mili, long seconds) {
        this.milli = mili;
        this.seconds = seconds;
    }

    public TimeVar(long mili, long seconds, long minutes) {
        this.milli = mili;
        this.seconds = seconds;
        this.minutes = minutes;
    }

    public TimeVar(long mili, long seconds, long minutes, long hours) {
        this.milli = mili;
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
    }

    public TimeVar(long mili, long seconds, long minutes, long hours, long days) {
        this.milli = mili;
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
        this.days = days;
    }

    public static String fromMs(long ms) {
        return new TimeVar(ms).toReadableString();
    }

    public static String fromNs(double ns) {
        return new TimeVar(ns).toReadableString();
    }

    public void setShowDays(boolean showDays) {
        this.showDays = showDays;
        
    }
//    public static TimeVar FromString(string tim) {
//        try {
//            var holderString = tim.Split(',');
//            var holder = new long[5];
//            for (var i = 0; i < 5; i++) holder[i] = long.Parse(holderString[i]);
//            return new TimeVar(holder[0], holder[1], holder[2], holder[3], holder[4]);
//        } catch {
//            return new TimeVar(0);
//        }
//    }
public static String wrapBackward(Object s, int len) {
    return wrapBackward(String.valueOf(s), len);
} public static String wrapBackward(String s, int len) {
        int spaceToAdd = len - s.length();
        if (spaceToAdd < 1) {
            return s;
        }
        return String.format("%" + spaceToAdd + "s", "") + s;
    }

    public String toReadableString() {
        Condense();
        StringBuilder holder = new StringBuilder();
        if (days > 0) {
            if (showDays) {
                holder.append(wrapBackward(days, 3)).append("d ");
                holder.append(wrapBackward(hours, 3)).append("h ");
            }else {
                holder.append(wrapBackward(hours+days*24, 3)).append("h ");
            }
            holder.append(wrapBackward(minutes, 3)).append("m ");
            holder.append(wrapBackward(seconds, 3)).append("s ");
            holder.append(wrapBackward(milli, 3)).append("ms");
        } else if (hours > 0) {
            holder.append(wrapBackward(hours, 3)).append("h ");
            holder.append(wrapBackward(minutes, 3)).append("m ");
            holder.append(wrapBackward(seconds, 3)).append("s ");
            holder.append(wrapBackward(milli, 3)).append("ms");
        } else if (minutes > 0) {
            holder.append(wrapBackward(minutes, 3)).append("m ");
            holder.append(wrapBackward(seconds, 3)).append("s ");
            holder.append(wrapBackward(milli, 3)).append("ms");
        } else if (seconds > 0) {
            holder.append(wrapBackward(seconds, 3)).append("s ");
            holder.append(wrapBackward(milli, 3)).append("ms");
        } else {
            holder.append(wrapBackward(milli, 3)).append("ms");
            if (micros > 0) {
                holder.append(' ').append(wrapBackward(micros, 3)).append("us");
            }
        }
        return holder.toString();
    }

    public void Condense() {
        long add = milli - milli % 1000;
        milli -= add;
        seconds += add / 1000;
        add = seconds - seconds % 60;
        seconds -= add;
        minutes += add / 60;
        add = minutes - minutes % 60;
        minutes -= add;
        hours += add / 60;
        add = hours - hours % 24;
        hours -= add;
        days += add / 24;
    }

    public long toMili() {
        Condense();
        return toSeconds() * 1000 + milli;
    }

    public long toSeconds() {
        Condense();
        return toMinutes() * 60 + seconds;
    }

    public long toMinutes() {
        Condense();
        return toHours() * 60 + minutes;
    }

    public long toHours() {
        Condense();
        return days * 24 + hours;
    }

    public String toHiddenString() {
        return milli + "," + seconds + "," + minutes + "," + hours + "," + days;
    }

    public String toShortReadableString() {
        Condense();
        StringBuilder holder = new StringBuilder();
        if (days > 0) {
            holder.append(wrapBackward(days, 3)).append("d ");
            holder.append(wrapBackward(hours, 3)).append("h ");
        } else if (hours > 0) {
            holder.append(wrapBackward(hours, 3)).append("h ");
            holder.append(wrapBackward(minutes, 3)).append("m ");
        } else if (minutes > 0) {
            holder.append(wrapBackward(minutes, 3)).append("m ");
            holder.append(wrapBackward(seconds, 3)).append("s ");
        } else if (seconds > 0) {
            holder.append(wrapBackward(seconds, 3)).append("s ");
            holder.append(wrapBackward(milli, 3)).append("ms");
        } else if (milli > 0) {
            holder.append(wrapBackward(milli, 3)).append("ms");
            if (micros > 0) {
                holder.append(' ').append(wrapBackward(micros, 3)).append("\u00B5s");
            }
        } else {
            holder.append(wrapBackward(micros, 3)).append("\u00B5s");
        }
        return holder.toString();
    }

    public void addMili(long milli) {
        this.milli += milli;
    }

    public void addSec(long seconds) {
        this.seconds += seconds;
    }

    public void addMin(long minutes) {
        this.minutes += minutes;
    }

    public void addHour(long hours) {
        this.hours += hours;
    }

    public void addDay(long days) {
        this.days += days;
    }
    }
