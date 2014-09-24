package com.aayao.todolist.data;


public class Item
{
	private String itemName;
	private boolean isChecked;
	
	public Item (String name){
		itemName = name;
		isChecked = false;
	}
	
	public String getName(){
		return itemName;
	}
	
	public void setName(String newName) {
		itemName = newName;
	}
	
	public boolean getCheck(){
		return isChecked;
	}
	
	public boolean toggle() {
		isChecked = !isChecked;
		return isChecked;
	}
}
