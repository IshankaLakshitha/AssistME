package project.sliit.assistme.Notifications;

import java.util.Calendar;

import project.sliit.assistme.ItemFinder.database.DBhandler;

/**
 * Created by DELL on 7/28/2017.
 */

public class remidItem {



    String Items="";

    DBhandler DB;

    //WeatherGetData Data;

    public remidItem(DBhandler DB){
        this.DB=DB;
        //Data=new WeatherGetData();
    }



    //return Curnt day
    public String currntDay(){
        String DAY="";
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                DAY="SUN";
                break;
            case Calendar.MONDAY:
                DAY="MON";
                break;
            case Calendar.TUESDAY:
                DAY="TUE";
                break;
            case Calendar.WEDNESDAY:
                DAY="WED";
                break;
            case Calendar.THURSDAY:
                DAY="THU";
                break;
            case Calendar.FRIDAY:
                DAY="FRI";
                break;
            case Calendar.SATURDAY:
                DAY="SAT";
                break;
        }
        return DAY;
    }


//return items for curnt day
    public String itemsForDay(){
        String DAY = currntDay();
        Items = DB.databasetostringSedule(DAY);
        return Items;
    }


}
