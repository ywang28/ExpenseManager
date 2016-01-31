package com.myandroid.expensemanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        TextView timeView = (TextView) activity.findViewById(R.id.expense_date);
        TextView amountView = (TextView) activity.findViewById(R.id.expense_amount);
        TextView categoryView = (TextView) activity.findViewById(R.id.expense_category);

        timeView.setText(intent.getStringExtra("time"));
        amountView.setText(intent.getStringExtra("amount"));
        categoryView.setText(intent.getStringExtra("category"));
    }
}
