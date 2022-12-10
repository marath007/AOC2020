package com.numberengineer.aoc2022;


import com.numberengineer.aoc2016.Stuff;
import com.numberengineer.aoc2022.days.*;

import java.lang.reflect.InvocationTargetException;

public class Runner {


    public static void main(String[] args) {
        for (int i = 25; i > 0; i--) {
            try {
                Class.forName("com.numberengineer.aoc2022.days.Day"+i).getMethod("day"+i).invoke(null);
                break;
            } catch (IllegalAccessException | NoSuchMethodException  |
                     ClassNotFoundException ignored) {
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}

