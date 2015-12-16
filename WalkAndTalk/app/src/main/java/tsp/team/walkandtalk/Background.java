package tsp.team.walkandtalk;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * This is the class that controls the background scrolling objects.
 * Takes advantage of sprite classes ability to update easily.
 */
public class Background {
    private float[]  windowCoords; // Coordinate location.
    public Square visibleImage, visibleImage2, visibleImage3; // All three images needed to produce smooth scrolling.
    private float backUVs[] = new float[]{ // UV mappings.
            1f,1f,
            1f,0f,
            0f,0f,
            0f,1f
    };
    private float ratio; // Screen size ratio.

    /**
     * Constructor which takes in the proper objects to build all of the sprites we need.s
     * @param backTex textureInfo for background
     * @param c Context
     */
    public Background(TextureInfo backTex, Context c, float ratio){
        this.ratio = ratio;
        DisplayMetrics dm = new DisplayMetrics();
        windowCoords = new float[]{
                -ratio*1.5f, -1f, 0.0f,   // bottom left
                -ratio*1.5f,  1f, 0.0f,   // top left
                ratio*1.5f, 1f, 0.0f,
                ratio*1.5f, -1f, 0.0f,   // bottom right
        };


        visibleImage = new Square(windowCoords, 0f, 0f, -0.015f, 0f, c, false, 0f, 0f, 0f, backTex);
        visibleImage2 = new Square(windowCoords, 2f*1.5f*ratio, 0f, -0.015f, 0f, c, false, 0f, 0f, 0f, backTex);
        visibleImage3 = new Square(windowCoords, 4f*1.5f*ratio, 0f, -0.015f, 0f, c, false, 0f, 0f, 0f, backTex);

        visibleImage.animUVs = backUVs;
        visibleImage.setShapeVertexs(windowCoords);

        visibleImage2.animUVs = backUVs;
        visibleImage2.setShapeVertexs(windowCoords);

        visibleImage3.animUVs = backUVs;
        visibleImage3.setShapeVertexs(windowCoords);
    }

    /**
     * This function is what is called by the outside classes to scroll the background.
     * Passed in values are UVs.
     * @param one Mapping for the first image.
     * @param two Mapping for the second image.
     * @param three Mapping for the third image.
     */
    public void testWrap(float[] one, float[] two, float[] three){
        if(this.visibleImage.px < -2.0f*1.5f*ratio){
            this.visibleImage.px = 2.0f*1.5f*ratio;
            this.visibleImage2.px = 0f;
        }
        if(this.visibleImage2.px < -2.0f*1.5f*ratio){
            this.visibleImage2.px = 2.0f*1.5f*ratio;
            this.visibleImage3.px = 0f;

        }
        if(this.visibleImage3.px < -2.0f*1.5f*ratio){
            this.visibleImage3.px = 2.0f*1.5f*ratio;
            this.visibleImage.px = 0f;

        }
        this.visibleImage.draw(one);
        this.visibleImage2.draw(two);
        this.visibleImage3.draw(three);


        this.visibleImage.updateShape();
        this.visibleImage2.updateShape();
        this.visibleImage3.updateShape();

    }
}