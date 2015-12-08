package tsp.team.walkandtalk;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by preston on 15-11-28.
 */
public class GLES20SurfaceView extends GLSurfaceView{

    private GameStuff gamestuff;
    private final GLES20Renderer mRenderer;

    public GLES20SurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        //Build the GameStuff object.
        gamestuff = new GameStuff(context);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new GLES20Renderer(gamestuff);
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    /**
     * This needs to be modified such that you can tell which object is being clicked on at what time.
     * Pretty sure this is going to be a bit messy so it can possibly be cleaned up later.
     * @param e
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        Log.v("EVENT", new Float(e.getRawX()).toString());

        Log.v("width", new Integer(getWidth()).toString());
        int height = getHeight();

        float x = e.getX();
        float y = e.getY();



        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                mRenderer.setAngle(
                        mRenderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}
