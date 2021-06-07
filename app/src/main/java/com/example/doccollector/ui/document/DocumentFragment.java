package com.example.doccollector.ui.document;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.doccollector.R;
import com.example.doccollector.docType.Images;
import com.example.doccollector.docType.Pdf;

public class DocumentFragment extends Fragment {

    ImageView ImageFolder , PdfFolder;
    LinearLayout linearLayout;
    public DocumentFragment()
    {
        // empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewDocument = inflater.inflate(R.layout.fragment_document, container, false);
        ImageFolder = viewDocument.findViewById(R.id.imageFolder);
        PdfFolder = viewDocument.findViewById(R.id.pdfFolder);
        linearLayout = viewDocument.findViewById(R.id.linearLayoutDocumentFrag);

      /*
      * this will replace the fragment
      */
        Images images = new Images();
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.linearLayoutDocumentFrag ,images);
        transaction.commit();

        ImageFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Images images = new Images();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.linearLayoutDocumentFrag ,images);
                transaction.commit();
            }
        });
        PdfFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pdf pdf= new Pdf();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.linearLayoutDocumentFrag , pdf);
                transaction.commit();
            }
        });
        return  viewDocument;
    }
}