/*
    ToDoList
    Copyright (C) 2014  Andy Ao Yao
    
    a full notice is found at 
    https://github.com/hjklz/ToeDoh/blob/master/LICENSE
*/

package com.aayao.todolist;

import java.util.ArrayList;

import com.aayao.todo.R;
import com.aayao.todolist.data.GsonTodo;
import com.aayao.todolist.data.Item;
import com.aayao.todolist.extension.itemClick;
import com.aayao.todolist.extension.listArrayAdapter;
import com.aayao.todolist.helper.emailHelper;
import com.aayao.todolist.helper.itemListHelper;
import com.aayao.todolist.helper.statsHelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity implements View.OnClickListener	//, DialogInterface.OnClickListener
{
	private EditText itemTxt;
	private Button addItem;
	
	private ListView toDoList;
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
		
		this.setTitle("To Do Items");
		
		setContentView(R.layout.activity_main);
		itemTxt = (EditText)findViewById(R.id.itemTxt);
		addItem = (Button)findViewById(R.id.addItem);
		
		toDoList = (ListView)findViewById(R.id.itemList);
		registerForContextMenu(toDoList);
		
		dataManager = new GsonTodo(this);
		
		addItem.setOnClickListener(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();

		toDoItems = dataManager.loadLists(FILENAME1);
		archiveItems = dataManager.loadLists(FILENAME2);
		
		// Set custom array adapter as the ListView's adapter.
		aa = new listArrayAdapter(this, toDoItems);
		toDoList.setOnItemClickListener(new itemClick(aa));	
		toDoList.setAdapter(aa);
		
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
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		if (item.getTitle().equals(getResources().getString(R.string.arch))) {
			//Toast.makeText(getApplicationContext(), "works", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, ArchiveActivity.class);
			startActivity(intent);
		} else if (item.getTitle().equals(getResources().getString(R.string.stats))){
			displayStats(new statsHelper(archiveItems, toDoItems).getStats());
		} else if (item.getTitle().equals(getResources().getString(R.string.emailTodo))){
			Intent intent = new Intent(this, EmailActivity.class);
			intent.putExtra("todo", true);
			startActivity(intent);
		} else if (item.getTitle().equals(getResources().getString(R.string.emailArch))){
			Intent intent = new Intent(this, EmailActivity.class);
			intent.putExtra("todo", false);
			startActivity(intent);
		} else if (item.getTitle().equals(getResources().getString(R.string.emailAll))){
			new emailHelper(this).sendAll(toDoItems, archiveItems);
		}
		
		return true;
	}
	
	//the following two context item methods were adapted from stack overflow user
	//PG_ Android
	//http://stackoverflow.com/questions/18632331/using-contextmenu-with-listview-in-android
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
		if (item.getTitle().equals(getResources().getString(R.string.edit))) {
			itemList.editItem(contextMenuParentID, toDoList);
			return true;
	    } else if (item.getTitle().equals(getResources().getString(R.string.del))) {
	    	itemList.deleteItem(contextMenuParentID, toDoItems);
	    	return true;
	    } else if (item.getTitle().equals(getResources().getString(R.string.archi))) {
	    	itemList.archiveItem(contextMenuParentID, archiveItems, toDoItems, toDoList);
	    	return true;
	    } else if (item.getTitle().equals(getResources().getString(R.string.email))) {
	    	itemList.emailItem(contextMenuParentID, toDoList);
	    	return true;
	    } else {
	        return false;
	    }		
	}
		
	@Override
	public void onClick(View v)
	{
		if (v == this.addItem) {
			itemList.addItem(this.itemTxt.getText().toString(), toDoItems, itemTxt);
		}
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
}
