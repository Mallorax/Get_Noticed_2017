package pl.patrykzygo.memegenerator.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import static pl.patrykzygo.memegenerator.Database.MemeDBContract.MemeDBEntry;


public class MemeDBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Memes.db";



    public MemeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating table
        db.execSQL(MemeDBEntry.CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + MemeDBEntry.DB_TABLE);

        // create new table
        onCreate(db);
    }

    public void addEntry( String name, byte[] image) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(MemeDBEntry.KEY_NAME, name);
        cv.put(MemeDBEntry.KEY_IMAGE, image);
        database.insert(MemeDBEntry.DB_TABLE, null, cv );
    }
}



