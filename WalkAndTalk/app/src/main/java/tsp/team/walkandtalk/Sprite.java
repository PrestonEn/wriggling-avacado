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
public class Sprite {

    // X and Y positions
    protected float px, py;

    // do we draw the sprite?
    protected boolean live = true;

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

    public Sprite() {

        animTexture = new float[][] {
                {
                        0.0f, 1.0f,     // bottom left     (V2)
                        0.0f, 0.0f,     // top left  (V1)
                        1.0f, 1.0f,     // bottom right    (V4)
                        1.0f, 0.0f      // top right (V3)
                },
        };

        aniFrames = 1;
        initBuffers();

    }

    public void initBuffers() {
        textureBuffers = new FloatBuffer[aniFrames];

        // float = 4 bytes
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        // allocates the memory from the byte buffer
        vertexBuffer = byteBuffer.asFloatBuffer();

        // fill the vertexBuffer with the vertices
        vertexBuffer.put(vertices);

        // set the cursor position to the beginning of the buffer
        vertexBuffer.position(0);

        for (int i = 0; i<aniFrames; i++) {
            byteBuffer = ByteBuffer.allocateDirect(animTexture[0].length * 4);
            byteBuffer.order(ByteOrder.nativeOrder());
            textureBuffers[i] = byteBuffer.asFloatBuffer();
            textureBuffers[i].put(animTexture[i]);
            textureBuffers[i].position(0);
        }
        currentFrame = 0;
    }


    public void loadTexture(Context context)
    {
        int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.raw.test, options);

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

    /** The draw method for the square with the GL context */
    public void draw(GL10 gl) {

        if (!live) return;

        //gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);

        gl.glPushMatrix();
        gl.glTranslatef(px, py, 0.0f);
        gl.glRotatef(90, 0.0f, 0.0f, 1.0f);

        //Proceed through animation
        currentFrame++;
        if (currentFrame == currentFrame)
            currentFrame = 0;

        // bind the previously generated texture
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texPointer[0]);

        // Point to our buffers
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        // Set the face rotation
        gl.glFrontFace(GL10.GL_CW);

        // Point to our vertex buffer
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffers[currentFrame]);

        // Draw the vertices as triangle strip
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

        //Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glPopMatrix();

    }
}
