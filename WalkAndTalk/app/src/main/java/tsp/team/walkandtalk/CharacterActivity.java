package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/** This class corresponds with the character selection activity.
 * It displays all characters available to the user, and when they
 * select one they are taken to the scene selection activity.
 * Alternatively, the user can return to the main menu.
 */
public class CharacterActivity extends Activity {

    private RecyclerView mRecyclerView;     // For horizontal listview
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /** onCreate
     * This method overrides Activity's OnCreate method.  It calls the
     * parent's method and then sets what layout to use.  It also overrides
     * the default font with a special chalkboard font, and populates the
     * recyclerview with the character images using the custom
     * list adapter.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

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

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdapter(getResources().obtainTypedArray(R.array.character_imgs), CharacterActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /** fromCharacterToMain
     * When the user presses the "Back To Main Menu" button, this method will run
     * and they will be taken back to the main menu activity.
     *
     * @param v View containing information about the nature of the event
     */
    public void fromCharacterToMain(View v) {

        Intent intent = new Intent(CharacterActivity.this, MainMenuActivity.class);
        startActivity(intent);

    } // fromCharacterToMain
}
