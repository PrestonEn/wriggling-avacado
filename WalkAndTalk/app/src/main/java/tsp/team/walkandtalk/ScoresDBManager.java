package tsp.team.walkandtalk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

/** ScoresDBManager
 * This class contains all of the methods related to reading and writing
 * from the scores database.  Only add getAll, and deleteAll methods are
 * needed.  Scores are returned in descending order of the score value.
 *
 */
public class ScoresDBManager {

    private SQLHelper helper;   // For creation and upgrading of table and table/column names
    private SQLiteDatabase db;  // Database
    // String arrays of table column names
    String[] SCORES_COLUMNS = new String[]{SQLHelper.KEY_ID, SQLHelper.KEY_SCORE, SQLHelper.KEY_DATETIME, SQLHelper.KEY_CHARACTER, SQLHelper.KEY_SCENE};

    /** ScoresDBManager
     * This is the constructor for the database manager.  It creates
     * the SQLHelper used for creating and upgrading the database, as well
     * as storing the column names.
     *
     * @param c Context passed on to the SQLHelper
     */
    public ScoresDBManager(Context c){
        helper=new SQLHelper(c);
    } // ScoresDBManager

    /** close
     * This method closes the SQLHelper.
     */
    public void close(){
        helper.close();
    }

    /**************************** Scores Table Methods *************************/

    /** addHighScoreRow
     * This method inserts a row into the Scores table, as long as the id is unique.
     *
     * @param id          Unique id for the high score
     * @param score       Value of the high score
     * @param datetime    Epoch time of when the game ended
     * @param character   Character used for this game
     * @param scene       Scene used for this game
     * @return            Whether the adding of the row was successful or not
     */
    public boolean addHighScoreRow(String id, long score, long datetime, String character, String scene){
        db = helper.getWritableDatabase();
        ContentValues newHighScore = new ContentValues(); // Store values with column names as keys
        newHighScore.put(SQLHelper.KEY_ID, id);
        newHighScore.put(SQLHelper.KEY_SCORE, score);
        newHighScore.put(SQLHelper.KEY_DATETIME, datetime);
        newHighScore.put(SQLHelper.KEY_CHARACTER, character);
        newHighScore.put(SQLHelper.KEY_SCENE, scene);

        try{
            db.insertOrThrow(SQLHelper.DB_TABLE_SCORES, null, newHighScore); // Try adding row to table
        } catch(Exception e) {
            Log.e("Error in row insert ", e.toString());
            e.printStackTrace();
            db.close();
            return false;
        }
        db.close();
        return true;
    } // addHighScoreRow

    /** retrieveHighScoreRows
     * This method gets all of the rows in the Scores table, sorted in order of date,
     * and stores them into an ArrayList.  This ArrayList is then returned.
     *
     * @return  ArrayList holding all high scores in the Scores table
     */
    public ArrayList<HighScore> retrieveHighScoreRows(){
        db = helper.getReadableDatabase();
        ArrayList<HighScore> scoresRows = new ArrayList<>(); // To hold all scores for return

        // Get all scores rows, sorted by score value
        Cursor scoreCursor = db.query(SQLHelper.DB_TABLE_SCORES, SCORES_COLUMNS, null, null, null, null, SQLHelper.KEY_SCORE + " DESC", null);
        scoreCursor.moveToFirst();

        while ( ! scoreCursor.isAfterLast() ) { // There are more scores
            // Create scores with result
            HighScore hs = new HighScore(scoreCursor.getString(0), scoreCursor.getLong(1), scoreCursor.getLong(2), scoreCursor.getString(3), scoreCursor.getString(4));
            scoresRows.add(hs); // Add high score to list
            scoreCursor.moveToNext();
        }
        if (scoreCursor != null && !scoreCursor.isClosed()) { // Close cursor
            scoreCursor.close();
        }
        db.close();
        return scoresRows; // Return ArrayList of all scores
    } // retrieveHighScoreRows

    /** getMaxScore
     * This method returns the maximum high score row.
     *
     * @return Max high score row
     */
    public HighScore getMaxScore(){
        db = helper.getReadableDatabase();
        Cursor scoreCursor = db.query(SQLHelper.DB_TABLE_SCORES, SCORES_COLUMNS, null, null, null, null, SQLHelper.KEY_SCORE + " DESC", null);
        scoreCursor.moveToFirst();
        HighScore hs = new HighScore(scoreCursor.getString(0), scoreCursor.getLong(1),
                scoreCursor.getLong(2), scoreCursor.getString(3), scoreCursor.getString(4));
        scoreCursor.moveToNext();
        if (scoreCursor != null && !scoreCursor.isClosed()) { // Close cursor
            scoreCursor.close();
        }
        db.close();
        return hs; // Return max high score
    } // getHighScore

    /** deleteAllHighScoreRows
     * This method deletes all high scores in the Scores table.
     *
     * @return Whether or not the delete was successful
     */
    public boolean deleteAllHighScoreRows(){
        db = helper.getWritableDatabase();
        int ifSuccess = db.delete(SQLHelper.DB_TABLE_SCORES, null, null); // Clear table
        db.close();
        return (ifSuccess != 0);
    } // deleteAllHighScoreRows

} // ScoresDBManager
