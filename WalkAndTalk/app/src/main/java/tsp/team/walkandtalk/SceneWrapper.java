package tsp.team.walkandtalk;

import java.io.Serializable;

/** Scene wrapper facilitates passing resource id's based on level and character
 *  selection to the openGL activity
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


    /**
     *
     * @param c character name
     * @param run integer to hold resourceID for running sprite sheet
     * @param jump  integer to hold ResourceID for jump sprite sheet
     * @param fall integer to hold ResourceID for fall sprite sheet
     */
    public SceneWrapper(String c, int run, int jump, int fall){
        character = c;
        char_run = run;
        char_jump = jump;
        char_fall = fall;
    }

    public String getCharacterName() {
        return character;
    }

    public void setCharacterName(String character) {
        this.character = character;
    }

    public int getCharacterRun() {
        return char_run;
    }

    public void setCharacterRun(int char_run) {
        this.char_run = char_run;
    }

    public int getCharacterJump() {
        return char_jump;
    }

    public void setCharacterJump(int char_jump) {
        this.char_jump = char_jump;
    }

    public int getCharacterFall() {
        return char_fall;
    }

    public void setCharacterFall(int char_fall) {
        this.char_fall = char_fall;
    }

    public String getSceneName() {
        return scene;
    }

    public void setSceneName(String scene) {
        this.scene = scene;
    }

    public int getSceneBackground() {
        return scene_back;
    }

    public void setSceneBackground(int scene_back) {
        this.scene_back = scene_back;
    }

    public int getEnemiesStill(int i) {
        return enemies_still[i];
    }

    public int getEnemiesStillLength() {
        return enemies_still.length;
    }

    public void setEnemiesStill(int[] enemies_still) {
        this.enemies_still = enemies_still;
    }

    public int getEnemiesRun(int i) {
        return enemies_run[i];
    }

    public int getEnemiesRunLength() {
        return enemies_run.length;
    }

    public void setEnemiesRun(int[] enemies_run) {
        this.enemies_run = enemies_run;
    }

    public int getEnemiesFly(int i) {
        return enemies_fly[i];
    }

    public int getEnemiesFlyLength() {
        return enemies_fly.length;
    }

    public void setEnemiesFly(int[] enemies_fly) {
        this.enemies_fly = enemies_fly;
    }
}
