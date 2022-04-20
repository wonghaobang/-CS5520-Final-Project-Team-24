package edu.neu.madcourse.memoryup.LevelThemes;

import edu.neu.madcourse.memoryup.R;

public class PlanetsImageMap {
    private final static int image0 = R.drawable.pluto;
    private final static int image1 = R.drawable.mars;
    private final static int image2 = R.drawable.jupiter;
    private final static int image3 = R.drawable.earth;
    private final static int image4 = R.drawable.mercury;
    private final static int image5 = R.drawable.neptune;
    private final static int image6 = R.drawable.saturn;
    private final static int image7 = R.drawable.uranus;
    private final static int image8 = R.drawable.venus;

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
            default:
                return image0;
        }
    }
}
