package edu.neu.madcourse.memoryup.LevelSelectorScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import edu.neu.madcourse.memoryup.CardMatchingScreen.CardMatchingActivity;
import edu.neu.madcourse.memoryup.LevelThemes.Levels;
import edu.neu.madcourse.memoryup.R;

public class LevelSelectorActivity extends AppCompatActivity implements LevelClickListener {
    private ViewPager2 viewPager2;
    private ArrayList<ViewPagerItem> viewPagerItemLst;
    private RecyclerView.LayoutManager rLayoutManger;
    private int page;

    private RandomNumberGenerator generate;
    private ArrayList<ArrayList<Integer>> lst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);

        viewPager2 = findViewById(R.id.pager);
        viewPagerItemLst = new ArrayList<>();

        ViewPagerItem viewPagerItem = new ViewPagerItem("Fruits","fruit1", "fruit2", "fruit3", "fruit4", "fruit5", "fruit6");
        viewPagerItemLst.add(viewPagerItem);

        ViewPagerItem viewPagerItem2 = new ViewPagerItem("Planets","planet1", "planet2", "planet3", "planet4", "planet5", "planet6");
        viewPagerItemLst.add(viewPagerItem2);

        ViewPagerItem viewPagerItem3 = new ViewPagerItem("Animals","animals1", "animals2", "animals3", "animals4", "animals5", "animals6");
        viewPagerItemLst.add(viewPagerItem3);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(viewPagerItemLst, this);
        rLayoutManger = new LinearLayoutManager(this);

        viewPager2.setAdapter(vpAdapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        this.page = viewPager2.getCurrentItem();
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

        if (page == 0){
            extras.putString("THEME","Fruits");

            generate = new RandomNumberGenerator(Levels.getTotal(1) / 2, 3, 10);
            lst = generate.generateCards();
        }
        else if (page == 1){
            extras.putString("THEME","Planets");

            generate = new RandomNumberGenerator(Levels.getTotal(1) / 2, 3, 10);
            lst = generate.generateCards();
        }
        else {
            extras.putString("THEME","Animals");

            generate = new RandomNumberGenerator(Levels.getTotal(1) / 2, 4, 9);
            lst = generate.generateCards();
        }

        extras.putString("CARDS", String.valueOf(lst));
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void level2Listener(View view){
        Intent intent = new Intent(this, CardMatchingActivity.class);
        Bundle extras = new Bundle();

        if (page == 0){
            extras.putString("THEME","Fruits");

            generate = new RandomNumberGenerator(Levels.getTotal(2) / 2, 3, 10);
            lst = generate.generateCards();
        }
        else if (page == 1){
            extras.putString("THEME","Planets");

            generate = new RandomNumberGenerator(Levels.getTotal(2) / 2, 3, 10);
            lst = generate.generateCards();
        }
        else {
            extras.putString("THEME","Animals");

            generate = new RandomNumberGenerator(Levels.getTotal(2) / 2, 4, 9);
            lst = generate.generateCards();
        }

        extras.putString("CARDS", String.valueOf(lst));
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void level3Listener(View view){
        Intent intent = new Intent(this, CardMatchingActivity.class);
        Bundle extras = new Bundle();
        if (page == 0){
            extras.putString("THEME","Fruits");

            generate = new RandomNumberGenerator(Levels.getTotal(3) / 2, 3, 10);
            lst = generate.generateCards();
        }
        else if (page == 1){
            extras.putString("THEME","Planets");

            generate = new RandomNumberGenerator(Levels.getTotal(3) / 2, 3, 10);
            lst = generate.generateCards();
        }
        else {
            extras.putString("THEME","Animals");

            extras.putString("CARDS", String.valueOf(lst));
            generate = new RandomNumberGenerator(Levels.getTotal(3) / 2, 4, 9);
            lst = generate.generateCards();
        }

        intent.putExtras(extras);
        startActivity(intent);
    }

    public void level4Listener(View view){
        Intent intent = new Intent(this, CardMatchingActivity.class);
        Bundle extras = new Bundle();
        if (page == 0){
            extras.putString("THEME","Fruits");

            generate = new RandomNumberGenerator(Levels.getTotal(4) / 2, 3, 10);
            lst = generate.generateCards();
        }
        else if (page == 1){
            extras.putString("THEME","Planets");

            generate = new RandomNumberGenerator(Levels.getTotal(4) / 2, 3, 10);
            lst = generate.generateCards();
        }
        else {
            extras.putString("THEME","Animals");

            extras.putString("CARDS", String.valueOf(lst));
            generate = new RandomNumberGenerator(Levels.getTotal(4) / 2, 4, 9);
            lst = generate.generateCards();
        }

        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void onLevelClick(int position) {

    }




}