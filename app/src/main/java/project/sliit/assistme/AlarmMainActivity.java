package project.sliit.assistme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.sliit.assistme.IntAlarm.AddMaualAlarmInterface;

public class AlarmMainActivity extends AppCompatActivity  {


    int preSelectedIndex = -1;
    String Name;
    String NamesArr [];

    String State;
    String StateArr[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity_main);


        ListView listView = (ListView) findViewById(R.id.listview);
        Name=MainActivity.DATABASEHANDLER.SelectAllAlarmName();
        NamesArr=Name.split("#");

        State=MainActivity.DATABASEHANDLER.SelectAllAlarmState();
        StateArr=State.split("#");

        final List<UserModel> users = new ArrayList<>();

        for(int i=1;i<NamesArr.length;i++) {
            Log.d("dd",StateArr[i]);
            if(StateArr[i].equals("true")) {
                users.add(new UserModel(true, NamesArr[i]));
            }else{
                users.add(new UserModel(false, NamesArr[i]));
            }
        }


        final CustomAdapter adapter = new CustomAdapter(this, users);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                UserModel model = users.get(i);

                if (model.isSelected())
                    model.setSelected(false);

                else
                    model.setSelected(true);

                users.set(i, model);

                //now update adapter
                adapter.updateRecords(users);
            }
        });


        FloatingActionButton addnew=findViewById(R.id.AddNewAlarm);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), AddMaualAlarmInterface.class);
                startActivity(intent);
            }
        });

    }
}

class CustomAdapter extends BaseAdapter {

    Activity activity;
    List<UserModel> users;
    LayoutInflater inflater;

    //short to create constructer using command+n for mac & Alt+Insert for window


    public CustomAdapter(Activity activity) {
        this.activity = activity;
    }

    public CustomAdapter(Activity activity, List<UserModel> users) {
        this.activity   = activity;
        this.users      = users;

        inflater        = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null){

            view = inflater.inflate(R.layout.alarm_list_view, viewGroup, false);

            holder = new ViewHolder();

            holder.tvUserName = (TextView)view.findViewById(R.id.tv_user_name);
            holder.ivCheckBox = (ImageView) view.findViewById(R.id.iv_check_box);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        UserModel model = users.get(i);

        holder.tvUserName.setText(model.getUserName());

        if (model.isSelected())
            holder.ivCheckBox.setBackgroundResource(R.drawable.checked);

        else
            holder.ivCheckBox.setBackgroundResource(R.drawable.check);

        return view;

    }

    public void updateRecords(List<UserModel> users){
        this.users = users;

        notifyDataSetChanged();
    }

    class ViewHolder{

        TextView tvUserName;
        ImageView ivCheckBox;

    }
}

class UserModel {

   boolean isSelected;
   String userName;

   //now create constructor and getter setter method using shortcut like command+n for mac & Alt+Insert for window.


   public UserModel(boolean isSelected, String userName) {
       this.isSelected = isSelected;
       this.userName = userName;
   }

   public boolean isSelected() {
       return isSelected;
   }

   public void setSelected(boolean selected) {
       isSelected = selected;
   }

   public String getUserName() {
       return userName;
   }

   public void setUserName(String userName) {
       this.userName = userName;
   }
}


