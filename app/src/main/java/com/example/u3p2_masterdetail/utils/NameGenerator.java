package com.example.u3p2_masterdetail.utils;

import java.util.Random;

public class NameGenerator {
    private static final Random random = new Random();

    // Arrays containing possible starting consonants, starting vowels, vowels, and consonants
    private static final String[] startingConsonants = {"B", "C", "D", "G", "H", "L", "M", "R", "Rh", "S", "T", "V"};
    private static final String[] startingVowels = {"A", "E", "I", "Y", "Ae"};
    private static final String[] vowels = {"a", "e", "i", "o", "u", "y", "eo", "ae"};
    private static final String[] consonants = {"w", "l", "d", "n", "r", "rc", "ll", "nn", "dd", "th"};

    // Arrays containing prefixes, suffixes, and centres for both consonants and vowels
    private static final String[] prefixesC = {"Add", "Den", "Derr", "Gum", "Mad", "Mar", "Ow", "Tedd", "T" + randomString(vowels), "Var", "Vin", "Vob", "Vug", "Yr", "Rhyr"};
    private static final String[] prefixesV = {"Ae", "Bl" + randomString(vowels), "C" + randomString(vowels), "D" + randomString(vowels), "Gl" + randomString(vowels), "G" + randomString(vowels), "Gw" + randomString(vowels), "Ha", "L" + randomString(vowels), "M" + randomString(vowels), "R" + randomString(vowels), "Rh" + randomString(vowels), "S" + randomString(vowels), "S" + randomString(vowels), "T" + randomString(vowels), "V" + randomString(vowels)};
    private static final String[] suffixesC = {"yn", "er", "yc", "ec", "oc", "yr", "in", "aent"};
    private static final String[] suffixesV = {"ry", "ryn", "ran", "lyn", "van", "nyc"};
    private static final String[] centresC = {"redd", "reor", "og", "thyn"};
    private static final String[] centresV = {"rae", "ra", "thar", "gwy"};

    public NameGenerator() {
        // Constructor (empty for now)
    }

    // Method to generate a random name
    public String generateName() {
        int randomNumber = random.nextInt(3);
        String result = "";
        switch (randomNumber) {
            case 0:
                result += generatePrefixC() + randomString(suffixesC);
                break;
            case 1:
                result += generatePrefixV() + randomString(suffixesV);
                break;
            case 2:
                result += randomString(prefixesC) + randomString(centresC) + randomString(suffixesC);
                break;
            default:
                result += randomString(prefixesV) + randomString(centresV) + randomString(suffixesV);
                break;
        }
        return result;
    }

    // Method to generate a prefix for vowels
    private static String generatePrefixV() {
        int randomNumber = random.nextInt(2);

        if (randomNumber > 0) {
            return randomString(startingVowels) + randomString(consonants);
        } else {
            return randomString(startingConsonants) + randomString(vowels) + randomString(consonants);
        }
    }

    // Method to generate a prefix for consonants
    private static String generatePrefixC() {
        int randomNumber = random.nextInt(2);

        if (randomNumber > 0) {
            return randomString(startingVowels);
        } else {
            return randomString(startingConsonants) + randomString(vowels);
        }
    }

    // Method to retrieve a random string from an array
    private static String randomString(String[] strings) {
        return strings[random.nextInt(strings.length)];
    }
}