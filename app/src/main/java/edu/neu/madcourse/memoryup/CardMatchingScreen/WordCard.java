package edu.neu.madcourse.memoryup.CardMatchingScreen;

import android.view.View;
import android.widget.TextView;

import edu.neu.madcourse.memoryup.R;

public class WordCard extends Card<String> {

    public WordCard(int backOfCard, String frontOfCard, int matchingId) {
        super(backOfCard, frontOfCard, matchingId);
    }

    @Override
    public int getLayout() { return R.layout.item_word_card; }

    @Override
    public void faceDown(View item) {
        TextView card = item.findViewById(R.id.card);
        card.setBackgroundResource(backOfCard);
        card.setText("");
    }

    @Override
    public void faceUp(View item) {
        TextView card = item.findViewById(R.id.card);
        card.setText(frontOfCard);
        card.setBackgroundResource(0);
    }
}
