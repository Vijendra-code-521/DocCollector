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

import android.os.Handler;
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

public class UploadImages extends Fragment {


    int requestCodeForImage = 0;
    TextView fileNameDocument;
    Button UploadBtnDocument;
    ImageView ImageToUpload;
    public Uri mImageUri;
    FirebaseAuth fAuth;
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    StorageTask mUploadTask;
    String path;
    String fileName;
    TextView aadharCardUpload , panCardUpload , markSheetUpload;
    TextView DLicenceUpload , VisaUpload , VoterIdUpload;
    public UploadImages() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewUploadImages =  inflater.inflate(R.layout.fragment_upload_images, container, false);
        
        UploadBtnDocument = viewUploadImages.findViewById(R.id.upload_btn_aadharCard);
        ImageToUpload = viewUploadImages.findViewById(R.id.ImageUploadAadharCard);
        fileNameDocument = viewUploadImages.findViewById(R.id.InputFileNameInUploadImages);

        aadharCardUpload = viewUploadImages.findViewById(R.id.adharcardUpload);
        panCardUpload = viewUploadImages.findViewById(R.id.PancardUpload);
        markSheetUpload = viewUploadImages.findViewById(R.id.MarksheetUpload);
        DLicenceUpload = viewUploadImages.findViewById(R.id.Driving_LicenceUpload);
        VisaUpload = viewUploadImages.findViewById(R.id.visaUpload);
        VoterIdUpload = viewUploadImages.findViewById(R.id.VoterIdUpload);
        
        fAuth = FirebaseAuth.getInstance();
        path = GlobalVariable.getGlobalglobalemail();

        mStorageRef = FirebaseStorage.getInstance().getReference(path);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(path);
        //*************************************************************************************************************************************/
        // Coding For Upload Button :

        UploadBtnDocument.setEnabled(false); // required
        //Now let User Select File From local Database
        aadharCardUpload .setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                requestCodeForImage = 12;
                    fileName = aadharCardUpload.getText().toString().trim();
                    fileNameDocument.setText(fileName);
                    selectFile();
                    aadharCardUpload.setTextColor(Color.GREEN);
            }
        });


        // for pancard

        panCardUpload .setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                requestCodeForImage = 13;
                fileName = panCardUpload.getText().toString().trim();
                fileNameDocument.setText(fileName);
                selectFile();
                panCardUpload.setTextColor(Color.GREEN);
            }
        });

        // for Visa
        VisaUpload .setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                requestCodeForImage = 14;
                fileName = VisaUpload.getText().toString().trim();
                fileNameDocument.setText(fileName);
                selectFile();
                VisaUpload.setTextColor(Color.GREEN);
            }
        });

        // for voterId

        VoterIdUpload .setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                requestCodeForImage = 15;
                fileName = VoterIdUpload.getText().toString().trim();
                fileNameDocument.setText(fileName);
                selectFile();
                VoterIdUpload.setTextColor(Color.GREEN);
            }
        });

        // for driving licence

        markSheetUpload .setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                requestCodeForImage = 16;
                fileName = DLicenceUpload.getText().toString().trim();
                fileNameDocument.setText(fileName);
                selectFile();
                markSheetUpload.setTextColor(Color.GREEN);
            }
        });
        // for marksheet
        markSheetUpload .setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                requestCodeForImage = 17;
                fileName = markSheetUpload.getText().toString().trim();
                fileNameDocument.setText(fileName);
                selectFile();
                markSheetUpload.setTextColor(Color.GREEN);
            }
        });

        return  viewUploadImages;
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
        String[] mimetypes = {"image/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(Intent.createChooser(intent, "SELECT IMAGE FILE"), requestCodeForImage);  //requestCode can be any +ve number.

    }

    //**********************************************************************************************************************

    // code for upload button
    @Override
   public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestCodeForImage && resultCode == RESULT_OK && data != null && data.getData() != null) {
            UploadBtnDocument.setEnabled(true);

            //aadharCardUpload .setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));
            mImageUri = data.getData();
            ImageToUpload.setImageURI(mImageUri);              // Substitute for Picasso
            UploadBtnDocument.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("image name" ,""+fileName);
                    if (fileName.isEmpty()) {
                        fileNameDocument.setError("File name is required");
                    }
                    else if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(getContext(), "Upload In Progress", Toast.LENGTH_SHORT).show(); //preventing multiple uploads
                    } else {
                        StorageReference reference = mStorageRef.child(fileName);
                        mUploadTask = reference.putFile(mImageUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(getContext(), "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                        ImageToUpload.setImageResource(R.drawable.uploadimg);
                                        UploadBtnDocument.setEnabled(false);

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