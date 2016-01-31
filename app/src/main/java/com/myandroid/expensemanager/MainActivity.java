package com.myandroid.expensemanager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * Main activity to show "add expense" session and expense summary
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    private ArrayList<String> expenses;
    private String totalExpense;
    private ArrayAdapter<String> adapter;
    private TextView totalExpenseView;
    private static final String FILE_NAME = "expenses.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView expenseListView = (ListView) findViewById(R.id.expense_list);
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

        // set up item click listener to show detail view
        expenseListView.setOnItemClickListener(this);
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

        String expenseString = genExpenseString(currExpense, category, date);

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
     * @param date
     * @return
     */
    private String genExpenseString(String currExpense,
                                    String category,
                                    String date) {
        return String.format("$%s on %s category at %s",
                currExpense,
                category,
                date
        );
    }

    private String[] getExpenseDetails(int position) {
        String[] expenseSplit = expenses.get(position).split(" ");
        return new String[]{
                "Time: " + expenseSplit[5] + " " + expenseSplit[6],
                "Amount: " + expenseSplit[0],
                "Category: " + expenseSplit[2]
        };

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] details = getExpenseDetails(position);

        // start new activity in portrait mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("time", details[0]);
            intent.putExtra("amount", details[1]);
            intent.putExtra("category", details[2]);
            startActivity(intent);
        }
        // update fragment in landscape mode
        else {
            DetailFragment frag = (DetailFragment) getFragmentManager().findFragmentById(R.id.detail_fragment);
            frag.setExpenseDetails(details[0], details[1], details[2]);
        }
    }
}
