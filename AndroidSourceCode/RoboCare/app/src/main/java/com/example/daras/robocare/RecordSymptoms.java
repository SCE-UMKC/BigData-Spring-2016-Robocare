package com.example.daras.robocare;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import java.util.ArrayList;

public class RecordSymptoms extends AppCompatActivity {


    ImageButton Record;
    ListView ListSymptoms;
    Button btnKnowDisease;
    private static final int RESULT_SPEECH = 1;

    ArrayList<String> ArrayListSymptoms =new ArrayList<String>();
    ArrayAdapter<String> adapter;
    int ListCounter =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_symptoms);

        Record = (ImageButton) findViewById(R.id.btnRecord);
        btnKnowDisease = (Button)findViewById(R.id.btnKnowDisease);
        ListSymptoms = (ListView) findViewById(R.id.lstsymptoms);

        ListSymptoms.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog(position);
                return true;
            }
        });


    }

    public void KnowDiseaseOnclick(View V)
    {
        try
        {
            Intent Options = new Intent(RecordSymptoms.this,DisplayDisease.class);
            startActivity(Options);
            Constants.PredictedDisease = "Cancer";//Return value from function
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void RecordVoiceonClick(View v) {

        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
            startActivityForResult(intent, RESULT_SPEECH);

        } catch (ActivityNotFoundException a) {
            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
            t.show();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String[] items = text.toArray(new String[0]);
                    Dialog dlg = showdialog(items);
                    dlg.show();

                }
                break;
            }

        }
    }

    public void AddtoListView(String Item)
    {
        try
        {
            ArrayListSymptoms.add(ListCounter, Item);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayListSymptoms);
            ListSymptoms.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void AlertDialog(int Position)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        try {
            final String RemoveItem = ListSymptoms.getItemAtPosition(Position).toString();
            builder.setTitle("Delete Entry" );
            builder.setMessage("Do you want to remove "+ RemoveItem);
            builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    adapter.remove(RemoveItem);
                    adapter.notifyDataSetChanged();
                }
            })
                    .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

builder.show();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public Dialog showdialog(final String[] Items) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        try
        {
            builder.setTitle(R.string.SelectSymptoms)
                    .setItems(Items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            AddtoListView(Items[which].toUpperCase());
                        }
                    });

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return builder.create();
    }





}
