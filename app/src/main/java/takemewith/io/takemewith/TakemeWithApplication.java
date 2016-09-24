package takemewith.io.takemewith;

import takemewith.io.takemewith.utils.UserPreferences;

import android.app.Application;
import android.util.Log;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.eddystone.Eddystone;

import java.util.List;

import static takemewith.io.takemewith.TakeMeWith.TAG;

public class TakemeWithApplication extends Application {

    private BeaconManager mBeaconManager;

    private HouseMonitoringListener mMonitoringListener;

    @Override
    public void onCreate() {
        super.onCreate();

        UserPreferences.init(getApplicationContext());
        mMonitoringListener = new HouseMonitoringListener(getApplicationContext());

        mBeaconManager = new BeaconManager(getApplicationContext());

        mBeaconManager.setRangingListener(new RangeListener(
                new SirenPlayer(getApplicationContext()),
                BeaconsData.TAG_BEACON_REGION.getIdentifier()));

        mBeaconManager.setMonitoringListener(mMonitoringListener);

        mBeaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                // Start monitoring regions
                mBeaconManager.startRanging(BeaconsData.TAG_BEACON_REGION);
                // For demo purposes we'll range the beacon instead of monitor it.
                // This is because the range of the monitoring is just too big, so
                // we'll detect when the phone is at more than 3 metres from the beacon as a
                // "leave the house simulation"
//                mBeaconManager.startMonitoring(BeaconsData.ROOM_REGION);
                mBeaconManager.startRanging(BeaconsData.ROOM_REGION);
                mBeaconManager.startEddystoneScanning();
            }
        });

        mBeaconManager.setEddystoneListener(new BeaconManager.EddystoneListener() {
            @Override
            public void onEddystonesFound(List<Eddystone> list) {
                Log.d(TAG,".onEddystonesFound() - entered - count "+list.size());
                for (Eddystone e : list) {
                    double temperature = e.telemetry.temperature;
                    Log.d(TAG,".onEddystonesFound() - temperature is "+temperature);
                    if (temperature < UserPreferences.get().getLowTempLimit()
                            || temperature > UserPreferences.get().getHighTempLimit()) {
                        SmsSender.sendSms(
                                String.format("Warning! Unexpected temperature detected: %d",
                                        (int) temperature));
                    }
                }
            }
        });
    }
}