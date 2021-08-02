import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author Jun Yeop Kim
 * @version 1.0
 * @userid jkim3663
 * @GTID 903624126
 * <p>
 * Collaborators: Joe Chen (I asked him about the RabinKarp optimization about BASE^n, no code shared)
 * <p>
 * Resources: YouTube
 */
public class PatternMatching {

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
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern inserted is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator inserted is null.");
        }
        int[] table = new int[pattern.length()];

        if (pattern.length() != 0) {
            table[0] = 0;
        }

        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                table[j] = i + 1;
                i++;
                j++;
            } else if (i == 0) {
                table[j] = 0;
                j++;
            } else {
                i = table[i - 1];
            }
        }

        return table;
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
            throw new IllegalArgumentException("The patter is null or empty.");
        }
        if (text == null) {
            throw new IllegalArgumentException("The text inserted is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator inserted is null.");
        }
        List<Integer> list = new ArrayList<>();

        if (pattern.length() > text.length()) {
            return list;
        }


        int[] table = buildFailureTable(pattern, comparator);

        int j = 0;
        int k = 0;

        while (k < text.length()) {
            if (j < pattern.length() && comparator.compare(pattern.charAt(j), text.charAt(k)) == 0) {
                if (j == pattern.length() - 1) {
                    list.add(k - pattern.length() + 1);
                }
                j++;
                if (j == pattern.length()) {
                    j = table[j - 1];
                }
                k++;
            } else if (j == 0) {
                k++;
                if (k > text.length() - pattern.length()) {
                    return list;
                }
            } else {
                j = table[j - 1];
                if (k > text.length() - pattern.length() + j) {
                    return list;
                }
            }
        }

        return list;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * <p>
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x, where x is a particular
     * character in the pattern.
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
     * to the index at which they last occurred in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern inserted is null.");
        }
        int m = pattern.length();
        Map<Character, Integer> last = new HashMap<>();

        if (m == 0) {
            return last;
        }

        for (int i = 0; i < m; i++) {
            last.put(pattern.charAt(i), i);
        }

        return last;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
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
            throw new IllegalArgumentException("The pattern inserted is null or empty.");
        }
        if (text == null) {
            throw new IllegalArgumentException("The text in parameter is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator in parameter is null.");
        }

        List<Integer> list = new ArrayList<>();
        Map<Character, Integer> table = buildLastTable(pattern);

        if (pattern.length() > text.length()) {
            return list;
        }

        int i = 0;
        int index;
        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
            if (j < 0) {
                list.add(i);
                i++;
            } else {
                index = table.getOrDefault(text.charAt(i + j), -1);
                if (index < j) {
                    i += j - index;
                } else {
                    i++;
                }
            }
        }

        return list;
    }

    /*
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     * <p>
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     * <p>
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     * <p>
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character.
     * The BASE to use been provided for you in the final variable on line 127.
     * <p>
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to find BASE^(m - 1).
     * <p>
     * For example: Hashing "bunn" as a substring of "bunny" with base 113 hash
     * = b * 113 ^ 3 + u * 113 ^ 2 + n * 113 ^ 1 + n * 113 ^ 0 = 98 * 113 ^ 3 +
     * 117 * 113 ^ 2 + 110 * 113 ^ 1 + 110 * 113 ^ 0 = 142910419
     * <p>
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     * <p>
     * remove the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar.
     * <p>
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y =
     * (142910419 - 98 * 113 ^ 3) * 113 + 121 = 170236090
     * <p>
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     * <p>
     * Do NOT use Math.pow() for this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null or an empty string.");
        }
        if (text == null) {
            throw new IllegalArgumentException("The text string inserted is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator inserted is null.");
        }

        List<Integer> list = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return list;
        }

        int pHash = pattern.charAt(pattern.length() - 1);
        int tHash = text.charAt(pattern.length() - 1);
        long power = BASE;

        for (int i = pattern.length() - 2; i >= 0; i--) {
            char cPattern = pattern.charAt(i);
            char cText = text.charAt(i);
            pHash += cPattern * power;
            tHash += cText * power;
            power *= BASE;
        }
        power /= BASE;

        if (pHash == tHash && check(pattern, text.subSequence(0, pattern.length()), comparator)) {
            list.add(0);
        }

        for (int i = 1; i < text.length() - pattern.length() + 1; i++) {
            tHash = (int) ((tHash - (text.charAt(i - 1) * power)) * BASE
                                + text.charAt(i + pattern.length() - 1));
            if (pHash == tHash && check(pattern, text.subSequence(i, i + pattern.length()), comparator)) {
                list.add(i);
            }
        }

        return list;
    }

    /**
     * Helper method for rabinKarp method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return true if pattern match, false if pattern does not match
     */
    private static boolean check(CharSequence pattern, CharSequence text, CharacterComparator comparator) {
        for (int i = 0; i < pattern.length(); i++) {
            if (comparator.compare(pattern.charAt(i), text.charAt(i)) != 0) {
                return false;
            }
        }

        return true;
    }


}
