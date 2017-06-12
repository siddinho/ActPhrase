package com.gopromoto.siddharth.act_phrase;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.util.Map;
import com.inmobi.ads.*;
import com.inmobi.sdk.*;

public class GameMenu extends AppCompatActivity {
    public Button play;
    public Button howtouse;
    public Button rateus;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
        InMobiSdk.init(this, "aa6120d2bd6a4ef4a4d94720c5b544f2");
        InMobiBanner banner = (InMobiBanner)findViewById(R.id.banner);
        banner.load();

        play= (Button) findViewById(R.id.playbtn);
        howtouse= (Button) findViewById(R.id.howtoplaybtn);
        rateus= (Button) findViewById(R.id.rateusbtn);
        context=getApplicationContext();
play.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(GameMenu.this,GameScreen.class);
        startActivity(i);
    }
});

        howtouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GameMenu.this,HowtoUse.class);
                startActivity(i);
//                Toast.makeText(getApplicationContext(),"After Inte",Toast.LENGTH_LONG).show();
            }
        });
        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {try{
                Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                startActivity(goToMarket);
            } catch(Exception e) {
//                Toast.makeText(getApplicationContext(),"Toast in catch",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));

            }}
        });
    }
}
