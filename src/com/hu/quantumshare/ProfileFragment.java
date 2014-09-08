package com.hu.quantumshare;

import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ProfileFragment extends Fragment implements OnClickListener{
	Context context;
	SharedPreferences prefs;
	TextView name;
	TextView phone;
	TextView email;
	Button edit;
	ParseUser user = ParseUser.getCurrentUser();
	public ProfileFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//context = getActivity().getApplicationContext();
		context = getActivity();
		View rootView = inflater.inflate(R.layout.fragment_profile,
				container, false);
		name = (TextView)rootView.findViewById(R.id.name);
		phone = (TextView)rootView.findViewById(R.id.phone);
		email = (TextView)rootView.findViewById(R.id.email);
		edit = (Button)rootView.findViewById(R.id.edit);
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
		return rootView;
	}

	@Override
	public void onClick(View v) {
 
		// custom dialog
		final Dialog popup = new Dialog(context);
		popup.setContentView(R.layout.edit_profile);
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

}
