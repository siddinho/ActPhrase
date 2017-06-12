package com.gopromoto.siddharth.act_phrase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameScreen extends AppCompatActivity {
MediaPlayer mp;
    int status=0;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView word;
    ProgressBar p;
    Button gameover;
    SQLiteDatabase mydatabase;
    Random r;
    int index;
    int rowcount;
    Cursor rs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        mp=MediaPlayer.create(this, R.raw.beep);
        p= (ProgressBar) findViewById(R.id.pb);
        gameover= (Button) findViewById(R.id.GameOverBtn);
        word= (TextView) findViewById(R.id.wordText);
        mydatabase = openOrCreateDatabase("wordbase",MODE_PRIVATE,null);
        mydatabase.execSQL("create table if not exists wordtable(word VARCHAR);");
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        status=sharedPreferences.getInt("status",0);
        Toast.makeText(getApplicationContext(),"Please Check Your Volume for Sound",Toast.LENGTH_LONG).show();
        if(status==0){
            p.setVisibility(View.VISIBLE);
          Config.addWords(mydatabase);
         rowcount= (int) DatabaseUtils.queryNumEntries(mydatabase,"wordtable");
//            Toast.makeText(getApplicationContext(),"First Run;;;;;"+rowcount,Toast.LENGTH_LONG).show();
            editor.putInt("status",1);
            editor.commit();
            p.setVisibility(View.GONE);
        }
        else{
            rowcount= (int) DatabaseUtils.queryNumEntries(mydatabase,"wordtable");


//            Toast.makeText(getApplicationContext(),"Initialised    :::"+rowcount,Toast.LENGTH_LONG).show();

        }

        gameover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GameScreen.this,GameMenu.class);
                startActivity(i);
                finish();

            }
        });

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
//long timeleft=millisUntilFinished/1000;
//                Toast.makeText(getApplicationContext(),"Time Left :"+timeleft,Toast.)
            }

            public void onFinish() {
                mp.stop();
                word.setVisibility(View.GONE);
                gameover.setVisibility(View.VISIBLE);
            }
        }.start();


//        new CountDownTimer()
    }
    public void game(View v){
mp.start();
        mp.setLooping(true);
        index= ThreadLocalRandom.current().nextInt(1,rowcount);
        rs=mydatabase.rawQuery("Select * from wordtable where rowid='"+index+"';",null);
        rs.moveToFirst();
        word.setText(""+rs.getString(0));


    }

    @Override
    protected void onPause() {
        mp.stop();
        super.onPause();
    }

}
