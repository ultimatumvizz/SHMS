package com.example.saiyashu51.eheal_reunion;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by saiyashu51 on 04/04/17.
 */

public class MyFirebaseInstanceIdService  extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("My Activity", "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
       /** FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("token");
        myRef.setValue(refreshedToken);
        **/
    }
}
