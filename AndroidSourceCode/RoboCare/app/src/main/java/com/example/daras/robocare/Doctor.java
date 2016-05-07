package com.example.daras.robocare;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Doctor extends AppCompatActivity {

    private String clientId = "MV0IECXG5AA0QBVYNAE4LIVTSPYXT5ZAKCM4KFPBCB500VSY";
    private String clientSecret = "XOMRCZL3ZJLNOQVFBKU5FWFQJRXRZR5U2XUX5BCDT3M5GDPU";
    //private  String url = "https://api.foursquare.com/v2/venues/explore?";
    private String url = "https://api.foursquare.com/v2/venues/search?";
    public static JSONObject json = null;
    public static String[] venuList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        new MyAsyncTask().execute();
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {


        public void SearchDoctor( ) {
            try {

                //GetDetails();
                //String userJSON = gson.toJson(User);

                String URI = url + "near=kansas&" + "query=" + Constants.DiseaseName + "&" + "limit=5&" + "client_id=" + clientId + "&" + "client_secret=" + clientSecret + "&v=20160501";
                HttpsURLConnection connection;

                connection = (HttpsURLConnection) (new URL(URI)).openConnection();

                connection.setDoOutput(true);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.connect();


                Integer responseCodee = connection.getResponseCode();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                }

                if (responseCodee == 200) {




                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    StringBuilder responseOutput = new StringBuilder();
                    System.out.println("output===============" + br);
                    while((line = br.readLine()) != null ) {
                        responseOutput.append(line);
                    }
                    br.close();
                    JSONArray venues = null;
                    json = new JSONObject(responseOutput.toString());
                    venues = json.getJSONObject("response").getJSONArray("venues");
                    venuList = new String[venues.length()];

                }
            } catch (Exception ex) {
                ex.printStackTrace();

            }

        }

        @Override
        protected Double doInBackground(String... params) {
            SearchDoctor();
            return null;
        }

    }
}
