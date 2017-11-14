package project.sliit.assistme.ItemFinder.GPS;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by DELL on 9/27/2017.
 */

public class GPSBackgroundReciver extends BroadcastReceiver{

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    // GPSTracker class
    GPSTracker gps;
    UpdateGps UG;
    String location;


    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Log.v("dd","dsd");



        // create class object
        gps = new GPSTracker(arg0);

        // check if GPS enabled
        if (gps.canGetLocation()) {
            //if(allItems!=null||!allItems.equals("")||!allItems.equals("#")) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                location = Double.toString(latitude) + "#" + Double.toString(longitude);

                new Thread(new UpdateGps(location,arg0)).start();

            //bindService(new Intent(this, BluetoothLEService.class), serviceConnection, BIND_AUTO_CREATE);

          /*  Toast.makeText(arg0, "Your Location is - \nLat: "
                    + Items[1] + "\nLong: " + Items[2], Toast.LENGTH_LONG).show();*/
            //String s=Integer.toString(myList.size())+myList.get(0);
                //Toast.makeText(arg0, s, Toast.LENGTH_LONG).show();
            //}
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

    }
}
