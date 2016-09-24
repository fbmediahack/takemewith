package takemewith.io.takemewith;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;


/**
 * Plays a message when beacon goes out of range
 */
public class SirenPlayingRangeListener implements BeaconManager.RangingListener {

    private static final int SIGNAL_THRESHOLD = -60;
    private final String mTargetRegion;
    private final SirenPlayer mPlayer;

    public SirenPlayingRangeListener(SirenPlayer player, String region) {
        mPlayer = player;
        mTargetRegion = region;
    }

    @Override
    public void onBeaconsDiscovered(Region region, List<Beacon> list) {
        if (!mTargetRegion.equals(region.getIdentifier())) {
            return;
        }

        if (!list.isEmpty() && list.get(0).getRssi() < SIGNAL_THRESHOLD && !mPlayer.isPlaying()) {
            mPlayer.play();
        } else {
            mPlayer.pause();
        }
    }

}
