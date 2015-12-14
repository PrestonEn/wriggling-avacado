package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GLES20SurfaceView extends GLSurfaceView{

    private final GLES20Renderer mRenderer;
    private GestureDetectorCompat mDetector;
    public TextView txtScore;

    public GLES20SurfaceView(Activity context, SceneWrapper scene, TextView score) {
        super(context);
        txtScore = score;

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new GLES20Renderer(scene, txtScore);
        mRenderer.mActivityContext = context;
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        mDetector = new GestureDetectorCompat(this.getContext(), new EnemyGestureListener(mRenderer));
    }

    /**
     * This method takes in a touch to the screen and tests what it has collided with.
     * Implementation is as follows. First scale coordinate systems to opengl system. Second, if the
     * character has been touched, render and apply a jump. Third, determine if an enemy was touched.
     * From there we would determine if it was the correct gesture.
     * @param e MotionEvent supplied by the surface view to examine.
     * @return Always returns true since we are handling the event actively.
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(mRenderer.getGamestuff() != null){ // Prevent early click errors.
            this.mDetector.onTouchEvent(e); // Pass the touch event into the GestureDetector.
        }
        return true;
    }
}

/**
 * This is a private class meant to implement detection of different gestures for killing enemies.
 */
class EnemyGestureListener extends GestureDetector.SimpleOnGestureListener{

    private GLES20Renderer mRenderer;

    public EnemyGestureListener(GLES20Renderer renderer){
        this.mRenderer = renderer;
    }

    /**
     * This is the gesture type that allows for Earl to jump.
     * @param e MotionEvent to examine.
     * @return Boolean handled or not.
     */
    @Override
    public boolean onDown(MotionEvent e){
        int height = this.mRenderer.getGamestuff().getScreenHeight();
        int width = this.mRenderer.getGamestuff().getScreenWidth();

        float touchX = ((e.getX()/((float)width*0.5f))-1)*((float)width/(float)height);
        float touchY = (e.getY()/((float)height*0.5f))-1;
        touchY = -touchY; //Above is coordinate maps.

        if(detectCharTouch(touchX, touchY, mRenderer.getGamestuff().getCharacter().getSquare())){ // Jump if on Earl..
            mRenderer.getGamestuff().getCharacter().applyJump(); // Signal the jump.
        }

        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        // Enemy kill information.
        return true;
    }

    /**
     * This method is meant to detect if a particular pair of X,Y coordinates in opengl space collide
     * within the bounding box of the character in game.
     * @param touchX X coordinate in the opengl system.
     * @param touchY Y coordinate in the opengl system.
     * @param c Character reference or sprite reference of the square..
     * @return Boolean if you are clicking on the character or not.
     */
    private boolean detectCharTouch(float touchX, float touchY, Sprite c){
        return (Math.abs(touchX - c.px) * 2 < (c.getWidth())) &&
                (Math.abs(touchY - c.py) * 2 < (c.getHeight()));
    }
}