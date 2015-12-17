package tsp.team.walkandtalk;

import android.app.Activity;
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
     * SoundWrapper wrapper class used to abstract the calling of sound resources based on
     * selected characters
     * @param c the activity
     * @param scene SceneWrapper providing resource ID's
     */
    public SoundWrapper(Activity c, SceneWrapper scene){
        soundIDs = new int[3];
        pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        soundOn = preferences.getBoolean(c.getString(R.string.saved_sound_preference), false);
        // TODO generalize sound loading
        soundIDs[0] = pool.load(c, scene.getGameSounds()[0],1);
        soundIDs[1] = pool.load(c, scene.getGameSounds()[1],1);
        soundIDs[2] = pool.load(c, scene.getGameSounds()[2],1);
    }

    /**
     * play death sound
     */
    public void deadSound(){
        if(soundOn)
        pool.play(soundIDs[0], 1, 1, 1, 0, 1);
    }

    /**
     * play milestone sound
     */
    public void mileStoneSound(){
        if(soundOn)
        pool.play(soundIDs[1], 1, 1, 1, 0, 1);
    }

    /**
     * play highscore sound
     */
    public void highScore(){
        if(soundOn)
            pool.play(soundIDs[2], 1, 1, 1, 0, 1);
    }
}
