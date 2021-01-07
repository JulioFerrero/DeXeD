package com.example.dexed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.telecom.InCallService;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class MainActivity extends AppCompatActivity {

    public static TextView txtName,txtHeight,txtWeight,txtID,txtType,txtDesc;
    public static Activity act;
    public static ImageView imgPok,imgType1,imgType2,icon;
    public static String getID;
    public static ImageButton btnUp,btnDown,btnSearch;
    public static int nextPok = 1;

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
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnSearch = findViewById(R.id.btnSearch);
        icon = findViewById(R.id.icon);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Pokemon.ttf");

        //String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/432.gif";
        //Glide.with(this).load(imageUrl).apply(new RequestOptions().override(288, 288)).into(imgPok);
        //txtID.setTypeface(face);

        fetchData process = new fetchData("1",getApplicationContext());
        process.execute();

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPok++;
                fetchData process = new fetchData(String.valueOf(nextPok),getApplicationContext());
                process.execute();
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPok--;
                fetchData process = new fetchData(String.valueOf(nextPok),getApplicationContext());
                process.execute();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTxtSearch();
            }
        });
    }

    public void showTxtSearch(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Search a Pokemon");

        final EditText input = new EditText(this);
        input.setHint("Pokemon");
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String pokSearch = input.getText().toString();
                fetchData process = new fetchData(pokSearch,getApplicationContext());
                process.execute();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

}