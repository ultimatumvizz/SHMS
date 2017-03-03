package com.crespoter.healthmonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class PatientInformation extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_information);
        String name = getIntent().getStringExtra("name");
        String age = getIntent().getStringExtra("age");
        String sex = getIntent().getStringExtra("sex");
        String id = getIntent().getStringExtra("id");
        String temperature = getIntent().getStringExtra("temperature");
        String pulse = getIntent().getStringExtra("pulse");
        ((TextView)findViewById(R.id.info_name_show)).setText(name);
        ((TextView)findViewById(R.id.info_name)).setText(name);
        ((TextView)findViewById(R.id.info_age)).setText(age);
        ((TextView)findViewById(R.id.info_sex)).setText(sex);
        ((TextView)findViewById(R.id.info_pulse)).setText(pulse);
        ((TextView)findViewById(R.id.current_temperature)).setText(temperature);
    }
}
