package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.os.Parcel;
import android.os.StrictMode;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Security;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static class Klasa4tr {
        public HashMap<String,String> info,Grupa1,Grupa2;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        URL url = null;
        StringBuilder json = new StringBuilder();
        try {
            url = new URL("http://zasob.itmargen.com/4TR/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            urlConnection.disconnect();
        }
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;
            while((inputLine=input.readLine())!=null){
                json.append(inputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper=new ObjectMapper();
        Klasa4tr klasa4tr= null;
        try {
            klasa4tr = objectMapper.readValue(json.toString(), Klasa4tr.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        TextView textView = new TextView(this);
        String tvString=new String();
        for(String i:klasa4tr.Grupa1.keySet()){
            tvString+=klasa4tr.Grupa1.get(i)+"\n";
        }
        for(String i:klasa4tr.Grupa2.keySet()){
            tvString+=klasa4tr.Grupa2.get(i)+"\n";
        }
        textView.setText(tvString);
        super.onCreate(savedInstanceState);
        setContentView(textView);
    }
}