package takemewith.io.takemewith;

import android.app.Application;

import takemewith.io.takemewith.utils.UserPreferences;

public class TakemeWithApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        UserPreferences.init(getApplicationContext());
    }
}