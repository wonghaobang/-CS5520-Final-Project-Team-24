package edu.neu.madcourse.memoryup.CardMatchingScreen;

import android.view.View;
import android.widget.ImageView;

import edu.neu.madcourse.memoryup.R;

public class ImageCard extends Card<Integer> {

    public ImageCard(int backOfCard, Integer frontOfCard, int matchingId) {
        super(backOfCard, frontOfCard, matchingId);
    }

    @Override
    public int getLayout() {
        return R.layout.item_image_card;
    }

    @Override
    public void faceDown(View item) {
        ImageView card = item.findViewById(R.id.card);
        card.setImageResource(backOfCard);
    }

    @Override
    public void faceUp(View item) {
        ImageView card = item.findViewById(R.id.card);
        card.setImageResource(frontOfCard);
    }
}
