package com.johnkagga.jkconverter;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.johnkagga.jkconverter.adapter.CurrencyConversionAdapter;
import com.johnkagga.jkconverter.dialog.ConversionDialogFragment;
import com.johnkagga.jkconverter.models.CurrencyConversion;
import com.johnkagga.jkconverter.utility.Constants;
import com.johnkagga.jkconverter.utility.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements
        ConversionDialogFragment.OnCurrencyConversion {

    private static final String CURRENCY_DIALOG = "Currency_dialog";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ArrayList<CurrencyConversion> mConversionArrayList;
    private CurrencyConversionAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.currency_recycler_view);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        mProgressBar = findViewById(R.id.progressBar);

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
    public void onCurrencyAdded(final String coin, final String currency) {
        if (!Helper.isConnected(this)) {
            Toast.makeText(this, "No internet, try again", Toast.LENGTH_SHORT).show();
            return;
        }
        getCurrencyConversionFromAPI(coin, currency);
    }

    /**
     * Get the latest Coin and currency conversion rate from the Cryptocompare API
     * and add them to the CurrencyConversion ArrayList.
     *
     * @param coin     BTC or ETH Coins
     * @param currency Base Currency i.e USD, EUR
     */
    private void getCurrencyConversionFromAPI(final String coin, final String currency) {
        mProgressBar.setVisibility(View.VISIBLE);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.CRYPTOCOMPARE_BASE_API).newBuilder();
        urlBuilder.addQueryParameter(Constants.COIN_TYPE, Helper.getCoinShortName(coin))
                .addQueryParameter(Constants.BASE_CURRENCY_TO_CONVERT_TO, currency);
        Helper.convert(urlBuilder.build().toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                hideProgressBar();
                Log.e(LOG_TAG, "Crypto API call failed " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                hideProgressBar();
                if (response.isSuccessful()) {
                    JSONObject json;
                    try {
                        json = new JSONObject(response.body().string());
                        String baseCurrency = json.getString(currency);
                        addCurrencyConversionToArrayList(baseCurrency, coin, currency);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Adds the new currency conversion from the API to the Array Adapter.
     *
     * @param baseCurrency Currency conversion from the API.
     * @param coin         Crypto currency
     * @param currency     Normal currency
     */
    private void addCurrencyConversionToArrayList(final String baseCurrency, final String coin,
                                                  final String currency) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConversionArrayList.add(0, new CurrencyConversion(Helper.getCoinImage(coin),
                        Helper.getCurrencySymbol(currency),
                        String.valueOf(baseCurrency)));
                mAdapter.notifyDataSetChanged();
                handleEmptyAdapter();
            }
        });
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

    /**
     * Hide the progress Bar after the Http request
     */
    private void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

}
