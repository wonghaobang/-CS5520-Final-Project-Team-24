package edu.neu.madcourse.memoryup.LevelThemes;

public interface Theme {

    static Theme getTheme(String name) {
        if (name.equals("Animals")) return new Animals();
        if (name.equals("Planets")) return new Planets();
        return new Fruits();
    }

    Object getItem(int categoryIndex, int itemIndex);
}
