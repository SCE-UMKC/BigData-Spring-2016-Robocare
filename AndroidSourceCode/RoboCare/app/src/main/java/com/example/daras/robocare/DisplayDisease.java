package com.example.daras.robocare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DisplayDisease extends AppCompatActivity {

    ImageButton KnowDoctor;
    ImageButton AboutDisease;
    String message = "";
    ServerSocket serverSocket;
    Socket ComputerSocket ;
   public static TextView PredictedDisease;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_disease);
        KnowDoctor = (ImageButton)findViewById(R.id.btnDoctor);
        AboutDisease = (ImageButton)findViewById(R.id.btnAboutDisease);
        PredictedDisease = (TextView)findViewById(R.id.txtPredictedDisease);
        PredictedDisease.setText(Constants.PredictedDisease);

        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
    }

    public void DoctoronClick(View V)
    {
        try
        {
            Intent Options = new Intent(DisplayDisease.this,Doctor.class);
            startActivity(Options);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void ABoutDiseaseOnClick(View v)
    {
        try
        {

            Thread socketServerThread = new Thread(new SocketServerThread());
            socketServerThread.start();
            Intent Options = new Intent(DisplayDisease.this,SearchDisease.class);
            startActivity(Options);


        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private class SocketServerThread extends Thread {

        static final int SocketServerPORT = 1234;
        int count = 0;

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                DisplayDisease.this.runOnUiThread(new Runnable() {

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
                    DisplayDisease.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Constants.DiseaseName = message;
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

                DisplayDisease.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                       Constants.DiseaseName = message;

                    }
                });

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message += "Something wrong! " + e.toString() + "\n";
            }

            DisplayDisease.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Constants.DiseaseName = message;
                }
            });
        }

    }
}
