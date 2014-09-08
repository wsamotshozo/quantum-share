package com.hu.quantumshare;

import java.util.HashMap;
import java.util.List;

import com.hu.quantumshare.utilities.TaskArrayAdapter;
import com.hu.quantumshare.utilities.Tasks;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup.LayoutParams;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class BrowseFragment extends Fragment{
	Context context;
	SharedPreferences prefs;
	ListView tasks;
	TableLayout rootView;
	ProgressDialog dialog;

	public BrowseFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity().getApplicationContext();
		rootView = (TableLayout)inflater.inflate(R.layout.fragment_browse_table, container,
				false);
		dialog = new ProgressDialog(getActivity());
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

		// query.whereEqualTo("taker", user.getUsername());
		query.orderByDescending("time");
		query.setLimit(10);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				TableRow title = new TableRow(context);
				title.setGravity(Gravity.CENTER_HORIZONTAL);
				TextView towner = new TextView(context);
				setTitleStyle(towner);
				towner.setText("owner");
				TextView tneeds = new TextView(context);
				tneeds.setText("needs");
				setTitleStyle(tneeds);
				TextView ttime = new TextView(context);
				ttime.setText("time");
				setTitleStyle(ttime);
				TextView ttaker = new TextView(context);
				ttaker.setText("taker");
				setTitleStyle(ttaker);
				title.addView(towner);
				title.addView(tneeds);
				title.addView(ttime);
				title.addView(ttaker);
				rootView.addView(title);
				rootView.setClickable(true);
				for (int i = 0; i < 10; i++) {
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
					time.setText(info.getString("time"));
					setStyle(time);
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
		
		return rootView;
	}
	public void setTitleStyle(TextView a)
	{
		a.setPadding(10, 10, 10, 10);
		a.setTextColor(Color.rgb(18, 81, 35));
		a.setTextSize(18);
		a.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		//a.setShadowLayer(3, 1, 1, Color.rgb(81	, 35, 18));
		a.setBackgroundResource(R.drawable.border);
		a.setAllCaps(true);
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
			final Dialog popup = new Dialog(getActivity());
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
			time.setText("time : "+info.getString("time"));
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

					PushService.setDefaultPushCallback(context, TaskTakerActivity.class);
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
					Fragment fragment = new BrowseFragment();
					FragmentManager fragmentManager = getFragmentManager();
		            fragmentManager.beginTransaction()
		                    .replace(R.id.frame_container, fragment).commit();
					popup.dismiss();
				}
				
			});
			decline.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					ParseQuery query = ParseInstallation.getQuery();

					PushService.setDefaultPushCallback(context, TaskTakerActivity.class);
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
					Fragment fragment = new BrowseFragment();
					FragmentManager fragmentManager = getFragmentManager();
		            fragmentManager.beginTransaction()
		                    .replace(R.id.frame_container, fragment).commit();
					popup.dismiss();
				}
				
			});
			//owner.setText(info.get("owner").toString());
			//time.setText(info.get("time").toString());
			//Log.e("dialog",info.get("owner") );
			



			popup.show();
	    }

	}
}

