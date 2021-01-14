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

public class fetchData1 extends AsyncTask<Void, Void, Void> {

    protected String Desc = "";
    protected String strTypes; // Create an ArrayList object
    protected String strLanguage = "";
    protected int StringID;
    protected String data1;
    @SuppressLint("StaticFieldLeak")
    protected Context context;

    public fetchData1(int StringID, Context context) {
        this.StringID = StringID;
        strTypes = "";
        this.context =context;

    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Make API connection
            URL url = new URL("https://pokeapi.co/api/v2/pokemon-species/"+ StringID + "/");
            HttpURLConnection httpURLConnection1;
            httpURLConnection1 = (HttpURLConnection) url.openConnection();

            InputStream inputStream1 = httpURLConnection1.getInputStream();
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
            StringBuilder sBuilder1 = new StringBuilder();
            String line1;
            while ((line1 = bufferedReader1.readLine()) != null){
                sBuilder1.append(line1 + "\n");
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

            JSONArray flavor_text_entries = new JSONArray(jObject.getString("flavor_text_entries"));

            for(int i=0; i<flavor_text_entries.length(); i++){
                JSONObject type = new JSONObject(flavor_text_entries.getString(i));
                JSONObject type2  = new JSONObject(type.getString("version"));

                JSONObject type3  = new JSONObject(type.getString("language"));
                strLanguage = type3.getString("name");

                strTypes = type2.getString("name");

                if (StringID > 649){
                    if (strTypes .equals("alpha-sapphire") && strLanguage .equals("en")){
                        JSONObject get0 = new JSONObject(flavor_text_entries.getString(i));
                        Desc += get0.getString("flavor_text");
                        Log.d("TAG", Desc);
                        break;
                    }
                }

                if (strTypes .equals("black") && strLanguage .equals("en")){
                    JSONObject get0 = new JSONObject(flavor_text_entries.getString(i));
                    Desc += get0.getString("flavor_text");
                    break;
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (MainActivity.array == null) {
            fetchTypes process2 = new fetchTypes();
            process2.execute();
        }


        // Set info
        MainActivity.txtDesc.setText(this.Desc);

    }
}
