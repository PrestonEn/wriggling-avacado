package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
     * parent's method and then sets what layout to use.
     *
     * @param savedInstanceState Bundle with saved information if page being recreated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

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

    /** startApp
     * When the user presses the displayed button, this method will run
     * and they will be taken to the next activity (MainMenuActivity).
     *
     * @param v View containing information about the nature of the event
     */
    public void startApp(View v) {

        Intent intent = new Intent(SplashScreenActivity.this, MainMenuActivity.class);
        startActivity(intent);

    } // startApp

} // SplashScreen
