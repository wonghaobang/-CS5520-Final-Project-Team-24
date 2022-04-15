package edu.neu.madcourse.memoryup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class LevelSelectorActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList<ViewPagerItem> viewPagerItemLst;

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

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(viewPagerItemLst);
        viewPager2.setAdapter(vpAdapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    public void previousButton(View view){
        viewPager2.setCurrentItem(viewPager2.getCurrentItem()-1,true);
    }

    public void nextButton(View view){
        viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1,true);
    }
}