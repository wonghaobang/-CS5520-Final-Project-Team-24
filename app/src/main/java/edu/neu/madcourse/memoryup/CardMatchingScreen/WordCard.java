package edu.neu.madcourse.memoryup.CardMatchingScreen;

import android.widget.TextView;

public class WordCard extends Card<String, TextView> {

    public WordCard(int backOfCard, String frontOfCard, TextView textView, int matchingId) {
        super(backOfCard, frontOfCard, textView, matchingId);
    }

    @Override
    public void faceDown() {
        cardView.setBackgroundResource(backOfCard);
        cardView.setText("");
    }

    @Override
    public void faceUp() {
        cardView.setText(frontOfCard);
        cardView.setBackgroundResource(0);
    }
}
