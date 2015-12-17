package tsp.team.walkandtalk;

import java.io.Serializable;

/** Scene wrapper facilitates passing resource id's based on level and character
 *  selection to the openGL activity.
 */
public class SceneWrapper implements Serializable {
    private String character;
    private int char_run;
    private int char_jump;
    private int char_fall;
    private String scene;
    private int scene_back;
    private int[] enemies_still;
    private int[] enemies_run;
    private int[] enemies_fly;
    private int[] gameSounds;


    /**
     * Constructor which builds up the scene for passing into the renderer and surface views for different
     * scenes.
     * @param c     character name
     * @param run   integer to hold resourceID for running sprite sheet
     * @param jump  integer to hold ResourceID for jump sprite sheet
     * @param fall  integer to hold ResourceID for fall sprite sheet
     * @param soundIds ResourceID's for sound assets
     */
    public SceneWrapper(String c, int run, int jump, int fall, int[] soundIds){
        character = c;
        char_run = run;
        char_jump = jump;
        char_fall = fall;
        gameSounds = soundIds;
    } // Constructor

    /**
     * Getter method to retrieve the character name.
     * @return string of character name
     */
    public String getCharacterName() {
        return character;
    }

    /**
     * Getter method to retrieve the ResourceID of the character run image.
     * @return int of ResourceID
     */
    public int getCharacterRun() {
        return char_run;
    }

    /**
     * Getter method to retrieve the ResourceID of the character jump image.
     * @return int of ResourceID
     */
    public int getCharacterJump() {
        return char_jump;
    }

    /**
     * Getter method to retrieve the ResourceID of the character fall image.
     * @return int of ResourceID
     */
    public int getCharacterFall() {
        return char_fall;
    }

    /**
     * Getter method to retrieve the scene name.
     * @return string of scene name
     */
    public String getSceneName() {
        return scene;
    }

    /**
     * Setter method to set the string of the scene name.
     */
    public void setSceneName(String scene) {
        this.scene = scene;
    }

    /**
     * Getter method to retrieve the ResourceID of the scene background image.
     * @return int of ResourceID
     */
    public int getSceneBackground() {
        return scene_back;
    }

    /**
     * Setter method to set the ResourceID of the scene background image.
     */
    public void setSceneBackground(int scene_back) {
        this.scene_back = scene_back;
    }

    /**
     * Getter method to retrieve the ResourceID at a specific index in the still enemies image array.
     * @return int of ResourceID
     */
    public int getEnemiesStill(int i) {
        return enemies_still[i];
    }

    /**
     * Getter method to retrieve the number of entries in the still enemies image array.
     * @return int of array length
     */
    public int getEnemiesStillLength() {
        return enemies_still.length;
    }

    /**
     * Setter method to set the array of ResourceIDs of still enemy images.
     */
    public void setEnemiesStill(int[] enemies_still) {
        this.enemies_still = enemies_still;
    }

    /**
     * Getter method to retrieve the ResourceID at a specific index in the running enemies image array.
     * @return int of ResourceID
     */
    public int getEnemiesRun(int i) {
        return enemies_run[i];
    }

    /**
     * Getter method to retrieve the number of entries in the running enemies image array.
     * @return int of array length
     */
    public int getEnemiesRunLength() {
        return enemies_run.length;
    }

    /**
     * Setter method to set the array of ResourceIDs of running enemy images.
     */
    public void setEnemiesRun(int[] enemies_run) {
        this.enemies_run = enemies_run;
    }

    /**
     * Getter method to retrieve the ResourceID at a specific index in the flying enemies image array.
     * @return int of ResourceID
     */
    public int getEnemiesFly(int i) {
        return enemies_fly[i];
    }

    /**
     * Getter method to retrieve the number of entries in the flying enemies image array.
     * @return int of array length
     */
    public int getEnemiesFlyLength() {
        return enemies_fly.length;
    }

    /**
     * Setter method to set the array of ResourceIDs of flying enemy images.
     */
    public void setEnemiesFly(int[] enemies_fly) {
        this.enemies_fly = enemies_fly;
    }

    /**
     * Getter method to retrieve the array of sound resource ID's
     * @return int array holding sound resource ID's
     */
    public int[] getGameSounds() {
        return gameSounds;
    }
} // SceneWrapper
