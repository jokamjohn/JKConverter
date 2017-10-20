package com.johnkagga.jkconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnkagga.jkconverter.utility.Constants;

public class ConversionActivity extends AppCompatActivity {

    private float mCoinRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        setTitle(getString(R.string.conversion_activity_title));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView coinImage = findViewById(R.id.coin_image);
        final TextView currencySymbol = findViewById(R.id.currency_symbol);
        final EditText conversionEditText = findViewById(R.id.conversion_edit_text);
        Button conversionButton = findViewById(R.id.conversion_button);
        final TextView baseCurrencyTxView = findViewById(R.id.base_currency_value);

        Intent intent = getIntent();

        if (intent != null) {
            coinImage.setImageDrawable(ContextCompat.getDrawable(this,
                    intent.getIntExtra(Constants.COIN_IMAGE_ID, R.drawable.ic_error_outline)));
            String cSymbol = intent.getStringExtra(Constants.CURRENCY_SYMBOL);
            currencySymbol.setText(cSymbol);
            mCoinRate = intent.getFloatExtra(Constants.COIN_RATE, 0);
        }

        /*
         * Calculate the supplied currency value by the Coin rate.
         */
        conversionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conversionEditText.getText().toString().isEmpty()) {
                    conversionEditText.setError("Field cannot be empty");
                    return;
                }
                float baseCurrency = mCoinRate *
                        Float.parseFloat(conversionEditText.getText().toString());
                baseCurrencyTxView.setText(String.valueOf(baseCurrency));
                currencySymbol.setVisibility(View.VISIBLE);
                baseCurrencyTxView.setVisibility(View.VISIBLE);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
