package com.example.saiyashu51.eheal_reunion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

public class pulse extends AppCompatActivity {

    private TextView textView;
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    private  Integer i;
    private Integer value;
    final private Double[] myList = new Double[6] ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, 0),
                new DataPoint(2, 0),
                new DataPoint(3, 0),
                new DataPoint(4, 0),
        });

        //series = new LineGraphSeries<DataPoint>();

        graph.addSeries(series);
        // customize a little view port
        Viewport view = graph.getViewport();
        view.setYAxisBoundsManual(true);
        view.setMinY(0);
        view.setMaxY(200);
        view.setScrollable(true);


        textView = (TextView) findViewById(R.id.textView2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("data");

       // for (i=1; i < 6 ; i+=1 ) {


            DatabaseReference myRef = database.getReference("pulseData");


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
                       textView.setText(Double.toString(myList[5])+" bpm");

                       if(myList[5] < 60.0 || myList[5] > 100.0) {
                           Toast.makeText(pulse.this,"Pulse is not normal , consult your doctor",Toast.LENGTH_LONG).show();
                       }
                       else
                       {
                           Toast.makeText(pulse.this,"Pulse is normal",Toast.LENGTH_LONG).show();
                       }
                       //series.appendData(new DataPoint(2,4), true, 10);
                       //series.resetData(generatedData());
                       //series.resetData(([1,2,3,4,5]))
                   }

                   @Override
                   public void onCancelled(DatabaseError error) {
                       // Failed to read value
                       Log.w("My Activity", "Failed to read value.", error.toException());
                   }
               });
     //   }


        //textView = (TextView) findViewById(R.id.textView2);

/**
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = dataSnapshot.getValue(String.class);
                //Log.d("My Activity", "Value is: " + value);
                textView.setText(value);
                //series.appendData(new DataPoint(2,4), true, 10);
                //series.resetData(generatedData());
                //series.resetData(([1,2,3,4,5]))
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("My Activity", "Failed to read value.", error.toException());
            }
        });     **/


     /**   series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                    //addEntry();
                    threadRun();
            }
        });       **/


    }
/**
  @Override
    protected void onResume()
    {
        super.onResume();
        //we're going to simulate real time with thread that append data to the graph

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("data/data");
        textView = (TextView) findViewById(R.id.textView2);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = dataSnapshot.getValue(String.class);
                //Log.d("My Activity", "Value is: " + value);
                textView.setText(value);

            /**    new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // we add 100 new points
                        for (int i = 99; i < 100;i++)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addEntry();
                                }
                            });
                            // sleep to slow down the entries
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }).start();
                //series.resetData(([1,2,3,4,5]))
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("My Activity", "Failed to read value.", error.toException());
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                // we add 100 new points
                for (int i = 0; i < 100;i++)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
                    // sleep to slow down the entries
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();
    }   **/


    private void threadRun() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // we add 100 new points
                for (int i = 99; i < 100;i++)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
                    // sleep to slow down the entries
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();

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