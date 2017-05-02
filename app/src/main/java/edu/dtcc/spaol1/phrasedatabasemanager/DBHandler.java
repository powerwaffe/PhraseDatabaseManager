package edu.dtcc.spaol1.phrasedatabasemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper
{
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "phrase";

    // Contacts table name
    private static final String TABLE_PHRASE_DETAIL = "phraseDetails";

    // Contacts Table Columns names
    private static final String PHRASE_ID = "id";
    private static final String PHRASE_TITLE = "phrase_title";
    private static final String PHRASE = "phrase";
    //private static final String KEY_PHONE_NO = "phone_number";

    public DBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** CREATE TABLE */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String PHRASE_TABLE = "CREATE TABLE " + TABLE_PHRASE_DETAIL + "("
                + PHRASE_ID + " INTEGER PRIMARY KEY,"
                + PHRASE_TITLE + " TEXT,"
                + PHRASE + " TEXT)";
        db.execSQL(PHRASE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHRASE_DETAIL);

        // Create tables again
        onCreate(db);
    }


    /** For adding a phrase to table */
    void addNewPhrase(Phrase newPhrase)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Set values of the phrase title and phrase
        values.put(PHRASE_TITLE, newPhrase.get_phrase_title());
        values.put(PHRASE, newPhrase.get_phrase());

        // Inserting into row
        db.insert(TABLE_PHRASE_DETAIL, null, values);
        db.close(); // Closing database connection
    }

    /** For editing the phrase in the table */
    public boolean editPhraseInfo(int updateID, String updatePhraseTitle, String updatePhrase)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();

        // Set values of the phrase title and phrase
        args.put(PHRASE_TITLE, updatePhraseTitle);
        args.put(PHRASE, updatePhrase);

        return db.update(TABLE_PHRASE_DETAIL, args, PHRASE_ID + "=" + updateID, null) > 0;
    }

    /** For deleting a phrase in the table */
    public boolean deletePhrase(int delID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PHRASE_DETAIL, PHRASE_ID + "=" + delID, null) > 0;
    }

    // Get all phrases
    public List<Phrase> getAllPhraseList()
    {
        List<Phrase> phraseList = new ArrayList<Phrase>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PHRASE_DETAIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                Phrase phrase = new Phrase();
                phrase.set_id(Integer.parseInt(cursor.getString(0)));
                phrase.set_phrase_title(cursor.getString(1));
                phrase.set_phrase(cursor.getString(2));
                //phrase.set_phone_number(cursor.getString(3));

                // Adding contact to list
                phraseList.add(phrase);
            }
            while (cursor.moveToNext());
        }
        return phraseList; // return contact list
    }
}