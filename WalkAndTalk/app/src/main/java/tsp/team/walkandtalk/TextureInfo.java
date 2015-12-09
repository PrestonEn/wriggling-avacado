package tsp.team.walkandtalk;

import java.nio.FloatBuffer;

/** Wrapper class for passing info from texture factory to
 *  draw methods
 */
public class TextureInfo {
    FloatBuffer textureBuffLoc;
    int binding;

    public TextureInfo(FloatBuffer buf, int binding){
        this.textureBuffLoc = buf;
        this.binding = binding;
    }

    public int getBinding() {
        return binding;
    }

    public FloatBuffer getTextureBuffLoc() {
        return textureBuffLoc;
    }
}
