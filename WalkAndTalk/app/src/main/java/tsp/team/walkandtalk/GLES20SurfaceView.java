package tsp.team.walkandtalk;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by preston on 15-11-28.
 */
public class GLES20SurfaceView extends GLSurfaceView{

    private final GLES20Renderer mRenderer;
    public TextView txtScore;

    public GLES20SurfaceView(Activity context, SceneWrapper scene, TextView score, long prevHighScore) {
        super(context);
        txtScore = score;

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new GLES20Renderer(scene, txtScore, prevHighScore);
        mRenderer.mActivityContext = context;
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
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
            float touchX = ((e.getX()/((float)this.getWidth()*0.5f))-1)*((float)this.getWidth()/this.getHeight());
            float touchY = (e.getY()/((float)this.getHeight()*0.5f))-1;
            touchY = -touchY; //Above is coordinate maps.

//        Log.e("test", touchX + "   " + touchY);
//        txtScore.setText("Touched: " + touchX + ", " + touchY);

            if(detectCharTouch(touchX, touchY, mRenderer.getGamestuff().getCharacter())){ // Jump.
                mRenderer.getGamestuff().getCharacter().applyJump(); // Signal the jump.
                return true; // Early exit.
            }
        }

        return true;
    }

    private boolean detectSpriteTouch(){

        return false;
    }

    public GameStuff passBack(){
        return mRenderer.getGamestuff();
    }

    /**
     * This method is meant to detect if a particular pair of X,Y coordinates in opengl space collide
     * within the bounding box of the character in game.
     * @param touchX X coordinate in the opengl system.
     * @param touchY Y coordinate in the opengl system.
     * @param c Character reference.
     * @return Boolean if you are clicking on the character or not.
     */
    private boolean detectCharTouch(float touchX, float touchY, Character c){
        return (Math.abs(touchX - c.getSquare().px) * 2 < (c.getSquare().getWidth())) &&
                (Math.abs(touchY - c.getSquare().py) * 2 < (c.getSquare().getHeight()));
    }
}