import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author Aman Patel
 * @version 1.0
 * @userid apatel734
 * @GTID 903379254
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     * <p>
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch and shifting down by 1.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> bruteForce(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is null");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("text/comparator is null");
        }
        int i = 0;
        List<Integer> bForceMatch = new ArrayList<>();
        while (i <= text.length() - pattern.length()) {
            int j = 0;
            while (j < pattern.length()
                    && comparator.compare(text.charAt(i + j),
                    pattern.charAt(j)) == 0) {
                j++;
            }
            if (j == pattern.length()) {
                bForceMatch.add(i);
            }
            i++;
        }
        return bForceMatch;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     * <p>
     * The table built should be the length of the input text.
     * <p>
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     * <p>
     * Ex. ababac
     * <p>
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     * <p>
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("pattern/comparator is null");
        }
        int[] ftable = new int[pattern.length()];
        if (pattern.length() != 0) {
            ftable[0] = 0;
        }
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(j), pattern.charAt(i)) == 0) {
                ftable[j++] = ++i;
            } else {
                if (i != 0) {
                    i = ftable[i - 1];
                } else {
                    ftable[j++] = i;
                }
            }
        }
        return ftable;
    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     * <p>
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatch will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is null");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("text/comparator is null");
        }
        List<Integer> kmpString = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return kmpString;
        }
        int[] shift = buildFailureTable(pattern, comparator);
        int i = 0;
        int j = 0;
        while (i + pattern.length() <= text.length()) {
            while (j < pattern.length()
                    && comparator.compare(pattern.charAt(j),
                    text.charAt(i + j)) == 0) {
                j++;
            }
            if (j == 0) {
                i++;
            } else {
                if (j == pattern.length()) {
                    kmpString.add(i);
                }
                i += j - shift[j - 1];
                j = shift[j - 1];
            }
        }
        return kmpString;
    }
    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * <p>
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     * <p>
     * Ex. octocat
     * <p>
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     * <p>
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern is null");
        }
        Map<Character, Integer> table = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            table.put(pattern.charAt(i), i);
        }
        return table;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     * <p>
     * <p>
     * <p>
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is null");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("text/comparator is null");
        }
        List<Integer> boyerMooreString = new ArrayList<>();
        Map<Character, Integer> table = buildLastTable(pattern);
        int i = 0;
        while (i + pattern.length() <= text.length()) {
            int j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(
                    text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
            if (j < 0) {
                boyerMooreString.add(i++);
            } else {
                int shift = table.getOrDefault(
                        text.charAt(i + pattern.length() - 1), -1);
                if (shift < j) {
                    i += j - shift;
                } else {
                    i++;
                }
            }
        }
        return boyerMooreString;
    }
}
