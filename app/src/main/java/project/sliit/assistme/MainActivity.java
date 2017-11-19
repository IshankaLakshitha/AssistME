package project.sliit.assistme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import project.sliit.assistme.Health.StepsCount;
import project.sliit.assistme.IntAlarm.InterligentAlarmReciver;
import project.sliit.assistme.ItemFinder.FirstTime.PersonalDetails;
import project.sliit.assistme.ItemFinder.GPS.GPSBackgroundReciver;
import project.sliit.assistme.ItemFinder.database.DBhandler;
import project.sliit.assistme.Notifications.CallRecivers;
import project.sliit.assistme.Notifications.WifiBroadcastReceiver;
import project.sliit.assistme.Settings.SettingsPage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static DBhandler DATABASEHANDLER;
    public static int firsttime=0;
    private PendingIntent pendingIntent;
    private AlarmManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
//
        //StepsCount.startCount();
        //sensorManager.registerListener(StepsCount.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        BroadcastReceiver broadcastReceiver = new WifiBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        //intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);

        Intent alarmIntent = new Intent(this, GPSBackgroundReciver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int interval = 15000;

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);


        DATABASEHANDLER=new DBhandler(this,null,null,1);
        //MainActivity.DATABASEHANDLER.addHealthDetails("QQ");
        if(isFirstTime()) {
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c.getTime());
            MainActivity.DATABASEHANDLER.addHealthDetails(formattedDate);
            firsttime=1;
            //Intent intent = new Intent(this, FirstTimeDevicesActivity.class);
            //startActivity(intent);
            Intent intent = new Intent(this, PersonalDetails.class);
            startActivity(intent);

        }else{
            firsttime=0;
            //btndone.setVisibility(View.INVISIBLE);
        }
        //Intent intent = new Intent(this, PersonalDetails.class);
        //startActivity(intent);
        CallAlrm();

    }

    public void CallAlrm(){//sundy is 0
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, CallRecivers.class);
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        //cal.set(Calendar.HOUR_OF_DAY, 22);
        //cal.set(Calendar.MINUTE, 55);
        cal.set(Calendar.SECOND,60);

        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, broadcast);
        /*alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                cal.getTimeInMillis(),
                broadcast);*/

    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Items) {
            Intent intent = new Intent(this, FirstTimeDevicesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Alarm) {
            Intent intent = new Intent(this, AlarmMainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Health) {
            Intent intent = new Intent(this, HealthMainActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_Settings) {
            Intent intent = new Intent(this, SettingsPage.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
