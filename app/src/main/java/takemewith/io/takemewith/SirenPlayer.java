package takemewith.io.takemewith;

import android.content.Context;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Plays a message on repeat until pause or shutDown called
 */
public class SirenPlayer implements TextToSpeech.OnInitListener {

    private static final String SIREN_MESSAGE = "Take me with you!";
    private static final int MESSAGE_INTERVAL = 2000;

    private final TextToSpeech mTextToSpeech;
    private final Runnable mMsgRunnable;
    private final Handler mHandler;

    private boolean mIsReady;

    public SirenPlayer(Context context) {
        mTextToSpeech = new TextToSpeech(context, this);
        mTextToSpeech.setLanguage(Locale.UK);
        mHandler = new Handler();
        mMsgRunnable = new Runnable() {
            @Override
            public void run() {
                mTextToSpeech.speak(SIREN_MESSAGE, TextToSpeech.QUEUE_FLUSH, null);
                mHandler.postDelayed(this, MESSAGE_INTERVAL);
            }
        };
    }

    public void play() {
        if (mIsReady) {
            mHandler.removeCallbacks(mMsgRunnable);
            mHandler.post(mMsgRunnable);
        }
    }

    public void pause() {
        mHandler.removeCallbacks(mMsgRunnable);
        mTextToSpeech.stop();
    }

    public void shutDown() {
        pause();
        mTextToSpeech.shutdown();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mIsReady = true;
        } else {
            Log.e("TakeMeWith", "Ooops, something went wrong! " + status);
        }
    }
}
