package takemewith.io.takemewith;


import android.app.Application;
import com.estimote.sdk.BeaconManager;


import takemewith.io.takemewith.utils.UserPreferences;

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

        mBeaconManager.connect((new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                // Start monitoring regions
                mBeaconManager.startRanging(BeaconsData.TAG_BEACON_REGION);
                mBeaconManager.startMonitoring(BeaconsData.ROOM_REGION);
            }
        }));
    }
}