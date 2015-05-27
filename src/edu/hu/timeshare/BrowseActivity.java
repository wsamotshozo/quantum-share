package edu.hu.timeshare;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import edu.hu.timeshare.utils.Constants;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class BrowseActivity extends Activity {
	Context context;
	SharedPreferences prefs;
	ListView tasks;
	TableLayout rootView;
	ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(getApplicationContext(),
				"bQi0vm88WutERgTC4HBbdyuM7JXlshXPY8ePL6N3",
				"CBcHwpRVr3I8Nn54YMKrp1G4TlOcHYVB1XfPf8AU");
		ParseInstallation.getCurrentInstallation().saveInBackground();
		setContentView(R.layout.activity_browse);
		rootView = (TableLayout) findViewById(R.id.main);
		context = this;
		dialog = new ProgressDialog(this);
		dialog.setMessage("Please wait.");
		dialog.setIndeterminate(true);
		dialog.show();
		/*
		 * mAdapter = new TaskAdapter(this, new ArrayList<Task>());
		 * mListView.setAdapter(mAdapter);
		 */
		//tasks = (ListView) rootView.findViewById(R.id.tasks);
		//final TextView temp = (TextView) rootView.findViewById(R.id.text);
		ParseUser user = ParseUser.getCurrentUser();
		// ParseQuery<Tasks> query = ParseQuery.getQuery(Tasks.class);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
		query.setLimit(10);
		query.orderByAscending("time");
		Date now = new Date();
		query.whereGreaterThan("time", now);
		query.findInBackground(new FindCallback<ParseObject>() {

			@SuppressWarnings("deprecation")
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				TableRow title = new TableRow(context);
				title.setGravity(Gravity.CENTER_HORIZONTAL);
				TextView towner = new TextView(context);
				setTitleStyle(towner);
				towner.setText("OWNER");
				TextView tneeds = new TextView(context);
				tneeds.setText("NEEDS");
				setTitleStyle(tneeds);
				TextView ttime = new TextView(context);
				ttime.setText("TIME");
				setTitleStyle(ttime);
				TextView ttaker = new TextView(context);
				ttaker.setText("TAKER");
				setTitleStyle(ttaker);
				title.addView(towner);
				title.addView(tneeds);
				title.addView(ttime);
				title.addView(ttaker);
				rootView.addView(title);
				rootView.setClickable(true);
				for (int i = 0; i < objects.size(); i++) {
					TableRow temp = new TableRow(context);
					temp.setClickable(true);
					ParseObject info = objects.get(i);
					temp.setOnClickListener(new GridOnClickListener(i,info));
					temp.setGravity(Gravity.CENTER_HORIZONTAL);
					
					TextView owner = new TextView(context);
					setStyle(owner);
					owner.setText(info.getString("owner"));
					TextView needs = new TextView(context);
					if(info.getString("needs").equals("someone to babysit"))
					{
						needs.setText("babysit");
					}
					else
					{
						needs.setText("driver");
					}
					setStyle(needs);
					TextView time = new TextView(context);
					time.setText(Constants.formatDate(info.getDate("time")));
					setStyle(time);
					TextView hour = new TextView(context);
					hour.setText(info.getString("hour"));
					setStyle(hour);
					TextView day = new TextView(context);
					day.setText(info.getString("day"));
					setStyle(day);
					TextView month = new TextView(context);
					month.setText(info.getString("month"));
					setStyle(month);
					TextView year = new TextView(context);
					year.setText(info.getString("year"));
					setStyle(year);
					TextView minute = new TextView(context);
					minute.setText(info.getInt("minute")+"");
					setStyle(minute);
					temp.addView(owner);
					temp.addView(needs);
					temp.addView(time);

					TextView taker = new TextView(context);
					if(info.getString("taker") == null)
					{
						taker.setText("no one");
					}
					else
					{
						taker.setText(info.getString("taker"));
					}
					setStyle(taker);
					temp.addView(taker);
					rootView.addView(temp);
				}
				dialog.dismiss();
			}

		});
	}
	public void setTitleStyle(TextView a)
	{
		a.setPadding(10, 10, 10, 10);
		a.setTextColor(Color.rgb(18, 81, 35));
		a.setTextSize(18);
		a.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		//a.setShadowLayer(3, 1, 1, Color.rgb(81	, 35, 18));
		a.setBackgroundResource(R.drawable.border);
		a.setGravity(Gravity.CENTER_HORIZONTAL);
		//a.set
	}
	public void setStyle(TextView a)
	{
		a.setPadding(10, 10, 10, 10);
		a.setTextColor(Color.rgb(18, 81, 35));
		a.setTextSize(18);
		//a.setShadowLayer(3, 1, 1, Color.rgb(81	, 35, 18));
		a.setBackgroundResource(R.drawable.border);
	}

	private class GridOnClickListener implements View.OnClickListener {
		private int row;
		private ParseObject info;
		 public GridOnClickListener(int i, ParseObject data) {
		        // keep references for your onClick logic 
			 	row = i;
			 	info = data;
			 
		    }

	    @Override public void onClick(View v) {
			final Dialog popup = new Dialog(context);
			popup.setContentView(R.layout.dialog_task);
			popup.setTitle("Task Details");
			Button accept = (Button)popup.findViewById(R.id.accept);
			Button decline = (Button)popup.findViewById(R.id.decline);
			//Button cancel = (Button)popup.findViewById(R.id.cancel);
			TextView needs = (TextView)popup.findViewById(R.id.need);
			TextView time = (TextView)popup.findViewById(R.id.when);
			TextView place = (TextView)popup.findViewById(R.id.where);
			TextView instructions = (TextView)popup.findViewById(R.id.special);
			TextView taker = (TextView)popup.findViewById(R.id.taker);
			TextView owner = (TextView)popup.findViewById(R.id.owner);
			needs.setText("needs : "+info.getString("needs"));
			owner.setText("owner : "+info.getString("owner"));
			time.setText("time : "+Constants.formatDate(info.getDate("time")));
			place.setText("place : " +info.getString("place"));
			instructions.setText("instructions : " +info.getString("instructions"));
			if(info.getString("taker") == null)
			{
				taker.setText("taker : no one");
			}
			else
			{
				taker.setText("taker : " +info.getString("taker"));
			}
			ParseUser user = ParseUser.getCurrentUser();
			//Log.e("username", user.getUsername());
			if(user.getUsername().equals(info.getString("owner")))
			{
				accept.setVisibility(View.GONE);
				decline.setVisibility(View.GONE);
			}
			else if(user.getUsername().equals(info.getString("taker")))
			{
				accept.setVisibility(View.GONE);
				
			}
			else if(info.getString("taker") == null ||info.getString("taker").equals("no one"))
			{
				decline.setVisibility(View.GONE);
			}
			else
			{
				accept.setVisibility(View.GONE);
				decline.setVisibility(View.GONE);
				//cancel.setClickable(false);
			}
			accept.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					ParseQuery query = ParseInstallation.getQuery();

					PushService.setDefaultPushCallback(context, LoginActivity.class);
					ParsePush androidPush = new ParsePush();
					// Notification for Android users
					query.whereEqualTo("deviceType", "android");
					
					ParseUser currentUser = ParseUser.getCurrentUser();
					androidPush.setMessage(currentUser.getUsername()+" has accepted your task");
					query.whereEqualTo("username", info.getString("owner"));
					info.put("taker", currentUser.getUsername());
					info.saveInBackground();
					androidPush.setQuery(query);
					androidPush.sendInBackground();
					Toast.makeText(context, "you accepted the task", Toast.LENGTH_SHORT).show();
					popup.dismiss();
					Intent intent = new Intent(BrowseActivity.this, BrowseActivity.class);
					startActivity(intent);
				}
				
			});
			decline.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					ParseQuery query = ParseInstallation.getQuery();

					PushService.setDefaultPushCallback(context, LoginActivity.class);
					ParsePush androidPush = new ParsePush();
					// Notification for Android users
					query.whereEqualTo("deviceType", "android");
					
					ParseUser currentUser = ParseUser.getCurrentUser();
					androidPush.setMessage(currentUser.getUsername()+" has declined your task");
					query.whereEqualTo("username", info.getString("owner"));
					info.put("taker", "no one");
					info.saveInBackground();
					androidPush.setQuery(query);
					androidPush.sendInBackground();
					Toast.makeText(context, "you declined the task", Toast.LENGTH_SHORT).show();
					popup.dismiss();
					Intent intent = new Intent(BrowseActivity.this, BrowseActivity.class);
					startActivity(intent);
				}
				
			});
			//owner.setText(info.get("owner").toString());
			//time.setText(info.get("time").toString());
			//Log.e("dialog",info.get("owner") );
			



			popup.show();
	    }

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.browse, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent intent;
		switch (item.getItemId()) {

		case R.id.view:
			intent = new Intent(BrowseActivity.this, ProfileActivity.class);
			startActivity(intent);
			return true;
		case R.id.create:
			intent = new Intent(BrowseActivity.this, CreatetaskActivity.class);
			startActivity(intent);
			return true;
		case R.id.browse:
			intent = new Intent(BrowseActivity.this, BrowseActivity.class);
			startActivity(intent);
			return true;
		case R.id.home:
			intent = new Intent(BrowseActivity.this, DashboardActivity.class);
			startActivity(intent);
			return true;
		case R.id.logout:
			Constants.logout(BrowseActivity.this);

			return true;
		case R.id.restart:
			restart();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void restart()
	{
		if (Build.VERSION.SDK_INT >= 11) {
		    recreate();
		} else {
		    Intent intent = getIntent();
		    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    finish();
		    overridePendingTransition(0, 0);

		    startActivity(intent);
		    overridePendingTransition(0, 0);
		}
	}
}
