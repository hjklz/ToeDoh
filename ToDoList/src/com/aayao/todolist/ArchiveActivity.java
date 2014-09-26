package com.aayao.todolist;

import java.util.ArrayList;

import com.aayao.todo.R;
import com.aayao.todolist.data.GsonTodo;
import com.aayao.todolist.data.Item;
import com.aayao.todolist.extension.itemClick;
import com.aayao.todolist.extension.listArrayAdapter;
import com.aayao.todolist.helper.itemListHelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ArchiveActivity extends Activity
{
	private ListView archiveList;
	private itemListHelper itemList;
	
	private GsonTodo dataManager;
	
	private int contextMenuParentID; //holds the parent of context menu (which item caused the menu to open)
	
	private ArrayList<Item> archiveItems;
	private ArrayList<Item> toDoItems;
	private ArrayAdapter<Item> aa;
	
	private static final String FILENAME1 = "todo.sav";
	private static final String FILENAME2 = "arch.sav";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.setTitle("Archived Items");
		setContentView(R.layout.archive_main);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		archiveList = (ListView)findViewById(R.id.archiveList);
		registerForContextMenu(archiveList);
		
		dataManager = new GsonTodo(this);
	}

	@Override
	protected void onStart() {
		super.onStart();

		toDoItems = dataManager.loadLists(FILENAME1);
		archiveItems = dataManager.loadLists(FILENAME2);
		
		// Set custom array adapter as the ListView's adapter.
		aa = new listArrayAdapter(this, archiveItems);
		archiveList.setOnItemClickListener(new itemClick(aa));	
		archiveList.setAdapter(aa);
		
		itemList = new itemListHelper(this, aa);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		dataManager.saveLists(toDoItems, FILENAME1);
		dataManager.saveLists(archiveItems, FILENAME2);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.item_menu, menu);
		
		menu.getItem(2).setTitle("Unarchive");
		
		if (v.getId() == R.id.archiveList) {
		    AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
		    contextMenuParentID = acmi.position;
		}
	}
	

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle().equals("Edit")) {
			itemList.editItem(contextMenuParentID, archiveList);
			return true;
	    } else if (item.getTitle().equals("Delete")) {
	    	itemList.deleteItem(contextMenuParentID, archiveItems);
	    	return true;
	    } else if (item.getTitle().equals("Unarchive")) {
	    	itemList.unarchiveItem(contextMenuParentID, archiveItems, toDoItems, archiveList);
	    	return true;
	    } else if (item.getTitle().equals("Email")) {
	    	itemList.emailItem(contextMenuParentID, archiveList);
	    	return true;
	    } else {
	        return false;
	    }		
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
}
