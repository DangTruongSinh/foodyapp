package com.example.foodyappversion2.model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DowloadPolyline extends AsyncTask<String,Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine())!= null)
            {
                stringBuilder.append(line);
            }
            Log.d("Tag",stringBuilder.toString());
            return stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
