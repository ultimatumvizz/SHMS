package com.example.saiyashu51.eheal_reunion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    //private TextView textView;

    private EditText emailAddr;
    private EditText passWord;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        emailAddr = (EditText) findViewById(R.id.EmailAddr);
        passWord = (EditText) findViewById(R.id.PassWord);
        button = (Button) findViewById(R.id.LoginIn);

        progressDialog = new ProgressDialog(this);
        button.setOnClickListener(this);

        /**  textView = (TextView) findViewById(R.id.textView2);

         // Write a message to the database  // It works ...
         FirebaseDatabase database = FirebaseDatabase.getInstance();
         DatabaseReference myRef = database.getReference("message");

         String token = FirebaseInstanceId.getInstance().getToken();

         myRef.setValue("Hello, World! Vasil");
         myRef.setValue(token);

         myRef.addValueEventListener(new ValueEventListener() {
        @Override public void onDataChange(DataSnapshot dataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        String value = dataSnapshot.getValue(String.class);
        Log.d("My Activity", "Value is: " + value);
        textView.setText(value);
        }

        @Override public void onCancelled(DatabaseError error) {
        // Failed to read value
        Log.w("My Activity", "Failed to read value.", error.toException());
        }
        });      **/


    }

    private void signIn() {
        String email = emailAddr.getText().toString().trim();
        String passd = passWord.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(passd)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Logging in ... ");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, passd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), homepage.class));
                        } else {
                            progressDialog.setMessage("Not Registered Redirecting to Sign up.......");
                            progressDialog.show();
                            startActivity(new Intent(MainActivity.this, register.class));
                        }

                    }
                });

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.LoginIn) {

            //if(emailAddr.getText().toString() == "vasil.r14@iiits.in" && passWord.getText().toString() == "123")
            //{
                startActivity(new Intent(MainActivity.this, homepage.class));
            //}

            //signIn();
            /*
            if(user.getText().toString().equals("shms@iiits.in") && pass.getText().toString().equals("123"))
            {
                Intent i = new Intent(MainActivity.this,homepage.class);
                startActivity(i);
            }
            else
            {
                Intent r = new Intent(MainActivity.this, register.class);
                startActivity(r);
            }*/
        }


        /**   public void onClick(View view)
         {
         if(view.getId() == R.id.button2)
         {
         Intent i = new Intent(MainActivity.this, pulse.class);
         startActivity(i);
         }
         }     **/

    }

}

