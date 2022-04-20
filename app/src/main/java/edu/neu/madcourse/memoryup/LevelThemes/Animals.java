package edu.neu.madcourse.memoryup.LevelThemes;

import java.util.ArrayList;

public class Animals {
    private int numberOfCategories = 3;
    private int numberOfItemsPerArray = 10;

    private ArrayList<Object> animalsImagesIdArray = new ArrayList<Object>();
    private ArrayList<Object>  animalsNamesArray = new ArrayList<Object>();
    private ArrayList<Object> animalsSpeciesNameArray = new ArrayList<Object>();

    public Animals(){
        /*Initialize fruits imagesId array*/
        for (int i = 0; i < numberOfItemsPerArray; i++){
            animalsImagesIdArray.add(i);
        }

        /*Initialize fruits name array*/
        animalsNamesArray.add("sheep");
        animalsNamesArray.add("camel");
        animalsNamesArray.add("snail");
        animalsNamesArray.add("walrus");
        animalsNamesArray.add("snake");
        animalsNamesArray.add("crab");
        animalsNamesArray.add("giraffe");
        animalsNamesArray.add("beaver");
        animalsNamesArray.add("shark");
        animalsNamesArray.add("pig");

        /*Initialize fruits color array*/
        animalsSpeciesNameArray.add("ovis aries");
        animalsSpeciesNameArray.add("camelus");
        animalsSpeciesNameArray.add("gastropoda");
        animalsSpeciesNameArray.add("odobenus rosmarus");
        animalsSpeciesNameArray.add("serpentes");
        animalsSpeciesNameArray.add("brachyura");
        animalsSpeciesNameArray.add("giraffa");
        animalsSpeciesNameArray.add("castor");
        animalsSpeciesNameArray.add("selachimorpha");
        animalsSpeciesNameArray.add("sus scrofa domesticus");
    }

    public int getNumberOfCategories(){return numberOfCategories;};

    public int getNumberOfItemsPerArray(){return numberOfItemsPerArray;};

    public ArrayList<Object> getCategoryArray(int index){
        switch (index) {
            case 0:
                return animalsImagesIdArray;
            case 1:
                return animalsNamesArray;
            default:
                return animalsSpeciesNameArray;
        }
    }
}
