package com.aayao.todolist.data;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.util.Log;

public class GsonTodo
{
	private Gson gson;
	private Context ctx;

	public GsonTodo(Context ctx) {
		this.ctx = ctx;
		gson = new Gson();
	}
	
	public ArrayList<Item> loadLists(String FILENAME) {
		ArrayList<Item> Lists = new ArrayList<Item>();

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(ctx.openFileInput(FILENAME)));
			String line;
			StringBuffer fileContent = new StringBuffer();

			while ((line = input.readLine()) != null) {
				fileContent.append(line);
			}

			Type collectionType = new TypeToken<Collection<Item>>() {}.getType();
			Lists = gson.fromJson(fileContent.toString(), collectionType);

		} catch (Exception e) {
			Log.i("ToDoList", "Error getting Items");
			e.printStackTrace();
		}

		return Lists;
	}

	public void saveLists(ArrayList<Item> todos, String FILENAME) {
		try {
			FileOutputStream fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);

			String jsonTodoList = gson.toJson(todos);
			fos.write(jsonTodoList.getBytes());
			fos.close();
			
			Log.i("Persistence", "Saved: " + jsonTodoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
