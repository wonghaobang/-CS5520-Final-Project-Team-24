package edu.neu.madcourse.memoryup.CardMatchingScreen;

import android.view.View;

public abstract class Card<T> {
    protected final int backOfCard;
    protected final T frontOfCard;
    protected int matchingId;

    public Card(int backOfCard, T frontOfCard, int matchingId) {
        this.backOfCard = backOfCard;
        this.frontOfCard = frontOfCard;
        this.matchingId = matchingId;
    }

    public int getMatchingId() {
        return matchingId;
    }

    abstract int getLayout();

    abstract void faceDown(View item);

    abstract void faceUp(View item);
}
