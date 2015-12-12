package tsp.team.walkandtalk;

import android.content.Context;

/**
 * Created by vex on 12/12/15.
 */
public class Character {

    private static int frame_count = 16;
    private static int frame_division = (int)Math.sqrt(frame_count);
    private float animUVs[];
    private float computeUVs[][] = new float[frame_count][];
    private Square squareImage;
    private TextureInfo textures;
    private int currentFrame = 0;
    private float squareCoords[] = {
            0.375f,  0.6f, 0.0f,   // top left
            0.375f, -0.6f, 0.0f,   // bottom left
            -0.375f, -0.6f, 0.0f,   // bottom right
            -0.375f,  0.6f, 0.0f }; // top right


    public Character(Context c,TextureInfo t,float screenRatio){
        this.textures = t;//1.35,-0.28

        for (int i = 0; i < frame_division; i++) {
            for (int j = 0; j < frame_division; j++) {
                animUVs = new float[]{
                        (float)(j + 1)/frame_division, (float)i/frame_division,
                        (float)(j + 1)/frame_division, (float)(i + 1)/frame_division,
                        (float)j/frame_division, (float)(i + 1)/frame_division,
                        (float)j/frame_division, (float)i/frame_division,
                };

                computeUVs[i*frame_division+j] = animUVs;
            }
        }

        squareImage = new Square(squareCoords,-1.35f,-0.28f,0.0f,0.0f,c,false,0.0f,0.0f,screenRatio,textures);
    }

    public void update(){
        squareImage.animUVs = computeUVs[currentFrame++%frame_count];
    }

    public Square getSquare(){
        return this.squareImage;
    }
}