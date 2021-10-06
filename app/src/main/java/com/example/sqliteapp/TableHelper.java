package com.example.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class TableHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "game.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableContract.FeedEntry.TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY," +
                    TableContract.FeedEntry.COLUMN_ID + " TEXT," +
                    TableContract.FeedEntry.COLUMN_GAME + " TEXT," +
                    TableContract.FeedEntry.COLUMN_STATUS + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TableContract.FeedEntry.TABLE_NAME;


    public TableHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        Game game = new Game("com.axis.net", "AxisNet", 0);
        ContentValues values = new ContentValues();
        values.put(TableContract.FeedEntry.COLUMN_ID, game.getGameId());
        values.put(TableContract.FeedEntry.COLUMN_GAME, game.getGameName());
        values.put(TableContract.FeedEntry.COLUMN_STATUS, game.getGameStatus());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = sqLiteDatabase.insert(TableContract.FeedEntry.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public long insertGame(Game game){
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TableContract.FeedEntry.COLUMN_ID, game.getGameId());
        values.put(TableContract.FeedEntry.COLUMN_GAME, game.getGameName());
        values.put(TableContract.FeedEntry.COLUMN_STATUS, game.getGameStatus());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TableContract.FeedEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    public Cursor readGame(String gameID){
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                TableContract.FeedEntry.COLUMN_ID,
                TableContract.FeedEntry.COLUMN_GAME,
                TableContract.FeedEntry.COLUMN_STATUS
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = TableContract.FeedEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = { gameID };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " DESC";


        Cursor cursor = db.query(
                TableContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        return cursor;
    }

    public int updateGame(String gameId, int status){
        SQLiteDatabase db = this.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(TableContract.FeedEntry.COLUMN_STATUS, status);

        // Which row to update, based on the title
        String selection = TableContract.FeedEntry.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { gameId };

        int count = db.update(
                TableContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }
}
