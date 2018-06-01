package com.example.saiyashu51.eheal_reunion;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.Map;

/**
 * Created by saiyashu51 on 17/02/17.
 */

public class Temperature extends AppCompatActivity {

    private BarGraphSeries<DataPoint> series;
    final private Double[] myList = new Double[6] ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        GraphView bar = (GraphView) findViewById(R.id.barTemp);
        series = new BarGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, 0),
                new DataPoint(2, 0),
                new DataPoint(3, 0),
                new DataPoint(4, 0),
        });
        bar.addSeries(series);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 10, (int) Math.abs(data.getY() * 255 / 10), 100);
            }
        });

        series.setSpacing(75);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        bar.getViewport().setScrollable(true);
        bar.getViewport().setScrollableY(true);
        bar.getViewport().setScalable(true);
        bar.getViewport().setScalableY(true);


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("temperature");



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, Double>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Double>>() {};
                Map<String, Double> map = dataSnapshot.getValue(genericTypeIndicator );
                for(int i=1; i < 6 ; i+=1)
                {
                    myList[i] = map.get("data"+Integer.toString(i));
                }
                addEntry();
                if( myList[5] >= 38.9 && myList[5] <= 35.56 ) {
                    Toast.makeText(Temperature.this,"Temperature is not normal , consult your doctor",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Temperature.this,"Temperature is normal",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void addEntry() {
        // here we choose to display max 10- points to viewport and scroll to end
        //series.appendData(new DataPoint(2,Integer.parseInt(value)), true, 10);
        series.resetData(generatedData());
    }

    private  DataPoint[] generatedData() {

        int count = 5;
        DataPoint[] values = new  DataPoint[count];
        for(int i = 0 ; i < count ; i++) {
            DataPoint v = new DataPoint(i,myList[i+1]);
            values[i] = v;
        }
        return values;
    }

}