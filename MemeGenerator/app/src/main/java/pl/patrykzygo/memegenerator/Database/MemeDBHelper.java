package pl.patrykzygo.memegenerator.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.patrykzygo.memegenerator.ImageHandlers.ImageConverter;
import pl.patrykzygo.memegenerator.Model.Meme;
import pl.patrykzygo.memegenerator.Model.UsersMeme;

import static pl.patrykzygo.memegenerator.Database.MemeDBContract.DATABASE_LOG;
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

    public void addEntry( String name, byte[] image){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(UsersMemesTable.MEME_NAME, name);
            cv.put(UsersMemesTable.MEME_DATA, image);
            database.insert(UsersMemesTable.MEMES_TABLE, null, cv);
            Log.v(DATABASE_LOG, "entry added");
        }catch (Exception e){
            Log.v(DATABASE_LOG, "failed to add entry");
        }
    }

    public List<Meme> getMemesFromDatabase(){
        List<Meme> memes = new ArrayList<>();
        String queryString = "SELECT * FROM " + UsersMemesTable.MEMES_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do {
                memes.add(new UsersMeme(cursor.getString(0), ImageConverter.getImage(cursor.getBlob(1))));
            }while(cursor.moveToNext());
        }
        return memes;
    }




}



