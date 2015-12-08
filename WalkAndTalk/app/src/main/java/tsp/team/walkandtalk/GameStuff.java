package tsp.team.walkandtalk;

import android.content.Context;
import android.widget.Toast;

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

    public Context getContextHolder() {
        return contextHolder;
    }

    public GameStuff(Context c){
        this.contextHolder = c;
        enemies = new LinkedList<Sprite>();
        earl = null;
    }

    public List<Sprite> getEnemies() {
        return enemies;
    }
}
