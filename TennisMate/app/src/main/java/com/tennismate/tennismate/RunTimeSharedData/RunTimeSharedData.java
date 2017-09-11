package com.tennismate.tennismate.RunTimeSharedData;
import com.tennismate.tennismate.bridge.FromDBToUserContext;
import com.tennismate.tennismate.user.UserContext;


public class RunTimeSharedData {

    private static UserContext smUserContext;
    public static boolean isChatActivityAtive = false;



    public static void  setUserContext( UserContext userContext){
        smUserContext = userContext;
    }

    public static UserContext getUserContext(){
        return smUserContext;
    }



    public static void fetchUserData(){

        smUserContext = new UserContext();
        FromDBToUserContext fetcher = new FromDBToUserContext(smUserContext);

        fetcher.fetchBaseUser();
        fetcher.fetchUserLocation();
        fetcher.fetchDialogs();
    }


}
