package com.johnkagga.jkconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnkagga.jkconverter.utility.Constants;

public class ConversionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        setTitle(getString(R.string.conversion_activity_title));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView coinImage = findViewById(R.id.coin_image);
        TextView currencySymbol = findViewById(R.id.currency_symbol);
        EditText conversionEditText = findViewById(R.id.conversion_edit_text);
        Button conversionButton = findViewById(R.id.conversion_button);

        Intent intent = getIntent();

        if (intent != null) {
            coinImage.setImageDrawable(ContextCompat.getDrawable(this,
                    intent.getIntExtra(Constants.COIN_IMAGE_ID, R.drawable.ic_error_outline)));
            String cSymbol = intent.getStringExtra(Constants.CURRENCY_SYMBOL);
            currencySymbol.setText(cSymbol);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
