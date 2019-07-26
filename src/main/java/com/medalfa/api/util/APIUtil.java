/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medalfa.api.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Aldo
 */
public class APIUtil {
    
    public static final SimpleDateFormat HEDER_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    public static final SimpleDateFormat DATE_TIME = new SimpleDateFormat("ddMMyyyyHHmmss");
    private static final String DATE_FORMAT = "dd/MMM/yyyy";
    
    public static String getDateHeader(){
        return  HEDER_FORMAT.format(new Date());
    }
    
    public static String getExpiresHeader(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 115);
        return  HEDER_FORMAT.format(cal.getTime());
    }
    
      public static String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        DATE_TIME.setLenient(false);
        return DATE_TIME.format(date).toUpperCase();
    }
      public static String dateToDateString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.format(date).toString().toUpperCase();
    }

      
     public static String toStringUsingJackson(Object object) throws IOException{

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        return jsonInString;
    }
    
}
