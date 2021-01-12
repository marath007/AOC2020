package com.numberengineer.aoc2015.utils;

import java.util.ArrayList;
import java.util.List;

public class Utilities {


    public static List<int[]> permutations(int n) {

        class Perm {
            private final List<int[]> permutations = new ArrayList<>();

            private void perm(int[] array, int step) {
                if (step == 1) permutations.add(array.clone());
                else for (int i = 0; i < step; i++) {
                    perm(array, step - 1);
                    int j = (step % 2 == 0) ? i : 0;
                    swap(array, step - 1, j);
                }
            }

            private void swap(int[] array, int i, int j) {
                int buffer = array[i];
                array[i] = array[j];
                array[j] = buffer;
            }

        }

        int[] nVector = new int[n];
        for (int i = 0; i < n; i++) nVector[i] = i;

        Perm perm = new Perm();
        perm.perm(nVector, n);
        return perm.permutations;

    }
    public static class Duck{
        private final static ArrayList<Duck> ducks=new ArrayList<>();
        public Duck() {
            ducks.add(this);
        }

        @Override
        protected void finalize() throws Throwable {
            ducks.add(this);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            new Duck();

        }
        System.gc();
    }
    // Driver code


}

