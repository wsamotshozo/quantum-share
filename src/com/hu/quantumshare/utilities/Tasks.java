package com.hu.quantumshare.utilities;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;
@ParseClassName("Task")
public class Tasks extends ParseObject{
	String need;
	String when;
	String where;
	String SpecialInstructions;
	String owner;
	String taker;
	public Tasks(){
		super();
		try{
		if(getString("owner")!=null)
		{
			owner = getString("owner");
		}
		if(getString("needs")!=null)
		{
			need = getString("needs");
		}
		if(getString("time")!=null)
		{
			when = getString("time");
		}
		if(getString("place")!=null)
		{
			where = getString("place");
		}
		if(getString("instructions")!=null)
		{
			SpecialInstructions = getString("instructions");
		}	
		}catch(NullPointerException e)
		{
			Log.e("task messed up",e.getMessage());
		}
	}
	

	@Override
	public String toString() {
		return owner + " needs " + need + " "+when +" " + where;
	}
}
