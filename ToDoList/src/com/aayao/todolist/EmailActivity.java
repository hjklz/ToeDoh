package com.aayao.todolist;

import java.util.ArrayList;

import com.aayao.todo.R;
import com.aayao.todolist.data.GsonTodo;
import com.aayao.todolist.data.Item;
import com.aayao.todolist.extension.listArrayAdapter;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EmailActivity extends Activity
{	
	private ListView emailList;
	
	private GsonTodo dataManager;
	
	private ArrayList<Item> emailItems;
	private ArrayAdapter<Item> aa;	
	
	private static final String FILENAME1 = "todo.sav";
	private static final String FILENAME2 = "arch.sav";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.setTitle("Select Email Items");
		
		setContentView(R.layout.email_main);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		emailList = (ListView)findViewById(R.id.emailList);
		dataManager = new GsonTodo(this);		
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (getIntent().getExtras().getBoolean("todo")) {
			emailItems = dataManager.loadLists(FILENAME1);
		} else {
			emailItems = dataManager.loadLists(FILENAME2);
		}
		
		for (Item i : emailItems) {
			i.setChecked(false);
		}
		
		aa = new listArrayAdapter(this, emailItems);
		emailList.setAdapter(aa);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email, menu);
		return true;
	}

	public void send(View v) 
	{
		
	}

	public void cancel(View v)
	{
		finish();
	}
		
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
