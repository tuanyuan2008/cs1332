import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Sarah Chen
 * @userid schen475
 * @GTID 903190753
 * @version 1.0
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     *
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> bruteForce(CharSequence pattern,
        CharSequence text, CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern must be "
                    + "of positive length.");
        } else if (text == null || comparator == null) {
            throw  new IllegalArgumentException("Text and Comparator "
                    + "cannot be null.");
        }
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            int j = i;
            int k = 0;
            while (j < i + pattern.length()
                    && comparator.compare(pattern.charAt(k),
                    text.charAt(j)) == 0) {
                j++;
                k++;
            }
            if (j == i + pattern.length()) {
                indices.add(i);
            }
        }
        return indices;
    }

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
            throw new IllegalArgumentException("Pattern must be "
                    + "of positive length.");
        } else if (text == null || comparator == null) {
            throw  new IllegalArgumentException("Text and Comparator "
                    + "cannot be null.");
        }
        List<Integer> matches = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return matches;
        }
        int i = 0;
        int j = 0;
        int[] f = buildFailureTable(pattern, comparator);
        while (i <= text.length() - pattern.length()) {
            while (j < pattern.length() && comparator.compare(
                    text.charAt(i + j), pattern.charAt(j)) == 0) {
                j++;
            }
            if (j == 0) {
                i++;
            } else {
                if (j == pattern.length()) {
                    matches.add(i);
                }
                int k = f[j - 1];
                i += j - k;
                j = k;
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
        if (pattern == null || comparator == null) {
            throw  new IllegalArgumentException("Pattern and Comparator "
                    + "cannot be null.");
        }
        int[] table = new int[pattern.length()];
        table[0] = 0;
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                table[j] = i + 1;
                i++;
                j++;
            } else {
                if (i == 0) {
                    j++;
                } else {
                    i = table[i - 1];
                }
            }
        }
        return table;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                       CharSequence text, CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern must be "
                    + "of positive length.");
        } else if (text == null || comparator == null) {
            throw  new IllegalArgumentException("Text and Comparator "
                    + "cannot be null.");
        }
        List<Integer> indices = new ArrayList<>();
        Map<Character, Integer> lastTable = buildLastTable(pattern);
        if (text.length() < pattern.length()) {
            return indices;
        }
        int i = pattern.length() - 1; // index in text
        int l = pattern.length() - 1; // index in pattern
        while (i < text.length()) {
            if (comparator.compare(pattern.charAt(l), text.charAt(i)) != 0) {
                i += pattern.length() - Math.min(l,
                        lastTable.getOrDefault(text.charAt(i), -1) + 1);
                l = pattern.length() - 1;
            } else {
                if (l != 0) {
                    i--;
                    l--;
                } else {
                    indices.add(i);
                    i += pattern.length();
                    l = pattern.length() - 1;
                }
            }
        }
        return indices;
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
            throw  new IllegalArgumentException("Pattern cannot be null.");
        }
        Map<Character, Integer> chars = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            chars.put(pattern.charAt(i), i);
        }
        return chars;
    }
}
