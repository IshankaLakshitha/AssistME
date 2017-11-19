package project.sliit.assistme.Health;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import project.sliit.assistme.R;

import static project.sliit.assistme.Health.UserDetails.PassName;

public class HelthTips extends AppCompatActivity {

    UserDetails us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_deletion);

        us = new UserDetails();
        TextView textView = (TextView) findViewById(R.id.txt_name);
        textView.setText("Hello "+PassName);
    }
}
