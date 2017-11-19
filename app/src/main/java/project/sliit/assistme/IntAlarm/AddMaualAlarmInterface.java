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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static project.sliit.assistme.MainActivity.DATABASEHANDLER;

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
    List<String> Items = new ArrayList<String>();
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
    TextView Alarmname;
    CheckBox checkBox;
    String allItems="";
    public List<String> myList = new ArrayList<>();
    LinearLayout linearMain1,linearMain2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_add_manual_alrm);
        this.context = this;

        Alarmname=findViewById(R.id.alarm_title);
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
        allItems=DATABASEHANDLER.SelectAllItemsName();
        String[] array = allItems.split("#");
        myList = Arrays.asList(array);
        linearMain1 =findViewById(R.id.linear_main1);
        linearMain2 =findViewById(R.id.linear_main2);


        for(int i = 1; i < myList.size(); i++) {

            checkBox = new CheckBox(this);
            checkBox.setId(i);

            checkBox.setText(myList.get(i));
            checkBox.setTextSize(18);

            //checkBox.setGravity(Gravity.CENTER);
            checkBox.setOnClickListener(RadioClick(checkBox));
            if(i%2==1) {
                linearMain1.addView(checkBox);
            }else{
                linearMain2.addView(checkBox);
            }
            //linearMain2.addView(checkBox);
        }
        //Spokane,US

        final Spinner spinner = (Spinner) findViewById(R.id.transportSpin);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Transportation_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        final Spinner spinnerDay = (Spinner) findViewById(R.id.daySpin);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.day_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDay.setAdapter(adapter1);

        spinnerDay.setOnItemSelectedListener(this);


        //turn_on
        Button alarm_on = (Button)findViewById(R.id.alarm_on);

        //Create an onclick listener to start the alarm
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index1=0;
                String allItems="";
                int len=Items.size();
                while (index1<len){
                    allItems=Items.get(index1)+"#"+allItems;

                    index1++;
                }
                Log.d("Isha",Alarmname.getText().toString());
                MainActivity.DATABASEHANDLER.addAlarm(Alarmname.getText().toString(),dest.latitude+"#"+dest.longitude,spinner.getSelectedItem().toString()
                        ,MyTimePicker.hoursTP+"#"+MyTimePicker.minsTP,spinnerDay.getSelectedItem().toString(),"true",allItems);
                //adapter.updateRecords(users);
                finish();
                //Intent intent1=new Intent(getApplicationContext(),InterligentAlarm.class);
                //startActivity(intent1);


            }
        });



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


    View.OnClickListener RadioClick(final Button button){
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean checked=((CheckBox)v).isChecked();
                if(checked)
                    Items.add(button.getText().toString());
                    //Log.d("ON_CLICK", "CheckBox ID: " + button.getId() + " Text: " + button.getText().toString());
                else
                    Items.remove(button.getText().toString());
                //Log.d("ON_CLICK111", "CheckBox ID: " + button.getId() + " Text: " + button.getText().toString());
            }
        };
    }


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
