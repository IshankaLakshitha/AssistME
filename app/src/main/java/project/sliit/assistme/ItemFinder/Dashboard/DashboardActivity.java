package project.sliit.assistme.ItemFinder.Dashboard;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import project.sliit.assistme.ItemFinder.CommonActivity;
import project.sliit.assistme.ItemFinder.BluetoothLEService;

import project.sliit.assistme.ItemFinder.GPS.GpsActivity;

import project.sliit.assistme.ItemFinder.database.Devices;
import project.sliit.assistme.R;

import static project.sliit.assistme.MainActivity.DATABASEHANDLER;

//import com.Sliit.assistme.GPS.GPS_Service;
//import com.Sliit.assistme.Preferences;


public class DashboardActivity extends CommonActivity implements project.sliit.assistme.ItemFinder.Dashboard.DashboardFragment.OnDashboardListener {

    private static final int NUM_PAGES = 3;
    public String Cordinates="";

    //    private static final int CONFIRM_REMOVE_EVENTS = 0;
    private static final int CONFIRM_REMOVE_KEYRING = 1;
    public static final String EVENTS_HISTORY_FRAGMENT = "eventsHistoryFragment";

    private BluetoothLEService service;

    private TextView Name;

    private boolean activated;

    private String address;

    private String name;

    private ViewPager mPager;

    private TabLayout mTab;

    private BroadcastReceiver broadcastReceiver;

    private FloatingActionButton mFab;
    private FloatingActionButton mLoc;

    //create ble class instance
    //Interface for monitoring the state of an application service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder instanceof BluetoothLEService.BackgroundBluetoothLEBinder) {
                service = ((BluetoothLEService.BackgroundBluetoothLEBinder) iBinder).service();
                service.connect(DashboardActivity.this.address);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(BluetoothLEService.TAG, "onServiceDisconnected()");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        //create gps class
        //Intent i =new Intent(getApplicationContext(),GPS_Service.class);
        //startService(i);

        Name=(TextView)findViewById(R.id.TagName);
        mPager = (ViewPager) findViewById(R.id.pager);
        final ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());

        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.hide();
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mFab.setImageResource((activated) ? android.R.drawable.ic_lock_silent_mode_off : android.R.drawable.ic_lock_silent_mode);
                activated = !activated;//set on alrm off
                onImmediateAlert(address, activated);//click cloating button imidiate alert
                //Log.v("dd","ringer");
            }
        });
        mLoc = (FloatingActionButton) findViewById(R.id.loc);
        ////mFab.hide();
        mLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cordinates=DATABASEHANDLER.databasetostringItem(address);
                if(Cordinates!=null||Cordinates!=""||Cordinates!=" "){
                    Intent intent = new Intent(getApplicationContext(), GpsActivity.class);
                    intent.putExtra("Cordinates", Cordinates);
                    startActivity(intent);
                }
                else{
                    //Toast.makeText(this," Done",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /*set Tittle and get values from device acc */
    @Override
    protected void onResume() {
        super.onResume();
        address = getIntent().getStringExtra(Devices.ADDRESS);
        name = getIntent().getStringExtra(Devices.NAME);
        Name.setText(name);

        //not used yet
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                   String test= (String) intent.getExtras().get("coordinates");
                    //Devices.updateDeviceGPS(context,address,test);


                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));

    }


    private void onImmediateAlert(final String address, final boolean activate) {
        service.immediateAlert(address, (activate) ? BluetoothLEService.HIGH_ALERT : BluetoothLEService.NO_ALERT);
    }


    //when acctivity start bind with bleclss
    @Override
    public void onDashboardStarted() {
        // bind service
        bindService(new Intent(this, BluetoothLEService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    //when go b dic with tag
    @Override
    public void onDashboardStopped() {
        if (service != null) {
            service.disconnect(this.address);
        }

        setRefreshing(false);

        //unbindService(serviceConnection);
    }

    //if con with tag show btn. passed by dashboardfragmnt. servicedicover in bleclass
    @Override
    public void onImmediateAlertAvailable() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFab.show();
            }
        });
    }


//not used yet
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //final Preferences.Source source;

            //source = Preferences.Source.single_click;//Sound

            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
                //Preferences.setRingtone(this, address, source.name(), uri.toString());
            }
        }
    }

//retrn to dashboardfragment
    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Fragment getItem(int position) {
                    return project.sliit.assistme.ItemFinder.Dashboard.DashboardFragment.instance(address);
        }
    }

    //ask location access
    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                // enable_buttons();
            }else {
                runtime_permissions();
            }
        }
    }


}
