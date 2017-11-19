package project.sliit.assistme.Health;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import project.sliit.assistme.HealthMainActivity;
import project.sliit.assistme.R;

public class UserDetails extends AppCompatActivity {

    boolean gender;
    Button button;
    EditText age;
    EditText height;
    EditText weight;
    EditText UName;
    RadioButton male;
    RadioButton female;
    RadioGroup radioGroup;
    TextView test;



    public static double result=0;
    public  static String PassName;
    public  static String passage;
    public  static String passweight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        button = (Button) findViewById(R.id.done_btn);
        age = (EditText) findViewById(R.id.txt_age);
        height = (EditText) findViewById(R.id.txt_height);
        weight = (EditText) findViewById(R.id.txt_weight);
        UName = (EditText) findViewById(R.id.Name);
        male = (RadioButton) findViewById(R.id.radioButton_male);
        female = (RadioButton) findViewById(R.id.radioButton_female);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupG);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pAge= Integer.parseInt(age.getText().toString());
                double pHeight= Double.parseDouble(height.getText().toString());
                double pWeight= Double.parseDouble(weight.getText().toString());
                PassName = UName.getText().toString();
                passage=age.getText().toString();
                passweight=weight.getText().toString();

                if (male.isSelected()){
                    result=66.47+ (13.75 * pWeight) + (5.0 *pHeight) - (6.75*pAge);
                }
                else {
                    result=665.09 + (9.56 *pWeight) + (1.84 *pHeight) - (4.67 *pAge);
                }



                Intent intent1 = new Intent(getApplicationContext(),HealthMainActivity.class);

                startActivity(intent1);



                finish();
            }
        });

    }

    public void OnClicked(View view)
    {

        int id= radioGroup.getCheckedRadioButtonId();

        switch(id)
        {
            case R.id.radioButton_male:
                gender=true;

                break;
            case R.id.radioButton_female:
                gender=false;

                break;
        }

    }


}
