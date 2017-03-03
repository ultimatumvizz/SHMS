package com.crespoter.healthmonitor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

public class patientAdapter extends ArrayAdapter<patient>{

    public patientAdapter(Context context, List<patient> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        patient p = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.patient_name,parent,false);
        }
        Button b = (Button)convertView.findViewById(R.id.patientName);
        b.setText(p.name);
        return convertView;
    }
}
