package com.example.saiyashu51.eheal_reunion;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Map;

public class ecg extends AppCompatActivity {

    private TextView textView;
    private LineGraphSeries<DataPoint> series;
    final private Double[] myList = new Double[6] ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecg);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, 0),
                new DataPoint(2, 0),
                new DataPoint(3, 0),
                new DataPoint(4, 0),
        });

        graph.addSeries(series);
        // customize a little view port
        Viewport view = graph.getViewport();
        view.setYAxisBoundsManual(true);
        view.setMinY(0);
        view.setMaxY(1500);
        view.setScrollable(true);


        textView = (TextView) findViewById(R.id.textView2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ecgData");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.v("Values:", "Data" + dataSnapshot.getValue());
                //Map<String,Integer> map = dataSnapshot.getValue(Map.class);

                GenericTypeIndicator<Map<String, Double>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Double>>() {};
                Map<String, Double> map = dataSnapshot.getValue(genericTypeIndicator );
                //Log.d("My Activity", "Value is: " + value);
                for(int i=1; i < 6 ; i+=1)
                {
                    myList[i] = map.get("data"+Integer.toString(i));
                }
                addEntry();
                textView.setText(Double.toString(myList[5]));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("My Activity", "Failed to read value.", error.toException());
            }
        });
        //   }

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