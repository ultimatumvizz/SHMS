package com.crespoter.healthmonitor;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    Sessions session;
    Intent patients;
    String userName ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Sessions(this.getApplicationContext());
        if(session.isInitialised())
        {
            Toast.makeText(this,"Already login in",Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_main);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    private class validateEmailHttp extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPostExecute(String s) {
            Log.d("HELLO", "onPostExecute: " +s);
            if("username invalid".equals(s))
            {
                Toast.makeText(MainActivity.this,"Invalid Username",Toast.LENGTH_LONG).show();
            }
            else if("password invalid".equals(s))
            {
                Toast.makeText(MainActivity.this,"Invalid Password",Toast.LENGTH_LONG).show();
            }
            else {
                patients = new Intent(MainActivity.this, patientList.class);
                session.setUserName(userName);
                session.setUserId(s);
                MainActivity.this.startActivity(patients);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String password = params[1];
            try {
                String sendingUrl = ip.hostIp+"validate_doctor?name="+URLEncoder.encode(name).replace("+","%20")+"&password="+URLEncoder.encode(password).replace("+","%20");
                Log.d("HELLO", "doInBackground: " + sendingUrl);
                URL url = new URL(sendingUrl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                char[] inputBuffer = new char[50];
                String status ="";
                int charsRead = reader.read(inputBuffer);
                while(charsRead>=0)
                {
                    status+=String.copyValueOf(inputBuffer,0,charsRead);
                    charsRead = reader.read(inputBuffer);
                }
                reader.close();
                return status;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";

        }
    }
    @Override
    public void onClick(View v) {
        userName = ((EditText)findViewById(R.id.userName)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();

        //BACKDOOR
        if("root".equals(userName) && "sudo letmein".equals(password))
        {
            session.setUserName("root");
            patients = new Intent(MainActivity.this,patientList.class);
            MainActivity.this.startActivity(patients);
        }
        else
        {
            validateEmailHttp validate = new validateEmailHttp();
            validate.execute(userName,password);
        }
    }
}
