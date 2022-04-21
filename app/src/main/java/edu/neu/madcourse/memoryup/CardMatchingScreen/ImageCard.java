package edu.neu.madcourse.memoryup.CardMatchingScreen;

import android.widget.ImageView;

public class ImageCard extends Card<Integer, ImageView> {

    public ImageCard(int backOfCard, Integer frontOfCard, ImageView imageView, int matchingId) {
        super(backOfCard, frontOfCard, imageView, matchingId);
    }

    @Override
    public void faceDown() {
        cardView.setImageResource(backOfCard);
    }

    @Override
    public void faceUp() {
        cardView.setImageResource(frontOfCard);
    }
}
