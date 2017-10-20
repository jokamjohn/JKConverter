package com.johnkagga.jkconverter;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.johnkagga.jkconverter.adapter.CurrencyConversionAdapter;
import com.johnkagga.jkconverter.dialog.ConversionDialogFragment;
import com.johnkagga.jkconverter.models.CurrencyConversion;
import com.johnkagga.jkconverter.utility.Helper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ConversionDialogFragment.OnCurrencyConversion {

    private static final String CURRENCY_DIALOG = "Currency_dialog";
    private ArrayList<CurrencyConversion> mConversionArrayList;
    private CurrencyConversionAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.currency_recycler_view);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConversionDialogFragment dialogFragment = ConversionDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), CURRENCY_DIALOG);
            }
        });

        mConversionArrayList = new ArrayList<>();

        mAdapter = new CurrencyConversionAdapter(this, mConversionArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleEmptyAdapter();
    }

    /**
     * Method to get coin and base currency the user has chosen.
     *
     * @param coin     Crypto Currency
     * @param currency Base Currency
     */
    @Override
    public void onCurrencyAdded(String coin, String currency) {
        mConversionArrayList.add(0, new CurrencyConversion(Helper.getCoinImage(coin),
                Helper.getCurrencySymbol(currency), "5628"));
        mAdapter.notifyDataSetChanged();
        handleEmptyAdapter();
    }

    /**
     * Toggle between the empty text view and recycler view.
     */
    private void handleEmptyAdapter() {
        TextView emptyTextView = findViewById(R.id.empty_view);
        if (mAdapter.getItemCount() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }
    }

}
