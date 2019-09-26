import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Tyler Jones
 * @userid tjones329
 * @GTID 903283773
 * @version 1.0
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern can not be null");
        } else if (text == null) {
            throw new IllegalArgumentException("Text can not be null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparaotr can not be null");
        } else if (pattern.length() > text.length()) {
            return new ArrayList<>();
        }

        int[] failureTable = buildFailureTable(pattern, comparator);
        List<Integer> matches = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i <= (text.length() - pattern.length())) {
            while (j < pattern.length()
                    && (comparator.compare(text.charAt(i + j),
                    pattern.charAt(j)) == 0)) {
                j++;
            }
            if (j == 0) {
                i++;
            } else {
                if (j == pattern.length()) {
                    matches.add(i);
                }
                int nextAlignment = failureTable[j - 1];
                i = i + j - nextAlignment;
                j = nextAlignment;
            }
        }
        return matches;

    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @throws IllegalArgumentException if the pattern or comparator is null
     * @param pattern a {@code CharSequence} you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern can not be null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("comparator can not be null");
        } else if (pattern.length() == 0) {
            return new int[0];
        }
        int[] failureTable = new int[pattern.length()];
        int patternFirstIndex = 0;
        int patternCheckIndex = 1;
        while (patternCheckIndex < pattern.length()) {
            char checkChar = pattern.charAt(patternCheckIndex);
            char firstChar = pattern.charAt(patternFirstIndex);
            if (comparator.compare(checkChar, firstChar) == 0) {
                failureTable[patternCheckIndex] = patternFirstIndex + 1;
                patternCheckIndex++;
                patternFirstIndex++;
            } else if (patternFirstIndex == 0) {
                failureTable[patternCheckIndex] = 0;
                patternCheckIndex++;
            } else {
                patternFirstIndex = failureTable[patternFirstIndex - 1];
            }
        }
        return failureTable;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern can not be null");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern length was 0");
        } else if (text == null) {
            throw new IllegalArgumentException("Text can not be null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparator can not be null");
        }

        Map<Character, Integer> buildLast = buildLastTable(pattern);
        List<Integer> listOfIndices = new ArrayList<>();

        int i = 0;
        while (i <= (text.length() - pattern.length())) {
            int j = pattern.length() - 1;
            while (j >= 0
                    && (comparator.compare(text.charAt(i + j),
                    pattern.charAt(j)) == 0)) {
                j = j - 1;
            }
            if (j == -1) {
                listOfIndices.add(i);
                i++;
            } else {
                Integer shiftedIndex = buildLast.get(text.charAt(i + j));
                if (shiftedIndex == null) {
                    shiftedIndex = -1;
                }
                if (shiftedIndex < j) {
                    i = i + (j - shiftedIndex);
                } else {
                    i++;
                }
            }
        }
        return listOfIndices;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("the pattern can not be null");
        }
        Map<Character, Integer> lastindices = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            if (lastindices.containsKey(pattern.charAt(i))) {
                lastindices.replace(pattern.charAt(i), i);
            } else {
                lastindices.put(pattern.charAt(i), i);
            }
        }
        return lastindices;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 101;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow. However, you will not need to handle this case.
     * You may assume there will be no overflow.
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 101 hash
     * = b * 101 ^ 3 + u * 101 ^ 2 + n * 101 ^ 1 + n * 101 ^ 0 = 98 * 101 ^ 3 +
     * 117 * 101 ^ 2 + 110 * 101 ^ 1 + 110 * 101 ^ 0 = 102174235
     *
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     *
     * remove the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 101
     * hash("unny") = (hash("bunn") - b * 101 ^ 3) * 101 + y =
     * (102174235 - 98 * 101 ^ 3) * 101 + 121 = 121678558
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     *
     * Do NOT use Math.pow() for this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern cant be null");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("pattern had size of 0");
        } else if (text == null) {
            throw new IllegalArgumentException("text cant be null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("comparator cant be null");
        } else if (text.length() < pattern.length()) {
            return new ArrayList<>();
        }
        List<Integer> matches = new java.util.ArrayList<Integer>();
        int patternHash = rollingHash(pattern, null, 0, 0);
        int newBase = 1;
        for (int i = pattern.length() - 2; i >= 0; i--) {
            newBase = BASE * newBase;
        }
        int textHash = rollingHash(text.subSequence(0, pattern.length()),
                null, 0, newBase);
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            if (patternHash == textHash) {
                int j = 0;
                while ((j < pattern.length())
                        && (comparator.compare(text.charAt(i + j),
                        pattern.charAt(j)) == 0)) {
                    j = j + 1;
                }
                if (j == pattern.length()) {
                    matches.add(i);
                }
            }
            i++;
            if (i <= text.length() - pattern.length()) {
                CharSequence a = text.subSequence(i, i
                        + pattern.length());
                CharSequence b = text.subSequence(i - 1, i
                        + pattern.length() - 1);
                textHash = rollingHash(a, b, textHash, newBase);
            }
        }

        return matches;

    }

    /**
     * Helper method to figure out the hashcode
     * of the given pattern/sequence.
     *
     * @param pattern the pattern to find the hashcode for
     * @param last the pattern we are using to find the rolling hash
     * @param num the last hashcode we are using to find rolling hash
     * @param totBase the total base
     * @return the new hashcode
     */
    private static int rollingHash(CharSequence pattern, CharSequence last,
                                   int num, int totBase) {
        if (last == null) {
            int total = (int) pattern.charAt(pattern.length() - 1);
            int newBase = 1;
            for (int i = pattern.length() - 2; i >= 0; i--) {
                int curr = (int) pattern.charAt(i);
                newBase = BASE * newBase;
                curr = curr * newBase;
                total = total + curr;
            }
            return total;

        } else {
            int total = (num - ((int) last.charAt(0) * totBase)) * BASE;
            total = total + (int) pattern.charAt(pattern.length() - 1);
            return total;
        }

    }
}
