package edu.neu.madcourse.memoryup.LevelThemes;

import java.util.ArrayList;

public class Fruits {
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

    public ArrayList<Object> getCategoryArray(int index){
        switch (index) {
            case 0:
                return fruitsImagesIdArray;
            case 1:
                return fruitsNamesArray;
            default:
                return fruitsColorsArray;
        }
    }
}
