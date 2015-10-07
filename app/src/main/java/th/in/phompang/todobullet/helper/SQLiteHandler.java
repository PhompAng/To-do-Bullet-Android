package th.in.phompang.todobullet.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by พิชัย on 5/10/2558.
 */
public class SQLiteHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "todobullet";
    public static final String TABLE_USER = "user";
    public static final int DB_VERSION = 1;

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CRERATE_TABLE = "CREATE TABLE " + TABLE_USER + "(" +  KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_TOKEN + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CRERATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS " + TABLE_USER);

        onCreate(db);
    }

    public void addUser(String name, String email, String token, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_TOKEN, token);
        values.put(KEY_CREATED_AT, created_at);

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
}
