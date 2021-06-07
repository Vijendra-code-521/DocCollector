package com.example.doccollector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doccollector.daoclasses.DetailsValidation;
import com.example.doccollector.daoclasses.GlobalVariable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {

    // declaring variable;
    Button LoginButtonInRegisterPage , SignUpButtonInRegisterPage;
    EditText UserName_register , Email_Register , Contact_Register;
    EditText Password_Register , Confirm_pass_Register;
    FirebaseAuth mFirebaseAuth ;
    FirebaseFirestore  firestore;
    String userId;
    DetailsValidation detailsValidationMail , detailsValidationContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();


        // finding views of variable from register xml
        mFirebaseAuth = FirebaseAuth.getInstance();
        firestore =  FirebaseFirestore.getInstance();
        LoginButtonInRegisterPage = findViewById(R.id.Login_btn_register_page);
        SignUpButtonInRegisterPage = findViewById(R.id.signup_btn_register);
        UserName_register = findViewById(R.id.InputUserNameInRegister);
        Email_Register = findViewById(R.id.InputEmailInRegister);
        Contact_Register = findViewById(R.id.InputPhoneNoInRegister);
        Password_Register = findViewById(R.id.InputPasswordInRegister);
        Confirm_pass_Register = findViewById(R.id.InputConfirmPasswordInRegister);


        // variable for storing values provided by user




        SignUpButtonInRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_for_Registration , email_for_registration , contact_for_registration;
                String password_for_Registration , confirm_pass_for_Registration;
                // initializing all string var

                username_for_Registration = UserName_register.getText().toString().trim();
                // Log.i("name","user name "+username_for_Registration);
                email_for_registration = Email_Register.getText().toString().trim();
                GlobalVariable.globalemail = email_for_registration;
                contact_for_registration = Contact_Register.getText().toString().trim();
                password_for_Registration = Password_Register.getText().toString().trim();
                confirm_pass_for_Registration = Confirm_pass_Register.getText().toString().trim();

                /*
                 * validating contact and email format
                 */
                detailsValidationMail = new DetailsValidation(email_for_registration);
                detailsValidationContact = new DetailsValidation(contact_for_registration);


                if(username_for_Registration.isEmpty())
                    UserName_register.setError("user name required");
                else if(email_for_registration.isEmpty())
                    Email_Register.setError("Email is required");
                else if(!detailsValidationMail.isEmailValid())
                    Email_Register.setError("Invalid Email address");
                else if(contact_for_registration.isEmpty())
                    Contact_Register.setError("Phone no is required");
                else if(!detailsValidationContact.isPhoneNumberValid())
                    Contact_Register.setError("Invalid contact number");
                else if(password_for_Registration.isEmpty())
                    Password_Register.setError("Password can not be empty");
                else if(confirm_pass_for_Registration.isEmpty())
                    Confirm_pass_Register.setError("Confirm your password");
                else if(confirm_pass_for_Registration.length() < 8)
                    Confirm_pass_Register.setError("Password is short .. enter strong password");
                else if (!password_for_Registration.equals(confirm_pass_for_Registration))
                {
                    Confirm_pass_Register.setError("Password mismatch");
                    Password_Register.setError("Password mismatch");
                }
                else
                {
                    mFirebaseAuth.createUserWithEmailAndPassword(email_for_registration , password_for_Registration).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                finish();
                                userId = Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).getUid() ;
                                DocumentReference documentReference = firestore.collection(GlobalVariable.getGlobalglobalemail()).document(userId);
                                Map<String , Object> user = new HashMap<>();
                                user.put("name",username_for_Registration);
                                user.put("email",email_for_registration);
                                user.put("Password" , password_for_Registration) ;
                                user.put("PhoneNumber" , contact_for_registration) ;
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), UserDashboard.class));

                            }
                            else
                            {
                                Toast.makeText(Register.this, "ERROR!! please check your email and password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }



            }
        });

        LoginButtonInRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, EmailLogin.class));
            }
        });
    }
}