package com.jakubsabuda.notpeadapp20022020.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilitiy {

    public static  String getCurrentTimestamp(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String currentDate = dateFormat.format(new Date());
        return currentDate;
    }

}
