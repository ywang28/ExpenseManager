package com.myandroid.expensemanager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.myandroid.expensemanager.ExpenseContract.ExpenseTable;
/**
 * Main activity to show "add expense" session and expense summary
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Add expense entry
     * @param view
     */
    public void addExpense(View view) {
        TextView totalExpense = (TextView) findViewById(R.id.total_expense);
        EditText input = (EditText) findViewById(R.id.input);

        // find out current selected category
        RadioGroup group = (RadioGroup) findViewById(R.id.cat_group);
        int catId = group.getCheckedRadioButtonId();

        RadioButton button = (RadioButton) findViewById(catId);
        String category = button.getText().toString();

        // start to set up database
        ExpenseDbHelper dbHelper = new ExpenseDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // get current date time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put(ExpenseTable.COLUMN_NAME_CATEGORY, category);
        values.put(ExpenseTable.COLUMN_NAME_AMOUNT, input.getText().toString());
        values.put(ExpenseTable.COLUMN_NAME_DATE, dateFormat.format(date));
        values.put(ExpenseTable.COLUMN_NAME_LOCATION, "places");

        long newRowId;
        newRowId = db.insert(
                ExpenseTable.TABLE_NAME,
                ExpenseTable.COLUMN_NAME_LOCATION,
                values);

        float existingExpense = Float.valueOf(totalExpense.getText().toString());
        float newExpense = Float.valueOf(input.getText().toString());

        totalExpense.setText(String.valueOf(existingExpense + newExpense));

        input.setText("");
    }
}
