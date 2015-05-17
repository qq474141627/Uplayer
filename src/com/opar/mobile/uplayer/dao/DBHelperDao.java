package com.opar.mobile.uplayer.dao;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.opar.mobile.aplayer.util.UplayerConfig;
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
	public synchronized void insertSearchKey(String key) {
		try {
			ContentValues values = new ContentValues();
			values.put(TableName.KEY, key);
			database = pipiDBHelp.getWritableDatabase();
			String[] args = {String.valueOf(key)};
			database.delete(TableName.UPlayer_KEYS_TABLENAME, TableName.KEY+" = ?", args);
			database.insert(TableName.UPlayer_KEYS_TABLENAME, null,values);
		}  catch (Exception e) {
			// TODO: handle exception
		}
	}
	public synchronized List<String> getSearchKeys(int num) {

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
						delSearchKey(cursor.getString(cursor
								.getColumnIndex(TableName.KEY)));
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeCursor();
			closeDB();
		}
		
		return keys;
	}
	public synchronized void delSearchKeys() {
		String sql = "delete from " + TableName.UPlayer_KEYS_TABLENAME;
		try {
			database = pipiDBHelp.getWritableDatabase();
			database.execSQL(sql);
		}  catch (Exception e) {
		}finally{
			closeDB();
		}
	}
	public synchronized void delSearchKey(String key) {
		String sql = "delete from " + TableName.UPlayer_KEYS_TABLENAME
				+ " where "+TableName.KEY+" = " + "'" + key + "'";
		try {
			database = pipiDBHelp.getWritableDatabase();
			database.execSQL(sql);
		}  catch (Exception e) {
		}finally{
			closeDB();
		}
	}
	
	/*
	 * 收藏节目
	 */
	public synchronized void insertSaveKey(String key) {
		try {
			if(!isSaveKey(key)){
				ContentValues values = new ContentValues();
				values.put(TableName.KEY, key);
				database = pipiDBHelp.getWritableDatabase();
				database.insert(TableName.UPlayer_SAVE_TABLENAME, null,values);
			}
		}  catch (Exception e) {
			// TODO: handle exception
		}finally {
			closeDB();
		}
		
	}
	public synchronized List<String> getSaveKeys() {

		List<String> keys = new ArrayList<String>();
		String sql = "select  *  from " + TableName.UPlayer_SAVE_TABLENAME
				+ " order by "+TableName.Movie_ID+" desc ";
		try {
			database = pipiDBHelp.getWritableDatabase();
			cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				keys.add(cursor.getString(cursor
						.getColumnIndex(TableName.KEY)));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeCursor();
			closeDB();
		}
		
		return keys;
	}
	public synchronized boolean isSaveKey(String key) {
		String sql = "select  *  from " + TableName.UPlayer_SAVE_TABLENAME
				+ " where "+TableName.KEY+"= '" + key + "'";
		try {
			database = pipiDBHelp.getReadableDatabase();
			cursor = database.rawQuery(sql,null);
			if (cursor.moveToNext()) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeCursor();
			closeDB();
		}
		return false;
	}
	public synchronized void delSaveKeys() {
		String sql = "delete from " + TableName.UPlayer_SAVE_TABLENAME;
		try {
			database = pipiDBHelp.getWritableDatabase();
			database.execSQL(sql);
		}  catch (Exception e) {
		}finally{
			closeDB();
		}
	}
	public synchronized void delSaveKey(String key) {
		String sql = "delete from " + TableName.UPlayer_SAVE_TABLENAME
				+ " where "+TableName.KEY+" = " + "'" + key + "'";
		try {
			database = pipiDBHelp.getWritableDatabase();
			database.execSQL(sql);
		}  catch (Exception e) {
		}finally{
			closeDB();
		}
	}
	
	/*
	 * 历史播放
	 */
	public synchronized void insertHistroyKey(String key) {
		try {
			ContentValues values = new ContentValues();
			values.put(TableName.KEY, key);
			database = pipiDBHelp.getWritableDatabase();
			String[] args = {String.valueOf(key)};
			database.delete(TableName.UPlayer_HISTROY_TABLENAME, TableName.KEY+" = ?", args);
			database.insert(TableName.UPlayer_HISTROY_TABLENAME, null,
					values);
		}  catch (Exception e) {
			// TODO: handle exception
		}
	}
	public synchronized List<String> getHistroyKeys() {

		List<String> keys = new ArrayList<String>();
		String sql = "select  *  from " + TableName.UPlayer_HISTROY_TABLENAME
				+ " order by "+TableName.Movie_ID+" desc ";
		try {
			database = pipiDBHelp.getWritableDatabase();
			cursor = database.rawQuery(sql, null);
			if (cursor != null && cursor.getCount() != 0) {
				while (cursor.moveToNext()) {
					keys.add(cursor.getString(cursor
							.getColumnIndex(TableName.KEY)));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeCursor();
			closeDB();
		}
		
		return keys;
	}
	public synchronized void delHistroyKeys() {
		String sql = "delete from " + TableName.UPlayer_HISTROY_TABLENAME;
		try {
			database = pipiDBHelp.getWritableDatabase();
			database.execSQL(sql);
		}  catch (Exception e) {
		}finally{
			closeDB();
		}
	}
	public synchronized void delHistroyKey(String key) {
		String sql = "delete from " + TableName.UPlayer_HISTROY_TABLENAME
				+ " where "+TableName.KEY+" = " + "'" + key + "'";
		try {
			database = pipiDBHelp.getWritableDatabase();
			database.execSQL(sql);
		}  catch (Exception e) {
		}finally{
			closeDB();
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
