package tsp.team.walkandtalk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import tsp.team.walkandtalk.R;

/**
 * Created by preston on 15-12-09.
 */
public class TextureFactory {

    TextureInfo earlFrame1;
    TextureInfo testTexture;

    //private Sprite theSprite;

    public TextureFactory(Context c){
        earlFrame1 = makeTexture(c, R.raw.test);
        testTexture = makeTexture(c, R.raw.test);
    }

    private TextureInfo makeTexture(Context mContext, int resourceID){
        // Log.e("LOADINGATEXTUREBOUNDTO", ""+bind);
        FloatBuffer textureBuffer;
        // Create our UV coordinates. (Texture coords)
        float animUVs[] = new float[]{
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
        int bindings[] = new int[1];
        GLES20.glGenTextures(1, bindings, 0);

        // Temporary create a bitmap
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.raw.earl);
        Log.e("bmp test: ", bmp.getPixel(0,0)+"");

        // Bind texture to texturename
        //GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, bindings[0]);
        Log.e("Lo, tis binding 0:" , ""+bindings[0]);

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

        return new TextureInfo(textureBuffer, bindings[0]);
    }

    public TextureInfo getTestTexture() {
        return testTexture;
    }

    public TextureInfo getEarlTexture(){
        return earlFrame1;
    }

}