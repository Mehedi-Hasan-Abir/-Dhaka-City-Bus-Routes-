package com.example.dark_knight.dhakacitybusroutes.Bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dark_knight.dhakacitybusroutes.Map.MapActivity;
import com.example.dark_knight.dhakacitybusroutes.Map.MapsActivity;
import com.example.dark_knight.dhakacitybusroutes.R;


public class BusInfoDetail extends AppCompatActivity {
    TextView busDetail;
    TextView fullRouteDetail;
    Button mapBtn ;
    String _Bus_Name;
    String routes[] ={};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);

        final Intent intent = getIntent();
        _Bus_Name =intent.getStringExtra("Bus_Name");

        BusRepo repo = new BusRepo(this);
        BusProfile Bus = new BusProfile();
        Log.e("Bus name:",""+_Bus_Name);
        //Log.e("Bus id:",_Bus_Id);

        Bus = repo.getBusById(0,_Bus_Name);
        busDetail = (TextView)findViewById(R.id.bus_detail);
        fullRouteDetail = (TextView)findViewById(R.id.route_detail);
        busDetail.setText(Bus.name+"\n"+Bus.seatNo+"\n"+Bus.route);


        String fullRoute = Bus.fullRoute;
        try{
            routes = fullRoute.split(",");
        }catch(Exception e){
            e.getStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        for(String i : routes) {
            sb.append(i+"\n");
        }
        Log.e("route ",""+sb);
        //String routes2[] = fullRoute.split(System.lineSeparator());
        fullRouteDetail.setMovementMethod(new ScrollingMovementMethod());
        fullRouteDetail.setText(sb);

        mapBtn = (Button)findViewById(R.id.map_button);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),MapActivity.class);
                intent1.putExtra("routes",routes);
                startActivity(intent1);
            }
        });
    }

}
