package project.sliit.assistme.Notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

import project.sliit.assistme.MainActivity;
import project.sliit.assistme.R;

public class NavigationActivity extends AppCompatActivity {

    String loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);

        loc=MainActivity.DATABASEHANDLER.databasetostringLocationSedule(currntDay());

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q="+loc));
        startActivity(intent);
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
