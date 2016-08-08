package com.example.common.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;


public class TemplateDao<T> {
	private String TAG = "TemplateDAO";
	private SQLiteOpenHelper dbHelper;
	private String tableName;
	private String idColumn;
	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public TemplateDao(SQLiteOpenHelper dbHelper) {
		this.dbHelper = dbHelper;
		this.clazz = ((Class<T>) ((java.lang.reflect.ParameterizedType) super
				.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		if (this.clazz.isAnnotationPresent(Table.class)) {
			Table table = (Table) this.clazz.getAnnotation(Table.class);
			this.tableName = table.name();
		}
		Field[] fields = this.clazz.getDeclaredFields();
		for (Field field : fields)
			if (field.isAnnotationPresent(Id.class)) {
				Column column = (Column) field.getAnnotation(Column.class);
				this.idColumn = column.name();
			}
	}

	public T get(int id) {
		System.out.println("get by " + this.idColumn);

		String selection = this.idColumn + " = ?";

		String[] selectionArgs = { Integer.toString(id) };
		System.out.println("id:" + id);
		System.out.println("where:" + selection);

		List<T> list = find(null, selection, selectionArgs, null, null, null,
				null);
		if ((list != null) && (list.size() > 0)) {
			return list.get(0);
		}
		return null;
	}

	public T get(String id) {
		System.out.println("get by " + this.idColumn);

		String selection = this.idColumn + " = ?";

		String[] selectionArgs = { id };
		System.out.println("id:" + id);
		System.out.println("where:" + selection);

		List<T> list = find(null, selection, selectionArgs, null, null, null,
				null);
		if ((list != null) && (list.size() > 0)) {
			return list.get(0);
		}
		return null;
	}

	public List<T> rawQuery(String sql, String[] selectionArgs) {
		List<T> list = new ArrayList<T>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			cursor = db.rawQuery(sql, selectionArgs);

			getListFromCursor(list, cursor);
		} catch (Exception e) {
			Log.d(this.TAG, "rawQuery from DB Exception.");
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		}

		return list;
	}

	public List<T> find() {
		return find(null, null, null, null, null, null, null);
	}

	public List<T> find(String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		List<T> list = new ArrayList<T>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			cursor = db.query(this.tableName, columns, selection,
					selectionArgs, groupBy, having, orderBy, limit);

			getListFromCursor(list, cursor);
		} catch (Exception e) {
			Log.d(this.TAG, "find from DB Exception");
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		}

		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	private void getListFromCursor(List<T> list, Cursor cursor)
			throws IllegalAccessException, InstantiationException {
		while (cursor.moveToNext()) {
			Object entity = this.clazz.newInstance();

			for (Field field : this.clazz.getDeclaredFields()) {
				Column column = null;
				if (field.isAnnotationPresent(Column.class)) {
					column = (Column) field.getAnnotation(Column.class);
					//输出列名
					
					
					field.setAccessible(true);
					Class fieldType = field.getType();

					if ((Integer.TYPE == fieldType)
							|| (Integer.class == fieldType)) {
						int fieldValue = cursor.getInt(cursor
								.getColumnIndex(column.name()));
						field.set(entity, Integer.valueOf(fieldValue));
					} else {
						if (String.class == fieldType) {
							String fieldValue = cursor.getString(cursor
									.getColumnIndex(column.name()));
							field.set(entity, fieldValue);
						} else if ((Long.TYPE == fieldType)
								|| (Long.class == fieldType)) {
							long fieldValue = cursor.getLong(cursor
									.getColumnIndex(column.name()));
							field.set(entity, Long.valueOf(fieldValue));
						} else if ((Float.TYPE == fieldType)
								|| (Float.class == fieldType)) {
							float fieldValue = cursor.getFloat(cursor
									.getColumnIndex(column.name()));
							field.set(entity, Float.valueOf(fieldValue));
						} else if ((Short.TYPE == fieldType)
								|| (Short.class == fieldType)) {
							short fieldValue = cursor.getShort(cursor
									.getColumnIndex(column.name()));
							field.set(entity, Short.valueOf(fieldValue));
						} else if ((Double.TYPE == fieldType)
								|| (Double.class == fieldType)) {
							double fieldValue = cursor.getDouble(cursor
									.getColumnIndex(column.name()));
							field.set(entity, Double.valueOf(fieldValue));
						} else if (Blob.class == fieldType) {
							byte[] fieldValue = cursor.getBlob(cursor
									.getColumnIndex(column.name()));
							field.set(entity, fieldValue);
						} else if (Character.TYPE == fieldType) {
							String fieldValue = cursor.getString(cursor
									.getColumnIndex(column.name()));
							if ((fieldValue != null)
									&& (fieldValue.length() > 0))
								field.set(entity,
										Character.valueOf(fieldValue.charAt(0)));
						}
					}
				}
			}

			list.add((T) entity);
		}
	}

	public long insert(T entity) {
		System.out.println("inset####################");
		SQLiteDatabase db = null;
		try {
			db = this.dbHelper.getWritableDatabase();

			ContentValues cv = new ContentValues();

			setContentValues(entity, cv, "create");

			long row = db.insert(this.tableName, null, cv);
			return row;
		} catch (Exception e) {
			Log.d(this.TAG, "insert into DB Exception.");
		} finally {
			if (db != null) {
				db.close();
			}
		}

		return 0L;
	}

	public void delete() {
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		db.delete(this.tableName, null, null);
		db.close();
	}

	public void delete(int id) {
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		String where = this.idColumn + " = ?";
		String[] whereValue = { Integer.toString(id) };
		System.out.println("delete where " + where + " " + whereValue);
		db.delete(this.tableName, where, whereValue);

		db.close();
	}

	public Boolean update(T entity) {
		System.out.println("update by " + this.idColumn);
		SQLiteDatabase db = null;
		try {
			db = this.dbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();

			setContentValues(entity, cv, "update");

			String where = this.idColumn + " = ?";
			int id = Integer.parseInt(cv.get(this.idColumn).toString());
			cv.remove(this.idColumn);

			System.out.println("id:" + id);
			System.out.println("where:" + where);

			String[] whereValue = { Integer.toString(id) };
			db.update(this.tableName, cv, where, whereValue);
		} catch (Exception e) {
			Log.d(this.TAG, "update DB Exception.");
			return false;
		} finally {
			if (db != null)
				db.close();
		}
		return true;
	}

	private void setContentValues(T entity, ContentValues cv, String type)
			throws IllegalAccessException {
		Field[] fields = this.clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!(field.isAnnotationPresent(Column.class))) {
				continue;
			}
			Column column = (Column) field.getAnnotation(Column.class);

			field.setAccessible(true);
			Object fieldValue = field.get(entity);
			if (fieldValue == null) {
				continue;
			}
			//自增长ID时不需要写入ID的值
			//if (("create".equals(type))
			//		&& (field.isAnnotationPresent(Id.class))) {
			//	continue;
			//}
			cv.put(column.name(), fieldValue.toString());
		}

			
	}

	public SQLiteDatabase getWritableDatabase() {
		return dbHelper.getWritableDatabase();
	}

	public SQLiteDatabase getReadableDatabase() {
		return dbHelper.getReadableDatabase();
	}
}