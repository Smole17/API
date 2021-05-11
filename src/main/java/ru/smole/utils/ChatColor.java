package ru.smole.utils;

import java.util.Locale;

public class ChatColor {

    public static String getUnformatted(String to) {
        final char[] chars = to.toCharArray();
        String out = "";
        boolean rmn = false;
        for (final char c : chars) {
            boolean add = true;
            if (rmn) {
                add = false;
                rmn = false;
            }
            if (c == '§') {
                rmn = true;
                add = false;
            }
            if (add) {
                out += c;
            }
        }
        return out;
    }

    public static int getCodesCount(String to) {
        final char[] chars = to.toCharArray();
        int count = 0;

        for (final char c : chars) {
            if (c == '§') {
                count++;
            }
        }

        return count;
    }

    public static int getCodesCount(String to, String code) {
        final char[] chars = to.toCharArray();

        char lc = code.toLowerCase().toCharArray()[0],
                uc = code.toUpperCase().toCharArray()[0];
        int count = 0;
        boolean is = false;

        for (final char c : chars) {
            if (is) {
                if (c == lc || c == uc)
                    count++;
                is = false;
            }
            if (c == '§') {
                is = true;
                count++;
            }
        }
        return count;
    }

}
