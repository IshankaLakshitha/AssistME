package project.sliit.assistme.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import project.sliit.assistme.FirstTimeDevicesActivity;
import project.sliit.assistme.Health.AddReminders;
import project.sliit.assistme.MainActivity;
import project.sliit.assistme.R;

/**
 * Created by DELL on 11/19/2017.
 */

public class HealthReminder extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent1 = new Intent(context, AddReminders.class);

        TaskStackBuilder stackBuilder1 = TaskStackBuilder.create(context);
        stackBuilder1.addParentStack(AddReminders.class);
        stackBuilder1.addNextIntent(notificationIntent1);

        PendingIntent pendingIntent1 = stackBuilder1.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //remidItem RM=new remidItem(MainActivity.DATABASEHANDLER);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        String rem="";
        rem=MainActivity.DATABASEHANDLER.databasetostringReminder(formattedDate);

        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(context);
        //RM.itemsForDay()
        Notification notification = builder1.setContentTitle("AssistMe")
                .setContentText(rem)
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.logo)
                .setContentIntent(pendingIntent1).build();

        NotificationManager notificationManager1 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager1.notify(1, notification);

        //BroadcastReceiver broadcastReceiver = new WifiBroadcastReceiver();

    }
}
