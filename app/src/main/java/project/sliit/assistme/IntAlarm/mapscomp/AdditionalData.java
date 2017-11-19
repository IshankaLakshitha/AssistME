package project.sliit.assistme.IntAlarm.mapscomp;

import java.util.ArrayList;

/**
 * Created by navin on 11/15/2017.
 */

public class AdditionalData {

    public String getNearbyTrainUrl(double latitude, double longitude, String nearbyPlace){
        StringBuilder googlePlaceTrainUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyAib7lBWc7HtYdmd709qHm_Cux1E5P-XvE
        googlePlaceTrainUrl.append("location="+latitude+","+longitude);
        //googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceTrainUrl.append("&rankby=distance");
        googlePlaceTrainUrl.append("&type="+nearbyPlace);
        //googlePlaceUrl.append("&keyword=railway");
        googlePlaceTrainUrl.append("&sensor=true");
        googlePlaceTrainUrl.append("&key="+"AIzaSyAw_r_WPuhoAIZGmaugq6dpmytovopBRY0");
//        googlePlaceTrainUrl.append("&key="+"AIzaSyAib7lBWc7HtYdmd709qHm_Cux1E5P-XvE");

        return googlePlaceTrainUrl.toString();
    }

    public String getDirectionUrl(double A_lat, double A_lng, double B_lat, double B_lng){
        StringBuilder googleDirectionUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionUrl.append("origin="+A_lat+","+A_lng);
        googleDirectionUrl.append("&destination="+B_lat+","+B_lng);
        googleDirectionUrl.append("&key="+"AIzaSyAw_r_WPuhoAIZGmaugq6dpmytovopBRY0");
//        googleDirectionUrl.append("&key="+"AIzaSyCeko4AxuvPO6rJULEUH5ee4HfDyLSK0qU");

        return googleDirectionUrl.toString();
    }

    public int extractDistance(String distance){
        String[] parts = distance.split(" ");
        int a_b = Integer.parseInt(parts[0]);

        return a_b;
    }

    public ArrayList<Integer> extractDuration(String duration){

        ArrayList<Integer> durationList = new ArrayList<>(0);
        String[] parts = duration.split(" ");

        if (parts[1] == "mins") {
            String durMins = parts[0];
            int durationMins = Integer.parseInt(durMins);

            durationList.add(1,durationMins);

            return durationList;
        }
        else{

            String durMins = parts[2];
            String durHrs = parts[0];

            int durationHours = Integer.parseInt(durHrs);
            int durationMins = Integer.parseInt(durMins);

            durationList.add(0,durationHours);
            durationList.add(1,durationMins);

            return durationList;
        }
    }

    public int extractTrainDuration(String trainDuration) {
        int trainDur = Integer.parseInt(trainDuration);
        return trainDur;
    }

}
