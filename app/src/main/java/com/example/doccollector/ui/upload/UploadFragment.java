package com.example.doccollector.ui.upload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.doccollector.R;
import com.example.doccollector.docType.Images;
import com.example.doccollector.docType.Pdf;
import com.example.doccollector.docType.UploadImages;
import com.example.doccollector.docType.UploadPdfFiles;


public class UploadFragment extends Fragment {

    ImageView ImageFolderUpload , PdfFolderUpload;
    LinearLayout linearLayoutUpload;
    public UploadFragment()
    {
        // empty constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewUpload =  inflater.inflate(R.layout.fragment_upload, container, false);
        ImageFolderUpload = viewUpload.findViewById(R.id.imageFolder_Upload);
        PdfFolderUpload = viewUpload.findViewById(R.id.pdfFolder_Upload);
        linearLayoutUpload = viewUpload.findViewById(R.id.linearLayoutDocumentFragUpload);


        UploadImages images_upload = new UploadImages();
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.linearLayoutDocumentFragUpload ,images_upload);
        transaction.commit();

        ImageFolderUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImages images_upload = new UploadImages();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.linearLayoutDocumentFragUpload ,images_upload);
                transaction.commit();
            }
        });
        PdfFolderUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadPdfFiles pdfUpload= new UploadPdfFiles();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.linearLayoutDocumentFragUpload , pdfUpload);
                transaction.commit();
            }
        });
        return viewUpload;

    }


}