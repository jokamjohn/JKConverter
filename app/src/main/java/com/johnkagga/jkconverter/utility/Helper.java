package com.johnkagga.jkconverter.utility;


import com.johnkagga.jkconverter.R;

import java.util.Currency;

public class Helper {

    private static final String BITCOIN = "BITCOIN";
    private static final String ETHEREUM = "ETHEREUM";

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
}
