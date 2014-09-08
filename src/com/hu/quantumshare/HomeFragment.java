package com.hu.quantumshare;

import com.parse.ParseUser;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
 

public class HomeFragment extends Fragment implements OnClickListener{
	Button viewProfile,createTask,browseTask;
	  public HomeFragment(){}
	    
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	  
	        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
	        Button logout = (Button)rootView.findViewById(R.id.logout);
	        logout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		            builder.setMessage("Are you sure that you want to logout?")
		               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   ParseUser.logOut();
		                	   Intent intent = new Intent(getActivity(),
		       						LoginActivity.class);
		       				startActivity(intent);
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
			});
	        viewProfile = (Button)rootView.findViewById(R.id.profile);
	        createTask = (Button)rootView.findViewById(R.id.createTask);
	        browseTask = (Button)rootView.findViewById(R.id.browseTask);
	        viewProfile.setOnClickListener(this);
	        createTask.setOnClickListener(this);
	        browseTask.setOnClickListener(this);
	        return rootView;
	    }

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == createTask)
			{
			Fragment fragment = new CreateTaskFragment();
			FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
			}
			else if(v == browseTask)
			{
			Fragment fragment = new BrowseFragment();
			FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
			}
			else
			{
			Fragment fragment = new ProfileFragment();
			FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
			}
		}
}
