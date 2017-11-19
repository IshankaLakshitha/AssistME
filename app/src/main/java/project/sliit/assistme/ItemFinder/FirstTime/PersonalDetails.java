package project.sliit.assistme.ItemFinder.FirstTime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import project.sliit.assistme.FirstTimeDevicesActivity;
import project.sliit.assistme.MainActivity;
import project.sliit.assistme.R;

public class PersonalDetails extends AppCompatActivity {


    EditText name,NName,Time;
    String n;
    String nn;
    String t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        name=(EditText) findViewById(R.id.txtName);
        NName=(EditText)findViewById(R.id.txtNName);
        Time=(EditText)findViewById(R.id.txtTime);


        //
    }



    public void done(View view) {

        n=name.getText().toString();
        nn=NName.getText().toString();
        t=Time.getText().toString();

        Log.d("Isha",n+nn+t+"");
        //MainActivity.DATABASEHANDLER.adduserNameDetails(name.getText().toString(),"Male",NName.getText().toString(),Time.getText().toString(),"aa");
        MainActivity.DATABASEHANDLER.adduserNameDetails(n,"ss",nn,t,"colombo");
        Intent intent=new Intent(this, FirstTimeDevicesActivity.class);
        startActivity(intent);
    }
}
