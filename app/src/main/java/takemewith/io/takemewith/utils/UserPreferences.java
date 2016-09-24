package takemewith.io.takemewith.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Vladimir Kislicins on 24/09/2016.
 */

public class UserPreferences {

    static final String TAG = UserPreferences.class.getSimpleName();

    private static final String PREF_NAME          = "io.takemewith.NAME";

    private static UserPreferences sInstance;

    private String mName;

    public static UserPreferences init(Context context) {
        sInstance = new UserPreferences(context);
        return sInstance;
    }

    public static UserPreferences get() {
        return sInstance;
    }

    private Context mContext;
    private SharedPreferences mPrefs;

    public UserPreferences(Context context) {
        this.mContext = context;
        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        // Save instance
        sInstance = this;

        // Reload the configuration
        reload();
    }

    private void onReadConfiguration(SharedPreferences prefs) {
        mName = prefs.getString(PREF_NAME, null);
    }

    private void onSaveConfiguration(SharedPreferences.Editor editor) {
        editor.putString(PREF_NAME, mName);
    }

    public final void reload() {
        Log.v(TAG, String.format("[%s] Loading configuration", getClass().getSimpleName()));

        SharedPreferences prefs = getSharedPreferences();
        onReadConfiguration(prefs);

        // If this is the first time we load the preference save what we have as defaults
        if (prefs.getAll() == null || prefs.getAll().isEmpty()) {
            Log.v(TAG,
                    String.format("[%s] Saving the configuration for the first time with defaults",
                            getClass().getSimpleName()));
            save();
        }
    }

    public final void save() {
        Log.v(TAG, String.format("[%s] Saving configuration (async)", getClass().getSimpleName()));
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                SharedPreferences.Editor editor = getSharedPreferences().edit();
                onSaveConfiguration(editor);
                editor.commit();

                return null;
            }
        }.execute();
    }

    public final void saveSync() {
        Log.v(TAG, String.format("[%s] Saving configuration (sync)", getClass().getSimpleName()));
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        onSaveConfiguration(editor);
        editor.commit();
    }

    protected SharedPreferences getSharedPreferences() {
        return mPrefs;
    }

    public String getName() {
        return mName;
    }

    public UserPreferences setName(String name) {
        mName = name;
        return this;
    }

}
