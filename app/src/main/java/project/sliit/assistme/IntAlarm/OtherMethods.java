package project.sliit.assistme.IntAlarm;

import android.util.Log;

/**
 * Created by navin on 10/18/2017.
 */

public class OtherMethods {

    public String getNearbyTrainUrl(double latitude, double longitude, String nearbyPlace){
        StringBuilder googlePlaceTrainUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyAib7lBWc7HtYdmd709qHm_Cux1E5P-XvE
        googlePlaceTrainUrl.append("location="+latitude+","+longitude);
        //googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceTrainUrl.append("&rankby=distance");
        googlePlaceTrainUrl.append("&type="+nearbyPlace);
        //googlePlaceUrl.append("&keyword=railway");
        googlePlaceTrainUrl.append("&sensor=true");
        googlePlaceTrainUrl.append("&key="+"AIzaSyAib7lBWc7HtYdmd709qHm_Cux1E5P-XvE");

        return googlePlaceTrainUrl.toString();
    }

    public String getDirectionUrl(double A_lat, double A_lng, double B_lat, double B_lng){
        StringBuilder googleDirectionUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionUrl.append("origin="+A_lat+","+A_lng);
        googleDirectionUrl.append("&destination="+B_lat+","+B_lng);
        googleDirectionUrl.append("&key="+"AIzaSyCeko4AxuvPO6rJULEUH5ee4HfDyLSK0qU");

        return googleDirectionUrl.toString();
    }


    public int extractDistance(String distance){
        Log.d("OtherMethods(extrctDis)","Got through YAYY = "+distance);

        String[] parts = distance.split(" ");

        float a_b_flt = Float.parseFloat(parts[0]);
        String km = parts[1];

        Log.d("OtherMethods(extrctDis)","Float Successful YAYY = "+a_b_flt);

        int a_b = (int)a_b_flt;

        Log.d("OtherMethods(extrctDis)","FLoat Convert Succes YAYY = "+a_b);

        return a_b;
    }
    public int[] extractDuration(String duration){

        Log.d("OtherMethods(extrctDis)","YAYYY = "+duration);
        String[] parts = duration.split(" ");
        int partsSize = parts.length;

        //ERROR IS HERE
        if (partsSize == 2) {
            String durSMins = parts[0];
            int[] durMins = {Integer.parseInt(durSMins)};

            return durMins;
        }
        else if (partsSize == 4){
            String durSMins = parts[2];
            String durSHrs = parts[0];

            int[] durHrsMins = {Integer.parseInt(durSHrs), Integer.parseInt(durSMins)};
//            String durSHrsMins = durSHrs + " " + durSMins;

            return durHrsMins;
        }
        else{
            Log.e("OtherMethods(extrctDis)","ERROR: Epic fail. NOT WORKING!");
            int[] arr = {0};
            return arr;
        }
    }
    public int extractTrainDuration(String trainDuration) {
        int trainDur = Integer.parseInt(trainDuration);
        return trainDur;
    }

    public String extractTrainStationName(String trainStationName){
        String[] parts = trainStationName.split(" ");
        String trainName = parts[0];
        return trainName;
    }

}
