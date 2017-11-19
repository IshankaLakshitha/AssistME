package project.sliit.assistme.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.TextView;

import project.sliit.assistme.FirstTimeDevicesActivity;
import project.sliit.assistme.MainActivity;
import project.sliit.assistme.R;


public class AlarmReceiverItemNotification extends BroadcastReceiver {

    private String myAppId = "dcb6553bfccc040683d9917eedd6cfbe";

    TextView aa;
    String Items="";

    //Weather weather = new Weather();

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, FirstTimeDevicesActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(FirstTimeDevicesActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        remidItem RM=new remidItem(MainActivity.DATABASEHANDLER);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        //RM.itemsForDay()
        Notification notification = builder.setContentTitle("AssistMe")
                .setContentText(RM.itemsForDay())
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.logo)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

        //BroadcastReceiver broadcastReceiver = new WifiBroadcastReceiver();


       /* IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        //intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        context.registerReceiver(broadcastReceiver, intentFilter);*/

      /*  BroadcastReceiver broadcastReceiver1 = new WifiBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        //intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        context.registerReceiver(broadcastReceiver1, intentFilter);
*/

    }

}
