package edu.neu.madcourse.memoryup.LevelThemes;

import java.util.HashMap;
import java.util.Map;

public class Levels {
    private static final Map<Integer, Integer> LEVEL_TO_TOTAL = new HashMap<Integer, Integer>() {{
        put(1, 12);
        put(2, 16);
        put(3, 20);
        put(4, 24);
    }};
    private static final int DEFAULT_TOTAL = 24;
    private static final Map<Integer, Integer> TOTAL_TO_LENGTH = new HashMap<Integer, Integer>() {{
        put(12, 4);
        put(16, 4);
        put(20, 5);
        put(24, 6);
    }};
    private static final int DEFAULT_LENGTH = 6;
    private static final Map<Integer, Integer> LEVEL_TO_TIME = new HashMap<Integer, Integer>() {{
        put(1, 45000);
        put(2, 50000);
        put(3, 55000);
        put(4, 60000);
    }};
    private static final int DEFAULT_TIME = 60000;

    public static Integer getTotal(int level) {
        return LEVEL_TO_TOTAL.getOrDefault(level, DEFAULT_TOTAL);
    }

    public static Integer getLength(int total) {
        return TOTAL_TO_LENGTH.getOrDefault(total, DEFAULT_LENGTH);
    }

    public static Integer getTime(int level) {
        return LEVEL_TO_TIME.getOrDefault(level, DEFAULT_TIME);
    }
}
