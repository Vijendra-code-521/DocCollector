package com.example.doccollector.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doccollector.R;
import com.example.doccollector.SelectLoginType;
import com.example.doccollector.daoclasses.GlobalVariable;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;
import java.util.concurrent.Executor;


public class ProfileFragment extends Fragment {

    Button logout;
    FirebaseAuth fAuth;
    //crating firebase instance
    private FirebaseFirestore fstore;
    private String userId;
    private TextView usernameProfile , EmailProfile , ContactProfile;


    public ProfileFragment()
    {
        //empty constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewProfile =  inflater.inflate(R.layout.fragment_profile, container, false);
        usernameProfile = viewProfile.findViewById(R.id.textViewName);
        EmailProfile = viewProfile.findViewById(R.id.textViewuserEmail);
        ContactProfile = viewProfile.findViewById(R.id.textViewuserContact);
        logout = viewProfile.findViewById(R.id.button_logout);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fstore.collection(GlobalVariable.getGlobalglobalemail()).document(userId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ProfileFragment user = documentSnapshot.toObject(ProfileFragment.class);
                usernameProfile.setText(documentSnapshot.getString("name"));
                EmailProfile.setText(documentSnapshot.getString("email"));
                ContactProfile.setText(documentSnapshot.getString("PhoneNumber"));

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                Toast.makeText(getContext(), "log out successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), SelectLoginType.class));
                requireActivity().finish();
            }
        });
        return  viewProfile;
    }

}