package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources;

import android.app.Activity;

public enum Resources {

    INSTANCE;
      
    public WeatherResource weatherResource;

    public TimeResource timeResource;

    public GeoLocation geoLocation;

    public ExchangeRateResource exchangeRateResource;

    public LightResource lightResource;

    public BitcoinResource bitcoinResource;

    public CovidResource covidResource;

    private Resources() {
        weatherResource = new WeatherResource();
        timeResource = new TimeResource();
        exchangeRateResource = new ExchangeRateResource();
        geoLocation = new GeoLocation();
        bitcoinResource = new BitcoinResource();
        covidResource = new CovidResource();
    }

    public void createLightResource(Activity activity) {
        lightResource = new LightResource(activity);
    }
}