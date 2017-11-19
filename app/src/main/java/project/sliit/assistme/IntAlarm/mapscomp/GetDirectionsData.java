package project.sliit.assistme.IntAlarm.mapscomp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.HashMap;

import project.sliit.assistme.IntAlarm.InterligentAlarmReciver;

/**
 * Created by navin on 11/15/2017.
 */

public class GetDirectionsData extends AsyncTask<Object, String, String> {

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    LatLng latLng;

    @Override
    protected String doInBackground(Object... objects) {

        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        latLng = (LatLng)objects[2];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;

    }

    @Override
    protected void onPostExecute(String s) {

        HashMap<String, String> directionsList = null;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(s);
        InterligentAlarmReciver.durationToDest = directionsList.get("duration");
        InterligentAlarmReciver.distanceToDest = directionsList.get("distance");

//        mMap.clear();
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.draggable(true);
//        markerOptions.title("Duration = "+MainActivity.durationToDest);
//        markerOptions.snippet("Distance = "+MainActivity.distanceToDest);

        Log.d("GetDirectionsData:exe"," Duration value = "+InterligentAlarmReciver.durationToDest);
        Log.d("GetDirectionsData:exe"," Distance value = "+InterligentAlarmReciver.distanceToDest);

//        mMap.addMarker(markerOptions);

    }
}
