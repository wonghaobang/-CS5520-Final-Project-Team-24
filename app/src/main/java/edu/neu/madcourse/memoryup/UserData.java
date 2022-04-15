package edu.neu.madcourse.memoryup;

import java.util.ArrayList;

public class UserData {
    public String name;
    public int score;

    // required for firebase getValue
    public UserData() { }

    public UserData(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
