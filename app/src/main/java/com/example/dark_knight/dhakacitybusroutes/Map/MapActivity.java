package com.example.dark_knight.dhakacitybusroutes.Map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.dark_knight.dhakacitybusroutes.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST = 500;
    ArrayList<LatLng> listPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listPoints = new ArrayList<>();
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
        String routes[] = intent.getStringArrayExtra("routes");
        //mMap.getUiSettings().setZoomControlsEnabled(true);
        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
        //return;
        //}

        //
        for(String a:routes){
            Log.e("//",""+a);
        }

        ArrayList<LatLng> routesLatLng = new ArrayList<>();

        HashMap<String,Pair<Double,Double>> locationLatLng = new HashMap<>();

        Pair lang;
        double lang1 = 0;
        double lang2 = 0;
        routes[10] = "Farmgate";
        LatLng location = new LatLng(0,0);
        LatLng location2 = new LatLng(0,0);
        locationLatLng.put("Mirpur 12",new Pair<>(23.828543, 90.365076));
        locationLatLng.put("Pallabi",new Pair<>(23.823952, 90.364343));
        locationLatLng.put("Purobi",new Pair<>(23.817813, 90.365679)); //Rupali Bank
        locationLatLng.put("Mirpur 11",new Pair<>(23.816090, 90.366207));
        locationLatLng.put("Mirpur 10",new Pair<>(23.806959, 90.368565));
        locationLatLng.put("Kazipara",new Pair<>(23.797354, 90.372783));
        locationLatLng.put("Bijoy Sarani",new Pair<>(23.764833, 90.385999));
        locationLatLng.put("Khamar Bari",new Pair<>(23.758948, 90.383815));
        locationLatLng.put("Farmgate",new Pair<>(23.758078, 90.390210));



        Log.e("routes length",""+routes.length);
        for(int i = 0 ; i < routes.length - 1; i++){
            if(locationLatLng.containsKey(routes[i])){
                lang = locationLatLng.get(routes[i]);
                lang1 = (double) lang.first;
                lang2 = (double) lang.second;
                Log.e("location",""+routes[i]);
                Log.e("Lang1",""+lang1);
                Log.e("Lang2",""+lang2);
                location = new LatLng(lang1,lang2);
            }
            if(locationLatLng.containsKey(routes[i+1])){
                lang = locationLatLng.get(routes[i+1]);
                lang1 = (double) lang.first;
                lang2 = (double) lang.second;
                Log.e("location",""+routes[i+1]);
                Log.e("Lang1",""+lang1);
                Log.e("Lang2",""+lang2);
                location2 = new LatLng(lang1,lang2);
            }
            String url = getRequestUrl(location,location2);
            TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
            Log.e("Req","polyline");
            taskRequestDirections.execute(url);
            //routesLatLng.add(location);
            //mMap.addMarker(new MarkerOptions().position(location).title("A"));
        }

        LatLng src = new LatLng(23.828543, 90.365076);
        LatLng des = new LatLng(23.757362, 90.390005);
        mMap.addMarker(new MarkerOptions().position(src).title("A"));
        mMap.addMarker(new MarkerOptions().position(des).title("B"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(src,16));

    }


    private String getRequestUrl(LatLng origin, LatLng dest) {
        //Value of origin
        String str_org = "origin=" + origin.latitude +","+origin.longitude;
        //Value of destination
        String str_dest = "destination=" + dest.latitude+","+dest.longitude;
        //Set value enable the sensor
        String sensor = "sensor=false";
        //Mode for find direction
        String mode = "mode=driving";
        //Build the full param
        String param = str_org +"&" + str_dest + "&" +sensor+"&" +mode;
        Log.e("requesting URL",""+param);
        //Output format
        String output = "json";
        //Create url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param;
        return url;
    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try{
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            //Get the response result
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
                break;
        }
    }

    public class TaskRequestDirections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Parse json here
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);
        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>> > {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            //Get list route and display it into the map

            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));

                    points.add(new LatLng(lat,lon));
                }

                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);
            }

            if (polylineOptions!=null) {
                mMap.addPolyline(polylineOptions);
            } else {
                //Toast.makeText(getApplicationContext(), "Direction not found!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
