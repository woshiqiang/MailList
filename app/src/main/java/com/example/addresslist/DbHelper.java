package com.example.addresslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库工具类
 */
public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context ctx) {
        super(ctx, "AddressList", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        db.execSQL("CREATE TABLE if not exists address(id integer PRIMARY KEY autoincrement,"
                + "name text, phone text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static DbHelper dbManager;

    public static DbHelper getInstance(Context ctx) {
        if (dbManager == null) {
            synchronized (DbHelper.class) {
                if (dbManager == null) {
                    dbManager = new DbHelper(ctx);
                }
            }
        }
        return dbManager;
    }

    /**
     * 保存通讯录
     *
     * @param bean
     */
    public void saveAddress(AddressBean bean) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("INSERT INTO address(name, phone) VALUES ('" + bean.getName() + "', '" + bean.getPhone() + "')");
        }
    }

    /**
     * 删除通讯录
     *
     * @param id
     */
    public void deleteAddress(int id) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("DELETE FROM address WHERE id = " + id);
        }
    }

    /**
     * 批量删除通讯录
     *
     * @param ids 如： 1,2,3
     */
    public void deleteAddressByIds(String ids) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("DELETE FROM address WHERE id in(" + ids + ")");
        }
    }

    /**
     * 更新通讯录
     *
     * @param record
     */
    public void updateAddress(AddressBean record) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", record.getName());
        contentValues.put("phone", record.getPhone());
        if (db != null) {
            db.update("address", contentValues, "id = ?", new String[]{record.getId() + ""});
        }
    }

    /**
     * 获取所有通讯录
     *
     * @return
     */
    public List<AddressBean> getAllAddress() {
        List<AddressBean> records = new ArrayList<AddressBean>();
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM address", null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                AddressBean note = new AddressBean();
                note.setId(id);
                note.setName(name);
                note.setPhone(phone);
                records.add(note);
            }
            cursor.close();
        }
        return records;
    }

}
