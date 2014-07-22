package com.hu.quantumshare.utilities;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;






import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class Utilities {
		public static final String EXTRA_MESSAGE = "message";
	    public static final String PROPERTY_REG_ID = "registration_id";
	    
	    /**
	     * Substitute you own sender ID here. This is the project number you got
	     * from the API Console, as described in "Getting Started."
	     */
	    public static String SENDER_ID = "856601727444";

	    /**
	     * Tag used on log messages.
	     */
	    static final String TAG = "Quantum";

	    TextView mDisplay;
	    public static AtomicInteger msgId = new AtomicInteger();
	    public static SharedPreferences prefs;
	    Context context;

	    String regid;

	 /**
	  * @return Application's version code from the {@code PackageManager}.
	  */
	 static int getAppVersion(Context context) {
	     try {
	         PackageInfo packageInfo = context.getPackageManager()
	                 .getPackageInfo(context.getPackageName(), 0);
	         return packageInfo.versionCode;
	     } catch (NameNotFoundException e) {
	         // should never happen
	         throw new RuntimeException("Could not get package name: " + e);
	     }
	 }



}
