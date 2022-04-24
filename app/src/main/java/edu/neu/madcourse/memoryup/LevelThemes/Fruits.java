package edu.neu.madcourse.memoryup.LevelThemes;

import java.util.ArrayList;

public class Fruits implements Theme {
    private int numberOfCategories = 3;
    private int numberOfItemsPerArray = 10;

    private ArrayList<Object> fruitsImagesIdArray = new ArrayList<Object>();
    private ArrayList<Object>  fruitsNamesArray = new ArrayList<Object>();
    private ArrayList<Object> fruitsColorsArray = new ArrayList<Object>();

    public Fruits(){
        /*Initialize fruits imagesId array*/
        for (int i = 0; i < numberOfItemsPerArray; i++){
            fruitsImagesIdArray.add(i);
        }

        /*Initialize fruits name array*/
        fruitsNamesArray.add("apple");
        fruitsNamesArray.add("banana");
        fruitsNamesArray.add("blackcurrant");
        fruitsNamesArray.add("blueberry");
        fruitsNamesArray.add("grapes");
        fruitsNamesArray.add("orange");
        fruitsNamesArray.add("peach");
        fruitsNamesArray.add("pear");
        fruitsNamesArray.add("pineapple");
        fruitsNamesArray.add("strawberry");

        /*Initialize fruits color array*/
        fruitsColorsArray.add("red");
        fruitsColorsArray.add("yellow");
        fruitsColorsArray.add("black");
        fruitsColorsArray.add("blue");
        fruitsColorsArray.add("purple");
        fruitsColorsArray.add("orange");
        fruitsColorsArray.add("pink");
        fruitsColorsArray.add("green");
        fruitsColorsArray.add("yellow");
        fruitsColorsArray.add("red");
    }

    public int getNumberOfCategories(){return numberOfCategories;};

    public int getNumberOfItemsPerArray(){return numberOfItemsPerArray;};

    public Object getItem(int categoryIndex, int itemIndex) {
        switch (categoryIndex) {
            case 0:
                return FruitsImageMap.getImage(itemIndex);
            case 1:
                return fruitsNamesArray.get(itemIndex);
            default:
                return fruitsColorsArray.get(itemIndex);
        }
    }

}
