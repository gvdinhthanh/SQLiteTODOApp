package com.example.sqlitetodoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<TodoModel> todos;
    public MyAdapter(Context context, ArrayList<TodoModel> todos) {
        this.context = context;
        this.todos = todos;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_list_item, null);
        }

        TextView txtTitle = convertView.findViewById(R.id.textViewTitle);
        TextView txtContent = convertView.findViewById(R.id.textViewContent);
        Button btEdit = convertView.findViewById(R.id.btEdit);
        Button btDelete = convertView.findViewById(R.id.btDelete);
        txtTitle.setText(todos.get(position).getTitle());
        txtContent.setText(todos.get(position).getContent());

        TodoModel t = todos.get(position);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditTodoActivity.class);
                i.putExtra("id", t.getId());
                i.putExtra("title", t.getTitle());
                i.putExtra("content", t.getContent());
                ((Activity) context).startActivityForResult(i, 2409);
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoDBHelper db = new TodoDBHelper(context);
                Boolean result = db.deleteTodo(t.getId());
                if (result == true) {
                    Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    delete(position);
                } else {
                    Toast.makeText(context, "Thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    public void delete(int position){
        todos.remove(position);
        this.notifyDataSetChanged();
    }
}
