package edu.neu.madcourse.memoryup;

import java.util.HashMap;
import java.util.Map;

public class Flag {


    static Map<String, String> flagMap  = new HashMap<String, String>() {{
        put("United States", "\uD83C\uDDFA\uD83C\uDDF2");
        put("United Kingdom", "\uD83C\uDDEC\uD83C\uDDE7");
        put("India", "\uD83C\uDDEE\uD83C\uDDF3");
        put("France", "\uD83C\uDDEB\uD83C\uDDF7");
        put("Kenya", "\uD83C\uDDF0\uD83C\uDDEA");
        put("Australia", "\uD83C\uDDE6\uD83C\uDDFA");
        put("Russia", "\uD83C\uDDF7\uD83C\uDDFA");
        put("Canada", "\uD83C\uDDE8\uD83C\uDDE6");
        put("Mexico", "\uD83C\uDDF2\uD83C\uDDFD");
        put("China", "\uD83C\uDDE8\uD83C\uDDF3");
        put("Japan", "\uD83C\uDDEF\uD83C\uDDF5");
        put("South Korea", "\uD83C\uDDF0\uD83C\uDDF7");
        put("Germany", "\uD83C\uDDE9\uD83C\uDDEA");
        put("Ireland", "\uD83C\uDDEE\uD83C\uDDEA");
        put("Brazil", "\uD83C\uDDE7\uD83C\uDDF7");
        put("Argentina", "\uD83C\uDDE6\uD83C\uDDF7");
        put("Zimbabwe", "\uD83C\uDDFF\uD83C\uDDFC");
        put("United Nations", "\uD83C\uDDFA\uD83C\uDDF3");
        put("None", "\uD83C\uDFF4");
    }};


    public static String getFlag(String country) {
        if (flagMap.get(country) == null) {
            return flagMap.get("None");
        } else {
            return flagMap.get(country);
        }
    }

}
