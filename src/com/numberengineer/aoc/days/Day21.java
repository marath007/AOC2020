package com.numberengineer.aoc.days;

import com.numberengineer.aoc.TikTok;

import java.util.*;
import java.util.regex.Pattern;

import static com.numberengineer.aoc.Utils.*;

public class Day21 {
    public static void day21() {
        TikTok tikTok = new TikTok(true);
        final var day = getDay();
//        boolean testMode = true;
        boolean testMode = false;
        String data = """
                mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
                trh fvjkl sbzzf mxmxvkd (contains dairy)
                sqjhc fvjkl (contains soy)
                sqjhc mxmxvkd sbzzf (contains fish)""";
        if (!testMode) {
            data = getData(day);
        }
        class Food {
            final String[] ingredients;
            final String[] alergens;

            Food(String[] ingredients, String[] alergens) {
                this.ingredients = ingredients;
                this.alergens = alergens;
            }

        }
        var o = new Object() {
            long part1 = 0;
            String part2;

            String[] unionStringArrays(String[] ss, String[] ss2) {
                final var strings = new HashSet<>(Arrays.asList(ss2));
                return Arrays.stream(ss).filter(strings::contains).toArray(String[]::new);
            }
        };
        final var strings = asLines(data);
//        final var longs = asLongs(data);
        Pattern ingredientFinder = Pattern.compile("([a-z]+)(?=.*[(])");
        Pattern allergenFinder = Pattern.compile("(?!.*[(])([a-z]+)");
        ArrayList<Food> foods = new ArrayList<>();
        HashMap<String, String> ingredientToAllergen = new HashMap<>();
        HashMap<String, String> allergenToIngredient = new HashMap<>();
        HashSet<String> ingredient = new HashSet<>();
        HashSet<String> allergen = new HashSet<>();
        for (int i = 0; i < strings.length; i++) {
            final var ingredients = ingredientFinder.matcher(strings[i]).results().map(matchResult -> matchResult.group(1)).peek(ingredient::add).toArray(String[]::new);
            final var allergens = allergenFinder.matcher(strings[i]).results().skip(1).map(matchResult -> matchResult.group(1)).peek(allergen::add).toArray(String[]::new);
            foods.add(new Food(ingredients, allergens));
        }
        while (allergenToIngredient.size() != allergen.size()) {
            allergen.forEach(s -> {
                final var foods1 = foods.stream().filter(food -> Arrays.stream(food.alergens).anyMatch(s1 -> s.equals(s1))).toArray(Food[]::new);
                final var reduce = Arrays.stream(foods1).map(f -> Arrays.stream(f.ingredients).filter(s1 -> !ingredientToAllergen.containsKey(s1)).toArray(String[]::new)).reduce(o::unionStringArrays);
                final var strings1 = reduce.get();
//                System.out.println(foods1.length + "->" + strings1.length);
                if (strings1.length == 1) {
//                    System.out.println("Mapped " + strings1[0] + " to " + s);
                    allergenToIngredient.put(s, strings1[0]);
                    ingredientToAllergen.put(strings1[0], s);
                }
            });
        }
        o.part1 = foods.stream().mapToLong(f -> Arrays.stream(f.ingredients).filter(s -> !ingredientToAllergen.containsKey(s)).count()).sum();
        o.part2 = ingredientToAllergen.keySet().stream().sorted(Comparator.comparing(ingredientToAllergen::get)).reduce((s, s2) -> s + "," + s2).get();
//        boolean unknownAllergens=true;
//        while (unknownAllergens) {
//            unknownAllergens=false;
//            for (int i = 0; i < foods.size(); i++) {
//                final var food = foods.get(i);
//                final var unknownIngredients = Arrays.stream(food.ingredients).filter(s -> !ingredientToAllergen.containsKey(s)).toArray(String[]::new);
//                final var unknownAllergen = Arrays.stream(food.alergens).filter(s -> !allergenToIngredient.containsKey(s)).toArray(String[]::new);
//                if (unknownIngredients.length == 1 && unknownAllergen.length == 1) {
//                    ingredientToAllergen.put(unknownIngredients[0], unknownAllergen[0]);
//                    allergenToIngredient.put(unknownAllergen[0], unknownIngredients[0]);
//                    System.out.println("Mapped " + unknownIngredients[0] + " to " + unknownAllergen[0]);
//                }
//                if (unknownAllergen.length!=0){
//                    unknownAllergens=true;
//                }
//                for (int j = i + 1; j < foods.size(); j++) {
//                    final var food1 = foods.get(j);
//                    final var similarAllergens = Arrays.stream(food.alergens).filter(s -> !allergenToIngredient.containsKey(s)).filter(s -> Arrays.stream(food1.alergens).anyMatch(s1 -> s1.equals(s))).toArray(String[]::new);
//                    final var similarIngredients = Arrays.stream(food.ingredients).filter(s -> !ingredientToAllergen.containsKey(s)).filter(s -> Arrays.stream(food1.ingredients).anyMatch(s1 -> s1.equals(s))).toArray(String[]::new);
//                    if (similarIngredients.length == 1 && similarAllergens.length == 1) {
//                        ingredientToAllergen.put(similarIngredients[0], similarAllergens[0]);
//                        allergenToIngredient.put(similarAllergens[0], similarIngredients[0]);
//                        System.out.println("Mapped " + similarIngredients[0] + " to " + similarAllergens[0]);
//                    }
//                }
//            }
//            System.out.println("Full scan?");
//        }
        endOfWork(tikTok, day, testMode, o.part1, o.part2);
    }

}

