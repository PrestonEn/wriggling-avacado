package tsp.team.walkandtalk;

import android.util.Log;

import java.nio.FloatBuffer;

/** Wrapper class for passing info from texture factory to
 *  draw methods
 */
public class TextureInfo {
    int binding;

    public TextureInfo(int binding){
        this.binding = binding;
    }

    public int getBinding() {
        return binding;
    }
}
