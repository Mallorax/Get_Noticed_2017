package pl.patrykzygo.memegenerator.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pl.patrykzygo.memegenerator.Meme;

import static pl.patrykzygo.memegenerator.Database.MemeDBContract.MemeDBEntry;


public class MemeDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Memes.db";



    public MemeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MemeDBEntry.CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MemeDBEntry.SQL_DELETE_TABLE);
        onCreate(db);
    }

    public void addMeme(Meme... memes){
        ContentValues values = new ContentValues();

        for(Meme meme : memes) {
            values.put(MemeDBEntry._ID, meme.getImageResource());
            values.put(MemeDBEntry.COLUMN_NAME, meme.getName());
        }
        SQLiteDatabase db = getWritableDatabase();
        db.insert(MemeDBEntry.TABLE_NAME, null, values);
        db.close();
    }
    


}
