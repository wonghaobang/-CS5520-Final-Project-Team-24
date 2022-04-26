package edu.neu.madcourse.memoryup.CardMatchingScreen;

import edu.neu.madcourse.memoryup.R;

public abstract class Card<T1, T2> {
    protected final int backOfCard;
    protected final int frontOfCardBackground;
    protected final T1 frontOfCard;
    protected final T2 cardView;
    protected int matchingId;

    protected Card(T1 frontOfCard, T2 cardView, int matchingId) {
        this.backOfCard = R.drawable.card_back;
        this.frontOfCardBackground = R.drawable.card_front_background;
        this.frontOfCard = frontOfCard;
        this.cardView = cardView;
        this.matchingId = matchingId;
    }

    public T2 getCardView() {
        return cardView;
    }

    public int getMatchingId() {
        return matchingId;
    }

    public abstract void faceDown();

    public abstract void faceUp();
}
