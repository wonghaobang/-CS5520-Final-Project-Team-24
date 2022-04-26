package edu.neu.madcourse.memoryup.CardMatchingScreen;

import android.widget.TextView;

public class WordCard extends Card<String, TextView> {

    public WordCard(String frontOfCard, TextView textView, int matchingId) {
        super(frontOfCard, textView, matchingId);
    }

    @Override
    public void faceDown() {
        cardView.setBackgroundResource(backOfCard);
        cardView.setText("");
    }

    @Override
    public void faceUp() {
        cardView.setBackgroundResource(frontOfCardBackground);
        cardView.setText(frontOfCard);
    }
}
