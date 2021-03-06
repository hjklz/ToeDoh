/*
    ToDoList
    Copyright (C) 2014  Andy Ao Yao
    
    a full notice is found at 
    https://github.com/hjklz/ToeDoh/blob/master/LICENSE
    
    entire class adapted from
    Andrew Lim Chong Liang 
    Copyright Andrew Lim Chong Liang
    
    http://windrealm.org/tutorials/android/listview-with-checkboxes-without-listactivity.php
*/

package com.aayao.todolist.extension;

import android.widget.CheckBox;
import android.widget.TextView;

/** Holds child views for one row. */  
public class itemViewHolder
{
	private CheckBox checkBox ;  
    private TextView textView ;  
    public itemViewHolder() {}  
    public itemViewHolder( TextView textView, CheckBox checkBox ) {  
      this.checkBox = checkBox ;  
      this.textView = textView ;  
    }  
    public CheckBox getCheckBox() {  
      return checkBox;  
    }  
    public void setCheckBox(CheckBox checkBox) {  
      this.checkBox = checkBox;  
    }  
    public TextView getTextView() {  
      return textView; 
    }  
    public void setTextView(TextView textView) {  
      this.textView = textView;  
    }    
}
