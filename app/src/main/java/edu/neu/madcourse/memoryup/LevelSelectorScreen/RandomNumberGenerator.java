package edu.neu.madcourse.memoryup.LevelSelectorScreen;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator  {
    private int numberOfCards;
    private int numberOfCategories;
    private int numberOfItemsPerArray;
    private ArrayList<ArrayList<Integer>> randomCards = new ArrayList<ArrayList<Integer>>();

    public RandomNumberGenerator(int numberOfCards, int numberOfCategories, int numberOfItemsPerArray){
        this.numberOfCards = numberOfCards;
        this.numberOfCategories= numberOfCategories;
        this.numberOfItemsPerArray = numberOfItemsPerArray;
    }

    public ArrayList<ArrayList<Integer>> generateCards(){
        int min = 0;
        int max;
        int randomNum;

        /*Iterate for number of cards*/
        for (int i = 0; i < this.numberOfCards; i++){
            max = this.numberOfCategories;
            ArrayList<Integer> matchCards = new ArrayList<Integer>();

            /*Choose two random categories*/
            for (int j = 0; j < 2; j++) {
                randomNum = ThreadLocalRandom.current().nextInt(min, max);
                matchCards.add(randomNum);
            }

            /*Choose one random Item*/
            max = this.numberOfItemsPerArray;
            randomNum = ThreadLocalRandom.current().nextInt(min, max );
            matchCards.add(randomNum);
            randomCards.add(matchCards);
        }

        return randomCards;
    }
}
