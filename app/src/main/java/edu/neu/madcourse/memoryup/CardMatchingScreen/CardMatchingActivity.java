package edu.neu.madcourse.memoryup.CardMatchingScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.neu.madcourse.memoryup.LevelSelectorScreen.LevelSelectorActivity;
import edu.neu.madcourse.memoryup.LevelThemes.Levels;
import edu.neu.madcourse.memoryup.LevelThemes.Theme;
import edu.neu.madcourse.memoryup.MainActivity;
import edu.neu.madcourse.memoryup.R;

public class CardMatchingActivity extends AppCompatActivity {
    private static final int MILLISECONDS_IN_SECOND = 1000;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int COUNTDOWN_INTERVAL_MILLISECONDS = 1000;
    private static final int DOUBLE_DIGITS = 10;
    private static final int CARD_SIZE = 225;
    private static final int CARD_REMOVAL_DELAY = 1000;
    private static final int MAX_FACE_UP_CARDS = 2;
    private static final int MATCH_POINTS = 100;
    private boolean started = false;
    private boolean paused = false;
    private int cardsLeft;
    private final List<Card<?, ?>> faceUpCards = new ArrayList<>();
    private CountDownTimer countDownTimer = null;
    private long millisecondsLeft = 0;
    private int points = 0;
    private int maxPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_matching);

        // Bundle Theme and CardsArray
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String themeName = extras.getString("THEME");
        ArrayList<ArrayList<Integer>> cardIndexes = convertCardsToArray(extras.getString("CARDS"));
        Log.i("Cards", String.valueOf(cardIndexes.get(0).get(0)));

        setUpHeader(themeName, 1);
        setUpCardGrid(themeName, cardIndexes);
    }

    // Converts CardsArray from String to ArrayList<ArrayList<Integer>>
    private ArrayList<ArrayList<Integer>> convertCardsToArray(String cards){
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        String[] splitCards = cards.split("],");
        for (int i = 0; i < splitCards.length; i++){
            ArrayList<Integer> matchedCards = new ArrayList<Integer>();
            String[] match = (splitCards[i].replace("[", "").replace("]", "")).split(",");

            for (int j = 0; j < match.length; j++){
                matchedCards.add(Integer.valueOf(match[j].replaceAll("\\s+","")));
            }

            result.add(matchedCards);
        }

        return result;
    }

    @Override
    protected void onStart() {
        resumeCountDownTimer();
        super.onStart();
    }

    @Override
    protected void onResume() {
        resumeCountDownTimer();
        super.onResume();
    }

    @Override
    protected void onPause() {
        pauseCountDownTimer();
        super.onPause();
    }

    @Override
    protected void onStop() {
        pauseCountDownTimer();
        super.onStop();
    }

    private void setUpHeader(String theme, Integer level) {
        TextView tvTitle = findViewById(R.id.title);
        tvTitle.setText(getString(R.string.title, theme, level));
        millisecondsLeft = Levels.getTime(level);
        buildCountDownTimer(millisecondsLeft);
        showPoints();
    }

    private void setUpCardGrid(String themeName, List<ArrayList<Integer>> cardIndexes) {
        Theme theme = Theme.getTheme(themeName);
        List<View> views = new ArrayList<>();
        for (int i = 0; i < cardIndexes.size(); i++) {
            ArrayList<Integer> indexSet = cardIndexes.get(i);
            List<Object> matchingCards = new ArrayList<>();
            matchingCards.add(theme.getItem(indexSet.get(0), indexSet.get(2)));
            matchingCards.add(theme.getItem(indexSet.get(1), indexSet.get(2)));
            for (Object matchingCard : matchingCards) {
                if (matchingCard.getClass() == Integer.class) {
                    Integer frontOfCard = (Integer) matchingCard;
                    ImageView imageView = new ImageView(this);
                    ImageCard imageCard = new ImageCard(frontOfCard, imageView, i);
                    imageCard.faceDown();
                    imageView.setOnClickListener(view -> onCardClick(imageCard));
                    views.add(imageView);
                } else if (matchingCard.getClass() == String.class) {
                    String frontOfCard = matchingCard.toString();
                    TextView textView = new TextView(this);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(Color.BLACK);
                    WordCard wordCard = new WordCard(frontOfCard, textView, i);
                    wordCard.faceDown();
                    textView.setOnClickListener(view -> onCardClick(wordCard));
                    views.add(textView);
                }
            }
        }

        cardsLeft = views.size();
        maxPoints = cardsLeft / MAX_FACE_UP_CARDS * MATCH_POINTS;
        int length = Levels.getLength(cardsLeft);
        int width = cardsLeft / length;

        GridLayout cardGrid = findViewById(R.id.cardGrid);
        cardGrid.setRowCount(length);
        cardGrid.setColumnCount(width);

        Collections.shuffle(views);
        for (View view : views) {
            cardGrid.addView(view, CARD_SIZE, CARD_SIZE);
        }
    }

    private void showTime(long milliseconds) {
        TextView tvTime = findViewById(R.id.time);
        long minutes = milliseconds / MILLISECONDS_IN_SECOND / SECONDS_IN_MINUTE;
        long seconds = milliseconds / MILLISECONDS_IN_SECOND % SECONDS_IN_MINUTE;
        tvTime.setText(getString(R.string.time,
                minutes,
                seconds < DOUBLE_DIGITS ? "0" + seconds : seconds));
    }

    private void buildCountDownTimer(long milliseconds) {
        showTime(milliseconds);

        countDownTimer = new CountDownTimer(milliseconds, COUNTDOWN_INTERVAL_MILLISECONDS) {
            public void onTick(long millisUntilFinished) {
                millisecondsLeft = millisUntilFinished;
                showTime(millisUntilFinished);
            }

            public void onFinish() {
                endGame();
            }
        };
    }

    private void resumeCountDownTimer() {
        if (paused) {
            buildCountDownTimer(millisecondsLeft);
            if (started) {
                countDownTimer.start();
            }
        }
        paused = false;
    }

    private void pauseCountDownTimer() {
        paused = true;
        countDownTimer.cancel();
    }

    private void onCardClick(Card<?, ?> card) {
        if (!started) {
            started = true;
            countDownTimer.start();
        }
        if (faceUpCards.size() == MAX_FACE_UP_CARDS) {
            flipFaceUpCards();
            faceUpCards.clear();
        }
        if (!faceUpCards.contains(card)) {
            card.faceUp();
            faceUpCards.add(card);
            if (faceUpCards.size() == MAX_FACE_UP_CARDS) {
                if (areFaceUpCardsMatching()) {
                    disableClicksOnMatchedCards();
                    updatePoints();
                    cardsLeft -= faceUpCards.size();
                    if (cardsLeft == 0) {
                        endGame();
                    }
                    List<Card<?, ?>> matchedCards = new ArrayList<>(faceUpCards);
                    new Handler().postDelayed(() ->
                                    removeMatchedCards(matchedCards),
                            CARD_REMOVAL_DELAY);
                    faceUpCards.clear();
                }
            }
        }
    }

    private void flipFaceUpCards() {
        for (Card<?, ?> faceUpCard : faceUpCards) {
            faceUpCard.faceDown();
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
        for (Card<?, ?> faceUpCard : faceUpCards) {
            View view = (View) faceUpCard.getCardView();
            view.setEnabled(false);
        }
    }

    private void removeMatchedCards(List<Card<?, ?>> matchedCards) {
        for (Card<?, ?> matchedCard : matchedCards) {
            View view = (View) matchedCard.getCardView();
            view.setVisibility(View.INVISIBLE);
        }
    }

    private void updatePoints() {
        points += MATCH_POINTS;
        showPoints();
    }

    private void showPoints() {
        TextView tvPoints = findViewById(R.id.points);
        tvPoints.setText(String.valueOf(points));
    }

    private void endGame() {
        countDownTimer.cancel();
        showResults();
    }

    private void showResults() {
        Dialog results = new Dialog(this);
        results.setContentView(R.layout.dialog_results);
        results.setCancelable(false);

        TextView tvTitle = results.findViewById(R.id.title);
        if (cardsLeft == 0) {
            tvTitle.setText(R.string.results_title_victory);
        } else {
            tvTitle.setText(R.string.results_title_time_up);
        }

        TextView tvPoints = results.findViewById(R.id.points);
        tvPoints.setText(getString(R.string.results_points, points, maxPoints));

        ImageView mainMenuIcon = results.findViewById(R.id.mainMenuIcon);
        mainMenuIcon.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        ImageView levelsIcon = results.findViewById(R.id.levelsIcon);
        levelsIcon.setOnClickListener(view -> {
            Intent intent = new Intent(this, LevelSelectorActivity.class);
            startActivity(intent);
        });

        results.show();
    }
}
