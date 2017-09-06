package com.tennismate.tennismate.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    public static String getFullTime(){

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String lastUpdatedDate = dateFormat.format(new Date());
        return lastUpdatedDate;
    }
}
