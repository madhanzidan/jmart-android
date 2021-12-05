package com.zidanJmartKD.jmart_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.zidanJmartKD.R;
import com.zidanJmartKD.jmart_android.model.ProductCategory;


public class FilterFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public static boolean filtered = false;
    public static String filterName;
    public static int lowestPriceFilter, highestPriceFilter;
    public static boolean newFilter, usedFilter;
    public static ProductCategory filterCategory;

    public FilterFragment() {}

    public static FilterFragment newInstance(String param1) {
        FilterFragment filterFragment = new FilterFragment();
        Bundle b = new Bundle();
        b.putString(ARG_PARAM1, param1);
        filterFragment.setArguments(b);
        return filterFragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.fragment_filter, container, false);
        View v = inflater.inflate(R.layout.fragment_filter, container, false);

        Button buttonApplyFilter = v.findViewById(R.id.buttonApplyFilter);
        Button buttonClearFilter = v.findViewById(R.id.buttonClearFilter);
        EditText inputNameFilter = v.findViewById(R.id.inputNameFilter);
        EditText inputLowestPriceFilter = v.findViewById(R.id.inputLowestPriceFilter);
        EditText inputHighestPriceFilter = v.findViewById(R.id.inputHighestPriceFilter);
        CheckBox checkBoxNewFilter = v.findViewById(R.id.checkBoxNewFilter);
        CheckBox checkBoxUsedFilter = v.findViewById(R.id.checkBoxUsedFilter);
        Spinner spinnerFilter = v.findViewById(R.id.spinnerFilter);

        //Clear form
        buttonClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    inputNameFilter.getText().clear();
                    inputLowestPriceFilter.getText().clear();
                    inputHighestPriceFilter.getText().clear();
                    checkBoxNewFilter.setChecked(false);
                    checkBoxUsedFilter.setChecked(false);
                    spinnerFilter.setSelection(0);
                    Toast.makeText(getActivity(), "Filter cleared", Toast.LENGTH_SHORT).show();
            }
        });

        //Apply filter
        buttonApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtered = true;
                if(inputNameFilter.getText().toString().isEmpty() || inputLowestPriceFilter.getText().toString().isEmpty() || inputHighestPriceFilter.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "All field should be filled", Toast.LENGTH_SHORT).show();
                }
                else {
                    filterName = inputNameFilter.getText().toString();
                    lowestPriceFilter = Integer.parseInt(inputLowestPriceFilter.getText().toString());
                    highestPriceFilter = Integer.parseInt(inputHighestPriceFilter.getText().toString());

                    if (checkBoxUsedFilter.isChecked())
                        usedFilter = true;
                    else
                        usedFilter = false;
                    if (checkBoxNewFilter.isChecked())
                        newFilter = true;
                    else
                        newFilter = false;
                    filterCategory = ProductCategory.valueOf(spinnerFilter.getSelectedItem().toString());
                    Toast.makeText(getActivity(), "Filter applied successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return v;
    }
}