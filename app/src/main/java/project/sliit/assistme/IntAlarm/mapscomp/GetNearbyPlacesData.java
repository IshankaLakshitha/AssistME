package project.sliit.assistme.IntAlarm.mapscomp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import project.sliit.assistme.IntAlarm.InterligentAlarmReciver;

/**
 * Created by navin on 11/14/2017.
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;
    String trainClassSName, trainClassVic;
    double trainClassLat, trainClassLng;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {

        List<HashMap<String, String>> nearbyPlaceList = null;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        showNearbyPlaces(nearbyPlaceList);

    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList) {
//        MarkerOptions markerOptions = new MarkerOptions();
        HashMap<String, String> googlePlace = nearbyPlaceList.get(0);

        trainClassSName = googlePlace.get("place_name");
        trainClassVic = googlePlace.get("vicinity");
        trainClassLat = Double.parseDouble(googlePlace.get("lat"));
        trainClassLng = Double.parseDouble(googlePlace.get("lng"));

//        MainActivity.nearestTrainLatLng = new LatLng(lat, lng);

        Log.d("GetNPlacesData:exe"," Train - latitude value = "+trainClassLat);
        Log.d("GetNPlacesData:exe"," Train - lngitude value = "+trainClassLng);
        Log.d("GetNPlacesData:exe"," Train - train station name = "+trainClassSName);
        Log.d("GetNPlacesData:exe"," Train - train station vicinity = "+trainClassVic);

        InterligentAlarmReciver.trainSName = trainClassSName;
        InterligentAlarmReciver.trainVicinity = trainClassVic;
        InterligentAlarmReciver.trainLat = trainClassLat;
        InterligentAlarmReciver.trainLng = trainClassLng;

//        markerOptions.position(latLng);
//        markerOptions.title(placeName + ":" + vicinity);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

//        mMap.addMarker(markerOptions);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        /*for (int i=0; i<nearbyPlaceList.size(); i++){

            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + ":" + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }*/
    }

}
