package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.preference.PreferenceManager;

/**
 * Contains methods for playing sounds in response to game events, used in Character
 * and GameStuff
 */
public class SoundWrapper {
    private int[] soundIDs;
    private SoundPool pool;
    private boolean soundOn;

    /**
     *
     * @param c The parent activity, used to access preferences
     */
    public SoundWrapper(Activity c){
        soundIDs = new int[3];
        pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        soundOn = preferences.getBoolean(c.getString(R.string.saved_sound_preference), false);
        // TODO generalize sound loading
        soundIDs[0] = pool.load(c, R.raw.dealie, 1);
        soundIDs[1] = pool.load(c, R.raw.face, 1);
    }


    public void deadSound(){
        if(soundOn)
        pool.play(soundIDs[1], 1, 1, 1, 0, 1);
    }

    public void dealieSound(){
        if(soundOn)
        pool.play(soundIDs[0], 1, 1, 1, 0, 1);
    }
}
