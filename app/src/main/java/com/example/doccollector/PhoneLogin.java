package com.example.doccollector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doccollector.daoclasses.DetailsValidation;

import java.util.Objects;

public class PhoneLogin extends AppCompatActivity {

    Button SendOtpBtn , VerifyBtn;
    EditText MobileNoInPhoneLogin , OTPinPhoneLogin;
    DetailsValidation detailsValidation_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        
        SendOtpBtn = findViewById(R.id.send_otp);
        VerifyBtn = findViewById(R.id.verify_otp_btn);
        MobileNoInPhoneLogin = findViewById(R.id.InputPhoneNoInLoginPhone);
        OTPinPhoneLogin = findViewById(R.id.enter_otp_field);
        
        SendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entered_phoneNo ;
                entered_phoneNo = MobileNoInPhoneLogin.getText().toString().trim();
                detailsValidation_phone = new DetailsValidation(entered_phoneNo);
                if(entered_phoneNo.isEmpty())
                    MobileNoInPhoneLogin.setError("Phone no is required");
                else if(!detailsValidation_phone.isPhoneNumberValid())
                    MobileNoInPhoneLogin.setError("Invalid contact number");
                else
                {
                    Toast.makeText(PhoneLogin.this, "authentication remaining ", Toast.LENGTH_SHORT).show();
                    // send Otp through firebase authentication
                }

            }
        });
        VerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receivedOTP ;
                receivedOTP = OTPinPhoneLogin.getText().toString().trim();

                if(receivedOTP.isEmpty())
                    MobileNoInPhoneLogin.setError("Enter OTP");
                else
                {
                    Toast.makeText(PhoneLogin.this, "authentication OTP", Toast.LENGTH_SHORT).show();
                    // Otp verification through firebase authentication
                }

            }
        });



    }
}