package com.example.dexed;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static TextView txtName,txtHeight,txtWeight,txtID,txtDesc;
    public static ImageView imgPok,imgType1,imgType2,icon;
    public static ImageButton btnUp,btnDown,btnSearch,btnType;
    public static String getID,nameTypePok;
    public static int nextPok = 0,simpleID;
    public static String[] array,arrayPok;
    public static boolean filterType = false;

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
        btnType = findViewById(R.id.btnType);
        icon = findViewById(R.id.icon);

        fetchData process = new fetchData("1",getApplicationContext());
        process.execute();

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (filterType){
                    nextPok++;
                        fetchData process = new fetchData(arrayPok[nextPok], getApplicationContext());
                        process.execute();
                }else{
                        nextPok = simpleID;
                        nextPok++;
                            fetchData process = new fetchData(String.valueOf(nextPok), getApplicationContext());
                            process.execute();
                }
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterType){
                    nextPok--;
                    if (nextPok > -1) {
                        fetchData process = new fetchData(arrayPok[nextPok], getApplicationContext());
                        process.execute();
                    } else {
                        nextPok = 0;
                    }

                }else {
                    nextPok--;
                    if (nextPok > 0) {
                        fetchData process = new fetchData(String.valueOf(nextPok), getApplicationContext());
                        process.execute();
                    } else {
                        nextPok = 1;
                    }
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTxtSearch();
            }
        });

        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypes();
            }
        });

        Log.d("this",Arrays.toString(array));
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
                filterType=false;
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    public void showTypes(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Select Type");

        alert.setItems(array, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameTypePok = array[which];
                filterType = true;
                nextPok = 0;
                fetchPokemonsByType process4 = new fetchPokemonsByType(array[which],getApplicationContext());
                process4.execute();



            }
        });
        alert.show();
    }
}