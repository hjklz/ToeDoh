/*
    ToDoList
    Copyright (C) 2014  Andy Ao Yao
    
    a full notice is found at 
    https://github.com/hjklz/ToeDoh/blob/master/LICENSE
*/

package com.aayao.todolist.helper;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.aayao.todolist.data.Item;


public class emailHelper
{
	private Intent intent;
	private Context c;
	
	public emailHelper(Context c) {
		this.c = c;
		
		intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Item(s) from ToDoList");
	}
	
	private String getItems(ArrayList<Item> items) {
		String text = "";
		
		for (Item i : items) {
			text += i.getName() + "\t isChecked? " + String.valueOf(i.getCheck() + "\n");
		}
		
		return text;
	}	
	
	
	// send and sendAll adapted from stack overflow user fiXedd
	// http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application
	public void send(ArrayList<Item> items) {
		if (items.isEmpty()) {
			return;
		}
		
		intent.putExtra(Intent.EXTRA_TEXT, getItems(items));
		
		try {
		    c.startActivity(Intent.createChooser(intent, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(c, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void sendAll(ArrayList<Item> items, ArrayList<Item> items2) {
		String text = "Non Archived Items: \n\n";
		text += getItems(items);
		text += "Archived Items: \n\n";
		text += getItems(items2);
		
		intent.putExtra(Intent.EXTRA_TEXT, text);
		
		try {
		    c.startActivity(Intent.createChooser(intent, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(c, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
