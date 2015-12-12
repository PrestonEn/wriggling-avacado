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

    SceneWrapper sceneWrapper;
    TextureInfo character_run;
    TextureInfo character_jump;
    TextureInfo character_fall;
    TextureInfo scene_back;
    TextureInfo[] scene_enemies_still;
    TextureInfo[] scene_enemies_run;
    TextureInfo[] scene_enemies_fly;

    public TextureFactory(Context c, SceneWrapper scene){
        int i;

        sceneWrapper = scene;

        character_run = makeTexture(c, sceneWrapper.getCharacterRun());
        character_fall = makeTexture(c, sceneWrapper.getCharacterFall());
        character_jump = makeTexture(c, sceneWrapper.getCharacterJump());
        scene_back = makeTexture(c, sceneWrapper.getSceneBackground());
        scene_enemies_still = new TextureInfo[sceneWrapper.getEnemiesStillLength()];
        for (i = 0; i < sceneWrapper.getEnemiesStillLength(); i++){
            scene_enemies_still[i] = makeTexture(c, sceneWrapper.getEnemiesStill(i));
        }
        scene_enemies_run = new TextureInfo[sceneWrapper.getEnemiesRunLength()];
        for (i = 0; i < sceneWrapper.getEnemiesRunLength(); i++){
            scene_enemies_run[i] = makeTexture(c, sceneWrapper.getEnemiesRun(i));
        }
        scene_enemies_fly = new TextureInfo[sceneWrapper.getEnemiesFlyLength()];
        for (i = 0; i < sceneWrapper.getEnemiesFlyLength(); i++){
            scene_enemies_fly[i] = makeTexture(c, sceneWrapper.getEnemiesFly(i));
        }

/*      private static int frame_count = 16;
        private static int frame_division = (int)Math.sqrt(frame_count);

        character_run = new TextureInfo[frame_count];
        character_fall = new TextureInfo[frame_count];

       for (i = 0; i < frame_division; i++) {
            for (j = 0; j < frame_division; j++) {
                animUVs = new float[]{
                    (float)j/frame_division, (float)i/frame_division,
                    (float)j/frame_division, (float)(i + 1)/frame_division,
                    (float)(j + 1)/frame_division, (float)(i + 1)/frame_division,
                    (float)(j + 1)/frame_division, (float)i/frame_division
                };
            }
        } */
    }

    public TextureInfo getCharacter_run() {
        return character_run;
    }

    public TextureInfo getCharacter_jump() {
        return character_jump;
    }

    public TextureInfo getCharacter_fall() {
        return character_fall;
    }

    public TextureInfo getScene_back() {
        return scene_back;
    }

    public TextureInfo getScene_enemies_still() {
        return scene_enemies_still[(int)(Math.random()*scene_enemies_still.length)];
    }

    public TextureInfo getScene_enemies_run() {
        return scene_enemies_run[(int)(Math.random()*scene_enemies_run.length)];
    }

    public TextureInfo getScene_enemies_fly() {
        return scene_enemies_fly[(int)(Math.random()*scene_enemies_fly.length)];
    }

    private TextureInfo makeTexture(Context mContext, int resourceID){

        // Generate Textures, if more needed, alter these numbers.
        int bindings[] = new int[1];
        // Generate Textures, if more needed, alter these numbers.
        GLES20.glGenTextures(1, bindings, 0);

        // Retrieve our image from resources.

        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), resourceID);

        // Bind texture to texturename
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, bindings[0]);

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

        return new TextureInfo(bindings[0]);
    }

    public TextureInfo getTestTexture() {
        return character_run;
    }

    public TextureInfo getEarlTexture(){
        return character_jump;
    }

}