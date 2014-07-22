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

import android.app.Fragment;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class BrowseFragment extends Fragment {
	Context context;
	SharedPreferences prefs;
	ListView tasks;
	public BrowseFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity().getApplicationContext();
		View rootView = inflater.inflate(R.layout.fragment_browse,
				container, false);
		/*mAdapter = new TaskAdapter(this, new ArrayList<Task>());
		mListView.setAdapter(mAdapter);*/
		tasks = (ListView)rootView.findViewById(R.id.tasks);
		final TextView temp = (TextView)rootView.findViewById(R.id.text);
		ParseUser user = ParseUser.getCurrentUser();
		//ParseQuery<Tasks> query = ParseQuery.getQuery(Tasks.class);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
		
		query.whereEqualTo("taker", user.getUsername());
		
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stu
				String [] values = new String[objects.size()];
				for(int i =0; i < values.length; i++)
				{
					ParseObject temp = objects.get(i);
					values[i] = temp.getString("owner")+" needs"+
								temp.getString("needs") + "at " +
								temp.getString("time");
					System.out.println(values[i]);
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,values);
				tasks.setAdapter(adapter);
				
			}
			});
		
		return rootView;
	}
	private class StableArrayAdapter extends ArrayAdapter<Tasks> {

	    HashMap<Tasks, Integer> mIdMap = new HashMap<Tasks, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<Tasks> objects) {
	    	
	      super(context, textViewResourceId, objects);
	      Log.e("null",objects.size()+"");
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      Tasks item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }

}
