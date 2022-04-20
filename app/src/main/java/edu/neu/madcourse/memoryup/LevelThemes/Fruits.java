package edu.neu.madcourse.memoryup.LevelThemes;

import java.util.ArrayList;

public class Fruits {
    private int numberOfCategories = 3;
    private int numberOfItemsPerArray = 10;

    private int[] fruitsImagesIdArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private String[]  fruitsNamesArray = {"apple", "banana", "blackcurrant", "blueberry", "grapes", "orange", "peach", "pear", "pineapple", "strawberry"};
    private String[] fruitsColorsArray = {"red", "yellow", "black", "blue", "purple", "orange", "pink", "green", "yellow", "red"};

    public int getNumberOfCategories(){return numberOfCategories;};

    public int getNumberOfItemsPerArray(){return numberOfItemsPerArray;};

    public int getImageId(int index){
        return fruitsImagesIdArray[index];
    }

    public String getName(int index){
        return fruitsNamesArray[index];
    }

    public String getColor(int index){
        return fruitsColorsArray[index];
    }
}
