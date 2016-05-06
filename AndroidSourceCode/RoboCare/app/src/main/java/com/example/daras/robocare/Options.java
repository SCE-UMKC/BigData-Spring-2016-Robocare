package com.example.daras.robocare;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Options extends AppCompatActivity {


    ImageButton Record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robo_symptoms);

        Record = (ImageButton)findViewById(R.id.btnRecord);




    }

    public  void Classifyonclick(View view)
    {
        try
        {

            Intent Options = new Intent(com.example.daras.robocare.Options.this,ClassifyImage.class);
            startActivity(Options);
        }catch (Exception ex)
        {

            ex.printStackTrace();
        }
    }
    public void RecordonClick(View V)
    {
        try
        {
            Intent Options = new Intent(com.example.daras.robocare.Options.this,RecordSymptoms.class);
            startActivity(Options);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void SearchDoctorOnClick(View view)
    {
        try
        {
            Intent Options = new Intent(com.example.daras.robocare.Options.this,Searchdoctor.class);
            startActivity(Options);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void EmergencyonClick(View V)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:8754531545"));
            startActivity(intent);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


}