package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.interfaces;

import android.location.Geocoder;
import android.location.Location;

public interface OnLocationUpdateListener {
    void onLocationChange(Geocoder geocoder, Location location);
    void onError(String error);
}
