package takemewith.io.takemewith;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import java.util.List;
import java.util.UUID;

import takemewith.io.takemewith.utils.UserPreferences;


public class RangeListener implements BeaconManager.RangingListener {

    private static final int SIGNAL_THRESHOLD = -60;
    private static final int SMSTHRESHOLD_MILLIS = 1 * 60 * 1000;
    private final String mTargetRegion;
    private final SirenPlayer mPlayer;
    private long lastMillis = 0;

    public RangeListener(SirenPlayer player, String region) {
        mPlayer = player;
        mTargetRegion = region;
    }

    @Override
    public void onBeaconsDiscovered(Region region, List<Beacon> list) {

        if (UserPreferences.get().getEmergencyNumber() == null)  {
            return;
        }

        if (BeaconsData.TAG_BEACON_REGION.getIdentifier().equals(region.getIdentifier())) {
            if (!list.isEmpty() && list.get(0).getRssi() < SIGNAL_THRESHOLD && !mPlayer.isPlaying()) {
                mPlayer.play();
            } else {
                mPlayer.pause();
            }
        } else if (BeaconsData.ROOM_REGION.getIdentifier().equals(region.getIdentifier())) {

            final UUID closestUUID = region.getProximityUUID();
            Beacon closestBeacon = null;
            for (Beacon beacon : list) {
                if (beacon.getProximityUUID().equals(closestUUID) && beacon.getMajor() == region.getMajor()) {
                    closestBeacon = beacon;
                }
            }
            if (closestBeacon != null) {
                if ((Utils.computeProximity(closestBeacon).equals(Utils.Proximity.IMMEDIATE)
                        || Utils.computeProximity(closestBeacon).equals(Utils.Proximity.FAR))
                        && (lastMillis == 0
                        || (lastMillis != 0
                        && System.currentTimeMillis() - lastMillis > SMSTHRESHOLD_MILLIS))) {
                    SmsSender.sendSms(
                            String.format("%s has left the building!!", UserPreferences.get().getName()));
                }
            }
        }
    }

}
