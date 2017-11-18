package project.sliit.assistme.IntAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import project.sliit.assistme.IntAlarm.weathercomp.data.JSONWeatherParser;
import project.sliit.assistme.IntAlarm.weathercomp.data.WeatherHttpClient;
import project.sliit.assistme.IntAlarm.weathercomp.model.BadWeather;
import project.sliit.assistme.IntAlarm.weathercomp.model.Weather;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by DELL on 11/18/2017.
 */

public class InterligentAlarmReciver extends BroadcastReceiver {

    AlarmManager alarm_manager;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;
    int alarm_tracks;
    DateFormat df;
    Date time1, time2;
    long delay = 900000;
    String timeNow;
    String destAddress;
    String parsedata="";
    String am_pm = "AM";

    int hour = 0;
    int minute = 0;

    //Weather >>>>>>>>>>>>>>>>>>>>>>>
    private boolean weatherCondition;
    private String myAppId = "dcb6553bfccc040683d9917eedd6cfbe";
    Weather weather = new Weather();
    BadWeather badWeather = new BadWeather();

    //Maps >>>>>>>>>>>>>>>>>>>>>>>>>>
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private LatLng dest, current;
    private int distance;
    double myLat, myLng;
    double destLat, destLng;
    int durationHours=0, durationMins=0;
    GoogleMap mMap;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("aa","TestReciver");
        alarm_manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        final Calendar calendar = Calendar.getInstance(); //Create an instance of the calendar
        final Intent my_intent = new Intent(context, Alarm_Receiver.class);//Create an intent to the alarm receiver class


        OtherMethods otherMethods = new OtherMethods();

        Location locationA = new Location("Point A");
        Location locationB = new Location("Point B");

        //MainActivity.DATABASEHANDLER.

        myLat = 6.9126927;
        myLng = 79.8484904;

        //destLat = dest.latitude;
        //destLng = dest.longitude;


        destLat=6.56048;
        destLng=79.50341;

        hour = intent.getIntExtra("Hour",0);
        minute = intent.getIntExtra("Mins",0);
        Log.d("Isha","IntAlarm"+hour+minute);

        //hour=14;
        //minute=00;

        locationA.setLatitude(myLat);
        locationA.setLongitude(myLng);

        locationB.setLatitude(destLat);
        locationB.setLongitude(destLng);

        float distanceA_B = locationA.distanceTo(locationB);

        int distA_B = (int) distanceA_B;

        Log.i("Distance is ", Integer.toString(distA_B));
        //String duration = otherMethods.extractDuration(GetDirectionsData.duration);
        String duration = "25";
        String[] parts = duration.split(" ");

        if (parts.length == 1) {
            String timeMinutes = parts[0];
            durationMins = Integer.parseInt(timeMinutes);
        } else if (parts.length == 2) {
            String timeHours = parts[0];
            String timeMinutes = parts[1];

            durationHours = Integer.parseInt(timeHours);
            durationMins = Integer.parseInt(timeMinutes);
        } else {
            Log.e("ERROR Duration ", "WRONG array");
        }

        parsedata = "Colombo,LK";
        renderWeatherData(parsedata);

//                int hour = alarm_time_picker.getHour();
//                int minute = alarm_time_picker.getMinute();

        //ADD TIME
        //hour = MyTimePicker.hoursTP;
        //minute = MyTimePicker.minsTP;


        hour = hour - durationHours;
        minute = minute - durationMins;

        Log.i("Hours PASS : ", Integer.toString(hour));
        Log.i("Mins PASS : ", Integer.toString(minute));

        //weatherCondition=false;
        if (weatherCondition) {
            Log.i("Weather data PASS : ", Boolean.toString(weatherCondition));
            distA_B = distA_B * 5;
            minute = minute - distA_B;
            if (minute < 0) {
                minute = 60 + minute;
                hour = hour - 1;
            }
        }


        calendar.set(Calendar.HOUR_OF_DAY, hour);//set calendar instance with hours and minutes on the time picker
        calendar.set(Calendar.MINUTE, minute);



        //set_alarm_text(hour_string + ":" + minute_string + am_pm);//changes the text in the update text box

        my_intent.putExtra("extra", "alarm on");//tells the clock that the alarm on button is pressed, putting extra string to my_intent
        my_intent.putExtra("alarm tone", alarm_tracks);//tell the app that you want a certain value from the spinner

        Log.e("The alarm id is", String.valueOf(alarm_tracks));

        pending_intent = PendingIntent.getBroadcast(context, 0,
                my_intent, PendingIntent.FLAG_UPDATE_CURRENT);//Create a pending intent

        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);


    }

    public void renderWeatherData(String city){

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city+"&appid="+myAppId}); //FIX if needed
        //weatherTask.execute(new String[]{city+"&appid=dcb6553bfccc040683d9917eedd6cfbe"});

    }

    private class WeatherTask extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... strings) {
            //data hold the whole StringBuffer that we returned from WeatherHttpClient class
            String data = ((new WeatherHttpClient()).getWeatherData(strings[0]));
            weather = JSONWeatherParser.getWeather(data);

            //Log.v("Data : ",weather.place.getCity());
            //Log.v("Data : ",weather.currentCondition.getDescription());
            String weatherSample = weather.currentCondition.getDescription();

            if ((badWeather.isCloudy(weatherSample)) || (badWeather.isRaining(weatherSample))){
                weatherCondition = true;
                Log.v("Good or Bad : true ", String.valueOf(weatherCondition));
            }
            else{
                weatherCondition = false;
                Log.v("Good or Bad : false ", String.valueOf(weatherCondition));
            }

            Log.v("Good or Bad : ", weather.currentCondition.getDescription());

            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
        }
    }
}
