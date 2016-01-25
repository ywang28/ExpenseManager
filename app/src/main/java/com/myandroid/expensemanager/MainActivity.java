package com.myandroid.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import static com.myandroid.expensemanager.ExpenseContract.ExpenseTable;
/**
 * Main activity to show "add expense" session and expense summary
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private ListView expenseListView;
    private ArrayList<String> expenses;
    private String totalExpense;
    private ArrayAdapter<String> adapter;
    private TextView totalExpenseView;
    private static final String FILE_NAME = "expenses.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenseListView = (ListView) findViewById(R.id.expense_list);
        expenses = new ArrayList<>();
        totalExpense = "0";

        // load all expenses from file
        readAllExpenses();
        totalExpenseView = (TextView) findViewById(R.id.total_expense);
        totalExpenseView.setText(totalExpense);

        // set up list view adapter
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                expenses);
        expenseListView.setAdapter(adapter);

        // set up item long click listener
        // long click on an expense item will remove it
        expenseListView.setOnItemLongClickListener(this);
    }

    /**
     * Load all expense from res raw file expenses.txt
     */
    private void readAllExpensesRaw() {
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.expenses));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            expenses.add(line);
        }
        scan.close();
    }

    /**
     * Load all expense from local file expenses.txt
     */
    private void readAllExpenses() {

        try {
            Scanner scan = new Scanner(openFileInput(FILE_NAME));
            if (scan.hasNextLine()) {
                totalExpense = scan.nextLine();
            }
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                expenses.add(line);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add expense entry when "Add expense" button clicked
     * @param view
     */
    public void addExpense(View view) {
        EditText input = (EditText) findViewById(R.id.input);
        String currExpense = input.getText().toString();

        // do nothing with empty expense
        if (TextUtils.isEmpty(currExpense)) {
            return;
        }

        // find out current selected category
        RadioGroup group = (RadioGroup) findViewById(R.id.cat_group);
        int catId = group.getCheckedRadioButtonId();

        RadioButton button = (RadioButton) findViewById(catId);
        String category = button.getText().toString();

        // find out date
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());

        // find out location
        String location = "somewhere";

        String expenseString = genExpenseString(currExpense, category, location, date);

        expenses.add(expenseString);
        adapter.notifyDataSetChanged();

        // addExpenseToDB(category, newExpense );

        updateTotalExpense(currExpense);
        input.setText("");
    }

    private void updateTotalExpense(String currExpense) {
        // update total expense
        float existingExpense = Float.valueOf(totalExpense);
        float newExpense = Float.valueOf(currExpense);
        totalExpense = String.format("%.2f", existingExpense + newExpense);
        totalExpenseView.setText(totalExpense);

        saveExpenseToFile();
    }

    /**
     * save expenses to local file
     */
    private void saveExpenseToFile() {
        FileOutputStream outputStream;
        String output = TextUtils.join("\n", expenses);
        output = totalExpense + "\n" + output;

        try {
            outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(output.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate a string to show expense information
     * @param currExpense
     * @param category
     * @param location
     * @param date
     * @return
     */
    private String genExpenseString(String currExpense,
                                    String category,
                                    String location,
                                    String date) {
        return String.format("$%s on %s category at %s",
                currExpense,
                category,
                date
        );
    }

    /**
     * Add expense entry to database
     * @param category
     * @param expense
     * @param date
     * @param place
     */
    private void addExpenseToDB(String category, String expense, String date, String place) {
        // start to set up database
        ExpenseDbHelper dbHelper = new ExpenseDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExpenseTable.COLUMN_NAME_CATEGORY, category);
        values.put(ExpenseTable.COLUMN_NAME_AMOUNT, expense);
        values.put(ExpenseTable.COLUMN_NAME_DATE, date);
        values.put(ExpenseTable.COLUMN_NAME_LOCATION, "places");

        long newRowId;
        newRowId = db.insert(
                ExpenseTable.TABLE_NAME,
                ExpenseTable.COLUMN_NAME_LOCATION,
                values);
    }

    /**
     * Long click to remove the item
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return true if item is successfully removed
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String currExpense = expenses.get(position).split(" ")[0].replace("$", "");
        expenses.remove(position);
        adapter.notifyDataSetChanged();
        updateTotalExpense("-" + currExpense);
        return true;
    }
}
