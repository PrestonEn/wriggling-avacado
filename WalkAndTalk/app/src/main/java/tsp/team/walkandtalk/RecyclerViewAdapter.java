package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The RecyclerViewAdapter is the custom adapter for the RecyclerViewer,
 * used to create a horizontal listview for selecting characters and
 * scenes, by scrolling left and right instead of up and down.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private TypedArray images; // ResourceIDs of the character/scene images
    private TypedArray names;  // Strings of the character/scene names
    private Activity activity; // CharacterActivity or SceneActivity
    private Typeface font;

    /**
     * Constructor for our custom RecyclerViewAdapter.
     *
     * @param imgs TypedArray of ResourceIDs of the character/scene images
     * @param n    TyedArray of strings of the character/scene names
     * @param a    Activity that created the RecyclerViewAdapter
     * @param f    Font to use for the names
     */
    public RecyclerViewAdapter(TypedArray imgs, TypedArray n, Activity a, Typeface f) {
        images = imgs;
        names = n;
        activity = a;
        font = f;
    } // Constructor

    /**
     * ViewHolder to hold the different view items included in te RecyclerView, and
     * to set the onClick listener for the ImageView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public IMyViewHolderClicks mListener;
        public ImageView mImageView;
        public TextView mTextView;
        public ViewHolder(View v, IMyViewHolderClicks listener) {
            super(v);
            mListener = listener;
            mTextView = (TextView)v.findViewById(R.id.textView);
            mImageView = (ImageView)v.findViewById(R.id.imageView);
            mImageView.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v instanceof ImageView) {
                mListener.onClick((ImageView) v);
            }
        }

        public static interface IMyViewHolderClicks {
            public void onClick(ImageView callerImage);
        }
    } // ViewHolder

    /**
     * Create new view for the RecyclerView (invoked by the layout manager).
     * @param parent
     * @param viewType
     * @return ViewHolder to hold the new RecyclerView
     */
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_recycler_view, parent, false);

        ViewHolder vh = new ViewHolder(v, new ViewHolder.IMyViewHolderClicks() {
            public void onClick(ImageView callerImage) {

                Resources r = activity.getResources();

                if (activity.getClass().getSimpleName().equals("CharacterActivity")) {

                    int position = (int)callerImage.getTag();
                    SceneWrapper scene = new SceneWrapper(r.obtainTypedArray(R.array.character_names).getString(position),
                            r.obtainTypedArray(R.array.character_run).getResourceId(position, -1),
                            r.obtainTypedArray(R.array.character_jump).getResourceId(position, -1),
                            r.obtainTypedArray(R.array.character_fall).getResourceId(position, -1)
                    );
                    Intent intent = new Intent(activity, SceneActivity.class);
                    intent.putExtra("scene", scene);
                    activity.startActivity(intent);

                } else if (activity.getClass().getSimpleName().equals("SceneActivity")) {
                    SceneWrapper scene = (SceneWrapper)activity.getIntent().getSerializableExtra("scene");
                    int position = (int)callerImage.getTag();
                    scene.setSceneName(r.obtainTypedArray(R.array.scene_names).getString(position));
                    scene.setSceneBackground(r.obtainTypedArray(R.array.scene_imgs).getResourceId(position, -1));
                    scene.setEnemiesStill(getEnemyIds(r, R.array.enemies_still, position));
                    scene.setEnemiesRun(getEnemyIds(r, R.array.enemies_run, position));
                    scene.setEnemiesFly(getEnemyIds(r, R.array.enemies_fly, position));

                    Intent intent = new Intent(activity, GameActivity.class);
                    intent.putExtra("scene", scene);
                    activity.startActivity(intent);
                }
            }
        });

        return vh;
    } // onCreateViewHolder

    /**
     * Create an integer array of the enemy image ResourceIDs based on the
     * passed id of the TypedArray and the passed scene position.
     * @param r         Resources
     * @param array     ID of the TypedArray
     * @param position  Position of the corresponding scene enemy array
     * @return          Int array of enemy image ResourceIDs for proper scene
     */
    private int[] getEnemyIds(Resources r, int array, int position){
        int id = r.obtainTypedArray(array).getResourceId(position, -1);
        TypedArray enemies = r.obtainTypedArray(id);
        int[] enemy_ids = new int[enemies.length()];
        for (int i = 0; i < enemies.length(); i++) {
            enemy_ids[i] = enemies.getResourceId(i, -1);
        }
        enemies.recycle();
        return enemy_ids;
    } // getEnemyIds

    /**
     * Replaces contents of the view with the proper image/name for this position
     * (invoked by the layout manager).
     * @param holder    ViewHolder of our view elements
     * @param position  Integer of current position in the list to populate view with
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImageView.setImageResource(images.getResourceId(position, -1));
        holder.mImageView.setTag(position);
        holder.mTextView.setTypeface(font);
        holder.mTextView.setText(names.getString(position));
    } // onBindViewHolder

    /**
     * Return the size of the dataset (number of images), as invoked by the
     * layout manager.
     * @return  integer size of our RecyclerView list
     */
    @Override
    public int getItemCount() {
        return images.length();
    } // getItemCount
    
} // RecyclerViewAdapter