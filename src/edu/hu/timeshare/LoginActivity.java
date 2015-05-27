package edu.hu.timeshare;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import edu.hu.timeshare.R.color;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Button loginbutton;
	Button signup;
	String usernametxt;
	String passwordtxt;
	EditText password;
	EditText username;
	Button fpassword;
	Context context;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// /ParseObject.registerSubclass(Tasks.class);
		Parse.initialize(getApplicationContext(),
				"bQi0vm88WutERgTC4HBbdyuM7JXlshXPY8ePL6N3",
				"CBcHwpRVr3I8Nn54YMKrp1G4TlOcHYVB1XfPf8AU");
		ParseInstallation.getCurrentInstallation().saveInBackground();
		// Get the view from main.xml
		setContentView(R.layout.activity_login);
		context = this;
		// Locate EditTexts in main.xml
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);

		// Locate Buttons in main.xml
		loginbutton = (Button) findViewById(R.id.login);
		signup = (Button) findViewById(R.id.signup);
		fpassword = (Button)findViewById(R.id.fpassword);

		// Login Button Click Listener
		loginbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// Retrieve the text entered from the EditText
				usernametxt = username.getText().toString();
				passwordtxt = password.getText().toString();

				// Send data to Parse.com for verification
				ParseUser.logInInBackground(usernametxt, passwordtxt,
						new LogInCallback() {
							public void done(ParseUser user, ParseException e) {
								if (user != null) {
									ParseInstallation installation = ParseInstallation
											.getCurrentInstallation();
									installation.put("username",
											user.getUsername());
									installation.saveInBackground();
									Intent intent = new Intent(
											LoginActivity.this,
											DashboardActivity.class);
									startActivity(intent);
									Toast.makeText(getApplicationContext(),
											"Successfully Logged in",
											Toast.LENGTH_LONG).show();
								} else {
									Toast.makeText(
											getApplicationContext(),
											"No such user password combination exists or you are not connected to the network",
											Toast.LENGTH_LONG).show();
								}
							}
						});
			}
		});
		// Sign up Button Click Listener
		signup.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,
						SignupActivity.class);
				startActivity(intent);
			}
		});
		fpassword.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				final Dialog popup = new Dialog(context);
				popup.setContentView(R.layout.dialog_forgot_password);
				popup.setTitle("Please enter your email");
				final EditText email = (EditText) popup.findViewById(R.id.email);
				Button enter = (Button) popup.findViewById(R.id.enter);
				enter.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ParseUser.requestPasswordResetInBackground(
								email.getEditableText().toString(),
								new RequestPasswordResetCallback() {
									public void done(ParseException e) {
										if (e == null) {
											// An email was successfully sent
											// with reset instructions.
											Toast toast = Toast.makeText(context, "Check your email for instructions", Toast.LENGTH_SHORT);
											TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
											v.setTextColor(color.counter_text_color);
											v.setBackgroundResource(R.color.counter_bg);
											toast.show();
										} else {
											// Something went wrong. Look at the
											// ParseException to see what's up.
											Toast toast = Toast.makeText(context, "An error occured with the email\n "+e.getMessage(), Toast.LENGTH_LONG);
											TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
											v.setTextColor(Color.RED);
											v.setBackgroundResource(R.color.counter_bg);
											toast.show();
										}
									}
								});
					popup.dismiss();
					}

				});
				popup.show();
			}
			
		});
		
	}/*
	public void onBackPressed() {
	    //do nothing
	}*/
	
}
