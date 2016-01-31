package com.myandroid.expensemanager;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialization code
        Activity activity = getActivity();
        Intent intent = activity.getIntent();

        String time = intent.getStringExtra("time");
        String amount = intent.getStringExtra("amount");
        String category = intent.getStringExtra("category");

        // if fragment is used in main landscape view, intent extras are null
        if (time == null) {
            time = "";
            amount = "";
            category = "";
        }

        setExpenseDetails(time, amount, category);
    }

    public void setExpenseDetails(String time, String amount, String category) {

        Activity activity = getActivity();

        TextView timeView = (TextView) activity.findViewById(R.id.expense_date);
        TextView amountView = (TextView) activity.findViewById(R.id.expense_amount);
        TextView categoryView = (TextView) activity.findViewById(R.id.expense_category);

        timeView.setText(time);
        amountView.setText(amount);
        categoryView.setText(category);
    }
}
