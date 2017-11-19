package project.sliit.assistme.Health;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.sliit.assistme.MainActivity;
import project.sliit.assistme.R;

public class HealthCountActivity extends Activity {

    int preSelectedIndex = -1;
    String date;
    String dateArr[];
    String step;
    String stepArr[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_count_activity_main);


        ListView listView = (ListView) findViewById(R.id.listview);


        final List<HealthUserModel> users = new ArrayList<>();
        date=MainActivity.DATABASEHANDLER.SelectAllStepDetailsDates();
        step=MainActivity.DATABASEHANDLER.SelectAllStepDetailsSteps();

        dateArr=date.split("#");
        stepArr=step.split("#");

        for(int i=1;i<dateArr.length;i++) {
            Log.d("Isha",stepArr[i]+" "+dateArr[i]);

            users.add(new HealthUserModel(stepArr[i],dateArr[i]));
        }

        final HealthCustomAdapter adapter = new HealthCustomAdapter(this, users);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                HealthUserModel model = users.get(i);



                users.set(i, model);

                //now update adapter
                adapter.updateRecords(users);
            }
        });

    }
}

class HealthCustomAdapter extends BaseAdapter {

    Activity activity;
    List<HealthUserModel> users;
    LayoutInflater inflater;

    //short to create constructer using command+n for mac & Alt+Insert for window


    public HealthCustomAdapter(Activity activity) {
        this.activity = activity;
    }

    public HealthCustomAdapter(Activity activity, List<HealthUserModel> users) {
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

            view = inflater.inflate(R.layout.health_count_list_view, viewGroup, false);

            holder = new ViewHolder();

            holder.tvUserName = (TextView)view.findViewById(R.id.tv_user_name);
            holder.ivCheckBox = (TextView) view.findViewById(R.id.tv_user_count);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        HealthUserModel model = users.get(i);

        holder.tvUserName.setText(model.getUserName());

        holder.ivCheckBox.setText(model.getCount());

        return view;

    }

    public void updateRecords(List<HealthUserModel> users){
        this.users = users;

        notifyDataSetChanged();
    }

    class ViewHolder{

        TextView tvUserName;
        TextView ivCheckBox;

    }
}

class HealthUserModel {

   String Count;
   String userName;

   //now create constructor and getter setter method using shortcut like command+n for mac & Alt+Insert for window.


   public HealthUserModel(String isSelected, String userName) {
       this.Count = isSelected;
       this.userName = userName;
   }

   public String isSelected() {
       return Count;
   }

    public String setCount() {
        return Count;
    }

   public String getUserName() {
       return userName;
   }

    public String getCount() {
        return Count;
    }

   public void setUserName(String userName) {
       this.userName = userName;
   }
}


