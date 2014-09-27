/*
    ToDoList
    Copyright (C) 2014  Andy Ao Yao
    
    a full notice is found at 
    https://github.com/hjklz/ToeDoh/blob/master/LICENSE
    
    syntax for listener below was adapted from 
    Andrew Lim Chong Liang 
    Copyright Andrew Lim Chong Liang
    
    http://windrealm.org/tutorials/android/listview-with-checkboxes-without-listactivity.php
*/
package com.aayao.todolist.extension;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.aayao.todolist.data.Item;

public class itemClick implements AdapterView.OnItemClickListener
{
	private ArrayAdapter<Item> aa;
	
	public itemClick(ArrayAdapter<Item> aa) {
		this.aa = aa;
	}
		
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {  
        Item item = aa.getItem(position);  
        item.toggle();  
        itemViewHolder viewHolder = (itemViewHolder)v.getTag();
        viewHolder.getCheckBox().setChecked(item.getCheck());
    }
}
