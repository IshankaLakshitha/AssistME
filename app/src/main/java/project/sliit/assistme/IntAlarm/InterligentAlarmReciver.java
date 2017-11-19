package project.sliit.assistme.IntAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import project.sliit.assistme.IntAlarm.mapscomp.GetDirectionsData;
import project.sliit.assistme.IntAlarm.mapscomp.GetNearbyPlacesData;
import project.sliit.assistme.IntAlarm.weathercomp.data.JSONWeatherParser;
import project.sliit.assistme.IntAlarm.weathercomp.data.WeatherHttpClient;
import project.sliit.assistme.IntAlarm.weathercomp.model.BadWeather;
import project.sliit.assistme.IntAlarm.weathercomp.model.Weather;
import project.sliit.assistme.MainActivity;
import project.sliit.assistme.Notifications.AlarmReceiverItemNotification;
import project.sliit.assistme.Notifications.HealthReminder;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by DELL on 11/18/2017.
 */

public class InterligentAlarmReciver extends BroadcastReceiver {

    //Alarm >>>>>>>>>>>>>>>>>>>
    AlarmManager alarm_manager;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;
    int alarm_tracks=1;
    DateFormat df;
    Date time1, time2;
    long delay = 900000;
    String timeNow;
    String destAddress;
    String parsedata="Colombo,LK";
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
    int durationHours=0, durationMins=0, durationDHours=0, durationDMins=0;
    int readyDurationMins=0;
    GoogleMap mMap;
    int distA_B;
    int[] durr, durrDestTrain;
    public static String durationToDest="";
    public static String distanceToDest="";
    public boolean modeOfTransport = false;
    //Train **************
//    public static LatLng nearestTrainLatLng;
    String trainDuration;
    public static String trainVicinity, trainSName;
    //    LatLng trainLatLngDest, trainLatLngMy;
    public static double trainLat, trainLng;
    String toTrainMyDur, toTrainDestDur;
    String trainDestName, trainDestVicinity;
    String trainMyName, trainMyVicinity;
    double trainDestLat, trainDestLng;
    double trainMyLat, trainMyLng;
    String Mode;

    @Override
    public void onReceive(final Context context, Intent intent) {
        myLat = 6.9126927;
        myLng = 79.8484904;

        //destLat = dest.latitude;
        //destLng = dest.longitude;


        destLat=intent.getDoubleExtra("lat",0.0);
        destLng=intent.getDoubleExtra("lang",0.0);

        hour = intent.getIntExtra("Hour",0);
        minute = intent.getIntExtra("Mins",0);
        Mode=intent.getStringExtra("Tmode");
        if(Mode.equals("Train")){
            modeOfTransport=false;
        }
        alarm_manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        final Calendar calendar = Calendar.getInstance(); //Create an instance of the calendar
        final Intent my_intent = new Intent(context, Alarm_Receiver.class);

        if (modeOfTransport) {

            Log.d("MainActivity:SetAlarm"," Car - Got through 1:YAYY");

            OtherMethods otherMethods = new OtherMethods();


            //Have to get our location

//                    myLat = 6.837140;
//                    myLng = 79.929397;



            Log.d("MainActivity:SetAlarm"," Car - Got through 2:YAYY");



            distanceFromAtoB(myLat,myLng,destLat,destLng);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    OtherMethods exe = new OtherMethods();
                    distA_B = exe.extractDistance(distanceToDest);
                    durr = exe.extractDuration(durationToDest);

                    Log.d("MainActivity:SetAlarm"," Car - Distance = "+distanceToDest);
                    Log.d("MainActivity:SetAlarm"," Car - Duration = "+durationToDest);

                    Log.d("MainActivity:SetAlarm"," Car - Got through 3:YAYY");

                    if(durr.length == 1){
                        durationMins = durr[0];
                    }
                    else if (durr.length == 2){
                        durationHours = durr[0];
                        durationMins = durr[1];
                    }
                    else {
                        Log.e("MainActivity:SetAlarm"," Car - ERROR: Array durr = "+ Arrays.toString(durr));
                    }

                    Log.d("MainActivity:SetAlarm", " Car - Distance is = "+Integer.toString(distA_B));


                    renderWeatherData(parsedata);




                    hour = hour - durationHours;
                    minute = minute - durationMins;

                    Log.i("MainActivity(SetAlarm)", " Car - Hours PASS = "+Integer.toString(hour));
                    Log.i("MainActivity(SetAlarm)", " Car - Mins PASS = "+Integer.toString(minute));

                    //weatherCondition=false;
                    if (weatherCondition) {
                        Log.i("MainActivity(SetAlarm)"," Car - Weather data PASS = "+Boolean.toString(weatherCondition));
                        distA_B = distA_B * 1;
                        minute = minute - distA_B;
                        if (minute < 0) {
                            minute = 60 + minute;
                            hour = hour - 1;
                        }
                    }

                }
            },4000);

//                    int distA_B = otherMethods.extractDistance(GetDirectionsData.distanceToDest);
//                    int[] durr = otherMethods.extractDuration(durationToDest);
        }

        //Train
        else if (!modeOfTransport) {

            Log.d("MainActivity:SetAlarm", " Train - Got through 1:(ButtonClick is working)");

            OtherMethods otherMethods = new OtherMethods();


            //Have to get our location

//                    myLat = 6.837140;
//                    myLng = 79.929397;

            //ADD TIME


            Log.i("MainActivity(SetAlarm)", " Train - Hours PASS = " + Integer.toString(hour));
            Log.i("MainActivity(SetAlarm)", " Train - Mins PASS = " + Integer.toString(minute));

            Log.d("MainActivity:SetAlarm", " Train - Got through 2:(Destination LatLng and Time taken)");

            findNearestStation(myLat, myLng);



            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    OtherMethods exe = new OtherMethods();

                    trainMyLat = trainLat;
                    trainMyLng = trainLng;
                    trainMyName = trainSName;
                    trainMyVicinity = trainVicinity;

                    Log.d("MainActivity:SetAlarm", " >> Train - lat MY = " + trainMyLat);//DONE
                    Log.d("MainActivity:SetAlarm", " >> Train - lng MY = " + trainMyLng);//DONE
                    Log.d("MainActivity:SetAlarm", " >> Train - trainName MY = " + trainMyName);//DONE
                    Log.d("MainActivity:SetAlarm", " >> Train - trainVic MY = " + trainMyVicinity);//DONE

                    Log.d("MainActivity:SetAlarm", " >> Train - Successfully captured MyTrain");//DONE

                    trainMyName = exe.extractTrainStationName(trainMyName);

                    //Travel duration for my train station
                    distanceFromAtoB(myLat, myLng, trainMyLat, trainMyLng);

                    trainLat = 0;
                    trainLng = 0;
                    trainSName = "";
                    trainVicinity = "";

                    findNearestStation(destLat, destLng);

                }
            }, 2500);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    OtherMethods exe = new OtherMethods();

                    trainDestLat = trainLat;
                    trainDestLng = trainLng;
                    trainDestName = trainSName;
                    trainDestVicinity = trainVicinity;

                    Log.d("MainActivity:SetAlarm", " >> Train - lat DEST = " + trainDestLat);//DONE
                    Log.d("MainActivity:SetAlarm", " >> Train - lng DEST = " + trainDestLng);//DONE
                    Log.d("MainActivity:SetAlarm", " >> Train - trainName DEST = " + trainDestName);//DONE
                    Log.d("MainActivity:SetAlarm", " >> Train - trainVic DEST = " + trainDestVicinity);//DONE

                    Log.d("MainActivity:SetAlarm", " >> Train - Successfully captured DestTrain");//DONE

                    toTrainMyDur = durationToDest;
                    durationToDest = "";

                    durr = exe.extractDuration(toTrainMyDur);

                    if (durr.length == 1) {
                        durationMins = durr[0];
                    } else if (durr.length == 2) {
                        durationHours = durr[0];
                        durationMins = durr[1];
                    } else {
                        Log.e("MainActivity:SetAlarm", " >> Train - ERROR: Array durr MY = " + Arrays.toString(durr));
                    }

                    Log.i("MainActivity(SetAlarm)", " >> Train - durationHours PASS MY = " + Integer.toString(durationHours));
                    Log.i("MainActivity(SetAlarm)", " >> Train - durationMins PASS MY = " + Integer.toString(durationMins));

                    //Substracting the time it takes for me to go to MY station
                    hour = hour - durationHours;
                    minute = minute - durationMins;

                    Log.i("MainActivity(SetAlarm)", " >> Train - Hours PASS MY = " + Integer.toString(hour));
                    Log.i("MainActivity(SetAlarm)", " >> Train - Mins PASS MY = " + Integer.toString(minute));

                    distanceFromAtoB(destLat, destLng, trainDestLat, trainDestLng);

                }
            }, 5000);//5000

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    OtherMethods exe = new OtherMethods();

                    toTrainDestDur = durationToDest;
                    durationToDest = "";

                    durrDestTrain = exe.extractDuration(toTrainDestDur);

                    if (durrDestTrain.length == 1) {
                        durationDMins = durrDestTrain[0];
                    } else if (durrDestTrain.length == 2) {
                        durationDHours = durrDestTrain[0];
                        durationDMins = durrDestTrain[1];
                    } else {
                        Log.e("MainActivity:SetAlarm", " >> Train - ERROR: Array durr DEST = " + Arrays.toString(durrDestTrain));
                    }

                    Log.i("MainActivity(SetAlarm)", " >> Train - durationHours PASS DEST = " + Integer.toString(durationDHours));
                    Log.i("MainActivity(SetAlarm)", " >> Train - durationMins PASS DEST = " + Integer.toString(durationDMins));

                    //Substracting the time it takes for me to go to MY station
                    hour = hour - durationDHours;
                    minute = minute - durationDMins;

                    Log.i("MainActivity(SetAlarm)", " >> Train - Hours PASS DEST = " + Integer.toString(hour));
                    Log.i("MainActivity(SetAlarm)", " >> Train - Mins PASS DEST = " + Integer.toString(minute));

                            /*//Uncomment
                            distA_B = exe.extractDistance(distanceToDest);
                            durr = exe.extractDuration(durationToDest);*/

//                            Log.d("MainActivity:SetAlarm"," Train - Distance = "+distanceToDest);
//                            Log.d("MainActivity:SetAlarm"," Train - Duration = "+durationToDest);

                    Log.d("MainActivity:SetAlarm", " >> Train - Got through 3:(Successfully substracted time)");


                }
            }, 7500);//5000

        }


        //calendar.set(Calendar.HOUR_OF_DAY, hour);//set calendar instance with hours and minutes on the time picker
        //calendar.set(Calendar.MINUTE, minute);


        calendar.set(Calendar.HOUR_OF_DAY, hour);//set calendar instance with hours and minutes on the time picker
        calendar.set(Calendar.MINUTE, minute);

        //set_alarm_text(hour_string + ":" + minute_string + am_pm);//changes the text in the update text box

        my_intent.putExtra("extra", "alarm on");//tells the clock that the alarm on button is pressed, putting extra string to my_intent
        my_intent.putExtra("alarm tone", alarm_tracks);//tell the app that you want a certain value from the spinner

        Log.e("The alarm id is", String.valueOf(alarm_tracks));

        pending_intent = PendingIntent.getBroadcast(context, 0,
                my_intent, PendingIntent.FLAG_UPDATE_CURRENT);//Create a pending intent

        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);



            String time = MainActivity.DATABASEHANDLER.SelectReadyTime();
            int Min = Integer.parseInt(time);
            //call items notification
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            //Intent notificationIntent = new Intent(context, AlarmReceiverItemNotification.class);
            Intent notificationIntent = new Intent(context, AlarmReceiverItemNotification.class);
            notificationIntent.addCategory("android.intent.category.DEFAULT");
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            //cal.set(Calendar.DAY_OF_WEEK,3);
            //cal.set(Calendar.HOUR_OF_DAY, reciverHr);
            //cal.set(Calendar.MINUTE, 30);
            cal.set(Calendar.MINUTE, Min);
            cal.set(Calendar.SECOND, 00);

            //alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, broadcast);


    }

    public void findNearestStation(double lat, double lng){

        OtherMethods otherMethods = new OtherMethods();
        String keyType = "train_station";
        String trainUrl = otherMethods.getNearbyTrainUrl(lat, lng, keyType);

        Log.d("MainAct(findStation)", "Train URL = "+trainUrl);

        Object dataTransfer[] = new Object[2];
        dataTransfer[0] = mMap;
        dataTransfer[1] = trainUrl;

        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(dataTransfer);
//        nearestTrainLatLng = getNearbyPlacesData.getLatLng(); // ADD CODE - GETLATLNG FROM getNearbyPlacesData

//        Log.d("MainAct(findStation)","Train LatLng = "+nearestTrainLatLng.toString());

//        return nearestTrainLatLng;
    }

    public void distanceFromAtoB(double A_lat, double A_lng, double B_lat, double B_lng){

        OtherMethods otherMethods = new OtherMethods();
        Object dataTransfer[] = new Object[3];
        String distanceUrl = otherMethods.getDirectionUrl(A_lat, A_lng, B_lat, B_lng);
        GetDirectionsData getDirectionsData = new GetDirectionsData();

        dataTransfer[0] = mMap;
        dataTransfer[1] = distanceUrl;
        dataTransfer[2] = new LatLng(B_lat,B_lng);

        getDirectionsData.execute(dataTransfer); // EXTRACT

        /*//COMMENT - fixing
        distance = otherMethods.extractDistance(GetDirectionsData.distanceToDest);*/

//        return distance;

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
