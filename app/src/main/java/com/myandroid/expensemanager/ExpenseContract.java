package com.myandroid.expensemanager;

import android.provider.BaseColumns;

/**
 *
 * Expense database schema
 *
 * Created by ywang28 on 1/11/16.
 */
public class ExpenseContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ExpenseContract() {}

    /**
     * Expense table schema
     */
    public static abstract class ExpenseTable implements BaseColumns {
        public static final String TABLE_NAME = "expense";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_LOCATION = "location";
    }

}
