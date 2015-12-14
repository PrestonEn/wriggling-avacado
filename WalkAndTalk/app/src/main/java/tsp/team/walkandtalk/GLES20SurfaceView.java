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


    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //Log.e("ratio",ratio+"");//2560
        float touchX = ((e.getX()/((float)this.getWidth()*0.5f))-1)*((float)this.getWidth()/this.getHeight());
        float touchY = (e.getY()/((float)this.getHeight()*0.5f))-1; //invert this
        touchY = -touchY;

        Log.e("test", touchX + "   " + touchY);
//        txtScore.setText("Touched: " + touchX + ", " + touchY);

        if(detectTouch(touchX,touchY,mRenderer.getGamestuff().getCharacter())){
            Log.e("TOUCH WORKED","YAAAAAAAAAAY");
        }

        return true;
    }

    private boolean detectTouch(float touchX, float touchY, Character c){
        float interval = 0.0f; // Open interval around the touch to examine.

        return (Math.abs(touchX - c.getSquare().px) * 2 < (interval + c.getSquare().getWidth())) &&
                (Math.abs(touchY - c.getSquare().py) * 2 < (interval + c.getSquare().getHeight()));
    }

    public GameStuff passBack(){
        return mRenderer.getGamestuff();
    }
}