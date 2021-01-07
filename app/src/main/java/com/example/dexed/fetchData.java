package com.example.dexed;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.ahmadrosid.svgloader.SvgLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class fetchData extends AsyncTask<Void, Void, Void> {

    protected String data = "";
    protected String name = "";
    protected String height = "";
    protected String weight = "";
    protected String id = "";
    protected String Desc = "";
    protected String strType1;// Create an ArrayList object
    protected String strType2;
    protected String pokSearch;
    protected Context context;

    public fetchData(String pokSearch, Context context) {
        this.pokSearch = pokSearch;
        strType1 = "";
        strType2 = "";
        this.context =context;

    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Make API connection
            URL urlPokemon = new URL("https://pokeapi.co/api/v2/pokemon/" + pokSearch);
            Log.i("logtest", "https://pokeapi.co/api/v2/pokemon/" + pokSearch);



            HttpURLConnection httpURLConnection = (HttpURLConnection) urlPokemon.openConnection();


            // Read API results
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sBuilder = new StringBuilder();



            // Build JSON String
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }



            inputStream.close();


            data = sBuilder.toString();


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
            jObject = new JSONObject(data);

            // Get JSON name, height, weight
            name += "Name: " + jObject.getString("name");
            height += "Height: " + jObject.getString("height");
            weight += "Weight: " + jObject.getString("weight");
            id += "ID:" + jObject.getString("id");
            String simpleID = jObject.getString("id");
            // Get type/types
            JSONArray types = new JSONArray(jObject.getString("types"));
            for(int i=0; i<types.length(); i++){
                JSONObject type = new JSONObject(types.getString(i));
                JSONObject type2  = new JSONObject(type.getString("type"));
                if (i == 0) {
                    strType1 = type2.getString("name");
                    String strimgtype1 = "https://raw.githubusercontent.com/msikma/pokesprite/master/misc/types/gen8/"+strType1+".png";
                    Glide.with(context).load(strimgtype1).apply(new RequestOptions().override(80, 80)).into(MainActivity.imgType1);
                }
                if (i == 1){
                    strType2 = type2.getString("name");
                    String strimgtype2 = "https://raw.githubusercontent.com/msikma/pokesprite/master/misc/types/gen8/"+strType2+".png";
                    Glide.with(context).load(strimgtype2).apply(new RequestOptions().override(80, 80)).into(MainActivity.imgType2);
                }
            }

            Log.d("TAG", strType1);
            Log.d("TAG", strType2);

            // Get img SVG
            JSONObject sprites = new JSONObject(jObject.getString("sprites"));
            JSONObject versions = new JSONObject(sprites.getString("versions"));
            JSONObject generation = new JSONObject(versions.getString("generation-v"));
            JSONObject blackWhite = new JSONObject(generation.getString("black-white"));
            JSONObject animated = new JSONObject(blackWhite.getString("animated"));

            img = animated.getString("front_default");


            //img  = dream_world.getString("front_default");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        fetchData1 process1 = new fetchData1(this.id,context);
        process1.execute();


        // Set info
        MainActivity.txtName.setText(this.name);
        MainActivity.txtHeight.setText(this.height);
        MainActivity.txtWeight.setText(this.weight);
        MainActivity.txtID.setText(this.id);
        MainActivity.getID = this.id;
        //Picasso.get().load(img).resize(288,288).into(MainActivity.imgPok);
        Glide.with(context).load(img).apply(new RequestOptions().override(280, 280)).into(MainActivity.imgPok);
        Log.d("TAG", this.name);
        Log.d("TAG", this.height);
        Log.d("TAG", this.weight);
    }
}
