package tsp.team.walkandtalk;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/** HighScore
 * This class represents a high score.
 */
public class HighScore {

    public String id;
    public long score;
    private long datetime;
    public String character;
    public String scene;

    /** High Score
     * This is the constructor for the class, which saves all passed values
     * as instance variables.
     *
     * @param id          Unique id of the high score
     * @param score       Value of the high score
     * @param datetime    Epoch time of when the game ended
     * @param character   Character used for this game
     * @param scene       Scene used for this game
     */
    public HighScore(String id, long score, long datetime, String character, String scene) {

        this.id = id;              // Save parameters as instance variables
        this.score = score;
        this.datetime = datetime;
        this.character = character;
        this.scene = scene;

    } // Constructor

    /** getDate
     * This method returns the formatted string holding the month, day and year
     * of when the game was finished.
     *
     * @return  Formatted date string
     */
    public String getDate(){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(this.datetime);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMMMM d, yyyy");
        return sdf.format(c.getTime());
    } // getDate

    /** getDateWithDayOfWeek
     * This method returns the formatted string holding the day of the week, month,
     * day and year of when the game was finished.
     *
     * @return  Formatted date string
     */
    public String getDateWithDayOfWeek(){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(this.datetime);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEEEEEE MMMMMMM d, yyyy");
        return sdf.format(c.getTime());
    } // getDateWithDayOfWeek

    /** getTime
     * This method returns the formatted string holding the time (in hour, minutes
     * and seconds) of when the game ended.
     *
     * @return Formatted time string
     */
    public String getTime(){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(this.datetime);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        return sdf.format(c.getTime());
    } // getTime

} // HighScore