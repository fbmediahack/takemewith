package takemewith.io.takemewith;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

public class MainActivity extends TakeMeWith implements View.OnClickListener {

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = (FloatingActionButton) findViewById(R.id.sos_button);
        mFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sos_button) {
            SmsSender.sendSms();
        }
    }
}
