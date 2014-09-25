package com.aayao.todolist.helper;

import java.util.ArrayList;
import com.aayao.todolist.data.Item;


public class statsHelper
{
	private ArrayList<Item> archiveItems;
	private ArrayList<Item> toDoItems;
	
	public statsHelper (ArrayList<Item> archive, ArrayList<Item> todo){
		archiveItems = archive;
		toDoItems = todo;
	}
	
	private int calcStats(ArrayList<Item> al) {		
		int checked = 0;
		
		for (Item i: al) {
			if (i.getCheck()) {
				checked++;
			}
		}
		 return checked;
	}
	
	public String getStats(){
		String stats ="";
		stats += "Total # of TODO items checked: " + calcStats(toDoItems);
	    stats += "\nTotal # of TODO items unchecked: " + (toDoItems.size() - calcStats(toDoItems));
		stats += "\n\nTotal # of archived TODO items: " + archiveItems.size();
		stats += "\nTotal # of checked archived TODO items: " + calcStats(archiveItems);
		stats += "\nTotal # of unchecked archived TODO items: " + (archiveItems.size() - calcStats(archiveItems));
		
		return stats;
	}
}
