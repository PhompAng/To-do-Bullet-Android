package th.in.phompang.todobullet.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by พิชัย on 5/10/2558.
 */
public class SQLiteHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "todobullet";
    public static final String TABLE_USER = "user";
    public static final String TABLE_TASK = "task";
    public static final int DB_VERSION = 1;

    class Table_User {
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_EMAIL = "email";
        public static final String KEY_TOKEN = "token";
        public static final String KEY_CREATED_AT = "created_at";
    }

     class Table_Task {
         public static final String KEY_ID = "id";
         public static final String KEY_TITLE = "title";
         public static final String KEY_DES = "description";
         public static final String KEY_TYPE = "type";
         public static final String KEY_TIME = "time";
     }

    public SQLiteHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" +  Table_User.KEY_ID + " INTEGER PRIMARY KEY," + Table_User.KEY_NAME + " TEXT,"
                + Table_User.KEY_EMAIL + " TEXT UNIQUE," + Table_User.KEY_TOKEN + " TEXT,"
                + Table_User.KEY_CREATED_AT + " TEXT" + ")";
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "(" +  Table_Task.KEY_ID + " INTEGER PRIMARY KEY," + Table_Task.KEY_TITLE + " TEXT,"
                + Table_Task.KEY_DES + " TEXT," + Table_Task.KEY_TYPE + " INTEGER,"
                + Table_Task.KEY_TIME + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public void addUser(String name, String email, String token, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Table_User.KEY_NAME, name);
        values.put(Table_User.KEY_EMAIL, email);
        values.put(Table_User.KEY_TOKEN, token);
        values.put(Table_User.KEY_CREATED_AT, created_at);

        long id = db.insert(TABLE_USER, null, values);
        db.close();
    }

    public HashMap<String, String> getUserDeails() {
        HashMap<String, String> user = new HashMap<String, String>();

        String selectQuery = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("token", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();

        return user;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, null, null);
        db.close();
    }

    private ContentValues putContentValues(String title, String des, int type, String time) {
        ContentValues values = new ContentValues();
        values.put(Table_Task.KEY_TITLE, title);
        values.put(Table_Task.KEY_DES, des);
        values.put(Table_Task.KEY_TYPE, type);
        values.put(Table_Task.KEY_TIME, time);

        return values;
    }

    public long addTask(String title, String des, int type, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = putContentValues(title, des,type,time);

//        ContentValues values = new ContentValues();
//        values.put(Table_Task.KEY_TITLE, title);
//        values.put(Table_Task.KEY_DES, des);
//        values.put(Table_Task.KEY_TYPE, type);
//        values.put(Table_Task.KEY_TIME, time);

        long id = db.insert(TABLE_TASK, null, values);
        db.close();

        return id;
    }

    public int updateTask(String title, String des, int type, String time, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = putContentValues(title, des, type, time);

//        ContentValues values = new ContentValues();
//        values.put(Table_Task.KEY_TITLE, title);
//        values.put(Table_Task.KEY_DES, des);
//        values.put(Table_Task.KEY_TYPE, type);
//        values.put(Table_Task.KEY_TIME, time);

        int row = db.update(TABLE_TASK, values, Table_Task.KEY_ID + "=" + id, null);

        return row;
    }

    public ArrayList<HashMap<String, String>> getTaskDeails() {
        ArrayList<HashMap<String, String>> tasks = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            HashMap<String, String> task = new HashMap<String, String>();
            task.put("id", cursor.getString(0));
            task.put("title", cursor.getString(1));
            task.put("description", cursor.getString(2));
            task.put("type", cursor.getString(3));
            task.put("time", cursor.getString(4));
            tasks.add(task);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return tasks;
    }

    public void deleteTask() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TASK, null, null);
        db.close();
    }

    public void deleteTask(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(TABLE_TASK, Table_Task.KEY_ID + "=" + id, null);
        database.close();
    }
}
