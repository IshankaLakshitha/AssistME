package project.sliit.assistme.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import project.sliit.assistme.R;

public class SettingsPage extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);



    }


    public void mondayAct(View view) {

        Intent intent = new Intent(getApplicationContext(),UpdateDayScedule.class);

        intent.putExtra("day","MON");

        startActivity(intent);
    }

    public void tuesdayAct(View view) {

        Intent intent = new Intent(getApplicationContext(),UpdateDayScedule.class);

        intent.putExtra("day","TUE");

        startActivity(intent);
    }

    public void wednesdayAct(View view) {


        Intent intent = new Intent(getApplicationContext(),UpdateDayScedule.class);

        intent.putExtra("day","WED");

        startActivity(intent);
    }

    public void thursdayAct(View view) {

        Intent intent = new Intent(getApplicationContext(),UpdateDayScedule.class);

        intent.putExtra("day","THU");

        startActivity(intent);
    }

    public void fridayAct(View view) {

        Intent intent = new Intent(getApplicationContext(),UpdateDayScedule.class);

        intent.putExtra("day","FRI");

        startActivity(intent);
    }

    public void satAct(View view) {


        Intent intent = new Intent(getApplicationContext(),UpdateDayScedule.class);

        intent.putExtra("day","SAT");

        startActivity(intent);
    }

    public void sunAct(View view) {


        Intent intent = new Intent(getApplicationContext(),UpdateDayScedule.class);

        intent.putExtra("day","SUN");

        startActivity(intent);
    }
}
