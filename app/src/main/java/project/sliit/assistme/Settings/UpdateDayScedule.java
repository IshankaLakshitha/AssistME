package project.sliit.assistme.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.sliit.assistme.MainActivity;
import project.sliit.assistme.R;


import static project.sliit.assistme.MainActivity.DATABASEHANDLER;

public class UpdateDayScedule extends AppCompatActivity {

    TextView test;
    TextView DAY;
//    Button GOPREV,GONEXT;

    int DayID=1;
    CheckBox CB;
    EditText Location;
    EditText Timehr,Timemin;
    Spinner spinner;
    int index=0;
    List<String> Items = new ArrayList<String>();

    LinearLayout linearMain1,linearMain2;
    CheckBox checkBox;

    String allItems="";
    public List<String> myList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scedule_day_update);

        //test=(TextView) findViewById(R.id.txtTest);
        DAY=(TextView) findViewById(R.id.txtDay);
        Location=(EditText)findViewById(R.id.txtLocation);
        Timehr=(EditText)findViewById(R.id.txtTimehr);
        Timemin=(EditText)findViewById(R.id.txtTimemin);
        spinner=(Spinner)findViewById(R.id.spinner);

        String s = getIntent().getStringExtra("day");

        showData(s);

        Log.e("HELLO", s);

        //DAY.setText(s);
        //addDays();

        //CB= (CheckBox) findViewById(R.id.IdCheckBox);

        //String dataItem = MainActivity.DATABASEHANDLER.databasetostringSedule(s);
        //String[] dataItemArr = dataItem.split("#");

       Spinner spinner = (Spinner) findViewById(R.id.spinner);
       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Transportation_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


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

    public void showData(String day){
        String data = DATABASEHANDLER.getDataOfColumns(day);
        //String dataItem = MainActivity.DATABASEHANDLER.databasetostringSedule(day);

        String[] dataArr = data.split("#");


        Log.i("showData","DAY = "+dataArr[0]);
        //Log.i("showData","ITEMS = "+dataItemArr[0]);
        Log.i("showData","LOCATION = "+dataArr[1]);
        Log.i("showData","TIME = "+dataArr[2]);
        Log.i("showData","TRANSPORT = "+dataArr[3]);

        DAY.setText(dataArr[0]);
        Location.setText(dataArr[3]);
        Timehr.setText(dataArr[4]);
        Timemin.setText(dataArr[5]);
    }


    //"WED","THU","FRI","SAT","SUN"
    public void goNEXT(View view) {
        //setContentView(R.layout.activity_enter_scedule);
        switch (DayID){
            case 1:
                ///CB.setChecked(false);
                DATABASEHANDLER.addProductSedule("MON");
                addData("MON");
                //String test1=DATABASEHANDLER.databasetostringSedule(1);
                //test.setText(test1);
                DayID++;
                break;
            case 2:
                DATABASEHANDLER.addProductSedule("TUE");
                addData("TUE");
                //CB.setChecked(false);
                //setContentView(R.layout.activity_enter_scedule);
                DayID++;
                break;
            case 3:
                DATABASEHANDLER.addProductSedule("WED");
                addData("WED");
                //CB.setChecked(false);
                DayID++;
                break;
            case 4:
                DATABASEHANDLER.addProductSedule("THU");
                addData("THU");
                //CB.setChecked(false);
                DayID++;
                break;
            case 5:
                DATABASEHANDLER.addProductSedule("FRI");
                addData("FRI");
                //CB.setChecked(false);
                DayID++;
                break;
            case 6:
                DATABASEHANDLER.addProductSedule("SAT");
                addData("SAT");
                //CB.setChecked(false);
                DAY.setText("SUNDAY");DayID++;
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
                //DayID--;
                break;
            case 2:
                //CB.setChecked(false);
                //setContentView(R.layout.activity_enter_scedule);
                //DayID--;
                break;
            case 3:
                //CB.setChecked(false);
                //DayID--;
                break;
            case 4:
                //CB.setChecked(false);
                //DayID--;
                break;
            case 5:
                //CB.setChecked(false);
                //DayID--;
                break;
            case 6:
                //CB.setChecked(false);
                DAY.setText("SUNDAY");

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
        DATABASEHANDLER.updateDataSedule(day,allItems,Location.getText().toString(),time,spinner.getSelectedItem().toString());
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

    public void doneAct(View view) {

    }





/*
    public void aaa(View view) {
        boolean checked=((CheckBox)view).isChecked();

        switch (((CheckBox) view).getText().toString()){

            case "Purse": if(checked){
                Items.add("Purse");
                // test.setText(Items.get(2));

            }else {
                Items.remove("Purse");
            }index++;
                break;

            case  "Car Keys": if(checked){
                Items.add("Car Keys");

            }else {
                Items.remove("Car Keys");
            }index++;
                break;

            case "Door Keys": if(checked){
                Items.add("Door Keys");

            }else {
                Items.remove("Door Keys");
            }index++;
                break;

            case  "Umbrella": if(checked){
                Items.add("Umbrella");

            }else {
                Items.remove("Umbrella");
            }index++;
                break;

            case "Water Bottle": if(checked){
                Items.add("Water Bottle");
            }else {
                Items.remove("Water Bottle");
            }index++;

                break;

        }
    }*/
}
