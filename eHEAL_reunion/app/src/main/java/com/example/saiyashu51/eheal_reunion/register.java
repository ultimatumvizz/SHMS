package com.example.saiyashu51.eheal_reunion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by saiyashu51 on 17/02/17.
 */

public class register extends Activity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private EditText editTextUserName;
    private EditText editTextEmailAddr;
    private EditText editTextPassword;
    private EditText getEditTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();

            startActivity(new Intent(getApplicationContext(), homepage.class));
        }

        editTextUserName = (EditText) findViewById(R.id.RFuserName);
        editTextEmailAddr = (EditText) findViewById(R.id.RFemailAddr);
        editTextPassword = (EditText) findViewById(R.id.RFpassword);
        getEditTextConfirmPassword = (EditText) findViewById(R.id.RFconfirm);

    }


    public void registerUser(){
        String userName = editTextUserName.getText().toString().trim();
        String emailAddr = editTextEmailAddr.getText().toString().trim();
        String passWord = editTextPassword.getText().toString().trim();
        String confPassWord = getEditTextConfirmPassword.getText().toString().trim();

        if(TextUtils.isEmpty(userName)) {
            Toast.makeText(this,"Please enter the full name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(emailAddr)){
            Toast.makeText(this, "Please enter the user name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passWord) || TextUtils.isEmpty(confPassWord)){
            Toast.makeText(this,"Please enter the password", Toast.LENGTH_LONG).show();
            return;
        }
        if(!passWord.equals(confPassWord)){
            Toast.makeText(this,"Please re-enter the password",Toast.LENGTH_LONG).show();
        }

        progressDialog.setMessage("Registering Please Wait ... ");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(emailAddr,passWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(register.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });



    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Rregister) {
            registerUser();
        }
    }
}
