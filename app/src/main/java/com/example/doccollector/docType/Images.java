package com.example.doccollector.docType;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doccollector.R;
import com.example.doccollector.daoclasses.GlobalVariable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Images extends Fragment {


    Button downloadImage , DeleteImage;
    StorageReference ref;
    StorageReference mStorageRef;
    String path;
    String filenameToDownload , fileNameToDelete;
    TextView aadharCardDowload , panCardDownload , markSheetDownload;
    TextView DLicenceDownload , VisaDownload , VoterIdDownload , fileNameSelected;

    public Images() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewImages = inflater.inflate(R.layout.fragment_images, container, false);
        downloadImage =  viewImages.findViewById(R.id.download_image);
        DeleteImage = viewImages.findViewById(R.id.delete_image);

        // uploaded document list
        aadharCardDowload = viewImages.findViewById(R.id.adharcardUploaded);
        panCardDownload = viewImages.findViewById(R.id.PancardUploaded);
        markSheetDownload = viewImages.findViewById(R.id.Marksheet);
        DLicenceDownload = viewImages.findViewById(R.id.Driving_Licence);
        VisaDownload = viewImages.findViewById(R.id.visa);
        VoterIdDownload = viewImages.findViewById(R.id.VoterIdUploaded);
        fileNameSelected = viewImages.findViewById(R.id.InputFileNameInDownloadImages);

        path = GlobalVariable.getGlobalglobalemail();
        mStorageRef = FirebaseStorage.getInstance().getReference(path);


        downloadImage.setEnabled(false);
        DeleteImage.setEnabled(false);
        aadharCardDowload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filenameToDownload = aadharCardDowload.getText().toString().trim();
                fileNameToDelete = aadharCardDowload.getText().toString().trim();
                fileNameSelected.setText(filenameToDownload);
                downloadImage.setEnabled(true);
                DeleteImage.setEnabled(true);

            }
        });
        panCardDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filenameToDownload = panCardDownload.getText().toString().trim();
                fileNameToDelete = panCardDownload.getText().toString().trim();
                fileNameSelected.setText(filenameToDownload);
                downloadImage.setEnabled(true);
                DeleteImage.setEnabled(true);
            }
        });
        markSheetDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filenameToDownload = markSheetDownload.getText().toString().trim();
                fileNameToDelete = markSheetDownload.getText().toString().trim();
                fileNameSelected.setText(filenameToDownload);
                downloadImage.setEnabled(true);
                DeleteImage.setEnabled(true);
            }
        });
        DLicenceDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filenameToDownload = DLicenceDownload.getText().toString().trim();
                fileNameToDelete = DLicenceDownload.getText().toString().trim();
                fileNameSelected.setText(filenameToDownload);
                downloadImage.setEnabled(true);
                DeleteImage.setEnabled(true);
            }
        });
        VisaDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filenameToDownload = VisaDownload.getText().toString().trim();
                fileNameToDelete = VisaDownload.getText().toString().trim();
                fileNameSelected.setText(filenameToDownload);
                downloadImage.setEnabled(true);
                DeleteImage.setEnabled(true);
            }
        });
        VoterIdDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filenameToDownload = VoterIdDownload.getText().toString().trim();
                fileNameToDelete = VoterIdDownload.getText().toString().trim();
                fileNameSelected.setText(filenameToDownload);
                downloadImage.setEnabled(true);
                DeleteImage.setEnabled(true);
            }
        });

        downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });

        //Implementing Delete Button

        DeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a reference to the file to delete
                StorageReference desertRef = mStorageRef.child(fileNameToDelete);

                // Delete the file
                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                        Toast.makeText(getContext(), "File Deleted Successfully", Toast.LENGTH_SHORT).show();
                        DeleteImage.setEnabled(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                        Toast.makeText(getContext(), "may be you haven't uploaded this image", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return viewImages ;
    }



    public void download()
    {

        ref = mStorageRef.child(filenameToDownload);
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadFiles(requireActivity(),filenameToDownload,".jpg",DIRECTORY_DOWNLOADS,url);
                Toast.makeText(getContext(),"Download successful",Toast.LENGTH_SHORT ).show();
                downloadImage.setEnabled(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"looks like you haven't upload this image",Toast.LENGTH_SHORT ).show();
                downloadImage.setEnabled(false);
            }
        });
    }
    public void downloadFiles(Context context, String fname, String fextension, String destinationDirectory, String url)
    {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri) ;

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) ;
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fname + fextension);

        downloadManager.enqueue(request) ;


    }
}