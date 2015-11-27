package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import tsp.team.walkandtalk.R;

/**
 *
 */
public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

    }

    /** startApp
     * When the user presses the displayed button, this method will run
     * and they will be taken to the next activity (Main).
     *
     * @param v View containing information about the nature of the event
     */
    public void startApp(View v) {

        Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
        startActivity(intent);

    } // startApp
}
