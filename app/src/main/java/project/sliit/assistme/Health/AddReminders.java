package project.sliit.assistme.Health;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import project.sliit.assistme.MainActivity;
import project.sliit.assistme.R;

public class AddReminders extends AppCompatActivity {
      String a="tst";
    Button btn;
    int year_x,month_x,day_x;
    static final int DILOG_ID =0;
    EditText addNote;

    ListView listView;
    String rem;
    String remArr[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminders);
        showDialogOnButtonClick();
        //TextView textView = (TextView) findViewById(R.id.textView2);

        addNote=findViewById(R.id.AddNote);
        rem= MainActivity.DATABASEHANDLER.SelectAllReminders();
        remArr=rem.split("#");

        listView =(ListView)findViewById(R.id.reminder_lstView);
        //String [] values ={"task1","task2"};
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.activity_list_item,android.R.id.text1,remArr);
        listView.setAdapter(adapter);



        //a= Databasefile.databasetostringReminder("2");
        //textView.setText(a);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            int ItemPosition=position;
            String itemValue =(String)listView.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(),"position:"+ItemPosition+"itemValue:"+itemValue,Toast.LENGTH_LONG).show();
            }
        });

    }

    public void showDialogOnButtonClick(){

        btn =(Button)findViewById(R.id.dateBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DILOG_ID);
            }
        });

    }

    protected Dialog onCreateDialog(int id){
        if (id == DILOG_ID)

            return new DatePickerDialog(this,dpickerListner,year_x,month_x,day_x);
            return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListner =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfyear, int dayOfmonth) {
                year_x =year;
                month_x =monthOfyear;
                day_x =dayOfmonth;
                Toast.makeText(AddReminders.this,year_x +"/" +month_x + "/"+day_x,Toast.LENGTH_LONG ).show();

            }
    };

    public void addnote(View view) {
        String Note=addNote.getText().toString();
        MainActivity.DATABASEHANDLER.addReminders(Note,year_x+"/"+month_x+"/"+day_x);
        Toast.makeText(getApplicationContext(),"Reminder Added",Toast.LENGTH_LONG).show();
    }
}
