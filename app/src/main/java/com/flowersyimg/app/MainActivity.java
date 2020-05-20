package com.flowersyimg.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import in.myinnos.savebitmapandsharelib.SaveAndShare;

public class MainActivity extends AppCompatActivity {

    private ViewPager vP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadUI();

    }

    void loadUI(){
        vP = findViewById(R.id.vP);
        ViewPagerAdapt adapter = new ViewPagerAdapt(this);
        vP.setAdapter(adapter);

        ImageButton imageButton = findViewById(R.id.ib_save);
        final Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.pict2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SaveAndShare.save(MainActivity.this,
                        icon, // bitmap image
                        null, // name for image
                        "Делюсь картинкой", // title for bottom popup
                        null // message for bottom popup
                );
            }
        });

        ImageButton imageButton1 = findViewById(R.id.ib_share);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent shareIntent = new Intent("android.intent.action.MAIN");
                shareIntent.setAction(Intent.ACTION_SEND);
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/mipmap/" + "share");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, "Share to"));


            }
        });
    }





}
