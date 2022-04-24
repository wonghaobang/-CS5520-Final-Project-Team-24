package edu.neu.madcourse.memoryup.LevelSelectorScreen;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {
    private int numberOfPairsOfCards;
    private int numberOfCategories;
    private int numberOfItemsPerArray;
    private ArrayList<ArrayList<Integer>> randomCards = new ArrayList<ArrayList<Integer>>();

    public RandomNumberGenerator(int numberOfPairsOfCards, int numberOfCategories, int numberOfItemsPerArray){
        this.numberOfPairsOfCards = numberOfPairsOfCards;
        this.numberOfCategories= numberOfCategories;
        this.numberOfItemsPerArray = numberOfItemsPerArray;
    }

    public ArrayList<ArrayList<Integer>> generateCards(){
        int min = 0;
        int max;
        int randomNum;
        int counter = 0;

        /*Iterate for number of cards*/
        for (int i = 0; i < this.numberOfPairsOfCards; i++){
            max = this.numberOfCategories;
            ArrayList<Integer> matchCards = new ArrayList<Integer>();

            /*Choose two random categories*/
            for (int j = 0; j < 2; j++) {
                randomNum = ThreadLocalRandom.current().nextInt(min, max);
                matchCards.add(randomNum);
            }

            /*Choose one random Item*/
            max = this.numberOfItemsPerArray;
            if (counter >= numberOfItemsPerArray) {
                counter = 0;
            }
            matchCards.add(counter);
            randomCards.add(matchCards);
            counter += 1;
        }

        return randomCards;
    }
}
