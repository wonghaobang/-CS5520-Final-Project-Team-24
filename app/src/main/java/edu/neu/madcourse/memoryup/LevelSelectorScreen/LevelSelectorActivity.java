package edu.neu.madcourse.memoryup.LevelSelectorScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.neu.madcourse.memoryup.CardMatchingScreen.CardMatchingActivity;
import edu.neu.madcourse.memoryup.LevelThemes.Levels;
import edu.neu.madcourse.memoryup.MainActivity;
import edu.neu.madcourse.memoryup.R;
import edu.neu.madcourse.memoryup.UserData;

public class LevelSelectorActivity extends AppCompatActivity implements LevelClickListener {
    private ViewPager2 viewPager2;
    private ArrayList<ViewPagerItem> viewPagerItemLst;
    private RecyclerView.LayoutManager rLayoutManger;
    private int page;

    private RandomNumberGenerator generate;
    private ArrayList<ArrayList<Integer>> lst;

    private String username;
    private boolean playAudio;
    //private LevelImageMap imageMap;

    private int levelOne = R.drawable.levelone;
    private int levelTwo = R.drawable.leveltwo;
    private int levelThree = R.drawable.levelthree;
    private int levelFour = R.drawable.levelfour;
    private int lock = R.drawable.lock;
    private int greyStar = R.drawable.greystar;

    private int maxFruitLevel;
    private int maxAnimalLevel;
    private int maxPlanetLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);


        // Get Username
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        username = extras.getString("Username");
        playAudio = extras.getBoolean("Audio");

        // Set Level Selector
        viewPager2 = findViewById(R.id.pager);
        viewPagerItemLst = new ArrayList<>();

        // Get Firebase user data
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users").child(username);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserData data = snapshot.getValue(UserData.class);
                    //Log.d("hello123", String.format("fruit: %d, animal: %d, planet: %d", data.maxFruit, data.maxAnimal, data.maxPlanet));

                    maxFruitLevel = data.maxFruit;
                    maxAnimalLevel = data.maxAnimal;
                    maxPlanetLevel = data.maxPlanet;

                    // clear current page list and reset
                    viewPagerItemLst.clear();

                    ViewPagerItem viewPagerItem;
                    if (maxFruitLevel == 0){
                        viewPagerItem = new ViewPagerItem("Fruits", greyStar, lock, lock, lock);
                    }
                    else if (maxFruitLevel == 1){
                        viewPagerItem = new ViewPagerItem("Fruits", levelOne, greyStar, lock, lock);
                    }
                    else if (maxFruitLevel == 2){
                        viewPagerItem = new ViewPagerItem("Fruits", levelOne, levelTwo, greyStar, lock);
                    }
                    else if (maxFruitLevel == 3) {
                        viewPagerItem = new ViewPagerItem("Fruits", levelOne, levelTwo, levelThree, greyStar);
                    }
                    else {
                        viewPagerItem = new ViewPagerItem("Fruits", levelOne, levelTwo, levelThree, levelFour);
                    }
                    viewPagerItemLst.add(viewPagerItem);

                    ViewPagerItem viewPagerItem2;
                    if (maxAnimalLevel == 0){
                        viewPagerItem2 = new ViewPagerItem("Animals", greyStar, lock, lock, lock);
                    }
                    else if (maxAnimalLevel == 1){
                        viewPagerItem2 = new ViewPagerItem("Animals", levelOne, greyStar, lock, lock);
                    }
                    else if (maxAnimalLevel == 2){
                        viewPagerItem2 = new ViewPagerItem("Animals", levelOne, levelTwo, greyStar, lock);
                    }
                    else if (maxAnimalLevel == 3){
                        viewPagerItem2 = new ViewPagerItem("Animals", levelOne, levelTwo, levelThree, greyStar);
                    }
                    else {
                        viewPagerItem2 = new ViewPagerItem("Animals", levelOne, levelTwo, levelThree, levelFour);
                    }
                    viewPagerItemLst.add(viewPagerItem2);

                    ViewPagerItem viewPagerItem3;
                    if (maxPlanetLevel == 0){
                        viewPagerItem3 = new ViewPagerItem("Planets", greyStar, lock, lock, lock);
                    }
                    else if (maxPlanetLevel == 1){
                        viewPagerItem3 = new ViewPagerItem("Planets", levelOne, greyStar, lock, lock);
                    }
                    else if (maxPlanetLevel == 2){
                        viewPagerItem3 = new ViewPagerItem("Planets", levelOne, levelTwo, greyStar, lock);
                    }
                    else if (maxPlanetLevel == 3){
                        viewPagerItem3 = new ViewPagerItem("Planets", levelOne, levelTwo, levelThree, greyStar);
                    }
                    else {
                        viewPagerItem3 = new ViewPagerItem("Planets", levelOne, levelTwo, levelThree, levelFour);
                    }

                    viewPagerItemLst.add(viewPagerItem3);

                    ViewPagerAdapter vpAdapter = new ViewPagerAdapter(viewPagerItemLst,  LevelSelectorActivity.this);
                    rLayoutManger = new LinearLayoutManager(LevelSelectorActivity.this);

                    viewPager2.setAdapter(vpAdapter);
                    viewPager2.setClipToPadding(false);
                    viewPager2.setClipChildren(false);
                    viewPager2.setOffscreenPageLimit(2);
                    viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
                    page = viewPager2.getCurrentItem();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LevelSelectorActivity.this, "Fail to read data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void previousButton(View view){
        viewPager2.setCurrentItem(viewPager2.getCurrentItem()-1,true);
        this.page = viewPager2.getCurrentItem();
    }

    public void nextButton(View view){
        viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1,true);
        this.page = viewPager2.getCurrentItem();
    }

    public void level1Listener(View view){
        Intent intent = new Intent(this, CardMatchingActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("LEVEL", 1);
        extras.putString("USERNAME", username);
        extras.putBoolean("AUDIO", playAudio);

        if (page == 0 && maxFruitLevel >= 0){
            extras.putString("THEME","Fruits");

            generate = new RandomNumberGenerator(Levels.getTotal(1) / 2, 3, 10);
            lst = generate.generateCards();
            extras.putString("CARDS", String.valueOf(lst));
            intent.putExtras(extras);
            startActivity(intent);
        }
        else if (page == 2 && maxPlanetLevel >= 0){
            extras.putString("THEME","Planets");

            generate = new RandomNumberGenerator(Levels.getTotal(1) / 2, 4, 9);
            lst = generate.generateCards();
            extras.putString("CARDS", String.valueOf(lst));
            intent.putExtras(extras);
            startActivity(intent);
        }
        else if (page == 1 && maxAnimalLevel >= 0) {
            extras.putString("THEME","Animals");

            generate = new RandomNumberGenerator(Levels.getTotal(1) / 2, 3, 10);
            lst = generate.generateCards();
            extras.putString("CARDS", String.valueOf(lst));
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    public void level2Listener(View view){
        Intent intent = new Intent(this, CardMatchingActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("LEVEL", 2);
        extras.putString("USERNAME", username);
        extras.putBoolean("AUDIO", playAudio);

        if (page == 0 && maxFruitLevel >= 1){
            extras.putString("THEME","Fruits");

            generate = new RandomNumberGenerator(Levels.getTotal(2) / 2, 3, 10);
            lst = generate.generateCards();
            extras.putString("CARDS", String.valueOf(lst));
            intent.putExtras(extras);
            startActivity(intent);
        }
        else if (page == 2 && maxPlanetLevel >= 1){
            extras.putString("THEME","Planets");

            generate = new RandomNumberGenerator(Levels.getTotal(2) / 2, 4, 9);
            lst = generate.generateCards();
            extras.putString("CARDS", String.valueOf(lst));
            intent.putExtras(extras);
            startActivity(intent);
        }
        else if (page == 1 && maxAnimalLevel >= 1){
            extras.putString("THEME","Animals");

            generate = new RandomNumberGenerator(Levels.getTotal(2) / 2, 3, 10);
            lst = generate.generateCards();
            extras.putString("CARDS", String.valueOf(lst));
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    public void level3Listener(View view){
        Intent intent = new Intent(this, CardMatchingActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("LEVEL", 3);
        extras.putString("USERNAME", username);
        extras.putBoolean("AUDIO", playAudio);

        if (page == 0 && maxFruitLevel >= 2){
            extras.putString("THEME","Fruits");

            generate = new RandomNumberGenerator(Levels.getTotal(3) / 2, 3, 10);
            lst = generate.generateCards();

            extras.putString("CARDS", String.valueOf(lst));
            intent.putExtras(extras);
            startActivity(intent);
        }
        else if (page == 2 && maxPlanetLevel >= 2){
            extras.putString("THEME","Planets");

            generate = new RandomNumberGenerator(Levels.getTotal(3) / 2, 4, 9);
            lst = generate.generateCards();

            extras.putString("CARDS", String.valueOf(lst));
            intent.putExtras(extras);
            startActivity(intent);
        }
        else if (page == 1 && maxAnimalLevel >= 2){
            extras.putString("THEME","Animals");

            generate = new RandomNumberGenerator(Levels.getTotal(3) / 2, 3, 10);
            lst = generate.generateCards();

            extras.putString("CARDS", String.valueOf(lst));
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    public void level4Listener(View view){
        Intent intent = new Intent(this, CardMatchingActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("LEVEL", 4);
        extras.putString("USERNAME", username);
        extras.putBoolean("AUDIO", playAudio);

        if (page == 0 && maxFruitLevel >= 3){
            extras.putString("THEME","Fruits");

            generate = new RandomNumberGenerator(Levels.getTotal(4) / 2, 3, 10);
            lst = generate.generateCards();

            extras.putString("CARDS", String.valueOf(lst));
            intent.putExtras(extras);
            startActivity(intent);
        }
        else if (page == 2 && maxPlanetLevel >= 3){
            extras.putString("THEME","Planets");

            generate = new RandomNumberGenerator(Levels.getTotal(4) / 2, 4, 9);
            lst = generate.generateCards();

            extras.putString("CARDS", String.valueOf(lst));
            intent.putExtras(extras);
            startActivity(intent);
        }
        else if (page == 1 && maxAnimalLevel >= 3){
            extras.putString("THEME","Animals");

            generate = new RandomNumberGenerator(Levels.getTotal(4) / 2, 3, 10);
            lst = generate.generateCards();
            extras.putString("CARDS", String.valueOf(lst));
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    @Override
    public void onLevelClick(int position) {

    }
}