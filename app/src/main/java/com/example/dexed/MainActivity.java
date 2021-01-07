package com.example.dexed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.telecom.InCallService;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class MainActivity extends AppCompatActivity {

    public static TextView txtName,txtHeight,txtWeight,txtID,txtType,txtDesc;
    public static Activity act;
    public static ImageView imgPok,imgType1,imgType2;
    public static String getID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName = findViewById(R.id.txtName);
        txtHeight = findViewById(R.id.txtHeight);
        txtWeight = findViewById(R.id.txtWeight);
        txtID =findViewById(R.id.txtID);
        imgPok = findViewById(R.id.sprite);
        imgType1 = findViewById(R.id.imageView2);
        imgType2 = findViewById(R.id.imageView3);
        txtDesc = findViewById(R.id.txtDesc);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Pokemon.ttf");

        //String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/432.gif";
        //Glide.with(this).load(imageUrl).apply(new RequestOptions().override(288, 288)).into(imgPok);
        //txtID.setTypeface(face);


        fetchData process = new fetchData("heatran",getApplicationContext());
        process.execute();


    }
}