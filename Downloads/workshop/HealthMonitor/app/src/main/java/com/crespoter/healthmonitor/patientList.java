package com.crespoter.healthmonitor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class patientList extends AppCompatActivity{
    private Sessions sessions;

    private class loadDetails extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPostExecute(String s) {
            final ArrayList<patient> patients = new ArrayList<>();
            try {
                Log.d("JSON", "onPostExecute: \n" +s);
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonPatientList = jsonObject.getJSONArray("patients");
                for(int i=0;i<jsonPatientList.length();i++)
                {
                    JSONObject row = jsonPatientList.getJSONObject(i);
                    patients.add(new patient(row.getString("name"),row.getString("age"),row.getString("sex"),
                            row.getString("temperature"),row.getString("pulse"),row.getString("id")));
                }
                final patientAdapter pa = new patientAdapter(patientList.this, patients);ListView patientViewList = (ListView) findViewById(R.id.patientsList);
                ListView patientList = (ListView) findViewById(R.id.patientsList);
                patientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position,
                                        long id) {

                    Intent intent = new Intent(patientList.this, PatientInformation.class);
                    intent.putExtra("name",patients.get(position).name);
                    intent.putExtra("age",patients.get(position).age);
                    intent.putExtra("sex",patients.get(position).sex);
                    intent.putExtra("temperature",patients.get(position).temperature);
                    intent.putExtra("pulse",patients.get(position).pulse);
                    intent.putExtra("id",patients.get(position).id);
                    startActivity(intent);
                }
            });
            patientList.setAdapter(pa);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String sendingUrl = ip.hostIp +"patient_list?doc_id=" + params[0];
                Log.d("myPatients", "doInBackground: " + sendingUrl);
                URL url = new URL(sendingUrl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                char[] inputBuffer = new char[50];
                String jsonList ="";
                int charsRead = reader.read(inputBuffer);
                while(charsRead>=0)
                {
                    jsonList+=String.copyValueOf(inputBuffer,0,charsRead);
                    charsRead = reader.read(inputBuffer);
                }
                reader.close();
                return jsonList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessions = new Sessions(getApplicationContext());
        String user = sessions.getUserName();
        final String id = sessions.getUserId();
        setContentView(R.layout.patients_list);
        ((TextView) findViewById(R.id.welcomeUser)).setText(("Welcome " + user));
        //BYPASS BACKDOOR
        if("root".equals(user)) {
            final ArrayList<patient> items = new ArrayList<>();
            items.add(new patient("Test Subject 1","18","Male","30","100","0"));
            items.add(new patient("Test Subject 2","18","Male","30","100","1"));
            items.add(new patient("Test Subject 3","18","Male","30","100","2"));
            final patientAdapter pa = new patientAdapter(this, items);
            ListView patientViewList = (ListView) findViewById(R.id.patientsList);
            patientViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position,
                                        long id) {

                    Intent intent = new Intent(patientList.this, PatientInformation.class);
                    intent.putExtra("name",items.get(position).name);
                    intent.putExtra("age",items.get(position).age);
                    intent.putExtra("sex",items.get(position).sex);
                    intent.putExtra("temperature",items.get(position).temperature);
                    intent.putExtra("pulse",items.get(position).pulse);
                    intent.putExtra("id",items.get(position).id);
                    startActivity(intent);
                }
            });
            patientViewList.setAdapter(pa);
        }
        else {
            final Handler handler = new Handler();
            loadDetails load = new loadDetails();
            load.execute(id);
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    loadDetails load = new loadDetails();
                    load.execute(id);
                    Log.d("DOWNLOADING", "run: downloading data");
                }
            }, 0, 3500);
        }

    }
}

