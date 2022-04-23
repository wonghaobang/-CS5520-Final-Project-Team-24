package edu.neu.madcourse.memoryup.LevelThemes;

import java.util.ArrayList;

public class Planets implements Theme {
    private int numberOfCategories = 4;
    private int numberOfItemsPerArray = 9;

    private ArrayList<Object> planetsImagesIdArray = new ArrayList<Object>();
    private ArrayList<Object>  planetsNamesArray = new ArrayList<Object>();
    private ArrayList<Object> planetsSizesArray = new ArrayList<Object>();
    private ArrayList<Object> planetsDistanceFromSunArray = new ArrayList<Object>();

    public Planets(){
        /*Initialize planets imagesId array*/
        for (int i = 0; i < numberOfItemsPerArray; i++){
            planetsImagesIdArray.add(i);
        }

        /*Initialize planets name array*/
        planetsNamesArray.add("pluto");
        planetsNamesArray.add("mars");
        planetsNamesArray.add("jupiter");
        planetsNamesArray.add("earth");
        planetsNamesArray.add("mercury");
        planetsNamesArray.add("neptune");
        planetsNamesArray.add("saturn");
        planetsNamesArray.add("uranus");
        planetsNamesArray.add("venus");

        /*Initialize planets size array*/
        planetsSizesArray.add("1188 km");
        planetsSizesArray.add("3390 km");
        planetsSizesArray.add("69911 km");
        planetsSizesArray.add("6371 km");
        planetsSizesArray.add("2440 km");
        planetsSizesArray.add("24622 km");
        planetsSizesArray.add("58232 km");
        planetsSizesArray.add("25362 km");
        planetsSizesArray.add("6052 km");

        /*Initialize planets distance from sun array*/
        planetsDistanceFromSunArray.add("5913 million km");
        planetsDistanceFromSunArray.add("227.9 million km");
        planetsDistanceFromSunArray.add("778.3 million km");
        planetsDistanceFromSunArray.add("149.6 million km");
        planetsDistanceFromSunArray.add("57.9 million km");
        planetsDistanceFromSunArray.add("4497.1 million km");
        planetsDistanceFromSunArray.add("1427 million km");
        planetsDistanceFromSunArray.add("2871 million km");
        planetsDistanceFromSunArray.add("108.2 million km");
    }

    public int getNumberOfCategories(){return numberOfCategories;};

    public int getNumberOfItemsPerArray(){return numberOfItemsPerArray;};

    public Object getItem(int categoryIndex, int itemIndex) {
        switch (categoryIndex) {
            case 0:
                return PlanetsImageMap.getImage(itemIndex);
            case 1:
                return planetsNamesArray.get(itemIndex);
            case 2:
                return planetsSizesArray.get(itemIndex);
            default:
                return planetsDistanceFromSunArray.get(itemIndex);
        }
    }
}
