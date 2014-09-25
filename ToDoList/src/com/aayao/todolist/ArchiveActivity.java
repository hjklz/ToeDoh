package com.aayao.todolist;

import java.util.ArrayList;

import com.aayao.todo.R;
import com.aayao.todolist.data.GsonTodo;
import com.aayao.todolist.data.Item;

import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
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
		
		archiveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override  
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {  
		        Item item = aa.getItem(position);  
		        item.toggle();  
		        itemViewHolder viewHolder = (itemViewHolder)v.getTag();  
		        viewHolder.getCheckBox().setChecked(item.getCheck());  
		    }
		});
	}

	@Override
	protected void onStart() {
		super.onStart();

		toDoItems = dataManager.loadLists(FILENAME1);
		archiveItems = dataManager.loadLists(FILENAME2);
		
		// Set custom array adapter as the ListView's adapter.
		aa = new listArrayAdapter(this, archiveItems);
		archiveList.setAdapter(aa);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		dataManager.saveLists(toDoItems, FILENAME1);
		dataManager.saveLists(archiveItems, FILENAME2);
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.item_menu, menu);
		
		menu.getItem(2).setTitle("Unarchive");
		
		if (v.getId() == R.id.archiveList) {
		    AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
		    contextMenuParentID = acmi.position;
		    
		    //menu.add(contextMenuParent.getName());
		    //menu.add(String.valueOf(contextMenuParent.getCheck()));
		}
	}
	

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle().equals("Edit")) {
			itemList.editItem(this, contextMenuParentID, archiveList, aa);
			return true;
	    } else if (item.getTitle().equals("Delete")) {
	    	itemList.deleteItem(this, contextMenuParentID, archiveItems, aa);
	    	return true;
	    } else if (item.getTitle().equals("Unarchive")) {
	    	//itemList.unarchiveItem(contextMenuParentID);
	    	return true;
	    } else {
	        return false;
	    }		
	}

}
