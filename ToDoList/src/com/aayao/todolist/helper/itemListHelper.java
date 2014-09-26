package com.aayao.todolist.helper;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.aayao.todo.R;
import com.aayao.todolist.data.Item;


public class itemListHelper
{
	private Context c;
	private ArrayAdapter<Item> aa;
	
	
	public itemListHelper(Context c, ArrayAdapter<Item> aa){
		this.c = c;
		this.aa = aa;
	}
		
	public void addItem(String name, ArrayList<Item> items, EditText itemTxt) {
		if (name.length() > 0) {
			Toast.makeText(c, name + " added", Toast.LENGTH_SHORT).show();
			items.add(new Item(name));
			aa.notifyDataSetChanged();
			itemTxt.setText("");
		}
	}
	
	public void deleteItem(int itemId, ArrayList<Item> items) {
		if (itemId >= 0) {
			Item item = items.get(itemId);
			Toast.makeText(c, item.getName() + " deleted", Toast.LENGTH_SHORT).show();
			items.remove(itemId);
			aa.notifyDataSetChanged();
		}
	}
	
	public void editItem(int itemId, ListView itemList) {
		if (itemId >= 0) {
			final Item item = (Item)itemList.getItemAtPosition(itemId);
			
            // get prompts.xml view
            LayoutInflater layoutInflater = LayoutInflater.from(c);
 
            View promptView = layoutInflater.inflate(R.layout.edit_prompt, null);
 
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
 
            // set edit_prompt.xml to be the layout file of the alertdialog builder
            alertDialogBuilder.setView(promptView);
 
            final EditText input = (EditText) promptView.findViewById(R.id.userInput); 
            final Context con = c;
            final ArrayAdapter<Item> aa2 = aa;
            
            // setup a dialog window
            alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                    	item.setName(input.getText().toString());
                    	Toast.makeText(con, item.getName() + " edited", Toast.LENGTH_SHORT).show();
            			aa2.notifyDataSetChanged();
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
	
	public void archiveItem(int itemId, ArrayList<Item> archive, ArrayList<Item> items, ListView itemList) {
		if (itemId >= 0) {
			Item item = (Item)itemList.getItemAtPosition(itemId);
			Toast.makeText(c, item.getName() + " archived", Toast.LENGTH_SHORT).show();
			archive.add(items.remove(itemId));
			aa.notifyDataSetChanged();
		}
	}
	
	//this probably could be combined with archiveItem method, but I wanted to keep the toast for testing
	public void unarchiveItem(int itemId, ArrayList<Item> archive, ArrayList<Item> items, ListView itemList) {
		if (itemId >= 0) {
			Item item = (Item)itemList.getItemAtPosition(itemId);
			Toast.makeText(c, item.getName() + " unarchived", Toast.LENGTH_SHORT).show();
			items.add(archive.remove(itemId));
			aa.notifyDataSetChanged();
		}
	}
	
	public void emailItem(int itemId, ListView itemList) {
		if (itemId >= 0) {
			ArrayList<Item> al = new ArrayList<Item>();
			al.add((Item)itemList.getItemAtPosition(itemId));
			
			new emailHelper(c).send(al);
		}
	}
}
