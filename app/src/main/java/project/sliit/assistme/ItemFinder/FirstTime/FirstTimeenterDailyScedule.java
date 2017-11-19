package project.sliit.assistme.ItemFinder.FirstTime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import project.sliit.assistme.R;



import static project.sliit.assistme.MainActivity.DATABASEHANDLER;

public class FirstTimeenterDailyScedule extends AppCompatActivity {

    TextView test;
    TextView DAY;
    Button GOPREV,GONEXT;
    String destAddress;
    private LatLng dest;
    Button butnLoc;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    int DayID=1;
    CheckBox CB;
    String Location;
    EditText Timehr,Timemin;
    Spinner spinner;
    int index=0;
    List<String> Items = new ArrayList<String>();
    String LatLan;

    LinearLayout linearMain1,linearMain2;
    CheckBox checkBox;

    String allItems="";
    public List<String> myList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_timeenter_daily_scedule);

        butnLoc=findViewById(R.id.buttonLoca);
        //test=(TextView) findViewById(R.id.txtTest);
        DAY=(TextView) findViewById(R.id.txtDay);
//        Location=(EditText)findViewById(R.id.txtLocation);
        Timehr=(EditText)findViewById(R.id.txtTimehr);
        Timemin=(EditText)findViewById(R.id.txtTimemin);
        spinner=(Spinner)findViewById(R.id.spinner);

        DAY.setText("MONDAY");
        //addDays();
        GOPREV=(Button) findViewById(R.id.btnPrev);
        GOPREV.setVisibility(View.INVISIBLE);

        GONEXT= (Button) findViewById(R.id.btnNext);

        GONEXT.setText("TUESDAY");

        //CB= (CheckBox) findViewById(R.id.IdCheckBox);

       Spinner spinner = (Spinner) findViewById(R.id.spinner);
       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Transportation_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        String text = spinner.getSelectedItem().toString();
        Log.d("Isha",text);

        linearMain1 =findViewById(R.id.linear_main1);
        linearMain2 =findViewById(R.id.linear_main2);
        //ArrayList<String> ItemList = new ArrayList<String>();

        allItems=DATABASEHANDLER.SelectAllItemsName();
        String[] array = allItems.split("#");
        myList = Arrays.asList(array);



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

    }


    //"WED","THU","FRI","SAT","SUN"
    public void goNEXT(View view) {
        //setContentView(R.layout.activity_enter_scedule);
        switch (DayID){
            case 1:
                ///CB.setChecked(false);
                DATABASEHANDLER.addProductSedule("MON");
                addData("MON");
                GOPREV.setVisibility(View.VISIBLE);
                DAY.setText("TUESDAY");GONEXT.setText("WEDSDAY");GOPREV.setText("MONDAY");
                //String test1=DATABASEHANDLER.databasetostringSedule(1);
                //test.setText(test1);
                DayID++;
                break;
            case 2:
                DATABASEHANDLER.addProductSedule("TUE");
                addData("TUE");
                //CB.setChecked(false);
                //setContentView(R.layout.activity_enter_scedule);
                DAY.setText("WEDSDAY");GONEXT.setText("THURSDAY");GOPREV.setText("TUESDAY");
                DayID++;
                break;
            case 3:
                DATABASEHANDLER.addProductSedule("WED");
                addData("WED");
                //CB.setChecked(false);
                DAY.setText("THURSDAY");GONEXT.setText("FRIDAY");GOPREV.setText("WEDSDAY");
                DayID++;
                break;
            case 4:
                DATABASEHANDLER.addProductSedule("THU");
                addData("THU");
                //CB.setChecked(false);
                DAY.setText("FRIDAY");GONEXT.setText("SATERDAY");GOPREV.setText("THURSDAY");
                DayID++;
                break;
            case 5:
                DATABASEHANDLER.addProductSedule("FRI");
                addData("FRI");
                //CB.setChecked(false);
                DAY.setText("SATERDAY");GONEXT.setText("SUNDAY");GOPREV.setText("FRIDAY");
                DayID++;
                break;
            case 6:
                DATABASEHANDLER.addProductSedule("SAT");
                addData("SAT");
                //CB.setChecked(false);
                DAY.setText("SUNDAY");GOPREV.setText("SATERDAY");
                GONEXT.setText("FINISH");DayID++;
                break;
            case 7:
                DATABASEHANDLER.addProductSedule("SUN");
                addData("SUN");
                Intent i = getBaseContext().getPackageManager().
                        getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                break;

        }
    }

    public void goPREV(View view) {
        DayID--;
        switch (DayID){

            case 1:
                ///CB.setChecked(false);
                DAY.setText("TUESDAY");GONEXT.setText("WEDSDAY");GOPREV.setText("MONDAY");
                //DayID--;
                break;
            case 2:
                //CB.setChecked(false);
                //setContentView(R.layout.activity_enter_scedule);
                DAY.setText("WEDSDAY");GONEXT.setText("THURSDAY");GOPREV.setText("TUESDAY");
                //DayID--;
                break;
            case 3:
                //CB.setChecked(false);
                DAY.setText("THURSDAY");GONEXT.setText("FRIDAY");GOPREV.setText("WEDSDAY");
                //DayID--;
                break;
            case 4:
                //CB.setChecked(false);
                DAY.setText("FRIDAY");GONEXT.setText("SATERDAY");GOPREV.setText("THURSDAY");
                //DayID--;
                break;
            case 5:
                //CB.setChecked(false);
                DAY.setText("SATERDAY");GONEXT.setText("SUNDAY");GOPREV.setText("FRIDAY");
                //DayID--;
                break;
            case 6:
                //CB.setChecked(false);
                DAY.setText("SUNDAY");GOPREV.setText("SATERDAY");
                GONEXT.setText("FINISH");//DayID--;

                break;
        }
    }

    /*public void addDays(){
        String []Days={"MON","TUE","WED","THU","FRI","SAT","SUN"};
        for (int d=0;d<=6;d++){
            DATABASEHANDLER.addProductSedule(Days[d]);
        }
    }*/


    String time;
    public void addData(String day){
        int index1=0;
        String allItems="";//get all item to upadate database(ex: Purse#Umbrella#....)
        int len=Items.size();
        while (index1<len){
            allItems=Items.get(index1)+"#"+allItems;

            index1++;
        }
        time=Timehr.getText().toString()+"#"+Timemin.getText().toString();
        //Location= Place.ge
        DATABASEHANDLER.updateDataSedule(day,allItems,LatLan,time,spinner.getSelectedItem().toString());
        Log.d("Isha",LatLan);
        //DATABASEHANDLER.updateDataSedule(day,allItems,Location.getText().toString(),Time.getText().toString(),"asas");
    }





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

    public void addLocation(View view) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                dest = place.getLatLng();

                destAddress = place.getAddress().toString();
                butnLoc.setText(place.getName());
                LatLan=dest.latitude+"#"+dest.longitude+"#"+place.getName();
                //LatLan=dest.toString();
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

}
