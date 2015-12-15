package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

/**
 *
 */
public class GameActivity extends Activity {

    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        SceneWrapper scene = (SceneWrapper)i.getSerializableExtra("scene");
        setContentView(R.layout.activity_game);

        Typeface font;

        try {
            //font = Typeface.createFromAsset(getAssets(), "DK Cool Crayon.ttf");
            font = Typeface.createFromAsset(getAssets(), "EraserRegular.ttf");
        } catch (java.lang.RuntimeException e) {
            font = null;
        }

        TextView details = (TextView) findViewById(R.id.txtDetails);
        TextView highScore = (TextView) findViewById(R.id.txtHighScore);
        TextView txtScore = (TextView) findViewById(R.id.txtScore);

        if (font != null) { // Set all text fields to use the chalkboard font

            details.setTypeface(font);
            highScore.setTypeface(font);
            txtScore.setTypeface(font);
        }

        details.setText(scene.getCharacterName() + " in the " + scene.getSceneName());
        ScoresDBManager scoresDB = new ScoresDBManager(this);
        HighScore hs = scoresDB.getMaxScore(); // Get max high score in scores table
        scoresDB.close();  // Close database manager
        highScore.setText("High Score: " + hs.score);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        mGLView = new GLES20SurfaceView(GameActivity.this , scene, txtScore, hs.score);
        LinearLayout surface = (LinearLayout)findViewById(R.id.test);
        surface.addView(mGLView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }
}
