package com.example.dark_knight.dhakacitybusroutes.Map;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;

import com.example.dark_knight.dhakacitybusroutes.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final Intent intent = getIntent();
        String routes[] =intent.getStringArrayExtra("routes");

        ArrayList<LatLng> routesLatLng = new ArrayList<>();

        HashMap<String,Pair<Double,Double>> locationLatLng = new HashMap<>();

        Pair lang;
        double lang1 = 0;
        double lang2 = 0;
        LatLng location = new LatLng(0,0);
        locationLatLng.put("Mirpur 12",new Pair<>(23.828543, 90.365076));
        locationLatLng.put("Pallabi",new Pair<>(23.823952, 90.364343));
        locationLatLng.put("Purobi",new Pair<>(23.819031, 90.365182));
        locationLatLng.put("Mirpur 11",new Pair<>(23.816090, 90.366207));
        locationLatLng.put("Mirpur 10",new Pair<>(23.806959, 90.368565));
        locationLatLng.put("Kazipara",new Pair<>(23.797354, 90.372783));
        locationLatLng.put("Bijoy Sarani",new Pair<>(23.764833, 90.385999));
        locationLatLng.put("Farmgate",new Pair<>(23.757362, 90.390005));


        for(String a:routes){
            if(locationLatLng.containsKey(a)){
                lang = locationLatLng.get(a);
                lang1 = (double) lang.first;
                lang2 = (double) lang.second;
                Log.e("location",""+a);
                Log.e("Lang1",""+lang1);
                Log.e("Lang2",""+lang2);
                location = new LatLng(lang1,lang2);
            }
            routesLatLng.add(location);
            mMap.addMarker(new MarkerOptions().position(location).title("A"));
        }

        LatLng src = new LatLng(23.813814, 90.371578);
        LatLng des = new LatLng(23.812862, 90.366919);
        //mMap.addMarker(new MarkerOptions().position(src).title("A"));
        //mMap.addMarker(new MarkerOptions().position(des).title("B"));



        mMap.addPolyline( new PolylineOptions().addAll(
                    routesLatLng
                )
                .width(10)
                .color(Color.RED)
                .geodesic(true)

        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(src,16));
    }

    public void changeType(View view) {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    /*
    public void onSearch(View view) {
        EditText location_tf = (EditText) findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();
        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);


            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        }
    }
    */
}
