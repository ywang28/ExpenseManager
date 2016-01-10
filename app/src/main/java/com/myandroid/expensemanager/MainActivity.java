package com.myandroid.expensemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addExpense(View view) {
        TextView totalExpense = (TextView) findViewById(R.id.total_expense);
        EditText input = (EditText) findViewById(R.id.input);

        float existingExpense = Float.valueOf(totalExpense.getText().toString());
        float newExpense = Float.valueOf(input.getText().toString());

        totalExpense.setText(String.valueOf(existingExpense + newExpense));

        input.setText("");
    }
}
