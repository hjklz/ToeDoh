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
