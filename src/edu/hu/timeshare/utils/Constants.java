package edu.hu.timeshare.utils;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.TimePicker;
import java.text.DateFormat;
import java.util.Date;

import com.parse.ParseUser;

import edu.hu.timeshare.DashboardActivity;
import edu.hu.timeshare.LoginActivity;

public class Constants {
	public static java.util.Date getDateTimeFromPickers(DatePicker datePicker,
			TimePicker timePicker) {
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year = datePicker.getYear();
		int hour = timePicker.getCurrentHour();
		int minute = timePicker.getCurrentMinute();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute);

		return calendar.getTime();
	}

	public static String formatDate(Date now) {
		return DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.SHORT).format(now);
	}
	public static void logout(final Activity a)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder.setMessage("Are you sure that you want to logout?")
           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
            	   ParseUser.logOut();
            	   Intent intent = new Intent(a,
   						LoginActivity.class);
            	   a.finish();
   				a.startActivity(intent);
               }
           })
           .setNegativeButton("No", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   
               }
           });
    // Create the AlertDialog object and return it
    AlertDialog popup = builder.create();
    popup.show();
	}

}
