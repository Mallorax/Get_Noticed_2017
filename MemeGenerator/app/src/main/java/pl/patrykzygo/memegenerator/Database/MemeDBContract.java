package pl.patrykzygo.memegenerator.Database;


import android.provider.BaseColumns;

public final class MemeDBContract {

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "Memes.db";

    private MemeDBContract(){}

    public abstract static class UsersMemesTable implements BaseColumns{

        // Table Names
        public static final String MEMES_TABLE = "table_image";
        // column names
        public static final String MEME_NAME = "image_name";
        public static final String MEME_DATA = "image_data";

        // Table create statement
        public static final String CREATE_MEMES_TABLE = "CREATE TABLE " + MEMES_TABLE + "("+
                MEME_NAME + " TEXT," +
                MEME_DATA + " BLOB);";
    }

}
