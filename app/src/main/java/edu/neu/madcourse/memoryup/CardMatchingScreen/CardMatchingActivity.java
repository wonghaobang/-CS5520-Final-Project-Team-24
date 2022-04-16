package edu.neu.madcourse.memoryup.CardMatchingScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.neu.madcourse.memoryup.R;

public class CardMatchingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_matching);

        setUpHeader();
        setUpCardGrid();
    }

    private void setUpHeader() {
        TextView level = findViewById(R.id.level);
        level.setText(getString(R.string.level, "Fruits", 1));
        TextView score = findViewById(R.id.score);
        score.setText(getString(R.string.score, 0));
        TextView tvTime = findViewById(R.id.time);
        tvTime.setText(getString(R.string.time, 2, "00"));
        TextView tvLives = findViewById(R.id.lives);
        tvLives.setText(getString(R.string.lives, 3));
    }

    private void setUpCardGrid() {
        List<Card<?>> cards = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            cards.add(new ImageCard(R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground, i));
            cards.add(new WordCard(R.drawable.ic_launcher_background, "fruit", i));
        }
        Collections.shuffle(cards);
        CardAdapter cardAdapter = new CardAdapter(this, cards);

        GridView cardGrid = findViewById(R.id.cardGrid);
        cardGrid.setNumColumns(4);
        cardGrid.setAdapter(cardAdapter);
    }
}
