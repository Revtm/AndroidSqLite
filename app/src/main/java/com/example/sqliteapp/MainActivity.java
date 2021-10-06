package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TableHelper gameDB;
    TextView gameId, gameStatus;
    EditText gameIdText, gameStatusText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameId = (TextView) findViewById(R.id.gameId);
        gameStatus = (TextView) findViewById(R.id.gameStatus);
        gameIdText = (EditText) findViewById(R.id.inputGameId);
        gameStatusText = (EditText) findViewById(R.id.inputGameStatus);

        gameDB = new TableHelper(this);
    }

    public void updateGameStatus(View view){
        int count;
        int statusText = Integer.parseInt(gameStatusText.getText().toString());
        String idText = gameIdText.getText().toString();
        count = gameDB.updateGame(gameIdText.getText().toString(), statusText);
        if(count > 0){
            Toast toast = Toast.makeText(this, "Updated", Toast.LENGTH_SHORT);
            toast.show();
            Cursor cursor = gameDB.readGame(gameIdText.getText().toString());
           while(cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndexOrThrow(TableContract.FeedEntry.COLUMN_ID));
                int status = cursor.getInt(cursor.getColumnIndexOrThrow(TableContract.FeedEntry.COLUMN_STATUS));
                gameId.setText(id);
                gameStatus.setText(String.valueOf(status));
            }
        }

    }

}