package edu.hu.timeshare;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ContactActivity extends ListActivity {
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    String[] values = new String[] { "Angelica Lee","Frankie Lee","Jonathan Lee", "Sharon Lee" };
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, values);
    setListAdapter(adapter);
  }
} 
