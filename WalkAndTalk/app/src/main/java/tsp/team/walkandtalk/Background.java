package tsp.team.walkandtalk;

import android.util.DisplayMetrics;

/**
 * Created by preston on 15-12-13.
 */
public class Background {
    TextureInfo image;
    private float[]  windowCoords;
    private Square visibleImage;

    public Background(TextureInfo backTex){
        DisplayMetrics dm = new DisplayMetrics();
        float ratio = (float)dm.widthPixels/(float)dm.heightPixels;
        windowCoords = new float[]{
                ratio, 1f, -3f,
                ratio,-1f, -3f,
                -ratio,-1f, -3f,
                -ratio,-1f, -3f
        };

        visibleImage = new Square(windowCoords, 0f, 0f, 0f, 0f, backTex);
    }

    public void updateUVs(){

    }
}
