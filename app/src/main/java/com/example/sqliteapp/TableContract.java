package com.example.sqliteapp;

import android.provider.BaseColumns;

public class TableContract {
    private TableContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "game_table";
        public static final String COLUMN_ID = "game_id";
        public static final String COLUMN_GAME = "game_name";
        public static final String COLUMN_STATUS = "game_status";
    }
}
