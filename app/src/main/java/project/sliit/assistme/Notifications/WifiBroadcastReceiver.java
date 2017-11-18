package project.sliit.assistme.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.sql.Time;
import java.util.Calendar;

import static project.sliit.assistme.MainActivity.DATABASEHANDLER;

/**
 * Created by DELL on 11/17/2017.
 */

public class WifiBroadcastReceiver extends BroadcastReceiver {
    Context arg0;
    int mins,hours;
    String time;
    String []Timearr;

    @Override
    public void onReceive(Context context, Intent intent) {

        arg0=context;
        String action = intent.getAction();
        //if (WifiManager.SUPPLICANT_STATE_CHANGED_ACTION .equals(action)) {
        SupplicantState state = intent.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);
        if (SupplicantState.isValidState(state)) {
            Log.d("aa","Running");

            boolean connected = checkConnectedToDesiredWifi();

            if(connected){
                Log.d("aa","con");
            }else{
                time=DATABASEHANDLER.databasetostringTimeSedule(currntDay());
                Timearr=time.split("#");
                hours = new Time(System.currentTimeMillis()).getHours();
                mins = new Time(System.currentTimeMillis()).getMinutes();
                if(hours>=Integer.parseInt(Timearr[0])){
                    Intent intent1=new Intent(context,NavigationActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
            }
        }
        //}
    }

    /** Detect you are connected to a specific network. */
    private boolean checkConnectedToDesiredWifi() {
        boolean connected = false;

        String desiredMacAddress = "84:5b:12:d4:93:0c";

        WifiManager wifiManager =
                (WifiManager) arg0.getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifi = wifiManager.getConnectionInfo();
        if (wifi != null) {
            // get current router Mac address
            String bssid = wifi.getBSSID();
            connected = desiredMacAddress.equals(bssid);
        }

        return connected;
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
