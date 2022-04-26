package edu.neu.madcourse.memoryup.LevelSelectorScreen;

import java.util.ArrayList;

import edu.neu.madcourse.memoryup.R;

public class LevelImageMap {
    private int maxFruitLevel;
    private int maxAnimalLevel;
    private int maxPlanetLevel;


    public LevelImageMap(int maxFruitLevel, int maxAnimalLevel, int maxPlanetLevel){
        this.maxFruitLevel = maxFruitLevel;
        this.maxAnimalLevel = maxAnimalLevel;
        this.maxPlanetLevel = maxPlanetLevel;
    }

    private ArrayList<Integer> getArray(int maxLevel){
        ArrayList<Integer> lst = new ArrayList<Integer>();
        switch (maxLevel) {
            case 0:
                lst.add(R.drawable.levelone);
                lst.add(R.drawable.lock);
                lst.add(R.drawable.lock);
                lst.add(R.drawable.lock);
                return lst;
            case 1:
                lst.add(R.drawable.levelone);
                lst.add(R.drawable.leveltwo);
                lst.add(R.drawable.lock);
                lst.add(R.drawable.lock);
                return lst;
            case 2:
                lst.add(R.drawable.levelone);
                lst.add(R.drawable.leveltwo);
                lst.add(R.drawable.levelthree);
                lst.add(R.drawable.lock);
                return lst;
            default:
                lst.add(R.drawable.levelone);
                lst.add(R.drawable.leveltwo);
                lst.add(R.drawable.levelthree);
                lst.add(R.drawable.levelfour);
                return lst;
        }
    }

    public ArrayList<Integer> getFruitArray(){
        return getArray(this.maxFruitLevel);
    }

    public ArrayList<Integer> getAnimalArray(){
        return getArray(this.maxAnimalLevel);
    }

    public ArrayList<Integer> getPlanetArray(){
        return getArray(this.maxPlanetLevel);
    }

}
