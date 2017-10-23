package com.johnkagga.jkconverter.utility;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.johnkagga.jkconverter.R;

import java.text.DecimalFormat;
import java.util.Currency;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Helper {

    private static final String BITCOIN = "BITCOIN";
    private static final String ETHEREUM = "ETHEREUM";
    private static final String BTC = "BTC";
    private static final String ETH = "ETH";

    /**
     * Return the currency ISO symbol.
     *
     * @param currencyIso Currency ISO code
     * @return String
     */
    public static String getCurrencySymbol(String currencyIso) {
        Currency currency = Currency.getInstance(currencyIso);
        return currency.getSymbol();
    }

    /**
     * Return the correct coin Image
     *
     * @param coin Coin Name
     * @return int
     */
    public static int getCoinImage(String coin) {
        switch (coin) {
            case BITCOIN:
                return R.drawable.ic_bitcoin;
            case ETHEREUM:
                return R.drawable.ic_ethereum;
            default:
                return R.drawable.ic_error_outline;
        }
    }

    /**
     * @param coin Crypto coin
     * @return Coin short name
     */
    public static String getCoinShortName(String coin) {
        switch (coin) {
            case BITCOIN:
                return BTC;
            case ETHEREUM:
                return ETH;
            default:
                return null;
        }
    }

    /**
     * Sends an HTTP Get request to the Cryptocompare API.
     *
     * @param url      API URL
     * @param callback OKHTTP callback.
     */
    public static void convert(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Check network and internet connectivity.
     *
     * @return boolean
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Format the number by adding appropriate commas
     *
     * @param number Number
     * @return String
     */
    public static String formatNumbers(String number) {
        double amount = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return formatter.format(amount);
    }
}
