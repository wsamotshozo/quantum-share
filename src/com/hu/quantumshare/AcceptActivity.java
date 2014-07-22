package com.hu.quantumshare;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.os.Build;

public class AcceptActivity extends Activity {
	Context context;
	SharedPreferences prefs;
	TextView needs;
	TextView time;
	TextView place;
	TextView instructions;
	ParseObject task;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accept);
		context = getApplicationContext();
		needs = (TextView)findViewById(R.id.need);
		time = (TextView)findViewById(R.id.when);
		place = (TextView)findViewById(R.id.where);
		instructions = (TextView)findViewById(R.id.special);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
		query.addDescendingOrder("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> taskList, ParseException e) {
				// TODO Auto-generated method stub
				if(e == null)
				{
					task = taskList.get(0);
					Log.d("data",task.getObjectId());
					Log.d("data",task.getString("owner"));
					Log.d("data",task.getString("needs"));
					Log.d("data",task.getString("time"));
					Log.d("data",task.getString("place"));
					Log.d("data",task.getString("instructions"));
					needs.setText(task.getString("owner") + " needs "+task.getString("needs"));
					time.setText("at "+ task.getString("time"));
					place.setText("in " +task.getString("place"));
					instructions.setText("instructions:"+task.getString("instructions"));
				}else{
					
				}
				
			}
		});
		/*if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accept, menu);
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
	public void accept(View v) {
		ParseInstallation.getCurrentInstallation().saveInBackground();
		ParseQuery query = ParseInstallation.getQuery();

			PushService.setDefaultPushCallback(context, TaskTakerActivity.class);
			ParsePush androidPush = new ParsePush();
			// Notification for Android users
			query.whereEqualTo("deviceType", "android");
			
			ParseUser currentUser = ParseUser.getCurrentUser();
			androidPush.setMessage(currentUser.getUsername()+" has accepted your task");
			query.whereNotEqualTo("username", currentUser.getUsername());
			task.put("taker", currentUser.getUsername());
			task.saveInBackground();
			androidPush.setQuery(query);
			androidPush.sendInBackground();
	}
	public void decline(View v) {

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	/*public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_accept,
					container, false);
			return rootView;
		}
	}*/

}
