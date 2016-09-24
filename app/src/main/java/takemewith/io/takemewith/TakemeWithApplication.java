package takemewith.io.takemewith;

import android.app.Application;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;

public class TakemeWithApplication extends Application {

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect((new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                // Start monitoring regions
                beaconManager.startMonitoring(BeaconsData.ROOM1_REGION);
            }
        }));

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {

            //Sample listener
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                if (region.equals(BeaconsData.ROOM1_REGION)) {
                    Toast.makeText(getApplicationContext(), "Region 1 entered", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
            }
        });
    }
}