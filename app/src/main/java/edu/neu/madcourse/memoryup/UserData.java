package edu.neu.madcourse.memoryup;


public class UserData {
    public String country;
    public int maxFruit;
    public int maxAnimal;
    public int maxPlanet;

    // required for firebase getValue
    public UserData() { }

    public UserData(String country, int maxFruit, int maxAnimal, int maxPlanet) {
        this.country = country;
        this.maxFruit = maxFruit;
        this.maxAnimal = maxAnimal;
        this.maxPlanet = maxPlanet;
    }
}
