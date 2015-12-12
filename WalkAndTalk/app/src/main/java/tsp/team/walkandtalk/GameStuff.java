package tsp.team.walkandtalk;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Class to handle the state of game elements (score, difficulty, characters, background),
 * loading textures
 * drawing, spawning and despawning enemies and playing sound events
 *
 */
public class GameStuff {

    private List<Sprite> enemies;
    private Character character;
    private Context contextHolder;
    private int ScreenWidth;
    private int ScreenHeight;
    private float screenRatio;
    private TextureFactory textureFactory;

    public int getScreenWidth() {
        return ScreenWidth;
    }

    public int getScreenHeight() {
        return ScreenHeight;
    }

    public Context getContextHolder() {
        return contextHolder;
    }

    public GameStuff(Context c, SceneWrapper scene){
        this.textureFactory = new TextureFactory(c, scene);
        this.contextHolder = c;
        WindowManager wm = (WindowManager)c.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        this.ScreenHeight = dm.heightPixels;
        this.ScreenWidth = dm.widthPixels;
        this.screenRatio = (float)this.getScreenWidth()/(float)this.getScreenHeight();
        enemies = new LinkedList<Sprite>();
        character = new Character(contextHolder,textureFactory.getEarlTexture(),screenRatio);
    }

    public Character getCharacter() {
        return character;
    }

    public void makeTestDummies(){
        //Eventually there needs to be some sort of scene drawing going on in here.
        float squareCoords[] = {
                0.375f,  0.6f, 0.0f,   // top left
                0.375f, -0.6f, 0.0f,   // bottom left
                -0.375f, -0.6f, 0.0f,   // bottom right
                -0.375f,  0.6f, 0.0f }; // top right

        Square aSquare = new Square(squareCoords, 0.5f, 0.4f, 0.01f, 0.01f, this.getContextHolder(),
                true, 0.0f, 0.5f, screenRatio, textureFactory.getEarlTexture()[0]);

        Square bSquare = new Square(squareCoords, -0.3f, 0.4f, -0.01f, -0.01f, this.getContextHolder(),
                true, 0.0f, 0.5f, screenRatio, textureFactory.getTestTexture());

        this.getEnemies().add(aSquare);
        this.getEnemies().add(bSquare);
    }

    public List<Sprite> getEnemies() {
        return enemies;
    }
}