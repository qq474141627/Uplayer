package com.opar.mobile.uplayer.dao;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.opar.mobile.uplayer.db.DBHelper;
import com.opar.mobile.uplayer.db.TableName;

public class DBHelperDao {

	private DBHelper pipiDBHelp;
	public static DBHelperDao dbHelperDao;
	private Cursor cursor;
	private SQLiteDatabase database = null;
	public DBHelperDao() {
		pipiDBHelp = DBHelper.getInstance();
	}

	public synchronized static DBHelperDao getDBHelperDaoInstace() {

		if (dbHelperDao == null) {
			dbHelperDao = new DBHelperDao();
		}
		return dbHelperDao;
	}
	
	/*
	 * 关键词操作
	 */
	public synchronized long insertKeys(String key) {
		long sec = -1;
		try {
			ContentValues values = new ContentValues();
			values.put(TableName.KEY, key);
			database = pipiDBHelp.getWritableDatabase();
			String[] args = {String.valueOf(key)};
			database.delete(TableName.UPlayer_KEYS_TABLENAME, TableName.KEY+" = ?", args);
		//	delSingleKey(key);//避免重复
			sec = database.insert(TableName.UPlayer_KEYS_TABLENAME, null,
					values);
		}  catch (Exception e) {
			// TODO: handle exception
		}
		return sec;
	}
	public synchronized List<String> getKeys(int num) {

		List<String> keys = new ArrayList<String>();
		String sql = "select  *  from " + TableName.UPlayer_KEYS_TABLENAME
				+ " order by "+TableName.Movie_ID+" desc ";
		try {
			database = pipiDBHelp.getWritableDatabase();
			cursor = database.rawQuery(sql, null);
			if (cursor != null && cursor.getCount() != 0) {
				while (cursor.moveToNext()) {
					if(keys.size()<6){
						keys.add(cursor.getString(cursor
								.getColumnIndex(TableName.KEY)));
					}else{
						delSingleKey(cursor.getString(cursor
								.getColumnIndex(TableName.KEY)));
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeCursor();
		}
		
		return keys;
	}
	public synchronized void delAllKeys() {

		String sql = "delete from " + TableName.UPlayer_KEYS_TABLENAME;
		try {
			database = pipiDBHelp.getWritableDatabase();
			database.execSQL(sql);
		}  catch (Exception e) {
			// TODO: handle exception
		}
	}
	public synchronized void delSingleKey(String key) {

		String sql = "delete from " + TableName.UPlayer_KEYS_TABLENAME
				+ " where "+TableName.KEY+" = " + "'" + key + "'";
		int sec = -1;
		try {
			database = pipiDBHelp.getWritableDatabase();
			database.execSQL(sql);
		}  catch (Exception e) {
		}
	}
	
	
	private void closeDB(){
		if (database != null) {
			database.close();
			database = null;
		}
	}
	private void closeCursor(){
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
	}
    
}
