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

    private final GLES20Renderer mRenderer;

    public GLES20SurfaceView(Context context, SceneWrapper scene) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new GLES20Renderer(scene);
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
        touchX = -touchX;

        if(detectTouch(touchX,touchY,mRenderer.getGamestuff().getCharacter())){
            Log.e("TOUCH WORKED","YAAAAAAAAAAY");
        }

        return true;
    }

    private boolean detectTouch(float touchX, float touchY, Character c){
        float interval = 0.0f; // Open interval around the touch to examine.

        Log.e("px",c.getSquare().px+"");
        Log.e("touches",touchX+","+touchY);

        return (Math.abs(touchX - c.getSquare().px) * 2 < (interval + c.getSquare().getWidth())) &&
                (Math.abs(touchY - c.getSquare().py) * 2 < (interval + c.getSquare().getHeight()));
    }
}