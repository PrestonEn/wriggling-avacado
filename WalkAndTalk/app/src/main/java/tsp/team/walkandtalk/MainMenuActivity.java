package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/** This class corresponds with the main menu activity. It displays
 * options for the user to navigate to different sections of the
 * application.
 *
 */
public class MainMenuActivity extends Activity {

    ImageView imgEarlArm; // For animation of Earl

    /** onCreate
     * This method overrides Activity's OnCreate method.  It calls the
     * parent's method and then sets what layout to use.  It also overrides
     * the default font with a special chalkboard font, and sets up user
     * preferences if it is the first time the application is being run.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Typeface font; // Chalkboard font

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        try {
            //font = Typeface.createFromAsset(getAssets(), "DK Cool Crayon.ttf");
            font = Typeface.createFromAsset(getAssets(), "EraserRegular.ttf");
        } catch (java.lang.RuntimeException e) {
            font = null;
        }

        if (font != null) { // Set all text fields to use the chalkboard font
            TextView title = (TextView) findViewById(R.id.txtTitle);
            Button quickPlay = (Button) findViewById(R.id.btnQuickPlay);
            Button play = (Button) findViewById(R.id.btnPlay);
            Button highScores = (Button) findViewById(R.id.btnScores);
            Button tutorial = (Button) findViewById(R.id.btnTutorial);
            Button settings = (Button) findViewById(R.id.btnSettings);

            title.setTypeface(font);
            quickPlay.setTypeface(font);
            play.setTypeface(font);
            highScores.setTypeface(font);
            tutorial.setTypeface(font);
            settings.setTypeface(font);
        }

        imgEarlArm = (ImageView)findViewById(R.id.imgArm); // For animation purposes

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstRun = !preferences.getBoolean("runBefore",false);

        if (firstRun) { // Set default user preferences on first run
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("runBefore", true);
            editor.putBoolean(getString(R.string.saved_sound_preference), true);
            editor.commit();
        }

    } // onCreate

    /** onResume
     * Run the Earl gesturing animation.
     */
    @Override
    protected void onResume() {
        super.onResume();

        Animation animate = new RotateAnimation(0, 80,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.4f);

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


    /** startGame
     * When the user presses the "Quick Play" button, this method will run
     * and they will be taken to the game activity.
     *
     * @param v View containing information about the nature of the event
     */
    public void startGame(View v) {

        Resources r = getResources();

        int position = (int)(Math.random()*r.obtainTypedArray(R.array.character_imgs).length());
        SceneWrapper scene = new SceneWrapper(r.obtainTypedArray(R.array.character_names).getString(position),
                r.obtainTypedArray(R.array.character_run).getResourceId(position, -1),
                r.obtainTypedArray(R.array.character_jump).getResourceId(position, -1),
                r.obtainTypedArray(R.array.character_fall).getResourceId(position, -1),
                getMultipleIds(r, R.array.character_sounds, position)

        );

        int back = (int)(Math.random()*r.obtainTypedArray(R.array.background_imgs).length());
        scene.setSceneName(r.obtainTypedArray(R.array.scene_names).getString(back));
        scene.setSceneBackground(r.obtainTypedArray(R.array.scene_imgs).getResourceId(back, -1));
        scene.setEnemiesStill(getMultipleIds(r, R.array.enemies_still, back));
        scene.setEnemiesRun(getMultipleIds(r, R.array.enemies_run, back));
        scene.setEnemiesFly(getMultipleIds(r, R.array.enemies_fly, back));

        Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
        intent.putExtra("scene", scene);
        startActivity(intent);

    } // startGame

    /**
     * Create an integer array of the multiple ResourceIDs based on the
     * passed id of the TypedArray and the passed scene/character position.
     * @param r         Resources
     * @param array     ID of the TypedArray
     * @param position  Position of the corresponding multiple ID array
     * @return          Int array of enemy/sound ResourceIDs for proper scene/character
     */
    private int[] getMultipleIds(Resources r, int array, int position){
        int id = r.obtainTypedArray(array).getResourceId(position, -1);
        TypedArray ids = r.obtainTypedArray(id);
        int[] resource_ids = new int[ids.length()];
        for (int i = 0; i < ids.length(); i++) {
            resource_ids[i] = ids.getResourceId(i, -1);
        }
        ids.recycle();
        return resource_ids;
    } // getMultipleIds

    /** goToCharacter
     * When the user presses the "Play" button, this method will run
     * and they will be taken to the character select activity.
     *
     * @param v View containing information about the nature of the event
     */
    public void goToCharacter(View v) {

        Intent intent = new Intent(MainMenuActivity.this, CharacterActivity.class);
        startActivity(intent);

    } // goToCharacter

    /** goToHighScores
     * When the user presses the "High Scores" button, this method will run
     * and they will be taken to the high scores activity.
     *
     * @param v View containing information about the nature of the event
     */
    public void goToHighScores(View v) {

        Intent intent = new Intent(MainMenuActivity.this, HighScoresActivity.class);
        startActivity(intent);

    } // goToHighScores

    /** goToSettings
    * When the user presses the "Settings" button, this method will run
    * and they will be taken to the settings activity.
    *
            * @param v View containing information about the nature of the event
    */
    public void goToSettings(View v) {

        Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
        startActivity(intent);

    } // goToSettings


    /** goToTutorial
     * When the user presses the "Tutorial" button, this method will run
     * and they will be taken to the tutorial activity.
     *
     * @param v View containing information about the nature of the event
     */
    public void goToTutorial(View v) {

        Intent intent = new Intent(MainMenuActivity.this, TutorialActivity.class);
        startActivity(intent);

    } // goToTutorial

    @Override
    public void onBackPressed(){
    }

} // MainMenuActivity