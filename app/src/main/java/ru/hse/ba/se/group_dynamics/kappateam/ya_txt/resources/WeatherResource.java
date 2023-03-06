package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherResource {

    private final String TAG = "WeatherResource";

    public WeatherResource() {
    }

    /**
     * Достаём из актуальных данных показатель влажности
     * @return показатель влажности
     * @throws ResourceNotAvailableException если полученные данные некорректны
     */
    public int getCurrentHumidity() throws ResourceNotAvailableException {
        try {
            Log.i(TAG, "[getCurrentHumidity] Getting server data, parsing humidity from JSONObject");
            JSONObject jObj = getWeatherInfo();
            JSONObject mainObj = getObject("main", jObj);

            return getInt("humidity", mainObj);
        } catch (JSONException ex) {
            throw new ResourceNotAvailableException("An error occurred: unable to parse data from server");
        }
    }

    /**
     * Достаём из актуальных данных показатель температуры
     * @return показатель температуры
     * @throws ResourceNotAvailableException если полученные данные некорректны
     */
    public double getCurrentTemperature() throws ResourceNotAvailableException {
        try {
            Log.i(TAG, "[getCurrentTemperature] Getting server data, parsing temperature from JSONObject");
            JSONObject jObj = getWeatherInfo();
            JSONObject mainObj = getObject("main", jObj);
            double fahrenheit  = getFloat("temp", mainObj);
            return (fahrenheit - 32) * 5.0/9.0;
        } catch (JSONException ignored) {
            throw new ResourceNotAvailableException("An error occurred: unable to parse data from server");
        }
    }

    /**
     * Достаём из актуальных данных описание погоды
     * @return тип погоды
     * @throws ResourceNotAvailableException если полученные данные некорректны
     */
    public String getWeatherType() throws ResourceNotAvailableException {
        try {
            Log.i(TAG, "[getWeatherType] Getting server data, parsing weather type from JSONObject");
            JSONObject jObj = getWeatherInfo();
            JSONArray jArr = jObj.getJSONArray("weather");
            JSONObject JSONWeather = jArr.getJSONObject(0);
            return getString("description", JSONWeather);
        } catch (JSONException ignored) {
            throw new ResourceNotAvailableException("An error occurred: unable to parse data from server");
        }
    }

    /**
     * Достаём из актуальных данных показатель давления
     * @return тип погоды
     * @throws ResourceNotAvailableException если полученные данные некорректны
     */
    public int getCurrentPressure() throws ResourceNotAvailableException {
        try {
            Log.i(TAG, "[getCurrentPressure] Getting server data, parsing pressure from JSONObject");
            JSONObject jObj = getWeatherInfo();
            JSONObject mainObj = getObject("main", jObj);
            return getInt("pressure", mainObj);
        } catch (JSONException ignored) {
            throw new ResourceNotAvailableException("An error occurred: unable to parse data from server");
        }
    }

    /**
     * Парсер полученной информации по тэгу в объект
     * @param tagName тэг
     * @param jObj объект, откуда достаём
     * @return необходимая информация
     * @throws JSONException если по какой-то причине парсинг не происходит корректно
     */
    private JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        Log.i(TAG, "[getObject] Searching " + tagName + "in weather server data");
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    /**
     * Парсер полученной информации по тэгу в строку
     * @param tagName тэг
     * @param jObj объект, откуда достаём
     * @return необходимая информация
     * @throws JSONException если по какой-то причине парсинг не происходит корректно
     */
    private String getString(String tagName, JSONObject jObj) throws JSONException {
        Log.i(TAG, "[getString] Searching " + tagName + "in weather server data");
        String data = jObj.getString(tagName);
        Log.i(TAG, "[getString] Found: " + data);
        return data;
    }

    /**
     * Парсер полученной информации по тэгу в число с плавающей запятой
     * @param tagName тэг
     * @param jObj объект, откуда достаём
     * @return необходимая информация
     * @throws JSONException если по какой-то причине парсинг не происходит корректно
     */
    private float getFloat(String tagName, JSONObject jObj) throws JSONException {
        float data = (float) jObj.getDouble(tagName);
        Log.i(TAG, "[getFloat] Found: " + data);
        return data;
    }

    /**
     * Парсер полученной информации по тэгу в целое число
     * @param tagName тэг
     * @param jObj объект, откуда достаём
     * @return необходимая информация
     * @throws JSONException если по какой-то причине парсинг не происходит корректно
     */
    private int getInt(String tagName, JSONObject jObj) throws JSONException {
        Log.i(TAG, "[getInt] Searching " + tagName + "in weather server data");
        int data = jObj.getInt(tagName);
        Log.i(TAG, "[getInt] Found: " + data);
        return data;
    }

    /**
     * Получение актуальных данных по погоде в данной локации
     * @return данные, полученные с сервера
     * @throws ResourceNotAvailableException в случае ошибки получения данных
     */
    private String retrieveInfo() throws ResourceNotAvailableException {
        final String functionName = "[retrieveInfo]";
        HttpURLConnection con = null;
        InputStream is = null;
        String API = "13aaf21161a716c501f251269e9f978e";
        String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?lat=";
        String LAT = Resources.INSTANCE.geoLocation.getLatitude().toString();
        String LON = Resources.INSTANCE.geoLocation.getLongitude().toString();

        try {
            Log.i(TAG, functionName + " Opening connection to openweathermap.org");
            con = (HttpURLConnection) (new URL(BASE_URL + LAT + "&lon=" + LON + "&units=metric&appid=" + API)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            Log.i(TAG, functionName + " Reading data from opened connection");
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null)
                buffer.append(line).append("rn");
            Log.i(TAG, functionName + " Data read successfully");

            is.close();
            con.disconnect();
            Log.i(TAG, functionName + " Disconnected from server");
            // Log.i(TAG, functionName + " Data received:" + buffer.toString());
            return buffer.toString();
        } catch (Throwable t) {
            throw new ResourceNotAvailableException("Error reading data from server.");
        } finally {
            try {
                assert is != null;
                is.close();
            } catch (Throwable t) {
                Log.i(TAG, functionName + " [Finally block] Error closing input stream: " + t.getMessage());
            }
            try {
                assert con != null;
                con.disconnect();
            } catch (Throwable t) {
                Log.i(TAG, functionName + " [Finally block] Error closing connection: " + t.getMessage());
            }
        }
    }

    /**
     * Перевод полученных данных в объект json
     * @return погодные данные в виде объекта
     * @throws JSONException в случае неудачной попытки перевоад в json
     * @throws ResourceNotAvailableException в случае ошибки получения данных с сервера
     */
    private JSONObject getWeatherInfo() throws JSONException, ResourceNotAvailableException {
        Log.i(TAG, "[getWeatherInfo] Parsing server data to JSONObject");
        return  new JSONObject(retrieveInfo());
    }
}