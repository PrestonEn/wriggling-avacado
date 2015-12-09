package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Stacey on 08/12/2015.
 */
public class SettingsActivity extends Activity {

    ImageView imgEarlArm;

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

        imgEarlArm = (ImageView)findViewById(R.id.imgArm);

    } // onCreate

    /** onResume
     * Run the Earl gesturing animation.
     */
    @Override
    protected void onResume() {
        super.onResume();

        Animation animate = new RotateAnimation(0, -90,
                Animation.RELATIVE_TO_SELF, 0.56f,
                Animation.RELATIVE_TO_SELF, 0.46f);

        animate.setStartOffset(1000);
        animate.setDuration(2000);
        animate.setRepeatCount(Animation.INFINITE);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animate);

        animationSet.setRepeatMode(Animation.REVERSE);

        imgEarlArm.startAnimation(animationSet);

    } // onResume

    /** onPause
     *
     */
    @Override
    protected void onPause() {
        super.onPause();

        imgEarlArm.clearAnimation();

    } // onPause

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
