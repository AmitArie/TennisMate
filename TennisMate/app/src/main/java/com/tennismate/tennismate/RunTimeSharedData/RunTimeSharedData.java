package com.tennismate.tennismate.RunTimeSharedData;
import com.tennismate.tennismate.user.UserContext;


public class RunTimeSharedData {

    private static UserContext smUserContext;


    public static void  setUserContext( UserContext userContext){
        smUserContext = userContext;
    }

    public static UserContext getUserContext(){
        return smUserContext;
    }
}
