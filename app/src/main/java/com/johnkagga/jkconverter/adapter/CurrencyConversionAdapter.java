package com.johnkagga.jkconverter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnkagga.jkconverter.ConversionActivity;
import com.johnkagga.jkconverter.R;
import com.johnkagga.jkconverter.models.CurrencyConversion;
import com.johnkagga.jkconverter.utility.Constants;
import com.johnkagga.jkconverter.utility.Helper;

import java.util.ArrayList;


public class CurrencyConversionAdapter extends RecyclerView.Adapter<CurrencyConversionAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<CurrencyConversion> mCurrencyConversions;

    public CurrencyConversionAdapter(Context context, ArrayList<CurrencyConversion> currencyConversions) {
        mContext = context;
        mCurrencyConversions = currencyConversions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currency, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CurrencyConversion conversion = mCurrencyConversions.get(position);
        final int coinImage = conversion.getCoinImage();
        holder.mCoinImage.setImageDrawable(ContextCompat.getDrawable(mContext, coinImage));
        holder.mBaseCurrency.setText(Helper.formatNumbers(conversion.getCurrencyValue()));
        final String currencySymbol = conversion.getCurrencySymbol();
        holder.mCurrencySymbol.setText(currencySymbol);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ConversionActivity.class)
                        .putExtra(Constants.COIN_IMAGE_ID, coinImage)
                        .putExtra(Constants.CURRENCY_SYMBOL, currencySymbol)
                        .putExtra(Constants.COIN_RATE, Float.parseFloat(conversion.getCurrencyValue()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCurrencyConversions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mCoinImage;
        public TextView mCurrencySymbol;
        public TextView mBaseCurrency;

        public ViewHolder(View itemView) {
            super(itemView);
            mCoinImage = itemView.findViewById(R.id.coin_image);
            mCurrencySymbol = itemView.findViewById(R.id.currency_symbol);
            mBaseCurrency = itemView.findViewById(R.id.base_currency);
        }
    }
}
