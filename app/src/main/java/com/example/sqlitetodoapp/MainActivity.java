package com.example.sqlitetodoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView todoListView;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private int idKhoa;
    private EditText edtTitle;
    private EditText edtContent;
    private ArrayList<TodoModel> todos;
    private TodoDBHelper db;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoListView = findViewById(R.id.listViewTodo);
        addButton = findViewById(R.id.buttonAddTodo);
        edtTitle = findViewById(R.id.editTextTitle);
        edtContent = findViewById(R.id.editTextContent);

        todos = new ArrayList<TodoModel>();

        loadTodos();

        adapter = new MyAdapter(MainActivity.this, todos);

        todoListView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString().trim();
                String content = edtContent.getText().toString().trim();

                if (title.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập title!", Toast.LENGTH_SHORT).show();
                } else if (content.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập content!", Toast.LENGTH_SHORT).show();
                } else {
                    TodoModel todo = new TodoModel(title, content);
                    if (db.insertTodo(todo)) {
                        todos.add(todo);
                        loadTodos();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        edtTitle.setText("");
                        edtContent.setText("");
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2409 && resultCode == RESULT_OK) {
            loadTodos();
            adapter.notifyDataSetChanged();
        }
    }

    private void loadTodos() {
        todos.clear();
        db = new TodoDBHelper(this);
        Cursor cursor = db.getTodo();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                todos.add(new TodoModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)));
            }
        }
    }
}