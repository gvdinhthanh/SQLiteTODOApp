package com.example.sqlitetodoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TodoDBHelper extends SQLiteOpenHelper {
    public TodoDBHelper(Context context) {
        super(context, "todos.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE todos(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE todos");
    }

    public Boolean insertTodo(TodoModel todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", todo.getTitle());
        contentValues.put("content", todo.getContent());
        long result = db.insert("todos", null, contentValues);
        return result != -1;
    }

    public Boolean updateTodo(TodoModel todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todos WHERE id=?", new String[] {String.valueOf(todo.getId())});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", todo.getTitle());
            contentValues.put("content", todo.getContent());
            long result = db.update("todos", contentValues, "id=?", new String[] {String.valueOf(todo.getId())});
            return result != -1;
        }
        return false;
    }

    public Boolean deleteTodo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todos WHERE id=?", new String[] {String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = db.delete("todos", "id=?", new String[] {String.valueOf(id)});
            return result != -1;
        }
        return false;
    }

    public Cursor getTodo() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM todos", null);
    }
}
