package project.sliit.assistme.ItemFinder.FirstTime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import project.sliit.assistme.FirstTimeDevicesActivity;
import project.sliit.assistme.R;

public class PersonalDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
    }

    public void done(View view) {
        Intent intent=new Intent(this, FirstTimeDevicesActivity.class);
        startActivity(intent);
    }
}
