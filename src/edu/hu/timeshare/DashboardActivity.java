package edu.hu.timeshare;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import edu.hu.timeshare.utils.Constants;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;


public class DashboardActivity extends Activity {
	ImageButton view,create,browse,logout,contact;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(getApplicationContext(),
				"bQi0vm88WutERgTC4HBbdyuM7JXlshXPY8ePL6N3",
				"CBcHwpRVr3I8Nn54YMKrp1G4TlOcHYVB1XfPf8AU");
		ParseInstallation.getCurrentInstallation().saveInBackground();
		setContentView(R.layout.activity_dashboard);
		context = this;
		create = (ImageButton)findViewById(R.id.create);
		view = (ImageButton)findViewById(R.id.profile);
		contact = (ImageButton)findViewById(R.id.contacts);
		logout = (ImageButton)findViewById(R.id.logout);
		logout.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				Constants.logout(DashboardActivity.this);
			}
		});
	}
	public void viewProfile(View v)
	{
		Intent intent = new Intent(DashboardActivity.this,
					ProfileActivity.class);
			startActivity(intent);
	}
	public void createTask(View v)
	{
		Intent intent = new Intent(DashboardActivity.this,
				CreatetaskActivity.class);
		startActivity(intent);
	}
	public void browseTask(View v)
	{
		Intent intent = new Intent(DashboardActivity.this,
				BrowseActivity.class);
		startActivity(intent);
	}
	public void viewContacts(View v)
	{
		Intent intent = new Intent(DashboardActivity.this,
				ContactActivity.class);
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent intent;
		switch (item.getItemId()) {

		case R.id.view:
			intent = new Intent(DashboardActivity.this, ProfileActivity.class);
			startActivity(intent);
			return true;
		case R.id.create:
			intent = new Intent(DashboardActivity.this, CreatetaskActivity.class);
			startActivity(intent);
			return true;
		case R.id.browse:
			intent = new Intent(DashboardActivity.this, BrowseActivity.class);
			startActivity(intent);
			return true;
		case R.id.home:
			intent = new Intent(DashboardActivity.this, DashboardActivity.class);
			startActivity(intent);
			return true;
		case R.id.logout:
			Constants.logout(DashboardActivity.this);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
