package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class TimeResource {
    private final String TAG = "TimeResource";

    public TimeResource() {
    }

    public static int[] getTimeFromString(String str) {
        int[] time = new int[2];
        time[0] = getHoursFromString(str.substring(0, 2));
        time[1] = getMinutesFromString(str.substring(3, 5));
        return time;
    }

    public static int getHoursFromString(String str) {
        if (str.charAt(0) == '0')
            str = str.substring(1);
        return Integer.parseInt(str);
    }

    public static int getMinutesFromString(String str) {
        if (str.charAt(0) == '0')
            str = str.substring(1);
        return Integer.parseInt(str);
    }

    public static String getStringFromTime(int[] time) {
        return String.format("%d%d:%d%d", time[0] / 10, time[0] % 10, time[1] / 10, time[1] % 10);
    }

    /**
     * Получаем текущие данные о времени и устанавливаем их в календарь
     *
     * @return календарь с текущими данными
     */
    private Calendar setCalendar() {
        Log.i(TAG, "[setCalendar] Reading current data");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Log.i(TAG, "[setCalendar] Read current data successfully");

        return calendar;
    }

    /**
     * Получить текущее время. Важны часы и минуты. В формате 24НН.
     *
     * @return текущие часы и минуты в формате 24НН
     */
    public int[] getTimeOfDay() {
        Log.i(TAG, "[getTimeOfDay] Getting current time 24HH:MM");
        int[] time = new int[2];

        Calendar calendar = setCalendar();

        time[0] = calendar.get(Calendar.HOUR_OF_DAY);
        time[1] = calendar.get(Calendar.MINUTE);

        Log.i(TAG, "[getTimeOfDay] Data: " + time[0] + ":" + time[1]);

        return time;
    }

    /**
     * Получить текущее значение часов. В формате 24HH.
     *
     * @return часы в формате 24НН
     */
    public int getHoursOfDay() {
        Log.i(TAG, "[getHoursOfDay] Getting current hours 24HH");
        Calendar calendar = setCalendar();

        Log.i(TAG, "[getHoursOfDay] Data: " + calendar.get(Calendar.HOUR_OF_DAY));

        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Получить текущее значение минут.
     *
     * @return текущее значение минут
     */
    public int getMinutes() {
        Log.i(TAG, "[getMinutes] Getting current minutes");
        Calendar calendar = setCalendar();
        Log.i(TAG, "[getMinutes] Data: " + calendar.get(Calendar.MINUTE));

        return calendar.get(Calendar.MINUTE);
    }

    /**
     * Получить текущее время. Важны часы и минуты. В формате 12НН.
     *
     * @return теукщие часы и минуты в формате 12НН
     */
    public int[] getTime() {
        Log.i(TAG, "[getTime] Getting current time 12HH:MM");
        int[] time = new int[2];
        Calendar calendar = setCalendar();

        time[0] = calendar.get(Calendar.HOUR);
        time[1] = calendar.get(Calendar.MINUTE);

        Log.i(TAG, "[getTime] Data: " + time[0] + ":" + time[1]);

        return time;
    }

    /**
     * Получить текущее значение часов. В формате 12HH.
     *
     * @return текущее значение часов в формате 12НН
     */
    public int getHours() {
        Log.i(TAG, "[getHours] Getting current hours 12HH");
        Calendar calendar = setCalendar();

        Log.i(TAG, "[getHours] Data: " + calendar.get(Calendar.HOUR));

        return calendar.get(Calendar.HOUR);
    }

    /**
     * Проверка: сегодня выходной день (сб,вс).
     *
     * @return сегодня выходной день
     */
    public boolean isWeekend() {
        Log.i(TAG, "[isWeekend] Checking if current day is weekend");
        Calendar calendar = setCalendar();

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        Log.i(TAG, "[isWeekend] Data: " + day + "is holiday: " + (day == 7 || day == 1));

        return day == 7 || day == 1;
    }

    /**
     * Проверка: сегодня будний день (пн-пт).
     *
     * @return сегодня будний день
     */
    public boolean isWorkDay() {
        Log.i(TAG, "[isWorkDay] Checking if current day is working day");

        return !isWeekend();
    }

    /**
     * Проверка: сегодня праздничный день (по российскому производственному календарю)
     *
     * @return сегодня праздничный день
     */
    public boolean isHoliday() {
        Log.i(TAG, "[isHoliday] Checking if current day is holiday");
        Calendar calendar = setCalendar();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Log.i(TAG, "[isHoliday] Data: " + day);
        switch (month) {
            case 1:
                switch (day) {
                    case 1:
                    case 2:
                    case 3:
                    case 4: // Новый год и Рождество
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        Log.i(TAG, "[isHoliday] New Year or Christmas");
                        return true;
                    default:
                        Log.i(TAG, "[isHoliday] Not a holiday");
                        return false;
                }
            case 2:
                Log.i(TAG, "[isHoliday] Defender of Motherland Day");
                return day == 23; // День защитника Отечества
            case 3:
                Log.i(TAG, "[isHoliday] International women day");
                return day == 8; // 8 марта
            case 5:
                Log.i(TAG, "[isHoliday] Labour day");
                return day == 9 || day == 1; // Праздник весны и труда, День Победы
            case 6:
                Log.i(TAG, "[isHoliday] Day of Russia");
                return day == 12; // День России
            case 11:
                Log.i(TAG, "[isHoliday] National unity day");
                return day == 4; // День народного единства
            default:
                Log.i(TAG, "[isHoliday] Not a holiday");
                return false;
        }
    }

    /**
     * Получить время года winter, spring, summer, fall.
     *
     * @return время года
     */
    public String getSeason() {
        Log.i(TAG, "[getSeason] Getting current season");
        Calendar calendar = setCalendar();
        int day = calendar.get(Calendar.MONTH);

        if (day == 12 || day == 1 || day == 2) {
            Log.i(TAG, "[getSeason] Winter");
            return "Зима";
        } else if (day > 2 && day < 6) {
            Log.i(TAG, "[getSeason] Spring");
            return "Весна";
        } else if (day > 5 && day < 9) {
            Log.i(TAG, "[getSeason] Summer");
            return "Лето";
        } else {
            Log.i(TAG, "[getSeason] Fall");
            return "Осень";
        }
    }

    /**
     * Получить день недели monday, tuesday ...
     *
     * @return день недели
     */
    public String getWeekDay() {
        Log.i(TAG, "[getWeekDay] Getting current day of week");
        Calendar calendar = setCalendar();
        String[] week = {"Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        Log.i(TAG, "[getWeekDay] is " + week[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

        return week[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * Получить время суток: night, morning, evening, afternoon.
     *
     * @return время суток
     */
    public String getDayTime() {
        Log.i(TAG, "[getDayTime] Getting current time of the day");
        Calendar calendar = setCalendar();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);

        if (hours < 6) {
            Log.i(TAG, "[getDayTime] Night");
            return "Ночь";
        } else if (hours < 12) {
            Log.i(TAG, "[getDayTime] Morning");
            return "Утро";
        } else if (hours < 18) {
            Log.i(TAG, "[getDayTime] Day");
            return "День";
        } else {
            Log.i(TAG, "[getDayTime] Evening");
            return "Вечер";
        }
    }

    /**
     * Получить месяц: jan, feb, ...
     *
     * @return текущий месяц
     */
    public String getMonth() {
        Log.i(TAG, "[getMonth] Getting current month");
        Calendar calendar = setCalendar();
        String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

        Log.i(TAG, "[getMonth] Month - " + months[calendar.get(Calendar.MONTH)]);

        return months[calendar.get(Calendar.MONTH)];
    }
}