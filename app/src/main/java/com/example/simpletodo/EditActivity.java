package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText etItem;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etItem = findViewById(R.id.etItem);
        btnSave = findViewById(R.id.btnSave);

        getSupportActionBar().setTitle("Edit Item");

        etItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create intent
                Intent intent = new Intent();
                if(etItem.getText().toString().length() > 0) {
                    //pass data
                    intent.putExtra(MainActivity.KEY_ITEM_TEXT, etItem.getText().toString());
                    intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                    //set result
                    setResult(RESULT_OK, intent);
                    //return to main screen
                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "Item updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}