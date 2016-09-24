package takemewith.io.takemewith;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import takemewith.io.takemewith.utils.UserPreferences;

public class StartupActivity extends TakeMeWith implements View.OnClickListener {

    private EditText mNameEditText;

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        mNameEditText = (EditText) findViewById(R.id.input_name);
        mFab = (FloatingActionButton) findViewById(R.id.next_button);

        mFab.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!TextUtils.isEmpty(UserPreferences.get().getName())) {
            startMain();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_button) {
            String name = mNameEditText.getText().toString();
            if (TextUtils.isEmpty(name)) {
                return;
            } else {
                UserPreferences.get().setName(name).saveSync();
            }
            startMain();
        }
    }

    private void startMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
