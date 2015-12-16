package tsp.team.walkandtalk;

import java.nio.FloatBuffer;

/**
 * Superclass for sprites drawn via openGL for active objects on screen (earl, math, etc.).
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

    /**
     * Abstract method to be overridden by anything that extends the sprite class.
     * @param mvpMatrix Model view projection matrix
     */
    abstract public void draw(float[] mvpMatrix);

    /**
     * Require a get rotate method so that we can know how to draw particular enemies.
     * @return Boolean if we need to rotate the sprite when drawing.
     */
    abstract public boolean needRotate();

    /**
     * Require the making of a update method that will move the shape in space.
     */
    abstract public void updateShape();

    /**
     * Get width and thus being abstract, force square to implement the ability to view height and width.
     * @return Float the width.
     */
    abstract public float getWidth();

    /**
     * Get height and thus being abstract, force square to implement the ability to view height and width.
     * @return Float the height.
     */
    abstract public float getHeight();

    /**
     * Abstract method which will allow you to view which gesture you need to kill a sprite if its an enemy.
     * @return EnemyKillGesture enumeration parameter.
     */
    abstract public EnemyKillGesture getKillGesture();

    /**
     * Set the kill gesture of a particular sprite.
     * @param killGesture EnemyKillGesture enumeration parameter.
     */
    abstract public void setKillGesture(EnemyKillGesture killGesture);
}