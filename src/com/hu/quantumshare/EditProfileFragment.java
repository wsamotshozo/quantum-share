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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.os.Build;

public class EditProfileFragment extends Fragment implements OnClickListener {

	EditText name;
	EditText email;
	EditText phone;
	Context context;
	ParseUser user = ParseUser.getCurrentUser();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity().getApplicationContext();
		View popup = inflater.inflate(R.layout.edit_profile,
				container, false);
		Button submit = ((Button) popup.findViewById(R.id.submit));
		name = (EditText) popup.findViewById(R.id.name);
		email = (EditText) popup.findViewById(R.id.email);
		phone = (EditText) popup.findViewById(R.id.phone);
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
		submit.setOnClickListener(this);

return popup;
	}

	@Override
	public void onClick(View v) {
		
		//Log.d("naem ", name.getText().toString());
		user.put("name", name.getText().toString());
		user.put("email", email.getText().toString());
		user.put("phone", phone.getText().toString());
		user.saveEventually();
	
		Toast.makeText(context, "data changed", Toast.LENGTH_SHORT).show();
	}

}
