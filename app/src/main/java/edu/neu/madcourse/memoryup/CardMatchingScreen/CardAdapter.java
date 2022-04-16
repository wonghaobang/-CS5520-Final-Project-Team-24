package edu.neu.madcourse.memoryup.CardMatchingScreen;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends ArrayAdapter<Card<?>> {
    private static final int CARD_REMOVAL_DELAY = 1000;
    private static final int MAX_FACE_UP_CARDS = 2;
    private final List<Card<?>> faceUpCards = new ArrayList<>();
    private final List<View> faceUpViews = new ArrayList<>();

    public CardAdapter(@NonNull Context context, List<Card<?>> cards) {
        super(context, 0, cards);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Card<?> card = getItem(position);

        View item = convertView;
        if (item == null) {
            item = LayoutInflater.from(getContext()).inflate(card.getLayout(), parent, false);
        }

        card.faceDown(item);

        View finalItem = item;
        item.setOnClickListener(view -> {
            if (faceUpCards.size() == MAX_FACE_UP_CARDS) {
                flipFaceUpCards();
                clearFaceUpCards();
            }
            if (!faceUpCards.contains(card)) {
                card.faceUp(finalItem);
                faceUpCards.add(card);
                faceUpViews.add(finalItem);
                if (faceUpCards.size() == MAX_FACE_UP_CARDS) {
                    if (areFaceUpCardsMatching()) {
                        disableClicksOnMatchedCards();
                        List<View> matchedViews = new ArrayList<>(faceUpViews);
                        new Handler().postDelayed(() ->
                                removeMatchedCards(matchedViews),
                                CARD_REMOVAL_DELAY);
                        clearFaceUpCards();
                    }
                }
            }
        });
        return item;
    }

    private void clearFaceUpCards() {
        faceUpCards.clear();
        faceUpViews.clear();
    }

    private void flipFaceUpCards() {
        for (int i = 0; i < faceUpCards.size(); i++) {
            faceUpCards.get(i).faceDown(faceUpViews.get(i));
        }
    }

    private boolean areFaceUpCardsMatching() {
        int id = faceUpCards.get(0).getMatchingId();
        for (int i = 1; i < faceUpCards.size(); i++) {
            if (faceUpCards.get(i).getMatchingId() != id) {
                return false;
            }
        }
        return true;
    }

    private void disableClicksOnMatchedCards() {
        for (View faceUpView : faceUpViews) {
            faceUpView.setEnabled(false);
        }
    }

    private void removeMatchedCards(List<View> matchedViews) {
        for (View matchedView : matchedViews) {
            matchedView.setVisibility(View.GONE);
        }
    }
}
