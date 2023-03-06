package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BitcoinResource {

    private final String TAG = "BitcoinResource";

    /**
     * Получить текущий курс биткоина к рублю.
     * @return текущий курс биткоина к рублю.
     */
    public double getExchangeRate() throws ResourceNotAvailableException {
        return getExchangeRate(7);
    }

    private double getExchangeRate(int tries_last) throws ResourceNotAvailableException {
        final String functionName = "[getExchangeRate]";
        try {
            JSONObject jsonObj;
            double data;
            String content = null;

            Log.i(TAG, functionName + " Trying to get Bitcoin Rate from https://blockchain.info/ticker");
            try {
                content = readStringFromURL("https://blockchain.info/ticker");
            } catch (IOException ex) {
                throw new ResourceNotAvailableException("Error getting exchange rate data.");
            }

            Log.i(TAG, functionName + " Attempt to parse data to JSONObj");
            try {
                jsonObj = new JSONObject(content);
                JSONObject subObj = jsonObj.getJSONObject("RUB");
                data = subObj.getDouble("sell");
                Log.i(TAG, functionName + " Got data: " + data);
            } catch (JSONException ignored) {
                throw new ResourceNotAvailableException("Error parsing currency to JSON");
            }

            return data;
        }
        catch (Exception e) {
            if (tries_last > 0) {
                try {
                    Thread.sleep(100);
                    return getExchangeRate(tries_last - 1);
                } catch (InterruptedException k) {
                    throw new ResourceNotAvailableException("Error parsing COVID stats to JSON");
                }
            }
            else {
                throw new ResourceNotAvailableException("BTC Resource not available.");
            }
        }



    }

    /**
     * Cчитывает контент со страницы
     * @param requestURL url страницы, с которой считываются данные
     * @return контент страницы
     * @throws IOException при ошибке чтения данных
     */
    private String readStringFromURL(String requestURL) throws IOException
    {
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
                StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}
