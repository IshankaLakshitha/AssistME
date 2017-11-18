package project.sliit.assistme.ItemFinder.GPS;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import project.sliit.assistme.R;

public class GpsActivity extends AppCompatActivity implements OnMapReadyCallback {

    String Cord;
    String []lan;

    GoogleMap mgoogleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cord = getIntent().getStringExtra("Cordinates");
        lan=Cord.split("#");
        if(googlePlayServiceAvailable()){
            //Toast.makeText(this," Done",Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_gps);
            initMap();
        }
    }

    private void initMap(){
        MapFragment mapFragment=(MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googlePlayServiceAvailable(){

        GoogleApiAvailability api=GoogleApiAvailability.getInstance();
        int isAvailabel=api.isGooglePlayServicesAvailable(this);
        if(isAvailabel== ConnectionResult.SUCCESS){
            return true;
        }else if(api.isUserResolvableError(isAvailabel)) {
            Dialog dialog = api.getErrorDialog(this, isAvailabel, 0);
        }else{

            Toast.makeText(this,"Cant Connect",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mgoogleMap=googleMap;
        goLocation(Double.parseDouble(lan[0]),Double.parseDouble(lan[1]));


    }

    private void goLocation(double lat, double lang) {
        LatLng ll=new LatLng(lat,lang);
        CameraUpdate update= CameraUpdateFactory.newLatLngZoom(ll,15);
        mgoogleMap.addMarker(new MarkerOptions().position(ll)
                .title("Your Item Is Here"));
        mgoogleMap.moveCamera(update);

    }
}
