package com.example.newcountingapp;



import android.content.Intent;
import android.view.View;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    private EditText button1Name, button2Name, button3Name, maxCount;
    private Button saveButton;
    private SharedPreferenceHelper sharedPreferenceHelper;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        button1Name = findViewById(R.id.textInputC1);
        button2Name = findViewById(R.id.textInputC2);
        button3Name = findViewById(R.id.textInputC3);
        maxCount = findViewById(R.id.textInputCountMax);
        saveButton = findViewById(R.id.btn_saveSettings);

        sharedPreferenceHelper = new SharedPreferenceHelper(this);

        // Find the Toolbar in the layout
        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);

        // Enable the "top Left" (back) button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainToolbar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void saveSettings(View v) {

        String count1 = button1Name.getText().toString();
        String count2 = button2Name.getText().toString();
        String count3 = button3Name.getText().toString();
        String countMaxStr = maxCount.getText().toString();

        // check if any of the fields are empty, and if so display a toast
        if(count1.isEmpty() || count2.isEmpty() || count3.isEmpty() || countMaxStr.isEmpty()) {
            Toast.makeText(SettingsActivity.this,"All fields should be filled",Toast.LENGTH_LONG).show();
            return;
        }
        // parse teh maxcount int
        int countM = Integer.parseInt(maxCount.getText().toString());

        //checking if count max is in the range of 5 and 200, otherwise display toast
        if(countM<5 || countM>200) {
            Toast.makeText(SettingsActivity.this,"Maximum count outside the limit of 5 to 200",Toast.LENGTH_LONG).show();
            return;
        }

        //if all goes correct, save the settings and go back to the display mode
        Settings s = new Settings(count1, count2, count3, countM);
        sharedPreferenceHelper.saveSettings(s);
        displaySettings();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    public void displaySettings() {
        // check if any setting is saved
        if(sharedPreferenceHelper.getSettings() != null) {
            // retrieve the values
            button1Name.setText(sharedPreferenceHelper.getSettings().getButton1Name());
            button2Name.setText(sharedPreferenceHelper.getSettings().getButton2Name());
            button3Name.setText(sharedPreferenceHelper.getSettings().getButton3Name());
            maxCount.setText(String.valueOf(sharedPreferenceHelper.getSettings().getMaxCount()));

            // disable them and make the save button disappear
            button1Name.setEnabled(false);
            button2Name.setEnabled(false);
            button3Name.setEnabled(false);
            maxCount.setEnabled(false);
            saveButton.setEnabled(false);
            saveButton.setVisibility(View.GONE);
        }
    }

    //added it such that when clear setting menu item pressed it goes to mainActivity
    //which in turn launch the setting again!
    public void launchMainActivity(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    //clear settings (added it to test)
    public void clearSettings(MenuItem item) {
        sharedPreferenceHelper.clearSettingData();
        launchMainActivity(v);  // redirect to MainActivity
    }


}


