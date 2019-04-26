package com.example.dark_knight.dhakacitybusroutes.Map;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Dark-Knight on 30-Jan-18.
 */

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public ArrayList<LatLng> points;

    public void printDetails()
    {
        System.out.println("TAGROUTEDETAILS ::: distance = " + distance.text + " " + distance.value +
                ", duration = " + duration.text + " " + duration.value +
                ", endAddress = '" + endAddress +
                ", endLocation = " + endLocation +
                ", startAddress = '" + startAddress +
                ", startLocation = " + startLocation);
    }


}
