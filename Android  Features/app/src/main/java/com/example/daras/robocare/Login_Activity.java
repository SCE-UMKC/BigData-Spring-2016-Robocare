package com.example.daras.robocare;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login_Activity extends AppCompatActivity {

    Button Login;
    Button Register;
    EditText EmailID;
    EditText Password;
    String TempEmail;
    String TempPassword;
    Gson gson;
    UserDetails User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        Login = (Button) findViewById(R.id.btnLogin);
        Register = (Button) findViewById(R.id.btnRegister);
        EmailID = (EditText) findViewById(R.id.txtEmail);
        Password = (EditText) findViewById(R.id.txtPassword);


    }

    public void LoginClick(View v)
    {
        try
        {
            if(ValidateUser())
            {
                hideKeyboard();
                Toast.makeText(Login_Activity.this,"ROBO CARE Welcomes You",Toast.LENGTH_LONG).show();
                Intent Options = new Intent(Login_Activity.this, com.example.daras.robocare.Options.class);
                startActivity(Options);

            }
            else
            {
                Toast.makeText(Login_Activity.this,"Login Failed",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex)
        {
         ex.printStackTrace();
        }

    }



    public boolean ValidateUser() {

        boolean isValid = true;
        try
        {
            TempEmail=EmailID.getText().toString();
            TempPassword=Password.getText().toString();
            if(!TempEmail.equals("dara.sandeep91@gmail.com"))
            {
                EmailID.setError("Enter a valid EmailID");
                EmailID.requestFocus();
                isValid = false;
            }
            else if(!TempPassword.equals("password")){
                Password.setError("Enter a valid password");
                Password.requestFocus();
                isValid = false;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return isValid;
    }

    public void RegisterOnclick(View view)
    {
        try
        {
            Intent Options = new Intent(Login_Activity.this,Register.class);
            startActivity(Options);

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }



    public void hideKeyboard(){

        try
        {
            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double>
    {


        public void doSignup() {
            try {



                String userJSON = gson.toJson(User);
                String URI = "https://api.mlab.com/api/1/databases/robocare/collections/userdetails?apiKey=WQdetFzPianTtgBryFsYkPkNE-osQ-Ue";
                HttpURLConnection connection;
                connection = (HttpURLConnection) (new URL(URI)).openConnection();
                //HttpURLConnection connection = (HttpURLConnection)con.openConnection();
                // String urlParameters = "fizz=buzz";
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(userJSON);
                dStream.flush();
                dStream.close();


                Integer responseCodee = connection.getResponseCode();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){}

                if (responseCodee == 200) {


                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    StringBuilder responseOutput = new StringBuilder();
                    System.out.println("output===============" + br);
                    while((line = br.readLine()) != null ) {
                        responseOutput.append(line);
                    }
                    br.close();

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        @Override
        protected Double doInBackground(String... params) {
            doSignup();
            return null;
        }

    }


}
