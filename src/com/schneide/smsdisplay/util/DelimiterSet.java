/**
 * Created: 21.06.2004
 */
package com.schneide.smsdisplay.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dali
 * @since 21.06.2004
 */
public class DelimiterSet {

    public static final DelimiterSet WHITESPACE = new DelimiterSet(" \t\n\r\f"); //$NON-NLS-1$
    public static final DelimiterSet PUNCTUATION = new DelimiterSet(".,:;-_!?"); //$NON-NLS-1$

    private final char[] sortedDelimiters;
    private boolean isEnabled;
    private boolean isHidden;

    public DelimiterSet(String delimiters) {
        this(DelimiterSet.extractChars(delimiters));
    }

    public DelimiterSet(char... delimiters) {
        super();
        this.sortedDelimiters = DelimiterSet.sortCharsAscending(delimiters);
        this.isEnabled = true;
        this.isHidden = true;
    }

    public DelimiterSet plus(DelimiterSet other) {
        List<Character> allChars = new ArrayList<Character>();
        for (char character : this.sortedDelimiters) {
            allChars.add(Character.valueOf(character));
        }
        for (char character : other.sortedDelimiters) {
            allChars.add(Character.valueOf(character));
        }
        char[] newChars = new char[allChars.size()];
        for (int i = 0; i < newChars.length; i++) {
            newChars[i] = allChars.get(i).charValue();
        }
        return new DelimiterSet(newChars);
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean newState) {
        this.isEnabled = newState;
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public void setHidden(boolean newState) {
        this.isHidden = newState;
    }

    private char getReferenceDelimiter() {
        return this.sortedDelimiters[this.sortedDelimiters.length - 1];
    }

    public boolean isDelimiter(char character) {
        if (character <= getReferenceDelimiter()) {
            for (char element : this.sortedDelimiters) {
                if (element == character) {
                    return true;
                }
            }
        }
        return false;
    }

    private static char[] extractChars(String string) {
        char[] result = new char[string.length()];
        string.getChars(0, string.length(), result, 0);
        return result;
    }

    private static char[] sortCharsAscending(char[] chars) {
        Arrays.sort(chars);
        return chars;
    }
}
