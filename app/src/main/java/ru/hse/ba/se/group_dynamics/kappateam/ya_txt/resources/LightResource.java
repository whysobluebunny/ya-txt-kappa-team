package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Ресурс света
 */
public class LightResource {
    private final static String LIGHT_NAME = "Светло";
    private final static String DARK_NAME = "Темно";

    /**
     * Тэг для логирования
     */
    private final static String TAG = LightResource.class.getName();

    /**
     * Менджер сенсоров
     */
    private final SensorManager mySensorManager;

    /**
     * Сенсор света
     */
    private final Sensor lightSensor;

    /**
     * Activity
     */
    private final Activity activity;

    /**
     * Истинно, если в окружении достаточно светло
     */
    private boolean isLight;

    /**
     * Значение LUX
     */
    private float lux;

    /**
     * Обработчик собитий сенсора
     */
    private final SensorEventListener lightSensorListener
            = new SensorEventListener() {

        /**
         * Событие при смене значения сенсора
         *
         * @param event событие
         */
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                lux = event.values[0];
            }
        }

        /**
         * Событие при смене четкости
         * @param sensor сенсор
         * @param accuracy четкость
         */
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Auto-generated method stub
        }
    };

    /**
     * Конструктор
     * @param activity activity
     */
    public LightResource(Activity activity) {
        this.activity = activity;
        mySensorManager = (SensorManager) activity.getSystemService(SENSOR_SERVICE);
        assert mySensorManager != null;
        lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor != null) {
            Log.i(TAG, "[Constructor] Sensor.TYPE_LIGHT Available");
            mySensorManager.registerListener(
                    lightSensorListener,
                    lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            Log.e(TAG, "[Constructor] Sensor.TYPE_LIGHT NOT Available", new ResourceNotAvailableException("Sensor.TYPE_LIGHT NOT Available"));
        }
    }

    /**
     * @return true, если в окружении достаточно светло
     */
    public boolean isLight() {
        return lux >= 20;
    }

    /**
     * @return true, если в окружении недостаточно светло
     */
    public boolean isDark() {
        return !isLight();
    }

    /**
     * @return значение LUX
     */
    public float getLux() {
        return lux;
    }

    /**
     * @return имя типа освещения
     */
    public String getTypeName() {
        return isLight() ? LIGHT_NAME : DARK_NAME;
    }
}
