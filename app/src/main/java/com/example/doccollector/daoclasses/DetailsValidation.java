package com.example.doccollector.daoclasses;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 *  validation class for validating user's email id contact number
 */

public class DetailsValidation {

    private String Mail_Phone;

    public DetailsValidation(){

    }
    public DetailsValidation(String  Email_phone)
    {
        this.Mail_Phone = Email_phone;

    }

    public boolean isEmailValid()
    {
        String regex = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+$";
        Pattern Email_pattern = Pattern.compile(regex);
        Matcher matcher = Email_pattern.matcher(Mail_Phone);
        return matcher.matches();

    }
    public boolean isPhoneNumberValid()
    {
        String phone = "^[6-9]\\d{9}$";
        Pattern Email_pattern = Pattern.compile(phone);
        Matcher matcher = Email_pattern.matcher(Mail_Phone);
        return matcher.matches();
    }


}
