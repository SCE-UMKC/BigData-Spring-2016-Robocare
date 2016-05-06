package com.example.daras.robocare;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.daras.robocare.UserDetails;

public class Register extends AppCompatActivity {



    EditText Name;
    EditText Age;
    EditText Email;
    EditText Password;
    RadioButton rdMale;
    RadioButton rdFemale;
    Button Register;
    UserDetails User;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = (EditText)findViewById(R.id.txtName);
        Age = (EditText)findViewById(R.id.txtAge);
        Email = (EditText)findViewById(R.id.txtEmail);
        Password = (EditText)findViewById(R.id.txtPassword);
        Register = (Button)findViewById(R.id.btnRegister);
        rdMale = (RadioButton)findViewById(R.id.rdMale);
        rdFemale = (RadioButton)findViewById(R.id.rdFemale);

        User = new UserDetails();

        gson = new Gson();

    }

    public void RegisterClicked(View v)
    {
        try
        {
            new MyAsyncTask().execute();
            Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_LONG).show();
            Intent Options = new Intent(Register.this,Login_Activity.class);
            startActivity(Options);

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void GetDetails()
    {
        User.setName(Name.getText().toString());
        User.setAge(Age.getText().toString());
        User.setEmail(Email.getText().toString());
        User.setPassword(Password.getText().toString());
        if (rdMale.isChecked())
        {
            User.setGender(Constants.SetMale);
        }
        else if(rdFemale.isChecked())
        {
            User.setGender(Constants.SetFemale);
        }
    }
    private class MyAsyncTask extends AsyncTask<String, Integer, Double>
    {


        public void doSignup() {
            try {

                GetDetails();
                String userJSON = gson.toJson(User);

           /* MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, userJSON);
            String requestUrl = "https://api.mlab.com/api/1/databases/robocare/collections/userdetails?apiKey=WQdetFzPianTtgBryFsYkPkNE-osQ-Ue";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(requestUrl).post(body).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, Throwable throwable) {
                    //Log.e("Error from the service", e.getMessage());
                    startActivity(mainIntent);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    final String respJson = response.body().string();
                    Log.i("Json String: ", respJson);
                    if (respJson.isEmpty()) {
                        Log.e("response json is empty", respJson);
                    }
                }
            });*/





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
