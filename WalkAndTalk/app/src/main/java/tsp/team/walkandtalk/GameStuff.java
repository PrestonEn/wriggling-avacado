package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to handle the state of game elements (score, difficulty, characters, background),
 * loading textures,drawing, spawning and despawning enemies, and playing sound events.
 */
public class GameStuff {
    private int score;
    private List<Sprite> enemies; // List of enemies to render.
    private Character character; // Reference to the Character on the screen.
    private Context contextHolder; // Context for building other objects.
    private Activity act;
    private int ScreenWidth;
    private int ScreenHeight;
    private float screenRatio; // ScreenWidth / ScreenHeight.
    private TextureFactory textureFactory; // Reference to a TextureFactory for building images.
    private Background background;
    private EnemyFactory enemyFactory; // Reference to a EnemyFactory that will build generic enemies.
    private int stillCounter, runCounter, flyCounter;
    private long prevHighScore;
    private boolean newHigh;
    private boolean soundOn;
    private SoundWrapper soundPlayer;
    /**
     * Constructor for the GameStuff object. GameStuff is meant to control the entire engine of our
     * game. This constructor builds all of the objects we need.
     * @param c Context of the application that this object belongs to.
     * @param scene SceneWrapper that will be used to specify backgrounds and enemy types.
     * @param prevHighScore top result from local score database
     */
    public GameStuff(Activity c, SceneWrapper scene, long prevHighScore){
        soundPlayer = new SoundWrapper(c);
        act = c;
        score = 0;
        newHigh = false;
        this.textureFactory = new TextureFactory(c, scene); // See TextureFactory class...
        this.contextHolder = c;
        this.prevHighScore = prevHighScore;
        WindowManager wm = (WindowManager)c.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm); // Above lines including this one find the width and height.
        this.ScreenHeight = dm.heightPixels;
        this.ScreenWidth = dm.widthPixels;
        this.screenRatio = (float)this.getScreenWidth()/(float)this.getScreenHeight(); // Build ratio.
        enemies = new LinkedList<Sprite>(); // Initialize the list of enemies.
        background = new Background(textureFactory.getScene_back(), contextHolder, screenRatio);
        character = new Character(contextHolder,textureFactory.getCharacter_run(),
        		textureFactory.getCharacter_jump(),textureFactory.getCharacter_fall(), textureFactory.getCharacter_jump(),screenRatio, soundPlayer);
        enemyFactory = new EnemyFactory(contextHolder,textureFactory,screenRatio);

        stillCounter = 125 + (int)(Math.random() * ((250 - 125) + 1));
        runCounter = 125 + (int)(Math.random() * ((250 - 125) + 1));
        flyCounter = 125 + (int)(Math.random() * ((250 - 125) + 1));

    }   // See character object for line above.

    /**
     * Getter method for the screen width.
     * @return Integer representation of screen width.
     */
    public int getScreenWidth() {
        return ScreenWidth;
    }

    /**
     * Getter method for the screen height.
     * @return Integer representation of screen height.
     */
    public int getScreenHeight() {
        return ScreenHeight;
    }

    /**
     * Getter method for the context saved inside this object.
     * @return Standard android Context.
     */
    public Context getContextHolder() {
        return contextHolder;
    }

    /**
     * Getter method for the Character in this object.
     * @return Character reference.
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * incrememnts score by 1 and tests for milestone of 500 points via mod
     */
    public void updateScore(){
        score++;
        if(score > prevHighScore && !newHigh){
            newHigh = true;
        }

        if(score % 500 == 0){
            //todo play ding sound
            soundPlayer.dealieSound();

        }

    }

    /**
     * get the current scoring
     * @return gamestate score
     */
    public int getScore(){
        return score;
    }

    /**
     * Returns the enumerable list of enemies for drawing in the renderer.
     * @return List of Sprite type.
     */
    public List<Sprite> getEnemies() {
        return enemies;
    }

    /**
     * Returns the background object
     * @return Background wrapper class
     */
    public Background getBackground(){
        return background;
    }

    /**
     * Generate new enemies after a randomly generated interval, and recalculate the counter.
     */
    public void spawnPoller(){
        if(stillCounter == 0){
            enemies.add(enemyFactory.makeStillEnemy(DifficultySetting.DIFFICULTY_EASY));
            stillCounter = 125 + (int)(Math.random() * ((250 - 125) + 1));
        }else{
            --stillCounter;
        }

        if(flyCounter == 0){
            enemies.add(enemyFactory.makeFlyEnemy(DifficultySetting.DIFFICULTY_EASY, character));
            flyCounter = 125 + (int)(Math.random() * ((250 - 125) + 1));
        }else{
            --flyCounter;
        }

        if(runCounter == 0){
            enemies.add(enemyFactory.makeRunEnemy(DifficultySetting.DIFFICULTY_EASY));
            runCounter = 125 + (int)(Math.random() * ((250 - 125) + 1));
        }else{
            --runCounter;
        }
    }
}