package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/** This class corresponds with the high scores activity. It displays
 * the high scores of the user in descending order using a listview,
 * showing the score, date, time, character and scene.
 */
public class HighScoresActivity extends Activity {

    ImageView imgEarlLeftArm;    // For animation of Earl's left arm
    ImageView imgEarlRightArm;   // For animation of Earl's right arm
    ListView hs_listView;        // List of high scores
    ArrayList<HighScore> scores; // ArrayList of high scores returned by database
    Typeface font;  // Chalkboard font

    /** onCreate
     * This method overrides Activity's OnCreate method.  It calls the
     * parent's method and then sets what layout to use.  It also overrides
     * the default font with a special chalkboard font, and populates the
     * listview with the high score data from the database using the custom
     * list adapter.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        ScoresDBManager scoresDB; // To get all high scores
        scoresDB = new ScoresDBManager(this);
        HighScoresAdapter hs_adapter;

        // todo *********** FOR TESTING ONLY - REMOVE LATER **********************
        scoresDB.addHighScoreRow("1", 555, new Date().getTime(), "Earl", "Library");
        scoresDB.addHighScoreRow("2", 666, new Date().getTime() - 20000, "Tyler", "Hallway");
        scoresDB.addHighScoreRow("3", 333, new Date().getTime() - 200000, "Preston", "Library");
        scoresDB.addHighScoreRow("4", 888, new Date().getTime() - 7000000, "Stacey", "Classroom");
        scoresDB.addHighScoreRow("5", 111, new Date().getTime(), "Earl", "Hallway");
        //*******************************************************************

        hs_listView = (ListView) findViewById(R.id.listView);

        scores = scoresDB.retrieveHighScoreRows(); // Get all high scores in scores table
        scoresDB.close();  // Close database manager

        hs_adapter = new HighScoresAdapter(this, scores); // Set list adapter
        hs_listView.setAdapter(hs_adapter);

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

    /** onRestart
     * This method overrides Activity's OnRestart method. It calls the
     * onCreate method to refresh the list every time we return to this
     * activity.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        onCreate(null);
    } // onRestart

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

    /** HighScoresAdapter
     * This class is a custom adapter that handles the formatting and
     * populating of the listview.
     */
    private class HighScoresAdapter extends ArrayAdapter<HighScore> {

        /** HighScoresAdapter
         * This is the constructor, which simply passes in the parameters to the
         * constructor of ArrayAdapter.
         *
         * @param context    Context for the listview
         * @param locations   Array to populate the list with
         */
        public HighScoresAdapter(Context context, ArrayList<HighScore> locations) {
            super(context, 0, locations);
        } // constructor

        /** getView
         * This function returns the view to be used for a specific list item.  For high scores,
         * there is only one type of view.
         *
         * @param position      Position of item in list
         * @param convertView   Previous view of this position in the list
         * @param parent        For layoutInflater
         * @return              The view for that list item
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            HighScore hs = getItem(position);

            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_high_score, parent, false);
            }

            // Get display fields
            TextView txtScore = (TextView) convertView.findViewById(R.id.txtScore);
            TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            TextView txtCharScene = (TextView) convertView.findViewById(R.id.txtCharScene);

            // Set display fields
            txtScore.setText(Long.toString(hs.score) + " m");
            if (font != null) txtScore.setTypeface(font);
            txtDate.setText("On " + hs.getDate() + " at " + hs.getTime());
            txtCharScene.setText("With " + hs.character + " in the " + hs.scene);

            // Return the completed view to render on screen
            return convertView;
        } // getView

    } // HighScoresAdapter

} // HighScoresActivity
