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
    private Sprite earl;
    private Context contextHolder;
    private int ScreenWidth;
    private int ScreenHeight;
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

    public GameStuff(Context c){
        this.textureFactory = new TextureFactory(c);
        this.contextHolder = c;
        WindowManager wm = (WindowManager)c.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        this.ScreenHeight = dm.heightPixels;
        this.ScreenWidth = dm.widthPixels;
        enemies = new LinkedList<Sprite>();
        earl = null;
    }

    public void makeTestDummies(){
        //Eventually there needs to be some sort of scene drawing going on in here.
        float squareCoords[] = {
                0.375f,  0.6f, 0.0f,   // top left
                0.375f, -0.6f, 0.0f,   // bottom left
                -0.375f, -0.6f, 0.0f,   // bottom right
                -0.375f,  0.6f, 0.0f }; // top right

        float color[] = { 0.7f, 0.5f, 1.0f, 1.0f};

        float ratio = (float)this.getScreenWidth()/(float)this.getScreenHeight();

        Square aSquare = new Square(squareCoords,color,0.5f,0.4f,0.01f,0.01f,this.getContextHolder(),
                R.raw.test,true,0.0f,0.5f,ratio, textureFactory.getEarlTexture());

        Square bSquare = new Square(squareCoords,color,-0.3f,0.4f,-0.01f,-0.01f,this.getContextHolder(),
                R.raw.earl,true,0.0f,0.5f,ratio, textureFactory.getTestTexture());

        this.getEnemies().add(aSquare);
        this.getEnemies().add(bSquare);
    }

    public List<Sprite> getEnemies() {
        return enemies;
    }
}
