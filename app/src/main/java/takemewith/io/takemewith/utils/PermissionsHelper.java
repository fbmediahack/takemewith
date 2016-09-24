package takemewith.io.takemewith.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import takemewith.io.takemewith.TakeMeWith;

/**
 * Created by Vladimir Kislicins on 23/09/2015.
 */
public class PermissionsHelper {

    public static final int TMW_PERMISSIONS = 100;

    public PermissionsHelper(){}

    private static final String TAG = PermissionsHelper.class.getSimpleName();

    public static boolean hasPermissions(Activity activity) {
        boolean hasLocationPermissions = (ActivityCompat
                .checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
        boolean hasBluetoothPermissions =(ActivityCompat
                .checkSelfPermission(activity, Manifest.permission.BLUETOOTH)
                == PackageManager.PERMISSION_GRANTED);
        boolean hasSmsPermission = (ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED);

        List<String> permissions = new ArrayList<>();
        if (!hasLocationPermissions) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!hasBluetoothPermissions) {
            permissions.add(Manifest.permission.BLUETOOTH);
        }
        if (!hasSmsPermission) {
            permissions.add(Manifest.permission.SEND_SMS);
        }
        if (!permissions.isEmpty()) {
            String[] params = permissions.toArray(new String[permissions.size()]);
            ActivityCompat
                    .requestPermissions(activity, params, TMW_PERMISSIONS);
            return false;
        }

        return true;
    }

}
