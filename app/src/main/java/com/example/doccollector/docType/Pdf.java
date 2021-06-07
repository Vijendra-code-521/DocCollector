package com.example.doccollector.docType;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
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

public class Pdf extends Fragment {
    Button downloadImage , DeletePdf;
    StorageReference ref;
    StorageReference mStorageRef;
    String path;
    String fileNameToDelete;
    String filenameToDownload;
    TextView Pdf1_Download , Pdf2_Download , Pdf3_Download;
    TextView Pdf4_Download , Pdf5_Download ,  fileNameSelected;

    public Pdf() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pdf, container, false);
        downloadImage =  view.findViewById(R.id.download_pdf);
        DeletePdf = view.findViewById(R.id.delete_pdf);

        // uploaded document list
        Pdf1_Download = view.findViewById(R.id.Uploadedpdf1);
        Pdf2_Download = view.findViewById(R.id.Uploadedpdf2);
        Pdf3_Download = view.findViewById(R.id.Uploadedpdf3);
        Pdf4_Download = view.findViewById(R.id.Uploadedpdf4);
        Pdf5_Download = view.findViewById(R.id.Uploadedpdf5);

        fileNameSelected = view.findViewById(R.id.InputFileNameInDownloadpdf);

        path = GlobalVariable.getGlobalglobalemail();
        mStorageRef = FirebaseStorage.getInstance().getReference(path);


        downloadImage.setEnabled(false);
        DeletePdf.setEnabled(false);
        Pdf1_Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filenameToDownload = Pdf1_Download.getText().toString().trim();
                fileNameToDelete = Pdf1_Download.getText().toString().trim();
                fileNameSelected.setText(filenameToDownload);
                downloadImage.setEnabled(true);
                DeletePdf.setEnabled(true);

            }
        });
        Pdf2_Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filenameToDownload = Pdf2_Download.getText().toString().trim();
                fileNameToDelete = Pdf2_Download.getText().toString().trim();
                fileNameSelected.setText(filenameToDownload);
                downloadImage.setEnabled(true);
                DeletePdf.setEnabled(true);
            }
        });
        Pdf3_Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filenameToDownload = Pdf3_Download.getText().toString().trim();
                fileNameToDelete = Pdf3_Download.getText().toString().trim();
                fileNameSelected.setText(filenameToDownload);
                downloadImage.setEnabled(true);
                DeletePdf.setEnabled(true);
            }
        });
        Pdf4_Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filenameToDownload = Pdf4_Download.getText().toString().trim();
                fileNameToDelete = Pdf4_Download.getText().toString().trim();
                fileNameSelected.setText(filenameToDownload);
                downloadImage.setEnabled(true);
                DeletePdf.setEnabled(true);
            }
        });
        Pdf5_Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filenameToDownload = Pdf5_Download.getText().toString().trim();
                fileNameToDelete = Pdf5_Download.getText().toString().trim();
                fileNameSelected.setText(filenameToDownload);
                downloadImage.setEnabled(true);
                DeletePdf.setEnabled(true);
            }
        });
        DeletePdf.setOnClickListener(new View.OnClickListener() {
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
                        DeletePdf.setEnabled(false);
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

        downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
        return view ;
    }
    public void download()
    {

        ref = mStorageRef.child(filenameToDownload);
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                String fextension ;        // storing  file extension from firebase 
                fextension = getFileExtension(uri);
                downloadFiles(requireActivity(),filenameToDownload,"."+fextension,DIRECTORY_DOWNLOADS,url);
                Toast.makeText(getContext(),"Download successful",Toast.LENGTH_SHORT ).show();
                downloadImage.setEnabled(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"looks like you haven't upload this pdf",Toast.LENGTH_SHORT ).show();
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
    
    // getting file extension through  uri 
    private String getFileExtension(Uri uri)
    {
        ContentResolver  contentResolver = requireContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}

