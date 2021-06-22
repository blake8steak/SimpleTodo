package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    //data
    List<String> items;

    //UI elements
    Button addBtn;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize UI elems, instance vars
        addBtn = findViewById(R.id.addBtn);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //remove item from items
                items.remove(position);
                // notify adapter of removal
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Removed from list", Toast.LENGTH_SHORT).show();
                writeItems();
            }
        };
        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                //add to model (push to list)
                items.add(todoItem);
                // notify adapter of insertion (update screen)
                itemsAdapter.notifyItemInserted(items.size()-1);
                //clear text
                etItem.setText("");
                //toast!
                Toast.makeText(getApplicationContext(), "Added to list", Toast.LENGTH_SHORT).show();
                writeItems();
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    //read data from file
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    //write data to file
    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (Exception e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}