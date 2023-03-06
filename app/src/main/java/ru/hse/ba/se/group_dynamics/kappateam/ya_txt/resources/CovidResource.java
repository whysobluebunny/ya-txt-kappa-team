package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CovidResource {
    private final String TAG = "CovidResource";

    /**
     * Получение данных о новых случаях, смертях, выздоровевших в России
     * @return тройка чисел: случаи, смерти, выздоровевшие
     * @throws ResourceNotAvailableException если произошла ошибка перевода полученных данных в json, то есть получены неверные данные
     */
    public int[] getNewRussiaData() throws ResourceNotAvailableException {
        int[] data = new int[3];
        try {
            JSONObject jsonObj = getCovidData();

            Log.i(TAG, "[getNewRussiaData] Parsing general data to JSON.");

            JSONArray subObj = jsonObj.getJSONArray("Countries");
            for (int i = 0; i < subObj.length(); i++) {
                JSONObject jojo = subObj.getJSONObject(i);
                String jojoString = jojo.getString("Country");
                if (jojoString.equals("Russian Federation")) {
                    data[0] = jojo.getInt("NewConfirmed");
                    data[1] = jojo.getInt("NewDeaths");
                    data[2] = jojo.getInt("NewRecovered");
                }
            }
        } catch (JSONException ignored) {
            throw new ResourceNotAvailableException("Error parsing COVID stats to JSON");
        }
        return data;
    }

    /**
     * Получение данных о новых случаях, смертях, выздоровевших в Мире
     * @return тройка чисел: случаи, смерти, выздоровевшие
     * @throws ResourceNotAvailableException если произошла ошибка перевода полученных данных в json, то есть получены неверные данные
     */
    public int[] getNewWorldData() throws ResourceNotAvailableException {
        int[] data = new int[3];
        try {
            JSONObject jsonObj = getCovidData();

            Log.i(TAG, "[getNewWorldData] Parsing general data to JSON.");

            JSONObject jojo = jsonObj.getJSONObject("Global");
            data[0] = jojo.getInt("NewConfirmed");
            data[1] = jojo.getInt("NewDeaths");
            data[2] = jojo.getInt("NewRecovered");
        } catch (JSONException ignored) {
            throw new ResourceNotAvailableException("Error parsing COVID stats to JSON");
        }
        return data;
    }

    /**
     * Получение данных о всех случаях, смертях, выздоровевших в России
     * @return тройка чисел: случаи, смерти, выздоровевшие
     * @throws ResourceNotAvailableException если произошла ошибка перевода полученных данных в json, то есть получены неверные данные
     */
    public int[] getTotalRussiaData() throws ResourceNotAvailableException {
        int[] data = new int[3];
        try {
            JSONObject jsonObj = getCovidData();

            Log.i(TAG, "[getTotalRussiaData] Parsing general data to JSON.");

            JSONArray subObj = jsonObj.getJSONArray("Countries");
            for (int i = 0; i < subObj.length(); i++) {
                JSONObject jojo = subObj.getJSONObject(i);
                String jojoString = jojo.getString("Country");
                if (jojoString.equals("Russian Federation")) {
                    data[0] = jojo.getInt("TotalConfirmed");
                    data[1] = jojo.getInt("TotalDeaths");
                    data[2] = jojo.getInt("TotalRecovered");
                }
            }
        } catch (JSONException ignored) {
            throw new ResourceNotAvailableException("Error parsing COVID stats to JSON");
        }
        return data;
    }

    /**
     * Получение данных о всех случаях, смертях, выздоровевших в Мире
     * @return тройка чисел: случаи, смерти, выздоровевшие
     * @throws ResourceNotAvailableException если произошла ошибка перевода полученных данных в json, то есть получены неверные данные
     */
    public int[] getTotalWorldData() throws ResourceNotAvailableException {
        int[] data = new int[3];
        try {
            JSONObject jsonObj = getCovidData();

            Log.i(TAG, "[getTotalWorldData] Parsing general data to JSON.");

            JSONObject jojo = jsonObj.getJSONObject("Global");
            data[0] = jojo.getInt("TotalConfirmed");
            data[1] = jojo.getInt("TotalDeaths");
            data[2] = jojo.getInt("TotalRecovered");
        } catch (JSONException ignored) {
            throw new ResourceNotAvailableException("Error parsing COVID stats to JSON");
        }
        return data;
    }

    /**
     * Получение данных с удалённого ресурса
     * @return данные о случаях ковида по миру
     * @throws ResourceNotAvailableException если произошла ошибка чтения данных с ресурса
     */
    private JSONObject getCovidData(int tries_left) throws ResourceNotAvailableException {
        final String functionName = "[getCovidData]";
        JSONObject jsonObj;

        try {
        Log.i(TAG, functionName + " Trying to get Bitcoin Rate from https://blockchain.info/ticker");
        String content = readURL("https://api.covid19api.com/summary");
        Log.i(TAG, functionName + " Got data");

        jsonObj = new JSONObject(content);

        }
        catch (Exception ignored) {
            if (tries_left > 0) {
                try {
                    Thread.sleep(100);
                    return getCovidData(tries_left-1);
                } catch (InterruptedException e) {
                    throw new ResourceNotAvailableException("Error parsing COVID stats to JSON");
                }
            }
            else {
                throw new ResourceNotAvailableException("Error parsing COVID stats to JSON");
            }
        }

        return jsonObj;
    }

    private JSONObject getCovidData() throws ResourceNotAvailableException {
        return getCovidData(5);
    }

    /**
     * Метод построкового чтения данных с ресурса
     * @param requestURL откуда читать
     * @return все прочитанные строки
     * @throws ResourceNotAvailableException при ошибке чтения данных с ресурса (например, ресурс не доступен или установлен лимит на чтение)
     */
    private String readURL(String requestURL) throws ResourceNotAvailableException {
        Log.i(TAG, "[readURL] Reading strings from URL.");
        StringBuilder contentBuilder = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            URLConnection con = url.openConnection();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = in.readLine()) != null) {
                    contentBuilder.append(line).append("\r\n");
                }
            }
        } catch (IOException e) {
            throw new ResourceNotAvailableException(e.getMessage());
        }
        return contentBuilder.toString();
    }
}
