package com.example.bitcoinminer;

import java.util.ArrayList;


import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final String PREFS = "MyPrefsFile";

	private String startMining = "Start Mining";
	private String stopMining = "Stop Mining";

	private TextView tvUsername;
	private TextView loginStat;
	private Button toggleButton;
	private Chronometer chrono;

	private boolean mining;
	private String username;
	/**
	private TimeSpan cachedTime;
	private Date start_time;
	**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		tvUsername = (TextView) findViewById(R.id.editText);
		loginStat = (TextView) findViewById(R.id.textLoginStatus);
		chrono = (Chronometer) findViewById(R.id.chronometer);
		toggleButton = (Button) findViewById(R.id.buttonMine);
		toggleButton.setEnabled(false);

		// This loads the state from your phone's local storage.
		SharedPreferences settings = getSharedPreferences(PREFS, 0);
		mining = Boolean.valueOf(settings.getString("mining_state_saved", ""));
		username = settings.getString("username_saved", "");
		if (username != null) login();
		/**
		start_time = settings...
		cached_time = settings...
		**/
    }

    public void onStop() {
    	super.onStop();
		//This saves the state onto your phone's local storage.
        SharedPreferences.Editor editor = getSharedPreferences(PREFS,0).edit();
        editor.putString("username_saved", username);
		editor.putString("mining_state_saved", String.valueOf(mining));
		//editor.putString("mining_start_saved", toString(start_time));
		//editor.putString("mining_time_cached", toString(cached_time));
		editor.commit();
    }

	public void login(View view) {
		login();
		//to hide the keyboard
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public void login() {
		username = tvUsername.getText().toString();
		if (username != null) {
			loginStat.setText("Logged in as " + username);
			toggleButton.setEnabled(true);
			updateMining();
		}
	}

	public void toggleMine(View view) {
		if (mining) {
			mining = false;
			chrono.stop();
			// save start time
		} else {
			mining = true;
			chrono.setBase(SystemClock.elapsedRealtime());
			chrono.start();
			// get current time.  figure out how much time passed since last start, add to cached time.
		}
		updateMining();
	}

	//update thingy
	public void updateMining() {
		if (mining) {
			toggleButton.setText("Stop Mining");
			//update chrono from savestate
		} else {
			toggleButton.setText("Start Mining");
			//update chrono...
		}
	}

}
