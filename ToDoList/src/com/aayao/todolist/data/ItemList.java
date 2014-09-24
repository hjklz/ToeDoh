package com.aayao.todolist.data;
import java.util.ArrayList;

public class ItemList
{
	ArrayList <Item> items;
	
	public ItemList() {
		items = new ArrayList<Item>();
		items.add(new Item ("New Item"));
	}
}
