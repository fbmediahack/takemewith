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
        mBeaconManager.setBackgroundScanPeriod(30000, 1000);
        mBeaconManager.setForegroundScanPeriod(30000, 1000);

        mBeaconManager.setRangingListener(new SirenPlayingRangeListener(
                new SirenPlayer(getApplicationContext()),
                BeaconsData.TAG_BEACON_REGION.getIdentifier()));

        mBeaconManager.setMonitoringListener(mMonitoringListener);

        mBeaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                // Start monitoring regions
                mBeaconManager.startRanging(BeaconsData.TAG_BEACON_REGION);
                mBeaconManager.startMonitoring(BeaconsData.ROOM_REGION);
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
                                        temperature));
                    }
                }
            }
        });
    }
}