package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Stacey on 08/12/2015.
 */
public class SettingsActivity extends Activity {

    /** onCreate
     * This method overrides Activity's OnCreate method.  It calls the
     * parent's method and then sets what layout to use.  It also overrides
     * the default font with a special chalkboard font.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Typeface font;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final Context c = this;

        final Switch sound = (Switch) findViewById(R.id.swSound);

        try {
            //font = Typeface.createFromAsset(getAssets(), "DK Cool Crayon.ttf");
            font = Typeface.createFromAsset(getAssets(), "EraserRegular.ttf");
        } catch (java.lang.RuntimeException e) {
            font = null;
        }

        if (font != null) {
            TextView title = (TextView) findViewById(R.id.txtTitle);
            Button back = (Button) findViewById(R.id.btnBack);

            title.setTypeface(font);
            sound.setTypeface(font);
            back.setTypeface(font);
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean soundOn = preferences.getBoolean(getString(R.string.saved_sound_preference), false);

        if(soundOn){
            sound.setChecked(true);
        } else{
            sound.setChecked(false);
        }

        // Attach a listener to check for changes in state
        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
                SharedPreferences.Editor editor = preferences.edit();
                if (isChecked) {
                    editor.putBoolean(getString(R.string.saved_sound_preference), true);
                    editor.commit();
                } else {
                    editor.putBoolean(getString(R.string.saved_sound_preference), false);
                    editor.commit();
                }

            }
        });

    } // onCreate

    /** fromSettingsToMain
     * When the user presses the displayed button, this method will run
     * and they will be taken back to the main menu activity.
     *
     * @param v View containing information about the nature of the event
     */
    public void fromSettingsToMain(View v) {

        Intent intent = new Intent(SettingsActivity.this, MainMenuActivity.class);
        startActivity(intent);

    } // fromSettingsToMain

}
