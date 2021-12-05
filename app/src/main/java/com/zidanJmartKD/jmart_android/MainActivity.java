package com.zidanJmartKD.jmart_android;

import static com.zidanJmartKD.jmart_android.FilterFragment.newInstance;
import static com.zidanJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zidanJmartKD.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Navigation Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //Apabila sebelum register store, add button akan invisible
        if(getLoggedAccount().store == null)
            menu.findItem(R.id.add_box).setVisible(false);
        else
            menu.findItem(R.id.add_box).setVisible(true);
        return true;
    }

    //Menentukan action untuk menu yang diklik

    private static final int NUM_PAGES = 2;
    //The pager widget, which handles animation and allows swiping horizontally to access previous and next wizard steps.
    public static ViewPager2 viewPager;
    // The pager adapter, which provides the pages to the view pager widget.
    private FragmentStateAdapter pagerAdapter;
    // Array of strings FOR TABS TITLES
    private String[] titles = new String[]{"Products", "Filter"};
// tab titles


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.mypager);
        pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        //inflate layout dari tab
        TabLayout tabLayout =(TabLayout) findViewById(R.id.tab_layout);
        //Mendisplay tab
        new TabLayoutMediator(tabLayout, viewPager,(tab, position) -> tab.setText(titles[position])).attach();


        //Inisiasi component
        Button buttonApplyFilter = findViewById(R.id.buttonApplyFilter);
        Button buttonClearFilter = findViewById(R.id.buttonClearFilter);
        EditText inputNameFilter = findViewById(R.id.inputNameFilter);
        EditText inputLowestPriceFilter = findViewById(R.id.inputLowestPriceFilter);
        EditText inputHighestPriceFilter = findViewById(R.id.inputHighestPriceFilter);
        CheckBox checkBoxNewFilter = findViewById(R.id.checkBoxNewFilter);
        CheckBox checkBoxUsedFilter = findViewById(R.id.checkBoxUsedFilter);
        Spinner spinnerFilter = findViewById(R.id.spinnerFilter);


    }

    private class MyPagerAdapter extends FragmentStateAdapter {
        public MyPagerAdapter(FragmentActivity fragmentActivity){
            super(fragmentActivity);
        }

        @Override
        public Fragment createFragment (int pos) {
            switch (pos) {
                case 0:
                    return ProductsFragment.newInstance("Products Fragment");
                case 1:
                    return FilterFragment.newInstance("Filter Fragment");
                default:
                    return ProductsFragment.newInstance("Products Fragment, Default");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_box:
                Intent toCreateProductPage = new Intent(MainActivity.this, CreateProductActivity.class);
                startActivity(toCreateProductPage);
                return true;
            case R.id.person:
                Intent toAboutMePage = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(toAboutMePage);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}