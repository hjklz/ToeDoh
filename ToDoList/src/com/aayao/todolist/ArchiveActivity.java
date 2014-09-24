package com.aayao.todolist;

import java.util.ArrayList;

import com.aayao.todo.R;
import com.aayao.todo.R.layout;
import com.aayao.todo.R.menu;
import com.aayao.todolist.data.Item;

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
	
	private int contextMenuParentID; //holds the parent of context menu (which item caused the menu to open)
	
	private ArrayList<Item> archiveItems;
	private ArrayAdapter<Item> aa;
	
	public final static String ARCHIVED = "com.aayao.todolist.ArchivedItems";
	
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
		
		archiveItems = getIntent().getParcelableArrayListExtra(ARCHIVED);
		
		archiveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override  
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {  
		        Item item = aa.getItem(position);  
		        item.toggle();  
		        itemViewHolder viewHolder = (itemViewHolder)v.getTag();  
		        viewHolder.getCheckBox().setChecked(item.getCheck());  
		    }
		});
		
		// Set custom array adapter as the ListView's adapter.
		aa = new listArrayAdapter(this, archiveItems);
		archiveList.setAdapter(aa);
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
		
		if (v.getId() == R.id.archiveList) {
		    AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
		    contextMenuParentID = acmi.position;
		    
		    //menu.add(contextMenuParent.getName());
		    //menu.add(String.valueOf(contextMenuParent.getCheck()));
		}
	}

}
