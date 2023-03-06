package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources;

import android.util.Log;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class ExchangeRateResource {

    private final String TAG = "ExchangeRateResource";

    public ExchangeRateResource() {
    }

    /**
     * Получить текущий курс рубля к доллару.
     * @return текущий курс рубля к доллару.
     */
    public String getCurrentExchangeRate() throws ResourceNotAvailableException {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date dateobj = new Date();

        Log.i(TAG, "[getCurrentExchangeRate] Reading current Exchange Rate from server");
        String data = Float.toString(Math.round(Float.parseFloat(getExchangeRate(df.format(dateobj)).replace(",", ".")) * 100.0f) / 100.0f);
        Log.i(TAG, "[getCurrentExchangeRate] Read Exchange Rate from server");
        return data;
    }

    /**
     * Проверить дневную динамику курса, чтобы проверить условия «Курс рубля к доллару понизился за последний день»
     * @return Курс рубля к доллару понизился за последний день
     */
    public boolean hasDecreasedInOneDay() throws ResourceNotAvailableException {
        Double previousExchangeRate, currentExchangeRate;
        final String functionName = "[hasDecreasedInOneDay]";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date currentDate = new Date();

        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Log.i(TAG, functionName + " Read day ago Exchange Rate from server");
            Number number = format.parse(getExchangeRate(df.format(new Date(calendar.getTimeInMillis()))));
            previousExchangeRate = number.doubleValue();
            Log.i(TAG, functionName + " Read current Exchange Rate from server");
            number = format.parse(getExchangeRate(df.format(currentDate)));
            currentExchangeRate = number.doubleValue();
        }
        catch (ParseException ignored){
            throw new ResourceNotAvailableException("Can't parse exchange rate");
        }

        return currentExchangeRate < previousExchangeRate;
    }

    /**
     * Проверить дневную динамику курса, чтобы проверить условия «Курс рубля к доллару повысился за последний день»
     * @return Курс рубля к доллару повысился за последний день
     */
    public boolean hasIncreasedInOneDay() throws ResourceNotAvailableException {
        Double previousExchangeRate, currentExchangeRate;
        final String functionName = "[hasIncreasedInOneDay]";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date currentDate = new Date();

        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Log.i(TAG, functionName + " Read day ago Exchange Rate from server");
            Number number = format.parse(getExchangeRate(df.format(new Date(calendar.getTimeInMillis()))));
            previousExchangeRate = number.doubleValue();
            Log.i(TAG, functionName + " Read current Exchange Rate from server");
            number = format.parse(getExchangeRate(df.format(currentDate)));
            currentExchangeRate = number.doubleValue();
        }
        catch (ParseException ignored){
            throw new ResourceNotAvailableException("Can't parse exchange rate");
        }

        return currentExchangeRate > previousExchangeRate;
    }

    /**
     * Проверить месячную динамику курса, чтобы проверить условия «Курс рубля к доллару понизился за последнюю неделю»
     * @return Курс рубля к доллару понизился за последнюю неделю
     */
    public boolean hasDecreasedInOneWeek() throws ResourceNotAvailableException {
        Double previousExchangeRate, currentExchangeRate;
        final String functionName = "[hasDecreasedInOneWeek]";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date currentDate = new Date();

        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Log.i(TAG, functionName + " Read week ago Exchange Rate from server");
            Number number = format.parse(getExchangeRate(df.format(new Date(calendar.getTimeInMillis()))));
            previousExchangeRate = number.doubleValue();
            Log.i(TAG, functionName + " Read current Exchange Rate from server");
            number = format.parse(getExchangeRate(df.format(currentDate)));
            currentExchangeRate = number.doubleValue();
        }
        catch (ParseException ignored){
            throw new ResourceNotAvailableException("Can't parse exchange rate");
        }

        return currentExchangeRate < previousExchangeRate;
    }

    /**
     * Проверить месячную динамику курса, чтобы проверить условия «Курс рубля к доллару повысился за последнюю неделю»
     * @return Курс рубля к доллару повысился за последнюю неделю
     */
    public boolean hasIncreasedInOneWeek() throws ResourceNotAvailableException {
        Double previousExchangeRate, currentExchangeRate;
        final String functionName = "[hasIncreasedInOneWeek]";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date currentDate = new Date();

        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Log.i(TAG, functionName + " Read week ago Exchange Rate from server");
            Number number = format.parse(getExchangeRate(df.format(new Date(calendar.getTimeInMillis()))));
            previousExchangeRate = number.doubleValue();
            Log.i(TAG, functionName + " Read current Exchange Rate from server");
            number = format.parse(getExchangeRate(df.format(currentDate)));
            currentExchangeRate = number.doubleValue();
        }
        catch (ParseException ignored){
            throw new ResourceNotAvailableException("Can't parse exchange rate");
        }

        return currentExchangeRate > previousExchangeRate;
    }

    /**
     * Проверить месячную динамику курса, чтобы проверить условия «Курс рубля к доллару понизился за последний месяц»
     * @return Курс рубля к доллару понизился за последний месяц
     */
    public boolean hasDecreasedInOneMonth() throws ResourceNotAvailableException {
        Double previousExchangeRate, currentExchangeRate;
        final String functionName = "[hasDecreasedInOneMonth]";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date currentDate = new Date();

        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Log.i(TAG, functionName + " Read month ago Exchange Rate from server");
            Number number = format.parse(getExchangeRate(df.format(new Date(calendar.getTimeInMillis()))));
            previousExchangeRate = number.doubleValue();
            Log.i(TAG, functionName + " Read current Exchange Rate from server");
            number = format.parse(getExchangeRate(df.format(currentDate)));
            currentExchangeRate = number.doubleValue();
        }
        catch (ParseException ignored){
            throw new ResourceNotAvailableException("Can't parse exchange rate");
        }

        return currentExchangeRate < previousExchangeRate;
    }

    /**
     * Проверить месячную динамику курса, чтобы проверить условия «Курс рубля к доллару повысился за последний месяц»
     * @return Курс рубля к доллару понизился за последний месяц
     */
    public boolean hasIncreasedInOneMonth() throws ResourceNotAvailableException {
        Double previousExchangeRate, currentExchangeRate;
        final String functionName = "[hasIncreasedInOneMonth]";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date currentDate = new Date();

        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Log.i(TAG, functionName + " Read month ago Exchange Rate from server");
            Number number = format.parse(getExchangeRate(df.format(new Date(calendar.getTimeInMillis()))));
            previousExchangeRate = number.doubleValue();
            Log.i(TAG, functionName + " Read current Exchange Rate from server");
            number = format.parse(getExchangeRate(df.format(currentDate)));
            currentExchangeRate = number.doubleValue();
        }
        catch (ParseException ignored){
            throw new ResourceNotAvailableException("Can't parse exchange rate");
        }

        return currentExchangeRate > previousExchangeRate;
    }

    /**
     * Получает курс рубля к доллару для переданной даты
     * @param date дата для получения курса
     * @return курс рубля к доллару
     */
    private String getExchangeRate(String date, int tries_left) throws ResourceNotAvailableException {
        final String functionName = "[getExchangeRate]";

        try {
            String content = null;
            Log.i(TAG, functionName + " Trying to get Exchange Rate from http://www.cbr.ru/scripts/XML_daily.asp");
            try {
                content = readStringFromURL("https://www.cbr.ru/scripts/XML_daily.asp?date_req=".concat(date));
            } catch (IOException ex) {
                Log.e(TAG, "[getExchangeRate] Can't get Exchange data: " + ex.getMessage(), new ResourceNotAvailableException("Error getting exchange rate data."));
            }

            if (content == null || content.isEmpty())
                Log.e(TAG, "Unable to read exchange rate", new ResourceNotAvailableException("Unable to read exchange rate"));

            Log.i(TAG, functionName + " Data got successfully.");

            final Pattern pattern_valute = Pattern.compile("<Valute ID=\"R01235\">(.+?)</Valute>", Pattern.DOTALL);
            final Matcher matcher_valute = pattern_valute.matcher(content);
            matcher_valute.find();

            final Pattern pattern = Pattern.compile("<Value>(.+?)</Value>", Pattern.DOTALL);
            final Matcher matcher = pattern.matcher(matcher_valute.group(1));
            matcher.find();

            Log.i(TAG, functionName + " Parsed data: " + matcher.group(1));

            return matcher.group(1);
        }
        catch(Exception e) {
            if (tries_left > 0) {
                try {
                    Thread.sleep(100);
                    return getExchangeRate(date, tries_left - 1);
                } catch (InterruptedException k) {
                    throw new ResourceNotAvailableException("Error parsing COVID stats to JSON");
                }
            }
            else {
                throw new ResourceNotAvailableException("Error getting exchange rate data.");
            }
        }
    }

    private String getExchangeRate(String date) throws ResourceNotAvailableException {
        return getExchangeRate(date, 7);
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