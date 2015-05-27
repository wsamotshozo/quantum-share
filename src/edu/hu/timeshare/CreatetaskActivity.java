package edu.hu.timeshare;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import edu.hu.timeshare.utils.Constants;
import edu.hu.timeshare.utils.SoundPoolPlayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreatetaskActivity extends Activity implements OnClickListener {
	Context context;
	SharedPreferences prefs;
	Spinner needs;
	DatePicker date;
	TimePicker time;
	EditText place;
	EditText instructions;
	Button submit;
	Calendar c = Calendar.getInstance();
	private static final String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
	private Pattern pattern;
	private Matcher matcher;
	int state = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(getApplicationContext(),
				"bQi0vm88WutERgTC4HBbdyuM7JXlshXPY8ePL6N3",
				"CBcHwpRVr3I8Nn54YMKrp1G4TlOcHYVB1XfPf8AU");
		ParseInstallation.getCurrentInstallation().saveInBackground();
		setContentView(R.layout.activity_createtask);
		pattern = Pattern.compile(DATE_PATTERN);
		context = this;
		needs = (Spinner) findViewById(R.id.needs);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				context, R.array.needs, R.layout.spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(R.layout.spinner_item);
		// Apply the adapter to the spinner
		needs.setAdapter(adapter);

		date = (DatePicker) findViewById(R.id.datePicker);
		time = (TimePicker) findViewById(R.id.time);
		// time.setIs24HourView(false);
		place = (EditText) findViewById(R.id.place);
		instructions = (EditText) findViewById(R.id.instructions);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
	}

	public void onClick(View v) {
		Log.d("date", Constants.getDateTimeFromPickers(date, time)+"");
		toggleButtonState(submit);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		ParseQuery query = ParseInstallation.getQuery();
		PushService.setDefaultPushCallback(context, LoginActivity.class);
		// Notification for Android users
		query.whereEqualTo("deviceType", "android");
		ParsePush androidPush = new ParsePush();
		// JSONObject data = new JSONObject();
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			Log.e("Parse error", "parse could not find the current user");
		} else {
			// store the task in the database
			ParseObject task = new ParseObject("Task");
			task.put("owner", currentUser.getUsername());
			task.put("needs",
					needs.getItemAtPosition(needs.getSelectedItemPosition())
							.toString());
			/*
			 * if(time.getCurrentHour() >9 ) { task.put("time",
			 * time.getCurrentHour() + ":"+time.getCurrentMinute()); } else {
			 * task.put("time", "0"+time.getCurrentHour() +
			 * ":"+time.getCurrentMinute()); }
			 */
			/*if (time.getCurrentHour() == 0) {
				task.put("hour", "" + 12);
				task.put("minute", time.getCurrentMinute());
				task.put("time", "AM");
			} else if (time.getCurrentHour() > 13) {
				Integer num = (time.getCurrentHour() - 12);
				if (num > 9) {
					task.put("hour", "" + num);
				} else {
					task.put("hour", "0" + num);
				}
				task.put("minute", time.getCurrentMinute());
				task.put("time", "PM");
			} else if (time.getCurrentHour() > 9) {
				task.put("hour", "" + time.getCurrentHour());
				task.put("minute", time.getCurrentMinute());
				task.put("time", "AM");

			} else {
				task.put("hour", "0" + time.getCurrentHour());
				task.put("minute", time.getCurrentMinute());
				task.put("time", "AM");

			}*/
			task.put("time",Constants.getDateTimeFromPickers(date, time));
			task.put("place", place.getText().toString());
			task.put("taker", "no one");
			task.put("instructions", instructions.getText().toString());
			task.saveInBackground();
			// stops the push notification from being sent to the sender
			query.whereNotEqualTo("username", currentUser.getUsername());
			// sending push notification
			androidPush.setMessage(currentUser.getUsername() + " needs "
					+ task.get("needs") + " at " + time.getCurrentHour() + ":"
					+ time.getCurrentMinute());
			androidPush.setQuery(query);
			androidPush.sendInBackground();
			Log.d("Push notification", "task sent");
		}
		Toast message = Toast.makeText(context, "task sent", Toast.LENGTH_SHORT);
		View view = message.getView();
		view.setBackgroundResource(R.drawable.toast);
		TextView text = (TextView) view.findViewById(android.R.id.message);
		text.setTextSize(24);
		text.setTextColor(Color.BLACK);
		SoundPoolPlayer sound = new SoundPoolPlayer(this); 
		sound.playShortResource(R.raw.rocket);
		Vibrator shake = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
		 shake.vibrate(500);
		 View back = findViewById(R.id.container);
		 toggleBackState(back);
		message.show();
		sound.release();
	}

	public boolean validate(final String date) {

		matcher = pattern.matcher(date);

		if (matcher.matches()) {

			matcher.reset();

			if (matcher.find()) {

				String day = matcher.group(1);
				String month = matcher.group(2);
				int year = Integer.parseInt(matcher.group(3));

				if (day.equals("31")
						&& (month.equals("4") || month.equals("6")
								|| month.equals("9") || month.equals("11")
								|| month.equals("04") || month.equals("06") || month
									.equals("09"))) {
					return false; // only 1,3,5,7,8,10,12 has 31 days
				} else if (month.equals("2") || month.equals("02")) {
					// leap year
					if (year % 4 == 0) {
						if (day.equals("30") || day.equals("31")) {
							return false;
						} else {
							return true;
						}
					} else {
						if (day.equals("29") || day.equals("30")
								|| day.equals("31")) {
							return false;
						} else {
							return true;
						}
					}
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.create, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent intent;
		switch (item.getItemId()) {

		case R.id.view:
			intent = new Intent(CreatetaskActivity.this, ProfileActivity.class);
			startActivity(intent);
			return true;
		case R.id.create:
			intent = new Intent(CreatetaskActivity.this, CreatetaskActivity.class);
			startActivity(intent);
			return true;
		case R.id.browse:
			intent = new Intent(CreatetaskActivity.this, BrowseActivity.class);
			startActivity(intent);
			return true;
		case R.id.home:
			intent = new Intent(CreatetaskActivity.this, DashboardActivity.class);
			startActivity(intent);
			return true;
		case R.id.logout:
			Constants.logout(CreatetaskActivity.this);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	private void toggleButtonState(final Button button)
	{button.setVisibility(View.GONE);
	    new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	                button.setVisibility(View.VISIBLE);

	        }
	    }, 1000);
	}
	private void toggleBackState(final View button)
	{button.setBackgroundColor(Color.RED);
	    new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	                button.setBackgroundColor(Color.WHITE);

	        }
	    }, 500);
	}
}
