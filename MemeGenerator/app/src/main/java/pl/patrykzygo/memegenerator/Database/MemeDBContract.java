package pl.patrykzygo.memegenerator.Database;


import android.provider.BaseColumns;

public final class MemeDBContract {

    private MemeDBContract(){}

    public static class MemeDBEntry implements BaseColumns{

        // Table Names
        protected static final String DB_TABLE = "table_image";

        // column names
        protected static final String KEY_NAME = "image_name";
        protected static final String KEY_IMAGE = "image_data";

        // Table create statement
        protected static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "("+
                KEY_NAME + " TEXT," +
                KEY_IMAGE + " BLOB);";
    }
}
