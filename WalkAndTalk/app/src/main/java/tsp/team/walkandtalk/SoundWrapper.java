package tsp.team.walkandtalk;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by preston on 15-12-16.
 */
public class SoundWrapper {
    private int[] soundIDs;
    private SoundPool pool;

    public SoundWrapper(Context c){
        soundIDs = new int[3];
        pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundIDs[0] = pool.load(c, R.raw.dealie, 1);
        soundIDs[1] = pool.load(c, R.raw.face, 1);
    }

    public void deadSound(){
        pool.play(soundIDs[1], 1, 1, 1, 0, 1);
    }

    public void dealieSound(){
        pool.play(soundIDs[0], 1, 1, 1, 0, 1);
    }
}
