package project.sliit.assistme.Health;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import project.sliit.assistme.MainActivity;
import project.sliit.assistme.R;

import static project.sliit.assistme.Health.UserDetails.result;


public  class StepsCount extends AppCompatActivity implements SensorEventListener, StepListener {

    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private static int numSteps=0;
    public TextView TvSteps;
    UserDetails us;
    String currentDateTimeString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_count);

        currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        //String a=MainActivity.DATABASEHANDLER.databasetostringStep(currentDateTimeString);
        //Log.d("qq",a);
        //numSteps=Integer.parseInt(MainActivity.DATABASEHANDLER.databasetostringStep(currentDateTimeString));

        us = new UserDetails();
        TextView textView = (TextView) findViewById(R.id.textView_bmi);
        textView.setText("BMR = "+ String.valueOf(result));

        // Get an instance of the SensorManager

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);
        TvSteps = (TextView) findViewById(R.id.tv_steps);
        final TextView textView1 = (TextView) findViewById(R.id.textViewdb);
        final Button BtnStart = (Button) findViewById(R.id.btn_start);
        Button BtnStop = (Button) findViewById(R.id.btn_stop);
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());




        BtnStart.setOnClickListener (new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(StepsCount.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

                BtnStart.setVisibility(View.INVISIBLE);


            }
        });


    BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //String date= DateFormat.getDateTimeInstance().format(new Date());



                //sensorManager.unregisterListener(StepsCount.this);

            }
        });

    }

    public void startCount(){
        sensorManager.registerListener(StepsCount.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
        String c= Integer.toString(numSteps);


        Calendar c1 = Calendar.getInstance();
        System.out.println("Current time => " + c1.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c1.getTime());

        MainActivity.DATABASEHANDLER.updateDataHealth(formattedDate,c);
    }


    public void ShowStep(View view) {
        Intent intent=new Intent(getApplicationContext(),HealthCountActivity.class);
        startActivity(intent);
    }

    public void addremindr(View view) {
        Intent intent=new Intent(getApplicationContext(),AddReminders.class);
        startActivity(intent);
    }
}
