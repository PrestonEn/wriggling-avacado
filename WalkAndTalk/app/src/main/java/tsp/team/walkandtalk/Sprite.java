package tsp.team.walkandtalk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

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

    protected static final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // The matrix must be included as a modifier of gl_Position.
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    protected static String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    // END TEXTURE SHADERS


    //Needed value of the width of the screen for live checking.
    protected float ScreenWidth;

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

    /**
     * Loads the current texture for the sprite to draw eventually.
     * @param mContext Standard android woogity boogity.
     * @param resourceID Reference to the texture that we will be drawing.
     */
    public void loadTexture(Context mContext,int resourceID)
    {
        // Create our UV coordinates. (Texture coords)
        animUVs = new float[]{
                0.0f, 0.0f,
                0.0f, 0.25f,
                0.25f, 0.25f,
                0.25f, 0.0f

        };

        // The texture buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(animUVs.length  * 4);
        bb.order(ByteOrder.nativeOrder());
        textureBuffer = bb.asFloatBuffer();
        textureBuffer.put(animUVs);
        textureBuffer.position(0);

        // Generate Textures, if more needed, alter these numbers.
        int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);

        // Temporary create a bitmap
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), resourceID);

        // Bind texture to texturename
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);

        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        // Set wrapping mode
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);

        // We are done using the bitmap so we should recycle it.
        bmp.recycle();
    }

    //Abstract method to be overridden by anything that extends the sprite class.
    abstract public void draw(float[] mvpMatrix);

    //Require a get rotate method so that we can know how to draw particular enemies.
    abstract public boolean needRotate();

    //Require the making of a update method that will move the shape in space.
    abstract public void updateShape();

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