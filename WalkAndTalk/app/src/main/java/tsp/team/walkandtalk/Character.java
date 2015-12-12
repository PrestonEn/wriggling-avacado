package tsp.team.walkandtalk;

import android.content.Context;

/**
 * Created by vex on 12/12/15.
 */
public class Character {

    private Square squareImage;
    private TextureInfo textures[];
    private float squareCoords[] = {
            0.375f,  0.6f, 0.0f,   // top left
            0.375f, -0.6f, 0.0f,   // bottom left
            -0.375f, -0.6f, 0.0f,   // bottom right
            -0.375f,  0.6f, 0.0f }; // top right


    public Character(Context c,TextureInfo t[],float screenRatio){
        this.textures = t;//1.35,-0.28
        squareImage = new Square(squareCoords,-1.35f,-0.28f,0.0f,0.0f,c,false,0.0f,0.0f,screenRatio,textures[0]);
    }

    public void update(){
        //Fill this later with the ability to iterate through the textures array.
    }

    public Square getSquare(){
        return this.squareImage;
    }

}
