package edu.neu.madcourse.memoryup.LeaderboardScreen;


public class LeaderItem implements Comparable<LeaderItem> {
    private String name;
    private int score;
    private int rank;

    public LeaderItem(String name, int score, int rank) {
        this.name = name;
        this.score = score;
        this.rank = rank;
    }


    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getRank() {
        return rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }


    @Override
    public int compareTo(LeaderItem leaderItem) {
        return leaderItem.score - this.score;
//        return this.score - leaderItem.score;
    }
}