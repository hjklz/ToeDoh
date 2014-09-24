package com.aayao.todolist;

import java.util.ArrayList;

import com.example.todolist.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity implements View.OnClickListener//, DialogInterface.OnClickListener
{
	private EditText Item;
	private Button addItem;
	private ListView itemList;
	
	private ArrayList<String> toDoItems;
	private ArrayAdapter<String> aa;
	
	/*
	String[] hot = {"Coffee", "Tea", "Hot Chocolate"};
	String[] wine = {"Burgundy", "Pinot", "Merlot"};
	String[] beer = {"Guiness", "Pedigree", "Artois"};
	String[] cocktail = {"Mary", "Vodka", "Colada"};
	String[] can = {"Coke", "Fanta", "Lemonade"};
	String currentMenu;*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Item = (EditText)findViewById(R.id.Item);
		addItem = (Button)findViewById(R.id.addItem);
		itemList = (ListView)findViewById(R.id.itemList);
		registerForContextMenu(itemList);
		
		addItem.setOnClickListener(this);
		
		toDoItems = new ArrayList<String>();
		aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
		itemList.setAdapter(aa);
		
		 itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		      @Override  
		      public void onItemClick(AdapterView<?> parent, View item, int position, long id) {  
		        String thing = aa.getItem(position);  
		        thing.toggleChecked();  
		        ItemViewHolder viewHolder = (ItemViewHolder)item.getTag();  
		        viewHolder.getCheckBox().setChecked(thing.isChecked());  
		      }
		 });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		/*
		menu.add("hot");
		menu.add("wine");
		menu.add("beer");
		menu.add("cocktail");
		menu.add("can");
		*/
		
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.item_menu, menu);
		
		if (v.getId() == R.id.itemList) {
		    ListView lv = (ListView) v;
		    AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
		    String obj = (String) lv.getItemAtPosition(acmi.position);

		    menu.add(obj);
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		return true;
	}
	
	/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		if (!item.hasSubMenu()){
			if (item.getTitle() == "hot") {
				currentMenu = "hot";
				this.displayPopup("Hot Drinks", this.hot);
			}
			if (item.getTitle() == "wine") {
				currentMenu = "wine";
				this.displayPopup("Wines", this.wine);
			}
			if (item.getTitle() == "beer") {
				currentMenu = "beer";
				this.displayPopup("Beers", this.beer);
			}
			if (item.getTitle() == "cocktail") {
				currentMenu = "cocktail";
				this.displayPopup("Cabinet", this.cocktail);
			}
			if (item.getTitle() == "can") {
				currentMenu = "can";
				this.displayPopup("Soft Drinks", this.can);
			}
		}
		return true;
	}
	*/

	private void addItem(String item) {
		if (item.length() > 0) {
			Toast.makeText(getApplicationContext(), item + " added", Toast.LENGTH_SHORT).show();
			this.toDoItems.add(item);
			this.aa.notifyDataSetChanged();
			this.Item.setText("");
		}
	}
	
	/*
	private void deleteItem(int itemId) {
		if (itemId >= 0) {
			String itemName = (String)itemList.getItemAtPosition(itemId);
			Toast.makeText(getApplicationContext(), itemName + " deleted", Toast.LENGTH_SHORT).show();
			this.toDoItems.remove(itemId);
			aa.notifyDataSetChanged();
		}
	}
	*/
		
	@Override
	public void onClick(View v)
	{
		if (v == this.addItem) {
			this.addItem(this.Item.getText().toString());
		}
		
	}
	
	/*	
	private void displayPopup(String title, String[] items) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setItems(items,  this);
		builder.show();
	}
	
	@Override
	public void onClick(DialogInterface dialog, int item)
	{
		if (currentMenu == "hot") {
			this.addItem(hot[item]);
		}
		if (currentMenu == "wine") {
			this.addItem(wine[item]);
		}
		if (currentMenu == "beer") {
			this.addItem(beer[item]);
		}
		if (currentMenu == "cocktail") {
			this.addItem(cocktail[item]);
		}
		if (currentMenu == "can") {
			this.addItem(can[item]);
		}
		
	}
	*/

}
