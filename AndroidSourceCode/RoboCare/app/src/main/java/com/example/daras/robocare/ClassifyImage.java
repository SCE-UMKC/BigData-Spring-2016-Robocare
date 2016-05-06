package com.example.daras.robocare;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClassifyImage extends AppCompatActivity {

    ImageButton ib;
    ImageView iv;
    ServerSocket serverSocket;
    Socket ComputerSocket ;
    static final int CAM_REQUEST = 1;
    String message = "";

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

    public void classifyOnclick(View view)
    {
        try
        {
            Thread socketServerThread = new Thread(new SocketServerThread());
            socketServerThread.start();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        String path = "sdcard/robocare_app/robocare_img.jpg";
        iv.setImageDrawable(Drawable.createFromPath(path));
    }

    public void notifyWatch()
    {
        {
            try
            {
                int notificationId = 1;
                NotificationCompat.Builder Notify = new NotificationCompat.Builder(this);
                Notify.setContentText("Sandeep Predicted Disease" + message);

                Notify.setContentTitle("ROBO CARE");
                NotificationManagerCompat NotifyManger = NotificationManagerCompat.from(this);
                NotifyManger.notify(notificationId, Notify.build());


            } catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }

    }

    private class SocketServerThread extends Thread {

        static final int SocketServerPORT = 1234;
        int count = 0;

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                ClassifyImage.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }
                });

                while (true) {
                    Socket socket = serverSocket.accept();
                    count++;
                    message += "#" + count + " from " + socket.getInetAddress()
                            + ":" + socket.getPort() + "\n" ;

                    int red = -1;
                    byte[] buffer = new byte[5*1024]; // a read buffer of 5KiB
                    byte[] redData;
                    StringBuilder clientData = new StringBuilder();
                    String redDataText;
                    while ((red = socket.getInputStream().read(buffer)) > -1) {
                        redData = new byte[red];
                        System.arraycopy(buffer, 0, redData, 0, red);
                        redDataText = new String(redData,"UTF-8"); // assumption that client sends data UTF-8 encoded
                        System.out.println("message part recieved:" + redDataText);
                        clientData.append(redDataText);
                    }
                    // System.out.println("Data From Client :" + clientData.toString());

                    message+=clientData;
                    ClassifyImage.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            notifyWatch();
                        }
                    });

                    SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                            socket, count);
                    socketServerReplyThread.run();

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private class SocketServerReplyThread extends Thread {

        private Socket hostThreadSocket;
        int cnt;

        SocketServerReplyThread(Socket socket, int c) {
            hostThreadSocket = socket;
            cnt = c;
        }

        @Override
        public void run() {
            OutputStream outputStream;
            String msgReply = "Hello from Android, you are #" + cnt;

            try {
                outputStream = hostThreadSocket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(msgReply);
                printStream.close();

                message += "replayed: " + msgReply + "\n";

                ClassifyImage.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }
                });

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message += "Something wrong! " + e.toString() + "\n";
            }

            ClassifyImage.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                }
            });
        }

    }
}


