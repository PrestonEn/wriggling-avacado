package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by preston on 15-12-16.
 */
public class TutorialActivity extends Activity{
    /** onCreate
     * This method overrides Activity's OnCreate method.  It calls the
     * parent's method and then sets what layout to use.  It also overrides
     * the default font with a special chalkboard font, and sets up user
     * preferences if it is the first time the application is being run.
     *
     * @param savedInstanceState
     */

    private Integer images[] = {R.drawable.earl_hit_tutorial};
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        mCustomPagerAdapter = new CustomPagerAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        Typeface font;
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
    }

    /** fromTutorialToMain
     * When the user presses the "Back To Main Menu" button, this method will run
     * and they will be taken back to the main menu activity.
     *
     * @param v View containing information about the nature of the event
     */
    public void fromTutorialToMain(View v) {

        Intent intent = new Intent(TutorialActivity.this, MainMenuActivity.class);
        startActivity(intent);

    } // fromTutorialToMain

    @Override
    public void onBackPressed(){
    }
}


