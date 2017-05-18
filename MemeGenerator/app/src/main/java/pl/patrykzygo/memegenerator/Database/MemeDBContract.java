package pl.patrykzygo.memegenerator.Database;


import android.provider.BaseColumns;

public final class MemeDBContract {

    private MemeDBContract(){}

    public static class MemeDBEntry implements BaseColumns{
        public static final String TABLE_NAME = "Memes";
        public static final String COLUMN_PICTURE_ID = "picture_id";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE_ENTRIES =
                "CREATE TABLE " +   TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_PICTURE_ID + " INTEGER," +
                        COLUMN_NAME + " TEXT)";
        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
