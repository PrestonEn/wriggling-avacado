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


    // X and Y positions
    protected float px, py;

    // do we draw the sprite?
    protected boolean live = true;

    //do we need to do some sort of rotation?
    protected boolean rotate;

    // frames in the sprites cycle
    private int aniFrames;

    // current frame in the cycle
    private int currentFrame;

    //vertexes need to be held in a buffer
    private  FloatBuffer vertexBuffer;
    protected float vertices[] = {
            0.0f, 0.0f,  0.0f,        // V1 - bottom left
            0.0f,  1024.0f,  0.0f,        // V2 - top left
            1024.0f, 0.0f,  0.0f,        // V3 - bottom right
            1024.0f,  1024.0f,  0.0f         // V4 - top right
    };

    private FloatBuffer textureBuffer;  // buffer for holding texture coords
    private FloatBuffer[] textureBuffers;

    protected float animTexture[][];


    // Texture Pointer
    private int[] texPointer;

    /**
     * Loads the current texture for the sprite to draw eventually.
     * @param context Standard android woogity boogity.
     * @param resourceID Reference to the texture that we will be drawing.
     */
    public void loadTexture(Context context,int resourceID)
    {
        int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID, options);

            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        texPointer = textureHandle;
    }

    //Abstract method to be overridden by anything that extends the sprite class.
    abstract public void draw(float[] mvpMatrix);

    //Require a get rotate method so that we can know how to draw particular enemies.
    abstract public boolean needRotate();

    //Require the making of a update method that will move the shape in space.
    abstract public void updateShape();
}
