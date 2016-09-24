package takemewith.io.takemewith;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.eddystone.Eddystone;

import java.util.List;

import takemewith.io.takemewith.utils.UserPreferences;

import static takemewith.io.takemewith.TakeMeWith.TAG;

public class TakemeWithApplication extends Application {

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        UserPreferences.init(getApplicationContext());
        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect((new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                // Start monitoring regions
                beaconManager.startRanging(BeaconsData.TAG_BEACON_REGION);
                beaconManager.startMonitoring(BeaconsData.ROOM_REGION);
            }
        }));

        beaconManager.setRangingListener(new SirenPlayingRangeListener(
                new SirenPlayer(getApplicationContext()),
                BeaconsData.TAG_BEACON_REGION.getIdentifier()));

        // SAMPLE LISTENER FOR THE REGIONS
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {

            //Sample listener
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                if (region.equals(BeaconsData.ROOM_REGION)) {
                    Toast.makeText(getApplicationContext(), "House entered", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
                if (region.equals(BeaconsData.ROOM_REGION)) {
                    Toast.makeText(getApplicationContext(), "House exited", Toast.LENGTH_LONG).show();
                }
            }
        });

        beaconManager.setEddystoneListener(new BeaconManager.EddystoneListener() {
            @Override
            public void onEddystonesFound(List<Eddystone> list) {
                for (Eddystone e : list) {
                    double temperature = e.telemetry.temperature;
                    Log.d(TAG,".onEddystonesFound() - temperature is "+temperature);
                }
            }
        });
    }

}