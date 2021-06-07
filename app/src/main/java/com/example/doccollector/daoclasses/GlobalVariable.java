package com.example.doccollector.daoclasses;

public class GlobalVariable {
    public static String globalemail;


    public static String getGlobalglobalemail() {
        for (int i = 0; i < globalemail.length(); i++) {
            if( (globalemail.charAt(i) == '@' || globalemail.charAt(i) == '.')) {

                globalemail = charRemoveAt(globalemail,i);
            }
        }
        return globalemail;

    }
    public static String charRemoveAt(String str, int p) {
        return str.substring(0, p) + str.substring(p + 1);
    }



}

