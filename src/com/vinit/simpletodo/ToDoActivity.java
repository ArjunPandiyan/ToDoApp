package com.vinit.simpletodo;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ToDoActivity extends Activity {

	EditText etext;
	ArrayList<String> items;
	ArrayAdapter<String> itemAdapter;
	ListView lvitems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		etext = (EditText) findViewById(R.id.et_enteritem);

		lvitems = (ListView) findViewById(R.id.lv_todolist);
		readItems();

		itemAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		lvitems.setAdapter(itemAdapter);
		items.add("First Item");
		items.add("Second Item");
		setupListViewListener();

	}

	private void setupListViewListener() {
		lvitems.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> aView, View item,
					int pos, long id) {
				items.remove(pos);
				itemAdapter.notifyDataSetInvalidated();
				saveItems();
				return true;
			}

		});

	}

	private void readItems() {
		File filesDir = getFilesDir();
		File toDoFile = new File(filesDir, "toDo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(toDoFile));
		} catch (Exception e) {
			items = new ArrayList<String>();
			e.printStackTrace();

		}

	}

	private void saveItems() {
		File filesDir = getFilesDir();
		File toDoFile = new File(filesDir, "toDo.txt");
		try {
			FileUtils.writeLines(toDoFile, items);
		} catch (Exception e) {
			items = new ArrayList<String>();
			e.printStackTrace();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
		return true;
	}

	public void onClick(View v) {
		// Get the entry added in EditTextBox
		String addedEntry = etext.getText().toString();
		// If it's not null then proceed.
		if (addedEntry.isEmpty()) {
			// Do Nothing
		} else {

			Toast.makeText(this, "added - " + addedEntry, Toast.LENGTH_SHORT)
					.show();
			etext.setText("");
			this.items.add(addedEntry);
			saveItems();

		}
	}

}
