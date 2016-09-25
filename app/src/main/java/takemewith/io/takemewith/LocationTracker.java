package takemewith.io.takemewith;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Used to start and stop tracking users location, when tracking location is send to simple web-server
 */
public class LocationTracker implements LocationListener {

    // Change to wherever the web server is running
    private static final String API = "http://localhost:3000/putLocation";
    private final LocationManager mLocationManager;
    private final Context mContext;

    public LocationTracker(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startTrackingLocation() {
        if (hasPermission()) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
        }
    }

    public void stopTrackingLocation() {
        if (hasPermission()) {
            mLocationManager.removeUpdates(this);
        }
    }

    private boolean hasPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("TakeMeWith", "no permissions to track location");
            return false;
        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = API + "?lat=" + location.getLatitude() +
                           "&lng=" + location.getLongitude();
        queue.add(new StringRequest(Request.Method.GET, url, null, null));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
