package com.example.j_ai;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateClass {
    private final Date date = new Date();
    String day;
    String month;
    String year;

    public void setDate(){
        getDate();
    }
    public String  setDate(SimpleDateFormat format){
       return getDate(format);
    }

    private void getDate() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            year = String.valueOf(localDate.getYear());
            month = String.valueOf(localDate.getMonth());
            day = String.valueOf(localDate.getDayOfMonth());
        }
    }
    private String getDate(SimpleDateFormat format){
        SimpleDateFormat outGoingDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        Calendar calendar = Calendar.getInstance();
      return  outGoingDateFormat.format(calendar.getTime());
    }

}
