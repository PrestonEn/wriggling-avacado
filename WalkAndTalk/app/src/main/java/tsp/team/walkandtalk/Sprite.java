package tsp.team.walkandtalk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Superclass for sprites drawn via openGL for active objects on screen (earl, math, etc.)
 * Created by preston
 *
 */
public abstract class Sprite {

    public static final String vs_SolidColor =
            "uniform    mat4        uMVPMatrix;" +
                    "attribute  vec4        vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    public static final String fs_SolidColor =
            "precision mediump float;" +
                    "void main() {" +
                    "  gl_FragColor = vec4(0.0,1.0,0,1);" +
                    "}";

    // TEXTURE SHADERS
    public static final String vs_Image =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 a_texCoord;" +
                    "varying vec2 v_texCoord;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  v_texCoord = a_texCoord;" +
                    "}";

    public static final String fs_Image =
            "precision mediump float;" +
                    "varying vec2 v_texCoord;" +
                    "uniform sampler2D s_texture;" +
                    "void main() {" +
                  "  gl_FragColor = texture2D( s_texture, v_texCoord );" +
                    "}";


    //Needed value of the width of the screen for live checking.
    protected float ScreenWidth;

    protected float color[];

    // X and Y positions
    protected float px, py;

    //Velocity for X and Y
    protected float vx, vy;

    // do we draw the sprite?
    protected boolean live = true;

    //do we need to do some sort of rotation?
    protected boolean rotate;

    // frames in the sprites cycle
    // default 1 for no animation
    private int aniFrames = 1;

    // current frame in the cycle
    private int currentFrame;

    //angle information
    protected float angleRate;
    protected float angle;

    public float getAngle() { //Able to get angle.
        return angle;
    }

    //vertexes need to be held in a buffer
    private  FloatBuffer vertexBuffer;

    protected float vertices[] = {
            0.0f, 0.0f,  0.0f,        // V1 - bottom left
            0.0f,  1024.0f,  0.0f,        // V2 - top left
            1024.0f, 0.0f,  0.0f,        // V3 - bottom right
            1024.0f,  1024.0f,  0.0f         // V4 - top right
    };

    protected float animUVs[];
    protected FloatBuffer textureBuffer;  // buffer for holding texture coords
    protected FloatBuffer[] textureBuffers;

    // Texture Pointer
    private int[] texPointer;

    // Abstract method to be overridden by anything that extends the sprite class.
    abstract public void draw(float[] mvpMatrix);

    // Require a get rotate method so that we can know how to draw particular enemies.
    abstract public boolean needRotate();

    // Require the making of a update method that will move the shape in space.
    abstract public void updateShape();

    // Get width and height below being abstract force square to implement the ability to view height and width.
    abstract public float getWidth();
    abstract public float getHeight();

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getAniFrames() {
        return aniFrames;
    }

    public void setAniFrames(int aniFrames) {
        this.aniFrames = aniFrames;
    }
}