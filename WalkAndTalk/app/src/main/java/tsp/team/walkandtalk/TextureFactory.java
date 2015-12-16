package tsp.team.walkandtalk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

/**
 * This class is a factory based design pattern which builds and binds textures in opengles for
 * drawing to our sprite class. The scenewrapper is what allows us to customize what textures to bind.
 * Any other information is passed out via a TextureInfo object.
 */
public class TextureFactory {

    SceneWrapper sceneWrapper; // References below are all here for constant time getters.
    TextureInfo character_run;
    TextureInfo character_jump;
    TextureInfo character_fall;
    TextureInfo scene_back;
    TextureInfo[] scene_enemies_still;
    TextureInfo[] scene_enemies_run;
    TextureInfo[] scene_enemies_fly;

    /**
     * Initialize the TextureFactory. Binds all textures based on the scene object passed in.
     * @param c Context of this texture factory.
     * @param scene SceneWrapper for custom scene and character textures to bind.
     */
    public TextureFactory(Context c, SceneWrapper scene){
        sceneWrapper = scene;

        character_run = makeTexture(c, sceneWrapper.getCharacterRun());
        character_fall = makeTexture(c, sceneWrapper.getCharacterFall());
        character_jump = makeTexture(c, sceneWrapper.getCharacterJump());
        scene_back = makeTexture(c, sceneWrapper.getSceneBackground());

        scene_enemies_still = new TextureInfo[sceneWrapper.getEnemiesStillLength()];
        for (int i = 0; i < sceneWrapper.getEnemiesStillLength(); i++){
            scene_enemies_still[i] = makeTexture(c, sceneWrapper.getEnemiesStill(i));
        }
        scene_enemies_run = new TextureInfo[sceneWrapper.getEnemiesRunLength()];
        for (int i = 0; i < sceneWrapper.getEnemiesRunLength(); i++){
            scene_enemies_run[i] = makeTexture(c, sceneWrapper.getEnemiesRun(i));
        }
        scene_enemies_fly = new TextureInfo[sceneWrapper.getEnemiesFlyLength()];
        for (int i = 0; i < sceneWrapper.getEnemiesFlyLength(); i++){
            scene_enemies_fly[i] = makeTexture(c, sceneWrapper.getEnemiesFly(i));
        }
    }

    /**
     * Get the character run sprite sheet.
     * @return TextureInfo of sprite sheet.
     */
    public TextureInfo getCharacter_run() {
        return character_run;
    }

    /**
     * Get the texture of the characters jump image.
     * @return TextureInfo of the texture.
     */
    public TextureInfo getCharacter_jump() {
        return character_jump;
    }

    /**
     * Get the sprite sheet of the falling animation.
     * @return TextureInfo of the sprite sheet.
     */
    public TextureInfo getCharacter_fall() {
        return character_fall;
    }

    /**
     * This method returns the binding of the background texture.
     * @return TextureInfo of the background.
     */
    public TextureInfo getScene_back() {
        return scene_back;
    }

    /**
     * Return a still enemy texture from the pool of still enemies from the location.
     * @return TextureInfo of a random still enemy.
     */
    public TextureInfo getScene_enemies_still() {
        return scene_enemies_still[(int)(Math.random()*scene_enemies_still.length)];
    }

    /**
     * Return a running enemy texture from the pool of running enemies from the location.
     * @return TextureInfo of a random running enemy.
     */
    public TextureInfo getScene_enemies_run() {
        return scene_enemies_run[(int)(Math.random()*scene_enemies_run.length)];
    }

    /**
     * Return a flying enemy texture from the pool of flying enemies from the location.
     * @return TextureInfo of a random flying enemy.
     */
    public TextureInfo getScene_enemies_fly() {
        return scene_enemies_fly[(int)(Math.random()*scene_enemies_fly.length)];
    }

    /**
     * Heavily abstracted method which allows you to custom make textures via inputted scenewrapper from
     * the constructor.
     * @param mContext This factories context.
     * @param resourceID resource ID of the texture to load and bind.
     * @return TextureInfo of bound texture in it's saved state.
     */
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
}