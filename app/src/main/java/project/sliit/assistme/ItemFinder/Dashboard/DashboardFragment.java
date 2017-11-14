package project.sliit.assistme.ItemFinder.Dashboard;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.sliit.assistme.ItemFinder.BluetoothLEService;
import project.sliit.assistme.ItemFinder.database.Devices;


public class DashboardFragment extends Fragment {

    private float batteryPercent = 100f, rssiValue = 0f;

    //private CircleDisplay mCircleDisplay;

    private OnDashboardListener presenter;

    private BroadcastReceiver receiver;

    public static DashboardFragment instance(final String address)
    {
        final DashboardFragment dashboardFragment = new DashboardFragment();
        Bundle arguments = new Bundle();
        arguments.putString(Devices.ADDRESS, address);
        dashboardFragment.setArguments(arguments);
        dashboardFragment.setRetainInstance(true);
        return dashboardFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view =null;
        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof OnDashboardListener) {
            presenter = (OnDashboardListener) activity;
        } else {
            throw new ClassCastException("must implement OnDashboardListener");
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, final Intent intent)
            {
                if (BluetoothLEService.IMMEDIATE_ALERT_AVAILABLE.equals(intent.getAction())) {
                    presenter.onImmediateAlertAvailable();
                }
            }
        };
        // register events
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter(BluetoothLEService.IMMEDIATE_ALERT_AVAILABLE));
        this.presenter.onDashboardStarted();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        this.presenter.onDashboardStopped();
    }



    public interface OnDashboardListener {

        void onDashboardStarted();

        void onDashboardStopped();

        void onImmediateAlertAvailable();
    }
}
