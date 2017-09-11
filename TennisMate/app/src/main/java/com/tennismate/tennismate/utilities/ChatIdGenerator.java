package com.tennismate.tennismate.utilities;


public class ChatIdGenerator {

    private static String separator = "----";

    public static String generate(String uid1, String uid2){

        int compare = uid1.compareTo(uid2);

        if( compare > 0){
            return uid1 + separator + uid2;
        }
        return uid2 + separator + uid1;
    }


    /**
     *  This functon extracts the other uid fro the chatId.
     * @param chatId String with format: x + separator + y
     * @param uid x xor y.
     * @return
     */
    public static String extractOtherId (String chatId, String uid){


        if( chatId.startsWith(uid) ){  // chatId = uid + separator + y
            return chatId.split(separator)[1];
        }
        else{ // chatId = y + separator + uid
            return chatId.split(separator)[0];
        }
    }
}
