package tsp.team.walkandtalk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** SQLHelper.java
 * This class holds the database elements, column names, and SQL statements
 * for the creation and management of the app's database.  The database has
 * one table - SCORES.
 */
public class SQLHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "highScores";
    public static final String DB_TABLE_SCORES = "scores";
    public static final int DB_VERSION = 1;

    // SCORES Table Column Names
    public static final String KEY_ID = "id";
    public static final String KEY_SCORE = "score";
    public static final String KEY_DATETIME = "datetime";
    public static final String KEY_CHARACTER = "character";
    public static final String KEY_SCENE = "scene";

    // Table Creations SQL Statement
    private static final String CREATE_TABLE_SCORES = "CREATE TABLE " + DB_TABLE_SCORES +
            " (" + KEY_ID + " TEXT PRIMARY KEY, " + KEY_SCORE + " INT8, " + KEY_DATETIME + " INT8, " +
            KEY_CHARACTER + " TEXT, " + KEY_SCENE + " TEXT);";

    /** SQLHelper
     * This is the constructor for the SQLHelper class, which calls
     * the constructor of the super class SQLiteOpenHelper, passing
     * along the database manager context, the database name and the
     * database version number.
     *
     * @param c Context of the database manager
     */
    public SQLHelper(Context c){
        super(c, DB_NAME, null, DB_VERSION);
    } // SQLHelper

    /** onCreate
     * This method overrides SQLiteOpenHelper's OnCreate method.  It executes
     * the SQL statements to create the scores table.
     *
     * @param db The database that the table should be created in
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SCORES);  // Creating table
    } // onCreate

    /** onUpgrade
     * This method overrides SQLiteOpenHelper's OnUpgrade method.  It executes
     * the SQL statements to drop the scores table and then create it again with
     * the upgrade.
     *
     * @param db           The database that is upgraded
     * @param oldVersion   The previous version number
     * @param newVersion   The new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("Scores Database", "Upgrading database i.e. dropping tables and recreating them");
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_SCORES); // On upgrade drop older table
        onCreate(db); // Create new table
    } // onUpgrade

} // SQLHelper
