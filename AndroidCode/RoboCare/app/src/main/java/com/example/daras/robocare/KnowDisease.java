package com.example.daras.robocare;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class KnowDisease extends AppCompatActivity {


    ImageButton Record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robo_symptoms);

        Record = (ImageButton)findViewById(R.id.btnRecord);




    }

    public void RecordonClick(View V)
    {
        try
        {
            Intent Options = new Intent(KnowDisease.this,RecordSymptoms.class);
            startActivity(Options);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


}