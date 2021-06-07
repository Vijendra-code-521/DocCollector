package com.example.doccollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doccollector.daoclasses.GlobalVariable;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

   FirebaseAuth firebaseAuthSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();

        firebaseAuthSplash = FirebaseAuth.getInstance();
        Thread thread = new Thread()
        {
            public void run()
            {
                try{
                    sleep(3000);
                    if(firebaseAuthSplash.getCurrentUser() != null)
                    {
                        GlobalVariable.globalemail = firebaseAuthSplash.getCurrentUser().getEmail();
                        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                        finish();
                    }
                    else
                    {
                        startActivity(new Intent(getApplicationContext(),SelectLoginType.class));
                        finish();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(SplashScreen.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        };thread.start();

    }
}