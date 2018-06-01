package com.example.saiyashu51.eheal_reunion;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class location extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private  LatLng sydney;
    private Double latitude = 13.0833;
    private  Double longitude = 80.2833;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       /** FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("location");


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


                    latitude = map.get("latitude");
                    longitude = map.get("longitude");
                    sydney = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("My Activity", "Failed to read value.", error.toException());
            }
        });  **/
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
         mMap = googleMap;

        // Add a marker in Sydney and move the camera
        sydney = new LatLng(latitude, longitude);

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

       FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("location");


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


                latitude = map.get("latitude");
                longitude = map.get("longitude");
                sydney  = new LatLng(latitude,longitude);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("My Activity", "Failed to read value.", error.toException());
            }
        });


    }
}
