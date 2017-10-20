package com.johnkagga.jkconverter.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnkagga.jkconverter.R;
import com.johnkagga.jkconverter.models.CurrencyConversion;

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
        CurrencyConversion conversion = mCurrencyConversions.get(position);
        holder.mCoinImage.setImageDrawable(ContextCompat.getDrawable(mContext, conversion.getCoinImage()));
        holder.mBaseCurrency.setText(conversion.getCurrencyValue());
        holder.mCurrencySymbol.setText(conversion.getCurrencySymbol());
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
