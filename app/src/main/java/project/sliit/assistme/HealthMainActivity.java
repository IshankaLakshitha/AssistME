package project.sliit.assistme;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Date;

import project.sliit.assistme.Health.ListViewRemovalAnimation;
import project.sliit.assistme.Health.StepsCount;
import project.sliit.assistme.Health.UserDetails;


public class HealthMainActivity extends AppCompatActivity {
    Button Abutton;
    Button Hbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_health);
        Abutton = (Button) findViewById(R.id.button1);
        Hbutton = (Button) findViewById(R.id.button2);



        Abutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),StepsCount.class);

                startActivity(intent1);



                finish();
            }
        });

        Hbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(),ListViewRemovalAnimation.class);

                startActivity(intent2);
                
                finish();
            }
        });
    }

    // method for first time run the userdetails
    private  boolean isFristTime()
    {
        SharedPreferences preferences =getPreferences(MODE_PRIVATE);
        boolean ranbefore =preferences.getBoolean("ranbefore",false);
        if (!ranbefore)
        {
            //check wether the interface run or not
            SharedPreferences.Editor editor =preferences.edit();
            editor.putBoolean("ranbefore",true);
            editor.commit();
        }
        return !ranbefore;
    }


}
