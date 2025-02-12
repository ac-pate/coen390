package com.example.newcountingapp;

import com.example.newcountingapp.R;

import android.annotation.SuppressLint;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnEvent1, btnEvent2, btnEvent3;

    private SharedPreferenceHelper sharedPreferenceHelper;
    private static final String PREFS_NAME = "EventPrefs";
    protected int counter1=0;
    protected int counter2=0;
    protected int counter3=0;
    protected int counterTotal=0;
    private TextView textViewCount;
    View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sharedPreferenceHelper = new SharedPreferenceHelper(MainActivity.this);

        btnEvent1 = findViewById(R.id.btnEvent1);
        btnEvent2 = findViewById(R.id.btnEvent2);
        btnEvent3 = findViewById(R.id.btnEvent3);
        textViewCount = findViewById(R.id.textViewTotalCount);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainToolbar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Settings settings = sharedPreferenceHelper.getSettings();

        if (settings == null) {
            // Redirect to Settings activity if no profile info is available
//            launchSettings(v);
        } else {
            // Display profile name on the button
            btnEvent1.setText(settings.getButton1Name());
            btnEvent2.setText(settings.getButton2Name());
            btnEvent3.setText(settings.getButton3Name());

            // Restore the counter values from SharedPreferences
            int[] counters = sharedPreferenceHelper.getCounters();
            counter1 = counters[0];
            counter2 = counters[1];
            counter3 = counters[2];
            counterTotal = counters[3];

            // Update the TextView with the restored total count
            textViewCount.setText("Total Count: " + counterTotal);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save the counter values in SharedPreferences
        sharedPreferenceHelper.saveCounters(counter1, counter2, counter3, counterTotal);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show(); // bottom of the screen for a short duration.
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //launch SettingsActivity as it acts like an event handler
    public void launchSettings(View view) {
        Intent i = new Intent(this,SettingsActivity.class);
        startActivity(i);
    }

    //launch DataActivity as it acts like an event handler
    public void launchDataActivity(View view) {
        Intent i = new Intent(this,DataActivity.class);
        startActivity(i);
    }
    //increments the countTotal and update the view
    public void incrementCountTotal() {
        counterTotal++;
        textViewCount.setText("Total Count: " + counterTotal);
    }

    //increments counter 1 value and total count value
    public void incrementCount1(View view) {
        if(counterTotal < sharedPreferenceHelper.getSettings().getMaxCount()) {
            counter1++;
            //Saving the string of the counter 1 with another string "1" for toggle events in dataActivity
            sharedPreferenceHelper.saveClickEvent(sharedPreferenceHelper.getSettings().getButton1Name(),"1");
            incrementCountTotal();
        }
        else error();
    }

    //increments counter 2 value and total count value
    public void incrementCount2(View view) {
        if(counterTotal < sharedPreferenceHelper.getSettings().getMaxCount()) {
            counter2++;
            //Saving the string of the counter 2 with another string "2" for toggle events in dataActivity
            sharedPreferenceHelper.saveClickEvent(sharedPreferenceHelper.getSettings().getButton2Name(),"2");
            incrementCountTotal();
        }
        else error();
    }

    //increments counter 3 value and total count value
    public void incrementCount3(View view) {
        if(counterTotal < sharedPreferenceHelper.getSettings().getMaxCount()) {
            counter3++;
            //Saving the string of the counter 3 with another string "3" for toggle events in dataActivity
            sharedPreferenceHelper.saveClickEvent(sharedPreferenceHelper.getSettings().getButton3Name(),"3");
            incrementCountTotal();
        }
        else error();
    }


    //displays toast message of max count reached
    public void error() {
        Toast.makeText(MainActivity.this,"Max Count Reached",Toast.LENGTH_SHORT).show();
    }
}
