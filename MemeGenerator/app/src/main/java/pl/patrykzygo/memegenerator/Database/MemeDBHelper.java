package pl.patrykzygo.memegenerator.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import static pl.patrykzygo.memegenerator.Database.MemeDBContract.UsersMemesTable;


public class MemeDBHelper extends SQLiteOpenHelper {

    public MemeDBHelper(Context context) {
        super(context, MemeDBContract.DATABASE_NAME, null, MemeDBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating table
        db.execSQL(UsersMemesTable.CREATE_MEMES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + UsersMemesTable.MEMES_TABLE);
        // create new table
        onCreate(db);
    }

    public void addEntry( String name, byte[] image) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(UsersMemesTable.MEME_NAME, name);
        cv.put(UsersMemesTable.MEME_DATA, image);
        database.insert(UsersMemesTable.MEMES_TABLE, null, cv );
    }
}



