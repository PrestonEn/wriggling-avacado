package tsp.team.walkandtalk;

import android.content.Context;
import android.util.Log;

/**
 * This class is used for holding the information that a Character needs for drawing and computing
 * UV maps.
 */
public class Character {

    private static float initPX = -1.35f;
    private static float initPY = -0.28f;
    private int deathFrame = 0;
    private static int frame_count = 16; // Fixed number of frames,
    private static int frame_division = (int)Math.sqrt(frame_count); // Allows us to evenly divide the frames.
    private float animUVs[]; // Storage for an individual frame.
    private float computeUVs[][] = new float[frame_count][]; // Array holding all of the UVs across all frames.
    private Square squareImage; // Reference to the sprite that this character uses.
    private TextureInfo texturesRun; // Reference to the sprite sheet from opengl bindings.
    private TextureInfo textureJump; // Reference to the texture for jumping.
    private TextureInfo texturesFall; // Reference to fall sprite sheet.
    private int currentFrame = 0; // Current frame to draw.
    private boolean jumping = false;
    private static int jumpDrawMax = 32; // Maximum amount of times the jump frame is to be drawn.
    private int jumpDrawCount = 0;
    private boolean doneDying = false; // Signal for when the dying anim is done.
    private static float[] standardUVMap = new float[]{ // Standard UV params.
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 0.0f,
    };
    private float squareCoords[] = { // Vertexes for drawing the sprite.
            0.375f,  0.6f, 0.0f,   // top left
            0.375f, -0.6f, 0.0f,   // bottom left
            -0.375f, -0.6f, 0.0f,   // bottom right
            -0.375f,  0.6f, 0.0f }; // top right

    /**
     * Public constructor for making the Character object.
     * This constructor builds all of the UV maps and stores them. It also builds the square sprite object.
     * @param c Context of this character.
     * @param tRun TextureInfo for the run animation.
     * @param tJump TextureInfo for the jump animation.
     * @param screenRatio Size of the screen ratio to be passed into square.
     */
    public Character(Context c,TextureInfo tRun,TextureInfo tJump, TextureInfo tFall,float screenRatio){
        this.texturesRun = tRun;
        this.textureJump = tJump;
        this.texturesFall = tFall;

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
        squareImage = new Square(squareCoords,initPX,initPY,0.0f,0.0f,c,false,0.0f,0.0f,screenRatio,texturesRun);
    }

    /**
     * Method to be executed when a touch on the character is detected.
     * Allows the character sprite to jump.
     */
    public void applyJump(){
        jumping = true; // Apply jumping state.
        jumpDrawCount = 0; // Reset number of frames to draw.
    }

    /**
     * This method is to be called after drawing each and every time.
     * This also implements auto-scrolling through each frame of the sprite.
     */
    public void update(){
        if(squareImage.live){
            if(jumping){
                if(jumpDrawCount == 0){ // Only overwrite the image UVs if we need to.
                    squareImage.animUVs = standardUVMap;
                    squareImage.vy = 0.015f;
                    squareImage.settInfo(this.textureJump); // Safe to update reference.
                }

                jumpDrawCount++; // Increment how many times we've drawn the frame.
                if(jumpDrawCount > jumpDrawMax){ // If we have drawn enough...
                    this.jumping = false;
                    jumpDrawCount = -1; // Flag so that we know when to update reference.
                }
            }
            else{
                if(jumpDrawCount == -1){  // Overwrite UVs with proper image splitting ones.
                    jumpDrawCount = 0; // Make it so this if statement does not constantly run.
                    squareImage.settInfo(this.texturesRun); // Only update when needed.
                }

                squareImage.animUVs = computeUVs[currentFrame++%frame_count]; // Increment and draw frames.
            }
            // Below control structure is outside of jumping loop because the anim can stop playing before we are done landing.
            if(squareImage.py > -2*initPY){ // Detect peak of the jump.
                squareImage.vy = -0.015f;
            }
            else if(squareImage.py < initPY){ // Land back on the ground safely.
                squareImage.vy = 0;
                squareImage.py = initPY;
            }
            squareImage.updateShape(); // Make sure the square also gets updated so it can move if needed.
        }
        else{ // Character is dead here.
            if(deathFrame == 0)squareImage.settInfo(this.texturesFall); // Update the reference.
            if(deathFrame < frame_count){ // Hold on the last frame.
                squareImage.animUVs = computeUVs[deathFrame]; // Increment and draw frames.
                deathFrame++;
            }
            else{
                deathFrame++;
                if(deathFrame > 100)this.doneDying = true;
            }
        }
    }

    /**
     * Examine if the dying animation has completed.
     * @return Boolean done dying.
     */
    public boolean isDoneDying() {
        return doneDying;
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