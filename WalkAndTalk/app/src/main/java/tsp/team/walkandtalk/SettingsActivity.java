package tsp.team.walkandtalk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

/** This class corresponds with the settings activity. It displays
 * different settings options for the user (including sound), and
 * they can change their preferences to affect their game play.
 */
public class SettingsActivity extends Activity {

    ImageView imgEarlArm;  // For animation of Earl

    /** onCreate
     * This method overrides Activity's OnCreate method.  It calls the
     * parent's method and then sets what layout to use.  It also overrides
     * the default font with a special chalkboard font, and sets the switch
     * widgets accordingly based on the loaded user preferences.  The onChange
     * listeners are set up to save any user preference setting changes.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Typeface font;  // Chalkboard font

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

        if (font != null) { // Set all text fields to use the chalkboard font
            TextView title = (TextView) findViewById(R.id.txtTitle);
            TextView sound_title = (TextView) findViewById(R.id.txtSound);
            Button resetScores = (Button) findViewById(R.id.btnResetScores);
            Button about = (Button) findViewById(R.id.btnAbout);
            Button back = (Button) findViewById(R.id.btnBack);

            title.setTypeface(font);
            sound_title.setTypeface(font);
            resetScores.setTypeface(font);
            about.setTypeface(font);
            back.setTypeface(font);
        }

        // Load user preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean soundOn = preferences.getBoolean(getString(R.string.saved_sound_preference), false);

        // Set switch objects to reflect current user preferences
        if(soundOn){
            sound.setChecked(true);
        } else{
            sound.setChecked(false);
        }

        // Attach a listener to the sound switch to check for changes in state
        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
                SharedPreferences.Editor editor = preferences.edit();
                if (isChecked) { // Update user preference
                    editor.putBoolean(getString(R.string.saved_sound_preference), true);
                    editor.commit();
                } else {
                    editor.putBoolean(getString(R.string.saved_sound_preference), false);
                    editor.commit();
                }
            }
        });

        imgEarlArm = (ImageView)findViewById(R.id.imgArm);  // For animation purposes

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
     * Stop the animation upon leaving the activity.
     */
    @Override
    protected void onPause() {

        super.onPause();
        imgEarlArm.clearAnimation();

    } // onPause

    /** resetHighScores
     * When the user presses the "Reset High Scores" button, this method will run
     * and they will be asked to verify their decision.  If they say yes, the
     * scores table is cleared.
     *
     * @param v View containing information about the nature of the event
     */
    public void resetHighScores(final View v) {

        final ScoresDBManager scoresDB = new ScoresDBManager(this); // For deleting
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  // For warning
        builder.setTitle("Delete All High Scores")
                .setMessage("Are you sure you want to reset all high scores?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    /** onClick
                     * This method runs when the user clicks yes to deleting all high scores.
                     *
                     * @param dialog Dialog verifying deletion
                     * @param which  Holds int representing which selection was made
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean success = scoresDB.deleteAllHighScoreRows(); // Delete all high scores
                        scoresDB.close(); // Close database manager
                        if (success) {
                            Toast.makeText(v.getContext(), "High Scores Reset", Toast.LENGTH_LONG).show();
                        } else{
                            Toast.makeText(v.getContext(), "No High Scores To Reset", Toast.LENGTH_LONG).show();
                        }
                    } // onClick
                })
                .setNegativeButton("Cancel", null) // Do nothing
                .show();

    } // resetHighScores

    /** fromSettingsToMain
     * When the user presses the "Back To Main Menu" button, this method will run
     * and they will be taken back to the main menu activity.
     *
     * @param v View containing information about the nature of the event
     */
    public void fromSettingsToMain(View v) {

        Intent intent = new Intent(SettingsActivity.this, MainMenuActivity.class);
        startActivity(intent);

    } // fromSettingsToMain

    /** fromSettingsToAbout
     * When the user presses the about button, this method will run
     * and they will be taken back to the about activity.
     *
     * @param v View containing information about the nature of the event
     */
    public void fromSettingsToAbout(View v){

        Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
        startActivity(intent);

    } // fromSettingsToAbout

    @Override
    public void onBackPressed(){
    }

} // SettingsActivity
