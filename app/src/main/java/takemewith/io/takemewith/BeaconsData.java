package takemewith.io.takemewith;


import com.estimote.sdk.Region;

import java.util.UUID;

public final class BeaconsData {

    public static final UUID TAG_BEACON =
            UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D");
    public static final Integer TAG_BEACON_MAJOR = 6168;
    public static final Integer TAG_BEACON_MINOR = 32661;

    public static final UUID HOUSE_BEACONS_UUID =
            UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE01");

    public static final Integer HOUSE_BEACON_PINK_MAJOR = 3169;
    public static final Integer HOUSE_BEACON_PINK_MINOR = 41988;


    public static final Integer HOUSE_BEACON_PURPLE_MAJOR = 18713;
    public static final Integer HOUSE_BEACON_PURPLE_MINOR = 56498;

    public static final Region ROOM_REGION = new Region("House Room", HOUSE_BEACONS_UUID,
            18713, 56498);


    public static final Region TAG_BEACON_REGION = new Region ("TagBeacon",
            TAG_BEACON, TAG_BEACON_MAJOR, TAG_BEACON_MINOR);

}
