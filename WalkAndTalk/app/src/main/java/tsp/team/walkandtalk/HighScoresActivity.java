package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/** This class corresponds with the high scores activity. It displays
 * the high scores of the user in descending order using a listview,
 * showing the score, date, time, character and scene.
 */
public class HighScoresActivity extends Activity {

    ImageView imgEarlLeftArm;  // For animation of Earl's left arm
    ImageView imgEarlRightArm;  // For animation of Earl's right arm

    /** onCreate
     * This method overrides Activity's OnCreate method.  It calls the
     * parent's method and then sets what layout to use.  It also overrides
     * the default font with a special chalkboard font, and populates the
     * listview with the high score data from the database.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Typeface font;  // Chalkboard font

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        try {
            //font = Typeface.createFromAsset(getAssets(), "DK Cool Crayon.ttf");
            font = Typeface.createFromAsset(getAssets(), "EraserRegular.ttf");
        } catch (java.lang.RuntimeException e) {
            font = null;
        }

        if (font != null) { // Set all text fields to use the chalkboard font
            TextView title = (TextView) findViewById(R.id.txtTitle);
            Button back = (Button) findViewById(R.id.btnBack);

            title.setTypeface(font);
            back.setTypeface(font);
        }

        imgEarlLeftArm = (ImageView)findViewById(R.id.imgLeftArm);  // For animation purposes
        imgEarlRightArm = (ImageView)findViewById(R.id.imgRightArm);

    } // onCreate

    /** onResume
     * Run the Earl gesturing animation.
     */
    @Override
    protected void onResume() {
        super.onResume();

        Animation animateLeftArm = new RotateAnimation(0, -70,
                Animation.RELATIVE_TO_SELF, 0.267f,
                Animation.RELATIVE_TO_SELF, 0.42f);

        animateLeftArm.setStartOffset(1000);
        animateLeftArm.setDuration(2000);
        animateLeftArm.setRepeatCount(Animation.INFINITE);

        Animation animateRightArm = new RotateAnimation(0, 70,
                Animation.RELATIVE_TO_SELF, 0.695f,
                Animation.RELATIVE_TO_SELF, 0.399f);

        animateRightArm.setStartOffset(1000);
        animateRightArm.setDuration(2000);
        animateRightArm.setRepeatCount(Animation.INFINITE);

        AnimationSet animationSetLeft = new AnimationSet(true);
        animationSetLeft.addAnimation(animateLeftArm);
        AnimationSet animationSetRight = new AnimationSet(true);
        animationSetRight.addAnimation(animateRightArm);

        animationSetLeft.setRepeatMode(Animation.REVERSE);
        animationSetRight.setRepeatMode(Animation.REVERSE);

        imgEarlLeftArm.startAnimation(animationSetLeft);
        imgEarlRightArm.startAnimation(animationSetRight);

    } // onResume

    /** onPause
     * Stop the animation upon leaving the activity.
     */
    @Override
    protected void onPause() {

        super.onPause();
        imgEarlLeftArm.clearAnimation();
        imgEarlRightArm.clearAnimation();

    } // onPause

    /** fromHighScoresToMain
     * When the user presses the "Back To Main Menu" button, this method will run
     * and they will be taken back to the main menu activity.
     *
     * @param v View containing information about the nature of the event
     */
    public void fromHighScoresToMain(View v) {

        Intent intent = new Intent(HighScoresActivity.this, MainMenuActivity.class);
        startActivity(intent);

    } // fromHighScoresToMain

} // HighScoresActivity
