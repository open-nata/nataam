package com.nata.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 15:31
 */
public class ReUtil {
    public static ArrayList<String> matchString(Pattern pattern, String str) {
        ArrayList<String> result = new ArrayList<String>();
        Matcher mc = pattern.matcher(str);

        while (mc.find()) {
            result.add(mc.group());
        }

        return result;
    }

    public static ArrayList<Integer> matchInteger(Pattern pattern, String str) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Matcher mc = pattern.matcher(str);

        while (mc.find()) {
            result.add(new Integer(mc.group().trim()));
        }

        return result;
    }
}
