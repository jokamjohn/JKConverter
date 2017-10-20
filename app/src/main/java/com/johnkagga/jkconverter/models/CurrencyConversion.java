package com.johnkagga.jkconverter.models;


public class CurrencyConversion {

    private int mCoinImage;
    private String mCurrencySymbol;
    private String mCurrencyValue;

    public CurrencyConversion(int coinImage, String currencySymbol, String currencyValue) {
        mCoinImage = coinImage;
        mCurrencySymbol = currencySymbol;
        mCurrencyValue = currencyValue;
    }

    public int getCoinImage() {
        return mCoinImage;
    }

    public String getCurrencySymbol() {
        return mCurrencySymbol;
    }

    public String getCurrencyValue() {
        return mCurrencyValue;
    }
}
