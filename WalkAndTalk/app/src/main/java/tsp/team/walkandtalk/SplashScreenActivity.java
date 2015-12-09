package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Date;

/**  SplashScreenActivity.java
 * This class corresponds with the splashscreen activity.  This is the activity that
 * is started when the app is run.  The app will stay on the splash screen until the
 * user presses the button to continue.  Then this activity destroys itself so that
 * if the user were to press the back button, they would exit the app instead of
 * returning to the splash screen.
 *
 */
public class SplashScreenActivity extends Activity {

    /** onCreate
     * This method overrides Activity's OnCreate method.  It calls the
     * parent's method and then sets what layout to use.  It then loads
     * a custom font, and after a delay takes the user to the next
     * activity (MainMenuActivity).
     *
     * @param savedInstanceState Bundle with saved information if page being recreated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Typeface font;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        try {
            //font = Typeface.createFromAsset(getAssets(), "DK Cool Crayon.ttf");
            font = Typeface.createFromAsset(getAssets(), "EraserRegular.ttf");
        } catch (java.lang.RuntimeException e) {
            font = null;
        }

        if (font != null) {
            TextView title = (TextView) findViewById(R.id.txtAppName);

            title.setTypeface(font);
        }

        // todo *********** FOR TESTING ONLY - REMOVE LATER **********************
        ScoresDBManager scoresDB = new ScoresDBManager(this);
        scoresDB.addHighScoreRow("1", 555, new Date().getTime(), "Earl", "Library");
        scoresDB.addHighScoreRow("2", 666, new Date().getTime() - 20000, "Tyler", "Hallway");
        scoresDB.addHighScoreRow("3", 333, new Date().getTime() - 200000, "Preston", "Library");
        scoresDB.addHighScoreRow("4", 888, new Date().getTime() - 7000000, "Stacey", "Classroom");
        scoresDB.addHighScoreRow("5", 111, new Date().getTime(), "Earl", "Hallway");
        //*******************************************************************

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this,
                        MainMenuActivity.class));
                finish();
            }
        }, 5000);

    } // onCreate

    /** onPause
     * This method overrides Activity's onPause method. It calls the
     * parent's method and then destroys the activity, because we do
     * not want the splashscreen to be reachable again.  If the user
     * presses the back button while on the next activity, they will
     * exit the app (instead of returning to this activity).
     */
    @Override
    protected void onPause() {

        super.onPause();
        finish();

    } //onPause

} // SplashScreen
