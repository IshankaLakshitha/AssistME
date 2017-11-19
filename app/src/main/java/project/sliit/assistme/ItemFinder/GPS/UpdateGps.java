package project.sliit.assistme.ItemFinder.GPS;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.sliit.assistme.ItemFinder.BluetoothLEService;

import static android.R.attr.x;
import static project.sliit.assistme.MainActivity.DATABASEHANDLER;

/**
 * Created by DELL on 10/2/2017.
 */

public class UpdateGps extends Activity implements  Runnable {



    private String location;
    String allItems="";
    public List<String> myList = new ArrayList<>();
    Context context;
    private BluetoothLEService service;
    String address;

    public UpdateGps(String Location,Context arg0) {

        this.location=Location;
        context=arg0;
        allItems=DATABASEHANDLER.SelectAllItems();
        String[] array = allItems.split("#");
        myList = Arrays.asList(array);

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            if (iBinder instanceof BluetoothLEService.BackgroundBluetoothLEBinder) {
                service = ((BluetoothLEService.BackgroundBluetoothLEBinder) iBinder).service();
                service.connect(myList.get(x));
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            Log.d(BluetoothLEService.TAG, "onServiceDisconnected()");
        }
    };


    @Override
    public void run() {
        if(myList!=null) {
            for (int x = 1; x < myList.size(); x++) {
                int y = 1;
                address = myList.get(x);
                Log.v("Isha", "Tag ok "+myList.get(x));
                //service.connect(address);
                if (y == 1) {
                    Log.v("dd", "Conected " + address);
                } else {
                    Log.v("dd", "Not Conected " + address);
                }
                //service.connect(address);
                Log.v("aa", address + "Ish");
                DATABASEHANDLER.updateDataItemLocation(myList.get(x), location);
                //Log.v("dd", "Done");
            }
        }try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            Log.v("dd","GG");
        }
    }




}
