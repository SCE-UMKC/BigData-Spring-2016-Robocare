package com.example.daras.robocare;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

public class ClassifyImage extends AppCompatActivity {

    ImageButton ib;
    ImageView iv;
    static final int CAM_REQUEST = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_image);
        ib = (ImageButton) findViewById(R.id.imageButton);
        iv = (ImageView) findViewById(R.id.imageView9);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent, CAM_REQUEST);
            }
        });
    }

    private File getFile()
    {
        File folder= new File("sdcard/robocare_app");

        if (!folder.exists())
        {

            folder.mkdir();
        }
        File image_file =  new File(folder,"robocare_img.jpg");
        return image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        String path = "sdcard/robocare_app/robocare_img.jpg";
        iv.setImageDrawable(Drawable.createFromPath(path));
    }
}
