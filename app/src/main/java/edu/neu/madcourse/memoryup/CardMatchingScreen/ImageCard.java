package edu.neu.madcourse.memoryup.CardMatchingScreen;

import android.widget.ImageView;

public class ImageCard extends Card<Integer, ImageView> {

    public ImageCard(Integer frontOfCard, ImageView imageView, int matchingId) {
        super(frontOfCard, imageView, matchingId);
    }

    @Override
    public void faceDown() {
        cardView.setImageResource(backOfCard);
    }

    @Override
    public void faceUp() {
        cardView.setImageResource(frontOfCard);
        cardView.setBackgroundResource(frontOfCardBackground);
    }
}
