package com.example.dexed;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class fetchPokemonsByType extends AsyncTask<Void, Void, Void> {

    protected String strTypes; // Create an ArrayList object
    protected String data1;
    protected String typePok;
    protected String[] arraypok;
    @SuppressLint("StaticFieldLeak")
    protected Context context;

    public fetchPokemonsByType(String typePok, Context context) {
        this.typePok = typePok;
        this.context =context;
        Log.d("TAG", typePok);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Make API connection
            URL url = new URL("https://pokeapi.co/api/v2/type/" + typePok + "/");
            HttpURLConnection httpURLConnection1;
            httpURLConnection1 = (HttpURLConnection) url.openConnection();

            InputStream inputStream1 = httpURLConnection1.getInputStream();
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
            StringBuilder sBuilder1 = new StringBuilder();
            String line1;
            while ((line1 = bufferedReader1.readLine()) != null){
                sBuilder1.append(line1).append("\n");
            }
            inputStream1.close();
            data1 = sBuilder1.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        JSONObject jObject;

        try {
            jObject = new JSONObject(data1);

            JSONArray arrayTypesJSON = new JSONArray(jObject.getString("pokemon"));

            arraypok = new String[arrayTypesJSON.length()];

            for(int i=0; i<arrayTypesJSON.length(); i++){
                JSONObject type = new JSONObject(arrayTypesJSON.getString(i));
                JSONObject type2 = new JSONObject(type.getString("pokemon"));
                strTypes = type2.getString("name");
                this.arraypok[i]=strTypes;
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Set info

        Log.d("TAG", Arrays.toString(arraypok));
        Log.d("TAG", String.valueOf(arraypok.length));
        MainActivity.arrayPok = arraypok;

        fetchData process = new fetchData(arraypok[0], context);
        process.execute();


    }
}
