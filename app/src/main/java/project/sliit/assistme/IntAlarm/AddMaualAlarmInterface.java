package project.sliit.assistme.IntAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import project.sliit.assistme.IntAlarm.weathercomp.data.JSONWeatherParser;
import project.sliit.assistme.IntAlarm.weathercomp.data.WeatherHttpClient;
import project.sliit.assistme.IntAlarm.weathercomp.model.BadWeather;
import project.sliit.assistme.IntAlarm.weathercomp.model.Weather;
import project.sliit.assistme.MainActivity;
import project.sliit.assistme.R;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddMaualAlarmInterface extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Alarm >>>>>>>>>>>>>>>>>>>
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manual_alarm);
        this.context = this;

        alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        update_text = (TextView)findViewById(R.id.update_alarm);
        final Calendar calendar = Calendar.getInstance(); //Create an instance of the calendar
        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);//Create an intent to the alarm receiver class

        //country=(EditText)findViewById(R.id.txt_Country);
        //city=(EditText)findViewById(R.id.txt_City);

        //WEATHER >>>>>>>>>>>>>>>>>>>>>
        //renderWeatherData("Colombo,LK");
        //ERROR
        //Colombo,LK
        //Spokane,US

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alarms_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);


        //turn_on
        Button alarm_on = (Button)findViewById(R.id.alarm_on);

        //Create an onclick listener to start the alarm
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.DATABASEHANDLER.addAlarm("Test",dest.latitude+"#"+dest.longitude,"Bus"
                        ,MyTimePicker.hoursTP+"#"+MyTimePicker.minsTP,"SAT","true","testItems");
                //adapter.updateRecords(users);
                finish();
                //Intent intent1=new Intent(getApplicationContext(),InterligentAlarm.class);
                //startActivity(intent1);


            }
        });

        //turn_off


        //Button alarm_off = (Button)findViewById(R.id.alarm_off);
        /*alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                set_alarm_text("Alarm Off");//changes the text in the update text box

                alarm_manager.cancel(pending_intent);

                my_intent.putExtra("extra", "alarm off");//tells the clock that the alarm off button is pressed, putting extra string to my_intent

                my_intent.putExtra("alarm tone",alarm_tracks);//prevent crashes in a null point exception

                sendBroadcast(my_intent);//Stops the ringtone

            }
        });*/


        //renderWeatherData("Spokane,US"); //ERROR
        //Colombo,LK
        //Spokane,US

    }

    public String extractCity(String fullAddress){
        if(!(fullAddress == null)){
            String[] parts = fullAddress.split(", ");
            String extracted = parts[1];
            Log.i("Extracted City:", extracted);
            return extracted;
        }
        else{
            Log.i("ERROR: ", "Can't extract city cos the String provided is NULL");
            return null;
        }
    }


/*
    public LatLng findNearestStation(double lat, double lng){

        LatLng nearestTrainLatLng;

        OtherMethods otherMethods = new OtherMethods();
        String keyType = "train_station";
        String trainUrl = otherMethods.getNearbyTrainUrl(lat, lng, keyType);

        Log.d("TRAIN URL", " "+trainUrl);

        Object dataTransfer[] = new Object[2];
        dataTransfer[0] = mMap;
        dataTransfer[1] = trainUrl;

        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(dataTransfer);
        nearestTrainLatLng = getNearbyPlacesData.getLatLng(); // ADD CODE - GETLATLNG FROM getNearbyPlacesData

        Log.d("Train LAT LNG",nearestTrainLatLng.toString());

        return nearestTrainLatLng;
    }*/

//    public int distanceFromAtoB(double A_lat, double A_lng, double B_lat, double B_lng){
//
//        OtherMethods otherMethods = new OtherMethods();
//        Object dataTransfer[] = new Object[3];
//        String distanceUrl = otherMethods.getDirectionUrl(A_lat, A_lng, B_lat, B_lng);
//        //GetDirectionsData getDirectionsData = new GetDirectionsData();
//
//        dataTransfer[0] = mMap;
//        dataTransfer[1] = distanceUrl;
//        dataTransfer[2] = new LatLng(B_lat,B_lng);
//
//       // getDirectionsData.execute(dataTransfer); // EXTRACT
//
//       // distance = otherMethods.extractDistance(GetDirectionsData.distance);
//
//        return distance;
//
//    }



    /*
    FRAGMENT
     */

    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_location:
            {
                // AUTOCOMPLETE FRAGMENT
                try {
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .setCountry("LK")
                            .build();

                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .setFilter(typeFilter)
                                    .build(this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
            break;

            case R.id.btn_time:
            {
                DialogFragment newFragment = new MyTimePicker();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
            break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                dest = place.getLatLng();
                destAddress = place.getAddress().toString();
                Log.i("Destination", "Place: " + place.getName());
                Log.i("City", extractCity(place.getAddress().toString()));
                Log.i("Address", place.getAddress().toString());
                Log.i("LatLng",dest.toString());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("Destination", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    /*
    FRAGMENT
     */





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

       /* long newId = id;
        ++newId;*/

        Toast.makeText(parent.getContext(), "spinner item is "+id, Toast.LENGTH_SHORT).show();
        alarm_tracks = (int)id;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        // Another interface callback
    }

    private void set_alarm_text(String output) {

        update_text.setText(output);

    }

}
