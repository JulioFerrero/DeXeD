package com.example.dexed;


import android.content.Context;
import android.os.AsyncTask;

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

    protected String data = "";
    protected String name = "";
    protected String height = "";
    protected String weight = "";
    protected String id = "";
    protected String Desc = "";
    protected String strTypes; // Create an ArrayList object
    protected String strLanguage = "";
    protected String StringID = "";
    protected String data1;
    protected Context context;

    public fetchData1(String StringID, Context context) {
        String[] arrOfStr = StringID.split(":");
        this.StringID = arrOfStr[1];
        strTypes = "";
        this.context =context;

    }

    public String getId() {
        return id;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Make API connection
            URL url = new URL("https://pokeapi.co/api/v2/pokemon-species/"+ StringID + "/");
            HttpURLConnection httpURLConnection1 = null;
            httpURLConnection1 = (HttpURLConnection) url.openConnection();


            InputStream inputStream1 = httpURLConnection1.getInputStream();
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
            StringBuilder sBuilder1 = new StringBuilder();
            String line1 = null;
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
        JSONObject jObject = null;
        String img = "";
        String typeName = "";
        String typeObj="";

        try {
            jObject = new JSONObject(data1);

            JSONArray flavor_text_entries = new JSONArray(jObject.getString("flavor_text_entries"));

            for(int i=0; i<flavor_text_entries.length(); i++){
                JSONObject type = new JSONObject(flavor_text_entries.getString(i));
                JSONObject type2  = new JSONObject(type.getString("version"));

                JSONObject type3  = new JSONObject(type.getString("language"));
                strLanguage = type3.getString("name");

                strTypes = type2.getString("name");
                if (strTypes .equals("black") && strLanguage .equals("en")){
                    JSONObject get0 = new JSONObject(flavor_text_entries.getString(i));
                    Desc += get0.getString("flavor_text");
                    break;
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Set info
        MainActivity.txtDesc.setText(this.Desc);

    }
}
