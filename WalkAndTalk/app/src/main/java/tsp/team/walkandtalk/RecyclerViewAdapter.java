package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Stacey on 11/12/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private TypedArray images;
    private Activity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public IMyViewHolderClicks mListener;
        public ImageView mImageView;
        public ViewHolder(ImageView v, IMyViewHolderClicks listener) {
            super(v);
            mListener = listener;
            mImageView = v;
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
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(TypedArray imgs, Activity a) {
        images = imgs;
        activity = a;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_character, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...

        ViewHolder vh = new ViewHolder((ImageView) v, new ViewHolder.IMyViewHolderClicks() {
            public void onClick(ImageView callerImage) {

                Resources r = activity.getResources();

                if (activity.getClass().getSimpleName().equals("CharacterActivity")) {

                    int position = (int)callerImage.getTag();
                    SceneWrapper scene = new SceneWrapper(r.obtainTypedArray(R.array.character_names).getString(position),
                            r.obtainTypedArray(R.array.character_run).getResourceId(position, -1),
                            r.obtainTypedArray(R.array.character_jump).getResourceId(position, -1),
                            r.obtainTypedArray(R.array.character_fall).getResourceId(position, -1)
                    );
                    Intent intent = new Intent(activity, MainMenuActivity.class);
                    intent.putExtra("scene", scene);
                    activity.startActivity(intent);

                } else if (activity.getClass().getSimpleName().equals("SceneActivity")) {
                    SceneWrapper scene = (SceneWrapper)activity.getIntent().getSerializableExtra("scene");

                    // todo Put all arrays into scenewrapper

                    Intent intent = new Intent(activity, GameActivity.class);
                    intent.putExtra("scene", scene);
                    activity.startActivity(intent);
                }
            }
        });

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mImageView.setImageResource(images.getResourceId(position, -1));
        holder.mImageView.setTag(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return images.length();
    }
}
