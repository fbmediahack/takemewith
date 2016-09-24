package takemewith.io.takemewith;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import takemewith.io.takemewith.utils.UserPreferences;

public class StartupActivity extends TakeMeWith implements View.OnClickListener {

    private EditText mNameEditText;

    private FloatingActionButton mFab;

    private EditText mEmergencyPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        mNameEditText = (EditText) findViewById(R.id.input_name);
        mEmergencyPhoneNumber = (EditText) findViewById(R.id.emergency_phone_number);
        mFab = (FloatingActionButton) findViewById(R.id.next_button);

        mFab.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (UserPreferences.get().isConfigCompleted()) {
            startMain();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_button) {
            String name = mNameEditText.getText().toString();
            String number = mEmergencyPhoneNumber.getText().toString();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)) {
                Toast.makeText(this, "Please input your name and the emergency telephone number",
                        Toast.LENGTH_SHORT).show();
                return;
            } else {
                UserPreferences.get().setName(name).saveSync();
                UserPreferences.get().setEmergencyNumber(number).saveSync();
            }
            startMain();
        }
    }

    private void startMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
