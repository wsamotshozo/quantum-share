package com.hu.quantumshare;

import java.util.Set;

import com.hu.quantumshare.utilities.Tasks;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Application;
import android.util.Log;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		ParseObject.registerSubclass(Tasks.class);
		Parse.initialize(this, "bQi0vm88WutERgTC4HBbdyuM7JXlshXPY8ePL6N3", "CBcHwpRVr3I8Nn54YMKrp1G4TlOcHYVB1XfPf8AU");


		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
	    
		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);
		
		ParseACL.setDefaultACL(defaultACL, true);
		//setContentView(R.layout.main);
		/*context = getApplicationContext();
		ParseAnalytics.trackAppOpened(getIntent());
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		/*ParseUser user = new ParseUser();
		user.setUsername("my name");
		user.setPassword("my pass");
		user.setEmail("email@example.com");
		  
		// other fields can be set just like with ParseObject
		user.put("phone", "650-555-0000");
		  
		user.signUpInBackground(new SignUpCallback() {
		  public void done(ParseException e) {
		    if (e == null) {
		      // Hooray! Let them use the app now.
		    } else {
		      // Sign up didn't succeed. Look at the ParseException
		      // to figure out what went wrong
		    }
		  }
		});*/
		
		/*PushService.subscribe(context, "Giants", this.getClass());
		Set<String> setOfAllSubscriptions = PushService
				.getSubscriptions(context);
		PushService.setDefaultPushCallback(context, this.getClass());
		Log.i("subscriptions", setOfAllSubscriptions.toString());
		PushService.setDefaultPushCallback(context, MainActivity.class);
		/*ParseQuery pushQuery = ParseInstallation.getQuery();
		pushQuery.whereEqualTo("scores", );
		ParsePush push = new ParsePush();
		push.setChannel("Giants");
		push.setMessage("This works.");
		push.sendInBackground();*/
		//ParseQuery query = ParseInstallation.getQuery();
		 
		// Notification for Android users
		/*query.whereEqualTo("deviceType", "android");
		ParsePush androidPush = new ParsePush();
		androidPush.setMessage("Your suitcase has been filled with tiny robots!");
		androidPush.setQuery(query);
		androidPush.sendInBackground();*/
	}

}
