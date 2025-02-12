package com.example.newcountingapp;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataActivity extends AppCompatActivity {
    protected TextView counter1, counter2, counter3, counterTotal;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private RecyclerView.Adapter eventListAdapter;
    private RecyclerView recyclerView;
    private List<String> clickEvents; //list to store all the events
    private boolean isEventName = true; // Flag to track the state of the button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data);

        // find the Toolbar in the layout
        Toolbar toolbar = findViewById(R.id.toolbarDataActivity);
        setSupportActionBar(toolbar);

        // enable the "Up" (back) button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // setting custom drawable
        toolbar.setOverflowIcon(getDrawable(R.drawable.round_more_vert_24));

        sharedPreferenceHelper = new SharedPreferenceHelper(this);
        //setting text view to show data with local variable
        counter1 = findViewById(R.id.count1);
        counter2 = findViewById(R.id.count2);
        counter3 = findViewById(R.id.count3);
        counterTotal = findViewById(R.id.totalCount);

        //stores the events
        clickEvents = new ArrayList<>();

        //recycler view
        recyclerView = findViewById(R.id.recyclerView);

        //setting layout manager and item animator
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //setting adapter
        eventListAdapter = new EventListAdapter(clickEvents);
        recyclerView.setAdapter(eventListAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainToolbar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        //turn on the name mode
        eventNameMode();

        // Retrieve and display click events
        clickEvents.clear();  // Clear existing events before adding new ones
        clickEvents.addAll(sharedPreferenceHelper.getClickEvents(isEventName));

        // Notify the adapter that data has changed
        eventListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate your menu XML file
        getMenuInflater().inflate(R.menu.data_menu, menu);
        return true;
    }

    //called whenever item menu is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        isEventName=!isEventName;
        if (item.getItemId() == R.id.action_ToggleEvents) {
            //if flag isEventName is true, turn on name mode otherwise button mode
            if(isEventName) {
                eventNameMode();
            }
            else {
                eventButtonMode();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void eventNameMode() {
        Settings settings = sharedPreferenceHelper.getSettings();
        int[] counts = sharedPreferenceHelper.getCounters();
        //setting the name of counter events with the name it was saved
        counter1.setText(String.format("%s: %d", settings.getButton1Name(), counts[0]));
        counter2.setText(String.format("%s: %d", settings.getButton2Name(), counts[1]));
        counter3.setText(String.format("%s: %d", settings.getButton3Name(), counts[2]));
        counterTotal.setText(String.format("Total Events: %d", counts[3]));

        // Retrieve and display click events
        clickEvents.clear();  // Clear existing events before adding new ones
        //retrieving the list of event stored and adding it in clickEvents
        clickEvents.addAll(sharedPreferenceHelper.getClickEvents(isEventName));
        // Notify the adapter that data has changed
        eventListAdapter.notifyDataSetChanged();
    }

    public void eventButtonMode() {
        Settings settings = sharedPreferenceHelper.getSettings();
        int[] counts = sharedPreferenceHelper.getCounters();
        counter1.setText(String.format("Counter 1: %d", counts[0]));
        counter2.setText(String.format("Counter 2: %d", counts[1]));
        counter3.setText(String.format("Counter 3: %d", counts[2]));
        counterTotal.setText(String.format("Total Events: %d", counts[3]));
        // Retrieve and display click events
        clickEvents.clear();  // Clear existing events before adding new ones
        //retrieving the list of events stored in number form and adding it in clickEvents
        clickEvents.addAll(sharedPreferenceHelper.getClickEvents(isEventName));
        Log.d("ClickEvent",clickEvents.toString());
        // Notify the adapter that data has changed
        eventListAdapter.notifyDataSetChanged();
    }


}
