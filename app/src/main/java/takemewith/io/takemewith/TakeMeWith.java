package takemewith.io.takemewith;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import takemewith.io.takemewith.utils.PermissionsHelper;

/**
 * Created by Vladimir Kislicins on 24/09/2016.
 */

public class TakeMeWith extends AppCompatActivity {

    public static final String TAG = TakeMeWith.class.getSimpleName();

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (permissions.length == 0 && grantResults.length == 0) {
            Log.d(TAG, ".onRequestPermissionsResult() - interrupted");
            return;
        }
        switch (requestCode) {
            case PermissionsHelper.TMW_PERMISSIONS: {
                boolean permissionDenied = false;
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    permissionDenied = true;
                }
                break;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionsHelper.hasPermissions(this);
    }
}
