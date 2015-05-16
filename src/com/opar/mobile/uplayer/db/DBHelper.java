package com.opar.mobile.uplayer.db;

import com.opar.mobile.uplayer.MyApplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库
 * 
 * @author qiny
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	// 数据库版本号
	public static final int dbVersion = 1;

	public static DBHelper pipiDBHelp = null;

	public  final String CREATETABLE = "( "+TableName.Movie_ID+" integer primary key autoincrement,"+TableName.MovieID+" text,"
			+TableName.MovieName+" Text,"+TableName.MovieLink+" Text ,"
			+TableName.MovieThumbnail+" text, "+TableName.MovieCategory+" text, "
			+TableName.MovieDuration+"  Integer,"+TableName.MoviePosition+"  Integer"+ " );";
	public  final String CREATETABLE2 = "( "+TableName.Movie_ID+" integer primary key autoincrement,"+TableName.KEY+" text "
			+ " );";
	private final String CREATE_STORE_TABLE = "create table "
			+ TableName.UPlayer_Movie_TABLENAME
			+CREATETABLE;
	private final String CREATE_HISTROY_TABLE = "create table "
			+ TableName.UPlayer_HISTROY_TABLENAME
			+ CREATETABLE;
	private final String CREATE_SAVE_TABLE = "create table "
			+ TableName.UPlayer_SAVE_TABLENAME
			+CREATETABLE;
	//搜索关键词
	private final String CREATE_KEYS_TABLE = "create table "
			+ TableName.UPlayer_KEYS_TABLENAME
			+CREATETABLE2;
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DBHelper() {
		super(MyApplication.getContext(), TableName.UPlayerDBName, null, dbVersion);
	}

	/**
	 * 单例
	 * 
	 * @param context
	 * @return
	 */
	public synchronized static DBHelper getInstance() {

		if (pipiDBHelp == null) {
			pipiDBHelp = new DBHelper();
		}

		return pipiDBHelp;

	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		// TODO Auto-generated method stub
		sqLiteDatabase.execSQL(CREATE_STORE_TABLE);
		sqLiteDatabase.execSQL(CREATE_HISTROY_TABLE);
		sqLiteDatabase.execSQL(CREATE_SAVE_TABLE);
		sqLiteDatabase.execSQL(CREATE_KEYS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if(newVersion>oldVersion){
			//选择你想删除更新哪些表，可以保留部分表数据
			 db.execSQL("DROP TABLE IF EXISTS  "+TableName.UPlayer_HISTROY_TABLENAME);
			 db.execSQL("DROP TABLE IF EXISTS  "+TableName.UPlayer_SAVE_TABLENAME);
			 db.execSQL("DROP TABLE IF EXISTS  "+TableName.UPlayer_Movie_TABLENAME);
	         onCreate(db);
		}
	}
	
	 public boolean deleteDatabase(Context context) { 
		 return context.deleteDatabase(TableName.UPlayerDBName); 
	 }
}
