package tsp.team.walkandtalk;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
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
        this.contextHolder = c;
        WindowManager wm = (WindowManager)c.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        this.ScreenHeight = dm.heightPixels;
        this.ScreenWidth = dm.widthPixels;
        enemies = new LinkedList<Sprite>();
        earl = null;
    }

    public void removeDeadEnemies(){
        for(Sprite s : enemies){
            if(!s.live)enemies.remove(s);
        }
    }

    public List<Sprite> getEnemies() {
        return enemies;
    }
}
