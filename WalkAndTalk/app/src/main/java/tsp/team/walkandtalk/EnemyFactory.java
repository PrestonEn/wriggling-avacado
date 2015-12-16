package tsp.team.walkandtalk;

import android.content.Context;
import android.util.Log;

import java.util.Random;

/**
 * This class is responsible for building enemies for the game loop to throw at the Character.
 * Basic Factory design patter at work.
 */
public class EnemyFactory {

    private Context contextHolder; // Reference to context needed for building sprites.
    private static float screenRatio; // Stored value of width over height of the phone.
    private TextureFactory textureFactory; // Texture factory which will get our opengl textures.
    private static float stillShape[] = { // Vertices for a still enemy.
         0.2f,  0.2f, 0.0f,   // top left
         0.2f, -0.2f, 0.0f,   // bottom left
         -0.2f, -0.2f, 0.0f,   // bottom right
         -0.2f,  0.2f, 0.0f }; // top right


    /**
     * Main constructor for the object which will just build the necessary instance variables.
     * @param c Context of application.
     */
    public EnemyFactory(Context c, TextureFactory textureFactory, float screenRatio){
        this.contextHolder = c;
        this.textureFactory = textureFactory;
        this.screenRatio = screenRatio;
    }

    /**
     * Method for building a still enemy of a particular difficulty.
     * @param difficulty DifficultySetting enumeration of EASY, MEDIUM, and HARD.
     * @return Sprite form of the particular enemy type.
     */
    public Square makeStillEnemy(DifficultySetting difficulty){
        float rate = 0.0f;
        switch(difficulty){
            case DIFFICULTY_EASY:
                rate = -0.02f;
                break;
            case DIFFICULTY_MEDIUM:
                rate = -0.033f;
                break;
            case DIFFICULTY_HARD:
                rate = -0.041f;
                break;
        }

        Square enemy =  new Square(stillShape, 2.49f, -0.75f, rate, 0.0f, contextHolder,
                false, 0.0f, 0.0f, screenRatio, textureFactory.getScene_enemies_still());
        enemy.setKillGesture(null); // No kill gesture for this.
        return enemy;
    }

    /**
     * Method for building a running enemy of a particular difficulty.
     * @param difficulty DifficultySetting enumeration of EASY, MEDIUM, and HARD.
     * @return Sprite form of the particular enemy type.
     */
    public Square makeRunEnemy(DifficultySetting difficulty){
        float rate = 0.0f;
        switch(difficulty){
            case DIFFICULTY_EASY:
                rate = -0.02f * 1.5f;
                break;
            case DIFFICULTY_MEDIUM:
                rate = -0.033f * 1.5f;
                break;
            case DIFFICULTY_HARD:
                rate = -0.041f * 1.5f;
                break;
        }

        Square enemy = new Square(stillShape, 2.49f, new Random().nextFloat() * -1f, rate, 0.0f, contextHolder,
                true, 0.0f, 10.0f, screenRatio, textureFactory.getScene_enemies_run());
        enemy.setKillGesture(EnemyKillGesture.GESTURE_FLING); // Fling anywhere for this.
        enemy.setWiggle(true);
        return enemy;
    }

    /**
     * Method for building a flying enemy of a particular difficulty.
     * @param difficulty DifficultySetting enumeration of EASY, MEDIUM, and HARD.
     * @return Sprite form of the particular enemy type.
     */
    public Square makeFlyEnemy(DifficultySetting difficulty, Character character){
        float rate = 0.0f;
        float x, y, dy;
        switch(difficulty){
            case DIFFICULTY_EASY:
                rate = -0.02f * 1.5f;
                break;
            case DIFFICULTY_MEDIUM:
                rate = -0.033f;
                break;
            case DIFFICULTY_HARD:
                rate = -0.041f;
                break;
        }
        Random r = new Random();
        //gen x between 0.0f and 2.4f
        x = 2.4f;
        //if x < screen ratio,
        if(x < screenRatio){
            y = r.nextFloat() + 1.f;
        }else{
            y = r.nextFloat() * 1.3f;
        }

        float xDif = x - character.getSquare().px;
        float yDif = y - character.getSquare().py;

        float roc = xDif/rate;

        dy = -1 * Math.abs(yDif) / Math.abs(roc);
        Log.e("dy", dy+"");
       // dy = -1 *

        Square enemy = new Square(stillShape, x, y , rate, dy, contextHolder,
                true, 0.0f, 0.9f, screenRatio, textureFactory.getScene_enemies_fly());
        enemy.setKillGesture(EnemyKillGesture.GESTURE_FLING_DOWN);
        return enemy;
    }

}