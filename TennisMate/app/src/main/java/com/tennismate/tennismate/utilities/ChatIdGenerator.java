package com.tennismate.tennismate.utilities;


public class ChatIdGenerator {

    private static String seperator = "----";

    public static String generate(String uid1, String uid2){

        int compare = uid1.compareTo(uid2);

        if( compare > 0){
            return uid1 + seperator + uid2;
        }
        return uid2 + seperator + uid1;
    }
}
