package com.aayao.todolist.extension;

import java.util.List;

import com.aayao.todo.R;
import com.aayao.todolist.data.Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


/** Custom adapter for displaying an array of Item objects. */  
public class listArrayAdapter extends ArrayAdapter<Item>
{
	private LayoutInflater inflater;  
      
    public listArrayAdapter(Context c, List<Item> itemList ) {  
      super(c, R.layout.simplerow, R.id.rowTextView, itemList);  
      // Cache the LayoutInflate to avoid asking for a new one each time.  
      inflater = LayoutInflater.from(c) ;  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
      // item to display  
      Item item = (Item)this.getItem(position);   
  
      // The child views in each row.  
      CheckBox checkBox;   
      TextView textView;   
        
      // Create a new row view  
      if ( convertView == null ) {  
        convertView = inflater.inflate(R.layout.simplerow, null);  
          
        // Find the child views.  
        textView = (TextView) convertView.findViewById(R.id.rowTextView);  
        checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);  
          
        // Optimization: Tag the row with it's child views, so we don't have to   
        // call findViewById() later when we reuse the row.  
        convertView.setTag(new itemViewHolder(textView,checkBox));  
  
        // If CheckBox is toggled, update the item it is tagged with.  
        checkBox.setOnClickListener( new View.OnClickListener() {  
          public void onClick(View v) {  
            CheckBox cb = (CheckBox) v ;  
            Item item = (Item) cb.getTag();  
            item.setChecked(cb.isChecked());  
          }  
        });          
      }  
      // Reuse existing row view  
      else {  
        // Because we use a ViewHolder, we avoid having to call findViewById().  
        itemViewHolder viewHolder = (itemViewHolder) convertView.getTag();  
        checkBox = viewHolder.getCheckBox() ;  
        textView = viewHolder.getTextView() ;  
      }  
  
      // Tag the CheckBox with the Item it is displaying, so that we can  
      // access the items in onClick() when the CheckBox is toggled.
      checkBox.setTag(item);   
        
      // Display items  
      checkBox.setChecked(item.getCheck());  
      textView.setText(item.getName());        
      
      return convertView;  
    }  
      
  } 
	

