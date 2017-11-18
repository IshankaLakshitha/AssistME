package project.sliit.assistme.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import project.sliit.assistme.IntAlarm.InterligentAlarmReciver;
import project.sliit.assistme.IntAlarm.Ringtone_Playing_Service;
import project.sliit.assistme.MainActivity;

import static project.sliit.assistme.MainActivity.DATABASEHANDLER;

/**
 * Created by DELL on 10/17/2017.
 */

public class CallRecivers extends BroadcastReceiver {

    int alarm_tracks=2;


    private boolean weatherCondition;
    private String myAppId = "dcb6553bfccc040683d9917eedd6cfbe";
    String AlarmDetails=" ";
    String AlarmDetailsArr[];
    String Time[];
    String state;
    int hr,Min,reciverHr;

    public List<String> myList = new ArrayList<>();


    @Override
    public void onReceive(Context context, Intent intent) {

        AlarmDetails=MainActivity.DATABASEHANDLER.databasetostringAlarmDetails(currntDay());

        if(AlarmDetails==null||AlarmDetails.equals(" ")){
            Log.d("isha","null Array");
            AlarmDetails=DATABASEHANDLER.databasetostringTimeSedule(currntDay());
            Time=AlarmDetails.split("#");
            hr=Integer.parseInt(Time[0]);
            Min=Integer.parseInt(Time[1]);
            reciverHr=hr-2;
            Log.d("isha","Sceduled Alarm"+hr+" "+Min);
        }else {
            AlarmDetailsArr=AlarmDetails.split("@");

            if(AlarmDetailsArr[1].equals("true")){
                Log.d("isha",AlarmDetailsArr[0]+" "+AlarmDetailsArr[1]);
                Time=AlarmDetailsArr[0].split("#");
                hr=Integer.parseInt(Time[0]);
                Min=Integer.parseInt(Time[1]);
                reciverHr=hr-2;
                Log.d("isha","Manual Alarm"+hr+" "+Min);
            }
        }



        //call notification about Items
        Log.d("Isha","Call Reciver Start");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //Intent notificationIntent = new Intent(context, AlarmReceiverItemNotification.class);
        Intent notificationIntent = new Intent(context, InterligentAlarmReciver.class);
        notificationIntent.putExtra("Hour",hr);
        notificationIntent.putExtra("Mins",Min);
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, reciverHr);
        cal.set(Calendar.MINUTE, Min);
        //cal.set(Calendar.SECOND,10);

        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, broadcast);
        /*alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                cal.getTimeInMillis(),
                broadcast);*/




    }

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

}
