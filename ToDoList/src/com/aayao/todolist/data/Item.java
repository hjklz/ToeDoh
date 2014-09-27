/*
    ToDoList
    Copyright (C) 2014  Andy Ao Yao
    
    a full notice is found at 
    https://github.com/hjklz/ToeDoh/blob/master/LICENSE
    
    NOTE: There was an experiment here with passing values with intents
    I determined using Gson would be simpler and easier
*/

package com.aayao.todolist.data;

//import android.os.Parcel;
//import android.os.Parcelable;


public class Item //implements Parcelable
{
	private String itemName;
	private boolean isChecked;
	
	public Item (String name){
		itemName = name;
		isChecked = false;
	}
	
	/*
	private Item (Parcel in) {
		itemName = in.readString();
		isChecked = in.readByte() != 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(itemName);
		dest.writeByte((byte) (isChecked ? 1 : 0));
	}
	
	public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
		public Item createFromParcel(Parcel in) {
		    return new Item(in);
		}
		
		public Item[] newArray(int size) {
		    return new Item[size];
		}
	};
	
	@Override
	public int describeContents()
	{
		return 0;
	}
	*/
	
	public String getName(){
		return itemName;
	}
	
	public void setName(String newName) {
		itemName = newName;
	}
	
	public boolean getCheck(){
		return isChecked;
	}
	
	public void setChecked(boolean checked) {  
	    isChecked = checked;  
	}  
	
	public boolean toggle() {
		isChecked = !isChecked;
		return isChecked;
	}

	
}
