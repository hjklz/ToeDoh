package com.aayao.todolist;

import java.util.ArrayList;

import com.aayao.todo.R;
import com.aayao.todolist.data.GsonTodo;
import com.aayao.todolist.data.Item;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity implements View.OnClickListener	//, DialogInterface.OnClickListener
{
	private EditText itemTxt;
	private Button addItem;
	private ListView itemList;
	
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
		setContentView(R.layout.activity_main);
		itemTxt = (EditText)findViewById(R.id.itemTxt);
		addItem = (Button)findViewById(R.id.addItem);
		itemList = (ListView)findViewById(R.id.itemList);
		registerForContextMenu(itemList);
		dataManager = new GsonTodo(this);
		
		addItem.setOnClickListener(this);
		
		toDoItems = new ArrayList<Item>();
		archiveItems = new ArrayList<Item>();
		//aa = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, toDoItems);
		
		itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
		aa = new listArrayAdapter(this, toDoItems);
		itemList.setAdapter(aa);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		if (item.getTitle().equals("View Archived Items")) {
			//Toast.makeText(getApplicationContext(), "works", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, ArchiveActivity.class);
			startActivity(intent);
		} else if (item.getTitle().equals("Statistics")){
			displayStats(getStats());
		}
		
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.item_menu, menu);
		
		if (v.getId() == R.id.itemList) {
		    AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
		    contextMenuParentID = acmi.position;
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle().equals("Edit")) {
			editItem(contextMenuParentID);
			return true;
	    } else if (item.getTitle().equals("Delete")) {
	    	deleteItem(contextMenuParentID);
	    	return true;
	    } else if (item.getTitle().equals("Archive")) {
	    	archiveItem(contextMenuParentID);
	    	return true;
	    } else {
	        return false;
	    }		
	}
	
	@Override
	public void onClick(View v)
	{
		if (v == this.addItem) {
			this.addItem(this.itemTxt.getText().toString());
		}
	}

	private void addItem(String name) {
		if (name.length() > 0) {
			Toast.makeText(getApplicationContext(), name + " added", Toast.LENGTH_SHORT).show();
			this.toDoItems.add(new Item(name));
			this.aa.notifyDataSetChanged();
			this.itemTxt.setText("");
		}
	}
	
	private void deleteItem(int itemId) {
		if (itemId >= 0) {
			Item item = (Item)itemList.getItemAtPosition(itemId);
			Toast.makeText(getApplicationContext(), item.getName() + " deleted", Toast.LENGTH_SHORT).show();
			this.toDoItems.remove(itemId);
			aa.notifyDataSetChanged();
		}
	}
	
	private void editItem(int itemId) {
		if (itemId >= 0) {
			final Item item = (Item)itemList.getItemAtPosition(itemId);
			
            // get prompts.xml view
            LayoutInflater layoutInflater = LayoutInflater.from(this);
 
            View promptView = layoutInflater.inflate(R.layout.edit_prompt, null);
 
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
 
            // set edit_prompt.xml to be the layout file of the alertdialog builder
            alertDialogBuilder.setView(promptView);
 
            final EditText input = (EditText) promptView.findViewById(R.id.userInput);
 
            // setup a dialog window
            alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                    	item.setName(input.getText().toString());
                    	Toast.makeText(getApplicationContext(), item.getName() + " edited", Toast.LENGTH_SHORT).show();
            			aa.notifyDataSetChanged();            			
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
 
            // create an alert dialog
            AlertDialog alertD = alertDialogBuilder.create();
 
            alertD.show();
		}
	}
	
	private void archiveItem(int itemId) {
		if (itemId >= 0) {
			Item item = (Item)itemList.getItemAtPosition(itemId);
			Toast.makeText(getApplicationContext(), item.getName() + " archived", Toast.LENGTH_SHORT).show();
			this.archiveItems.add(this.toDoItems.remove(itemId));
			aa.notifyDataSetChanged();
		}
	}
	
	private String getStats() {
		String stats ="";
		int checked = 0, unchecked = 0;
		
		for (Item i: toDoItems) {
			if (i.getCheck()) {
				checked++;
			} else {
				unchecked++;
			}
		}
		
		stats += "Total # of TODO items checked: " + checked + "\nTotal # of TODO items unchecked: " + unchecked;
		
		checked = 0;
		unchecked = 0;
		
		for (Item i: archiveItems) {
			if (i.getCheck()) {
				checked++;
			} else {
				unchecked++;
			}
		}
		
		stats += "\n\nTotal # of archived TODO items: " + archiveItems.size();
		stats += "\nTotal # of checked archived TODO items: " + checked + "\nTotal # of unchecked archived TODO items: " + unchecked;
		
		return stats;
	}
	
	private void displayStats(String stats) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder
			.setTitle("Statistics")
			.setMessage(stats)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            })
			.setCancelable(true);
			
		builder.show();
	}
	
	/*
	@Override
	public void onClick(DialogInterface dialog, int item)
	{
		if (currentMenu == "hot") {
			this.addItem(hot[item]);
		}
		if (currentMenu == "wine") {
			this.addItem(wine[item]);
		}
		if (currentMenu == "beer") {
			this.addItem(beer[item]);
		}
		if (currentMenu == "cocktail") {
			this.addItem(cocktail[item]);
		}
		if (currentMenu == "can") {
			this.addItem(can[item]);
		}
		
	}
	*/

}
