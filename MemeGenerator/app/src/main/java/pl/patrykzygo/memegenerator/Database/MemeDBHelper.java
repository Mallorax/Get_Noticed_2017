package pl.patrykzygo.memegenerator.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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
        db.execSQL(UsersMemesTable.CREATE_MEMES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsersMemesTable.MEMES_TABLE);
        onCreate(db);
    }

    public boolean addEntry( String name, byte[] image){
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(UsersMemesTable.MEME_NAME, name);
            cv.put(UsersMemesTable.MEME_DATA, image);
            db.insert(UsersMemesTable.MEMES_TABLE, null, cv);
            Log.v(DATABASE_LOG, "entry added");
            db.close();
            return true;
        }catch (Exception e){
            Log.v(DATABASE_LOG, "failed to add entry");
            return false;
        }
    }

    public List<Meme> getMemesFromDatabase(){
        List<Meme> memes = new ArrayList<>();
        String queryString = "SELECT * FROM " + UsersMemesTable.MEMES_TABLE;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do {
                UsersMeme meme = new UsersMeme(cursor.getString(0), ImageConverter.getImage(cursor.getBlob(1)));
                System.out.println(meme.getName());
                memes.add(meme);
            }while(cursor.moveToNext());
        }
        db.close();
        return memes;
    }

    public boolean deleteEntry(UsersMeme meme){
           try {
               SQLiteDatabase db = this.getWritableDatabase();
               db.execSQL("DELETE FROM " + UsersMemesTable.MEMES_TABLE + " WHERE " + UsersMemesTable.MEME_NAME + "= '" + meme.getName() + "'");
               db.close();
               return true;
           }catch (SQLException e){
               e.printStackTrace();
               return false;
           }

    }




}



