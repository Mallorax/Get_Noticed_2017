package pl.patrykzygo.memegenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MemeDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Memes.db";
    public static final String TABLE_NAME = "Memes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PICTURE_ID = "picture_id";
    public static final String COLUMN_NAME = "name";
    public static final String CREATE_ENTRIES =
            "CREATE TABLE " +   TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PICTURE_ID + " INTEGER," +
                    COLUMN_NAME + " TEXT)";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public MemeDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addMeme (Meme meme){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PICTURE_ID, meme.getImageResource());
        values.put(COLUMN_NAME, meme.getName());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteMeme(String memeName){
        SQLiteDatabase db = getWritableDatabase();
    }


}
