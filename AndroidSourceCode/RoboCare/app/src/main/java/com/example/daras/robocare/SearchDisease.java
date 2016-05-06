package com.example.daras.robocare;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.record.PageBreakRecord;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public class SearchDisease extends AppCompatActivity {


    TextView DiseaseName;
       TextView AboutDisease;
       TextView Drug1;
      TextView Drug2;
     TextView Drug3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disease);
        DiseaseName = (TextView)findViewById(R.id.txtDiseaseName);
        AboutDisease = (TextView)findViewById(R.id.txtAboutDisease);
        Drug1 = (TextView)findViewById(R.id.txtDrug1);
        Drug2 = (TextView)findViewById(R.id.txtDrug2);
        Drug3 = (TextView)findViewById(R.id.txtDrug3);
        readExcelFile(this, "Data.xls",Constants.PredictedDisease );
    }

    private static boolean isExternalStorageReadOnly() {
        boolean isPresent = false;
        try
        {
            String extStorageState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
                isPresent =  true;
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return  isPresent;
    }

    private static boolean isExternalStorageAvailable() {
       boolean isPresent = false;
        try
        {
            String extStorageState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
                isPresent =  true;
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
       return  isPresent;
    }

    public   void readExcelFile(Context context, String filename,String SearchDisease)
    {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {

            return;
        }

        try{
            // Creating Input Stream
            File file = Environment.getExternalStorageDirectory();
            File Main = new File(file,filename);
            FileInputStream myInput = new FileInputStream(Main);


            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();


            Iterator row = mySheet.rowIterator();
            while (row.hasNext())
            {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Cell cc = myRow.getCell(0);
                String sc = cc.getStringCellValue();
                if(sc.toUpperCase().contains( SearchDisease.toUpperCase() ))
                {
                    Toast.makeText(context, SearchDisease, Toast.LENGTH_SHORT).show();
                    ReadRow(myRow);
                    break;
                }

            }


        }catch (Exception e)
            {
            e.printStackTrace();
        }

        return;
    }

    private  void ReadRow(HSSFRow Row)
    {
        try
        {
            DiseaseName.setText(Row.getCell(0).getStringCellValue());
            AboutDisease.setText(Row.getCell(1).getStringCellValue());
            Drug1.setText(Row.getCell(2).getStringCellValue());
            Drug2.setText(Row.getCell(3).getStringCellValue());
            Drug3.setText(Row.getCell(4).getStringCellValue());

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
