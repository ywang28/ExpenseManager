package com.myandroid.expensemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.myandroid.expensemanager.ExpenseContract.ExpenseTable;
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
    private static final String DATE_TYPE = " DATE";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ExpenseTable.TABLE_NAME + " (" +
                    ExpenseTable._ID + " INTEGER PRIMARY KEY," +
                    ExpenseTable.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                    ExpenseTable.COLUMN_NAME_AMOUNT + TEXT_TYPE + COMMA_SEP +
                    ExpenseTable.COLUMN_NAME_DATE + DATE_TYPE + COMMA_SEP +
                    ExpenseTable.COLUMN_NAME_LOCATION + TEXT_TYPE +
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
