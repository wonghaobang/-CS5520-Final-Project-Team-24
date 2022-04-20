package edu.neu.madcourse.memoryup.LevelThemes;

import edu.neu.madcourse.memoryup.R;

public class FruitsImageMap {
    private final static int image0 = R.drawable.apple;
    private final static int image1 = R.drawable.banana;
    private final static int image2 = R.drawable.blackcurrant;
    private final static int image3 = R.drawable.blueberries;
    private final static int image4 = R.drawable.grapes;
    private final static int image5 = R.drawable.orange;
    private final static int image6 = R.drawable.peach;
    private final static int image7 = R.drawable.pear;
    private final static int image8 = R.drawable.pineapple;
    private final static int image9 = R.drawable.strawberry;

    public static int getImage(int id) {
        switch (id) {
            case 1:
                return image1;
            case 2:
                return image2;
            case 3:
                return image3;
            case 4:
                return image4;
            case 5:
                return image5;
            case 6:
                return image6;
            case 7:
                return image7;
            case 8:
                return image8;
            case 9:
                return image9;
            default:
                return image0;
        }
    }
}
