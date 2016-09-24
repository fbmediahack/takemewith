package takemewith.io.takemewith;


import com.estimote.sdk.Region;

import java.util.UUID;

public final class BeaconsData {

    public static final UUID TAG_BEACON =
            UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D");
    public static final Integer TAG_BEACON_MAJOR = 6168;
    public static final Integer TAG_BEACON_MINOR = 32661;

    public static final UUID HOUSE_BEACON_PINK =
            UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D");
    public static final Integer HOUSE_BEACON_PINK_MAJOR = 3169;
    public static final Integer HOUSE_BEACON_PINK_MINOR = 41988;

    public static final UUID HOUSE_BEACON_PURPLE =
            UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D");
    public static final Integer HOUSE_BEACON_PURPLE_MAJOR = 18713;
    public static final Integer HOUSE_BEACON_PURPLE_MINOR = 56498;

    public static final Region ROOM1_REGION = new Region("Room1", HOUSE_BEACON_PINK,
            HOUSE_BEACON_PINK_MAJOR, HOUSE_BEACON_PINK_MINOR);

    public static final Region ROOM2_REGION = new Region("Room2", HOUSE_BEACON_PURPLE,
            HOUSE_BEACON_PURPLE_MAJOR, HOUSE_BEACON_PURPLE_MINOR);

    public static final Region TAG_BEACON_REGION = new Region ("TagBeacon",
            TAG_BEACON, TAG_BEACON_MAJOR, TAG_BEACON_MINOR);

}
