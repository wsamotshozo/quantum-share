package edu.hu.timeshare;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import edu.hu.timeshare.utils.Constants;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity implements OnClickListener{
	Context context;
	SharedPreferences prefs;
	TextView name;
	TextView phone;
	TextView email;
	Button edit;
	ParseUser user = ParseUser.getCurrentUser();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(getApplicationContext(),
				"bQi0vm88WutERgTC4HBbdyuM7JXlshXPY8ePL6N3",
				"CBcHwpRVr3I8Nn54YMKrp1G4TlOcHYVB1XfPf8AU");
		ParseInstallation.getCurrentInstallation().saveInBackground();
		setContentView(R.layout.activity_profile);
		context = this;
		name = (TextView)findViewById(R.id.name);
		phone = (TextView)findViewById(R.id.phone);
		email = (TextView)findViewById(R.id.email);
		edit = (Button)findViewById(R.id.edit);
		edit.setOnClickListener(this);
		if(user.getString("name") != null)
		{
			name.setText(user.getString("name"));
		}
		if(user.getString("phone") != null)
		{
			phone.setText(user.getString("phone"));
		}
		if(user.getEmail() != null)
		{
			email.setText(user.getEmail());
		}
	}
	@Override
	public void onClick(View v) {
 
		// custom dialog
		final Dialog popup = new Dialog(context);
		popup.setContentView(R.layout.dialog_edit_profile);
		popup.setTitle("Edit Your Profile");


		Button submit = ((Button) popup.findViewById(R.id.submit));
		Button cancel = (Button)popup.findViewById(R.id.cancel);
		final EditText cname = (EditText) popup.findViewById(R.id.name);
		final EditText cemail = (EditText) popup.findViewById(R.id.email);
		final EditText cphone = (EditText) popup.findViewById(R.id.phone);
		if(user.getString("name") != null)
		{
			cname.setText(user.getString("name"));
		}
		if(user.getString("phone") != null)
		{
			cphone.setText(user.getString("phone"));
		}
		if(user.getEmail() != null)
		{
			cemail.setText(user.getEmail());
		}
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				//Log.d("naem ", name.getText().toString());
				user.put("name", cname.getText().toString());
				user.put("email", cemail.getText().toString());
				user.put("phone", cphone.getText().toString());
				name.setText(cname.getText().toString());
				email.setText(cemail.getText().toString());
				phone.setText(cphone.getText().toString());
				user.saveEventually();
			
				Toast.makeText(context, "data changed", Toast.LENGTH_SHORT).show();
				popup.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popup.dismiss();
			}
		});

		popup.show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent intent;
		switch (item.getItemId()) {

		case R.id.view:
			intent = new Intent(ProfileActivity.this, ProfileActivity.class);
			startActivity(intent);
			return true;
		case R.id.create:
			intent = new Intent(ProfileActivity.this, CreatetaskActivity.class);
			startActivity(intent);
			return true;
		case R.id.browse:
			intent = new Intent(ProfileActivity.this, BrowseActivity.class);
			startActivity(intent);
			return true;
		case R.id.home:
			intent = new Intent(ProfileActivity.this, DashboardActivity.class);
			startActivity(intent);
			return true;
		case R.id.logout:
			Constants.logout(ProfileActivity.this);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
