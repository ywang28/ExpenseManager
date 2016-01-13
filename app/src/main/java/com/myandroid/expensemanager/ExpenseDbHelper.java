package com.myandroid.expensemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 *
 * Expense database helper
 *
 * Created by ywang28 on 1/11/16.
 */
public class ExpenseDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Expense.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ExpenseContract.ExpenseTable.TABLE_NAME + " (" +
                    ExpenseContract.ExpenseTable._ID + " INTEGER PRIMARY KEY," +
                    ExpenseContract.ExpenseTable.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                    ExpenseContract.ExpenseTable.COLUMN_NAME_AMOUNT + TEXT_TYPE + COMMA_SEP +
                    ExpenseContract.ExpenseTable.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    ExpenseContract.ExpenseTable.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ExpenseContract.ExpenseTable.TABLE_NAME;

    public ExpenseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
