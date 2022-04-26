package edu.neu.madcourse.memoryup.LevelSelectorScreen;

import android.widget.ImageView;

public class ViewPagerItem {
    String theme;
    int text1;
    int text2;
    int text3;
    int text4;

    public ViewPagerItem(String theme, int text1, int text2, int text3, int text4) {
        this.theme = theme;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
    }

    public String getTheme(){
        return this.theme;
    }
    public int getLevel1(){
        return this.text1;
    }
    public int getLevel2(){
        return this.text2;
    }
    public int getLevel3(){
        return this.text3;
    }
    public int getLevel4(){
        return this.text4;
    }
}
