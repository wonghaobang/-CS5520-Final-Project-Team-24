package edu.neu.madcourse.memoryup;


public class UserData {
    public int maxFruit;
    public int maxAnimal;
    public int maxPlanet;

    // required for firebase getValue
    public UserData() { }

    public UserData(int maxFruit, int maxAnimal, int maxPlanet) {
        this.maxFruit = maxFruit;
        this.maxAnimal = maxAnimal;
        this.maxPlanet = maxPlanet;
    }
}
