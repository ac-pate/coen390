package com.example.newcountingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferenceHelper {
    private final SharedPreferences preferences;
    private static final String PREFS_NAME = "ProfilePreference";

    public SharedPreferenceHelper(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE );
    }

    public void saveSettings(Settings settings) {
        SharedPreferences.Editor editor = preferences.edit();
        //saving input fields from the SettingsActivity by retrieving it from the settings
        editor.putString("button1Name", settings.getButton1Name());
        editor.putString("button2Name", settings.getButton2Name());
        editor.putString("button3Name", settings.getButton3Name());
        editor.putInt("maxCount",settings.getMaxCount());
        editor.commit();
    }

    public int getEventCount(String eventKey) {
        return preferences.getInt(eventKey, 0);
    }

    public Settings getSettings() {
        String button1Name = preferences.getString("button1Name", null);
        String button2Name = preferences.getString("button2Name", null);
        String button3Name = preferences.getString("button3Name", null);
        int maxCount = preferences.getInt("maxCount", -1); // <- default value is not 0

        if (button1Name == null || button2Name == null || button3Name == null || maxCount == -1) {
            return null; // If any field is missing, return null to indicate no settings (just in case!)
        }
        return new Settings(button1Name, button2Name, button3Name, maxCount);
    }

    //to clear settings data`
    public void clearSettingData() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    // Method to save counters
    public void saveCounters(int counter1, int counter2, int counter3, int counterTotal) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("counter1", counter1);
        editor.putInt("counter2", counter2);
        editor.putInt("counter3", counter3);
        editor.putInt("counterTotal", counterTotal);
        editor.apply();
    }

    // Method to get counters
    public int[] getCounters() {
        int counter1 = preferences.getInt("counter1", 0); // Default to 0 if not set
        int counter2 = preferences.getInt("counter2", 0);
        int counter3 = preferences.getInt("counter3", 0);
        int counterTotal = preferences.getInt("counterTotal", 0);
        return new int[] {counter1, counter2, counter3, counterTotal}; // return type => array of 4
    }

    //to save the click event from the mainActivity in 2 form - name form and counter number form
    public void saveClickEvent(String event, String numStr) {
        SharedPreferences.Editor editor = preferences.edit();

        //storing 2 different kinds of string with name and with numbers
        String existingEvents = preferences.getString("clickEvents", "");
        String existingEventsNumStr = preferences.getString("clickEventsNumStr","");

        // Append new event to existing ones
        if (!existingEvents.isEmpty()) {
            existingEvents += ",";
        }
        if(!existingEventsNumStr.isEmpty()) {
            existingEventsNumStr += ",";
        }
        String updatedEvents = existingEvents + event;
        String updatedEventsNumStr = existingEventsNumStr + numStr;
        editor.putString("clickEvents", updatedEvents);
        editor.putString("clickEventsNumStr",updatedEventsNumStr);
        editor.apply();
    }

    // Retrieve the click events
    public List<String> getClickEvents(Boolean eventName) {
        String eventsString;
        //check boolean value of name mode to return either counter number string or counter name string
        if(eventName) {
            eventsString = preferences.getString("clickEvents", "");
        }
        else {
            eventsString = preferences.getString("clickEventsNumStr", "");
        }

        List<String> eventList = new ArrayList<>();

        if (!eventsString.isEmpty()) {
            // Split the string by commas to get the individual events
            String[] eventsArray = eventsString.split(",");
            for (String event : eventsArray) {
                if (!event.trim().isEmpty()) {
                    eventList.add(event);
                }
            }
            Log.d("GetString",eventList.toString());
        }

        return eventList; // Return the list of events
    }

}
