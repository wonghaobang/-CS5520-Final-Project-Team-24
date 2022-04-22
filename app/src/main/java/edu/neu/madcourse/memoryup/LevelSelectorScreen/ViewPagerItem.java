package edu.neu.madcourse.memoryup.LevelSelectorScreen;

import android.widget.ImageView;

public class ViewPagerItem {
    String theme;
    String text1;
    String text2;
    String text3;
    String text4;
    String text5;
    String text6;

    public ViewPagerItem(String theme, String text1, String text2, String text3, String text4, String text5, String text6) {
        this.theme = theme;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
        this.text5 = text5;
        this.text6 = text6;
    }

    public String getTheme(){
        return this.theme;
    }
    public String getLevel1(){
        return this.text1;
    }
    public String getLevel2(){
        return this.text2;
    }
    public String getLevel3(){
        return this.text3;
    }
    public String getLevel4(){
        return this.text4;
    }
    public String getLevel5(){
        return this.text5;
    }
    public String getLevel6(){
        return this.text6;
    }
}
