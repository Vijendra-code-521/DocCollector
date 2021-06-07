package com.example.doccollector.docType;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doccollector.R;
import com.example.doccollector.daoclasses.GlobalVariable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;


public class UploadPdfFiles extends Fragment {

    TextView fileSelector ,fileSelector1 ,fileSelector2, fileSelector3 , fileSelector4;
    int requestCodeForPdf;
    TextView fileNameDocumentInPdf;
    Button UploadBtnPdf;
    ImageView PdfImageView;
    public Uri mImageUri;
    FirebaseAuth fAuth;
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    StorageTask mUploadTask;
    String pathPdf;
    String fileNamePdf;

    public UploadPdfFiles() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewUploadPdf= inflater.inflate(R.layout.fragment_upload_pdf_files, container, false);
        fileSelector = viewUploadPdf.findViewById(R.id.Pdf1);
        fileSelector1 = viewUploadPdf.findViewById(R.id.Pdf2);
        fileSelector2 = viewUploadPdf.findViewById(R.id.Pdf3);
        fileSelector3 = viewUploadPdf.findViewById(R.id.Pdf4);
        fileSelector4 = viewUploadPdf.findViewById(R.id.Pdf5);


        UploadBtnPdf = viewUploadPdf.findViewById(R.id.upload_btn_Pdf);
        PdfImageView = viewUploadPdf.findViewById(R.id.ImageUploadPdf);
        fileNameDocumentInPdf = viewUploadPdf.findViewById(R.id.InputFileNamePdf);


        fAuth = FirebaseAuth.getInstance();
        pathPdf = GlobalVariable.getGlobalglobalemail();

        mStorageRef = FirebaseStorage.getInstance().getReference(pathPdf);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(pathPdf);
        //*************************************************************************************************************************************/
        // Coding For Upload Button :

        UploadBtnPdf.setEnabled(false); // required
        //Now let User Select File From local Database
        fileSelector.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                requestCodeForPdf = 12;
                fileNamePdf = fileSelector.getText().toString().trim() ;
                fileNameDocumentInPdf.setText(fileNamePdf);
                fileSelector.setTextColor(Color.GREEN);
                selectFile();


            }
        });
        fileSelector1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                requestCodeForPdf = 13;
                fileNamePdf = fileSelector1.getText().toString().trim() ;
                fileNameDocumentInPdf.setText(fileNamePdf);
                fileSelector1.setTextColor(Color.GREEN);
                selectFile();


            }
        });
        fileSelector2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                requestCodeForPdf = 14;
                fileNamePdf = fileSelector2.getText().toString().trim() ;
                fileNameDocumentInPdf.setText(fileNamePdf);
                fileSelector2.setTextColor(Color.GREEN);
                selectFile();


            }
        });
        fileSelector3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                requestCodeForPdf = 15;
                fileNamePdf = fileSelector3.getText().toString().trim() ;
                fileNameDocumentInPdf.setText(fileNamePdf);
                fileSelector3.setTextColor(Color.GREEN);
                selectFile();


            }
        });
        fileSelector4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                requestCodeForPdf = 16;
                fileNamePdf = fileSelector4.getText().toString().trim() ;
                fileNameDocumentInPdf.setText(fileNamePdf);
                fileSelector4.setTextColor(Color.GREEN);
                selectFile();


            }
        });
        return viewUploadPdf;
    }
    //*************************************************************************************************************************************************//
    // selecting file type
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void selectFile() {

        // FROM STACKOVERFLOW :
        //Here is the list of common MIME type that you can set in setType():
//        image/jpeg
//        audio/mpeg4-generic
//        text/html
//        audio/mpeg
//        audio/aac
//        audio/wav
//        audio/ogg
//        audio/midi
//        audio/x-ms-wma
//        video/mp4
//        video/x-msvideo
//        video/x-ms-wmv
//        image/png
//        image/jpeg
//        image/gif
//        .xml ->text/xml
//        .txt -> text/plain
//        .cfg -> text/plain
//        .csv -> text/plain
//        .conf -> text/plain
//        .rc -> text/plain
//        .htm -> text/html
//        .html -> text/html
//        .pdf -> application/pdf
//        .apk -> application/vnd.android.package-archive

        // AMAZING HELP FROM STACKOVERFLOW ... This guys are Amazing .
        // GOT THIS SNIPPET FROM STACKOVERFLOW 👇

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        String[] mimetypes = { "application/pdf"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(Intent.createChooser(intent, "SELECT IMAGE OR PDF FILE"), requestCodeForPdf);  //requestCode can be any +ve number.

    }

    // code for upload button
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestCodeForPdf && resultCode == RESULT_OK && data != null && data.getData() != null) {
            UploadBtnPdf.setEnabled(true);

           // fileSelector.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));
            mImageUri = data.getData();
            PdfImageView.setImageURI(mImageUri);              // Substitute for Picasso


            UploadBtnPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i("image name" ,""+fileNamePdf);
                    if (fileNamePdf.isEmpty()) {
                        fileNameDocumentInPdf.setError("File name is required");
                    }
                    else if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(getContext(), "Upload In Progress", Toast.LENGTH_SHORT).show(); //preventing multiple uploads
                    } else {
                        StorageReference reference = mStorageRef.child(fileNamePdf);
                        mUploadTask = reference.putFile(mImageUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(getContext(), "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                        PdfImageView.setImageResource(R.drawable.uploadimg);
                                        UploadBtnPdf.setEnabled(false);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("TAG1", "onFailuer Upload" + e.getMessage());
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                }
            });


        }
    }

}