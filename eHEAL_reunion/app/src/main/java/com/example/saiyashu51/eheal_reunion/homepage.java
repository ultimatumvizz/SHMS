package com.example.saiyashu51.eheal_reunion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by saiyashu51 on 16/02/17.
 */

public class homepage extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }
    public void onPulseClick(View v)
    {
        if(v.getId() == R.id.pulse)
        {
            Intent i = new Intent(homepage.this, pulse.class);
            startActivity(i);
        }
    }
    public void onTempClick(View t)
    {
        if (t.getId() == R.id.temp)
        {
            Intent a = new Intent(homepage.this, Temperature.class);
            startActivity(a);
        }
    }
    public void onLocationClick(View m)
    {
        if(m.getId()==R.id.location)
        {
            Intent a = new Intent(homepage.this, location.class);
            startActivity(a);
        }
    }
    public void onECGClick(View m)
    {
        if(m.getId()==R.id.ecg)
        {
            Intent a = new Intent(homepage.this, ecg.class);
            startActivity(a);
        }
    }
}

