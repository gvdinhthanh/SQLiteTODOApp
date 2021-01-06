package com.example.sqlitetodoapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditTodoActivity extends AppCompatActivity {

    private EditText edtTitle;
    private EditText edtContent;
    private Button btnUpdate;
    private TodoDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_edit_todo);

        db = new TodoDBHelper(this);

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnUpdate = findViewById(R.id.btnUpdate);

        Intent myIntent = getIntent();
        int id = myIntent.getIntExtra("id", -1);
        String title = myIntent.getStringExtra("title");
        String content = myIntent.getStringExtra("content");
        edtTitle.setText(title);
        edtContent.setText(content);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString().trim();
                String content = edtContent.getText().toString().trim();
                Boolean result = db.updateTodo(new TodoModel(id, title, content));
                if (result == true) {
                    Toast.makeText(EditTodoActivity.this, "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(EditTodoActivity.this, "Thất bại, vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}