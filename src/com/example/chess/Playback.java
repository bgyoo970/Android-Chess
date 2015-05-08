package com.example.chess;
/*
 * CS 213 
 * Android Project
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Playback extends Activity {
	
	public static Record currentGameSelection;
	public static TextView prevSelection;
	public static Context context;
	
	// MAIN
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playback);
		context = getApplicationContext();
		
		Button play = (Button)findViewById(R.id.playback_play);
		play.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				Replay.currentReplayRecord = currentGameSelection;
				
				if (Replay.currentReplayRecord != null) {
					Intent intent = new Intent(view.getContext(), Replay.class);
					startActivityForResult(intent, 0);
				}
				else {
					print("Please select a game");
				}
			}
		});
		Button back = (Button)findViewById(R.id.playback_back);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), MainMenu.class);
				startActivityForResult(intent, 0);
			}
		});
		
		
		String [] allFiles = context.fileList();
		if (allFiles != null) {
			for (String currFile : allFiles) {
				// go through every file
				// add each record into the recordList
				Record currRecord = Play.load(context, currFile);
				if (currRecord != null) {
					if (!Play.isDuplicate(currRecord.recordName))		
						Play.recordList.add(currRecord);
				}
			}
		}
		createList();
	}
	public void print(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void createList() {
		TextView[] gamesList = new TextView[Play.recordList.size()];
		LinearLayout layout = (LinearLayout) findViewById(R.id.gamesList);
		layout.removeAllViews();
		
		for (int i = 0; i < Play.recordList.size(); i++) {
			
			gamesList[i] = new TextView(getApplicationContext());
			String name = Play.recordList.get(i).recordName;
			final Record currGame = Play.recordList.get(i);
			final TextView curr = gamesList[i];
			
			gamesList[i].setText(name);
			gamesList[i].setTextColor(Color.BLUE);
			gamesList[i].setTypeface(null, Typeface.BOLD_ITALIC);
			gamesList[i].setTextSize(16);

            layout.addView(gamesList[i]);
            
            gamesList[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	
                	if (currGame != currentGameSelection) {
                		// un-highlight the previous selection
                		if (prevSelection != null) {
                			prevSelection.setBackgroundColor(Color.WHITE);
                		}
                		// highlight the current selection
                    	curr.setBackgroundColor(Color.YELLOW);
                	}
                	// Used to save pervious selection. Helps with highlighting
                	prevSelection = curr;
                	
                	// onClick, saves a reference. Used to display the selected game in Replay
                	currentGameSelection = currGame;
                }
            }); 
			
		}
		

		// Create a sort by title, sort by date.
		// sort()
		// -- sort will sort the recordList.
		// save/persist.
		// createList()
	}
	
	public void sortByTitle() {
		String highest;
		Record highestR;
		int index;
		ArrayList<Record> temp = new ArrayList<Record>();
		for (int i = 0; i < Play.recordList.size(); i++) {
			highest = Play.recordList.get(i).recordName;
			index = i;
			
			for (int j = 0; j < Play.recordList.size(); j++) {
				if (highest.compareTo(Play.recordList.get(i).recordName) > 0) {
					highest = Play.recordList.get(i).recordName;
					index = i;
				}
				else if (highest.compareTo(Play.recordList.get(i).recordName) < 0) {
				}
			}
			
			temp.add(Play.recordList.get(index));
		}
		
		for (int k = 0; k < temp.size(); k++) {
			Play.recordList.set(k, temp.get(k));
		}
	}
	
	public void sortByDate() {
		String highest;
		Record highestR;
		int index;
		ArrayList<Record> temp = new ArrayList<Record>();
		for (int i = 0; i < Play.recordList.size(); i++) {
			highest = Play.recordList.get(i).recordName;
			index = i;
			
			for (int j = 0; j < Play.recordList.size(); j++) {
				if (highest.compareTo(Play.recordList.get(i).recordName) > 0) {
					highest = Play.recordList.get(i).recordName;
					index = i;
				}
			}
			
			temp.add(Play.recordList.get(index));
		}
		
		Play.recordList = temp;
	}
	
}
