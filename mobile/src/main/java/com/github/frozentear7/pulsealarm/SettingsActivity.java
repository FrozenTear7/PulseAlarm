package com.github.frozentear7.pulsealarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveOnClick(View view) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();

        EditText editTextHeartRateLower = findViewById(R.id.editTextHeartRateLower);
        EditText editTextHeartRateUpper = findViewById(R.id.editTextHeartRateUpper);

        try {
            int prefLowerHeartRate = Integer.parseInt(editTextHeartRateLower.getText().toString());
            int prefUpperHeartRate = Integer.parseInt(editTextHeartRateUpper.getText().toString());

            if (prefLowerHeartRate < 0 || prefLowerHeartRate >= prefUpperHeartRate) {
                Toast.makeText(this, "Please provide valid values: bigger than 0 and upper > lower", Toast.LENGTH_SHORT).show();
            } else {
                editor.putInt(getString(R.string.prefLowerHeartRate), prefLowerHeartRate);
                editor.putInt(getString(R.string.prefUpperHeartRate), prefUpperHeartRate);
                editor.apply();

                finish();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please provide valid numeric values", Toast.LENGTH_SHORT).show();
        }
    }
}
