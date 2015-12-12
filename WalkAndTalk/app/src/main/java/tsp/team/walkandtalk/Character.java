package tsp.team.walkandtalk;

import android.content.Context;

/**
 * This class is used for holding the information that a Character needs for drawing and computing
 * UV maps.
 */
public class Character {

    private static int frame_count = 16; // Fixed number of frames,
    private static int frame_division = (int)Math.sqrt(frame_count); // Allows us to evenly divide the frames.
    private float animUVs[]; // Storage for an individual frame.
    private float computeUVs[][] = new float[frame_count][]; // Array holding all of the UVs across all frames.
    private Square squareImage; // Reference to the sprite that this character uses.
    private TextureInfo textures; // Reference to the sprite sheet from opengl bindings.
    private int currentFrame = 0; // Current frame to draw.
    private float squareCoords[] = { // Vertexes for drawing the sprite.
            0.375f,  0.6f, 0.0f,   // top left
            0.375f, -0.6f, 0.0f,   // bottom left
            -0.375f, -0.6f, 0.0f,   // bottom right
            -0.375f,  0.6f, 0.0f }; // top right

    /**
     * Public constructor for making the Character object.
     * This constructor builds all of the UV maps and stores them. It also builds the square sprite object.
     * @param c Context for passing into the square.
     * @param t Texture info that this object will use for rendering.
     * @param screenRatio Size of the screen so that we can pass into square.
     */
    public Character(Context c,TextureInfo t,float screenRatio){
        this.textures = t;

        for (int i = 0; i < frame_division; i++) { // Following loops build all of the frame data from the
            for (int j = 0; j < frame_division; j++) { // sprite sheet and save it in an array.
                animUVs = new float[]{ // Building...
                        (float)(j + 1)/frame_division, (float)i/frame_division,
                        (float)(j + 1)/frame_division, (float)(i + 1)/frame_division,
                        (float)j/frame_division, (float)(i + 1)/frame_division,
                        (float)j/frame_division, (float)i/frame_division,
                };

                computeUVs[i*frame_division+j] = animUVs; // Assignment.
            }
        }
        //Build the sprite image at designated points and give it proper initial values that allow it to not move.
        squareImage = new Square(squareCoords,-1.35f,-0.28f,0.0f,0.0f,c,false,0.0f,0.0f,screenRatio,textures);
    }

    /**
     * This method is to be called after drawing each and every time.
     * This also implements autoscrolling through each frame of the sprite.
     */
    public void update(){
        squareImage.animUVs = computeUVs[currentFrame++%frame_count];
    }

    /**
     * This getter is implemented so that the sprite that Character is using is able to be read from,
     * from outside classes.
     * @return Square sprite that the Character is represented by.
     */
    public Square getSquare(){
        return this.squareImage;
    }
}