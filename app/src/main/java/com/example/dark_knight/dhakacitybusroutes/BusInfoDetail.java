package com.example.dark_knight.dhakacitybusroutes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class BusInfoDetail extends AppCompatActivity {
    TextView busDetail;
    String _Bus_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);
        Intent intent = getIntent();
        _Bus_Id =intent.getStringExtra("Bus_Name");
        BusRepo repo = new BusRepo(this);
        BusProfile Bus = new BusProfile();
        Log.e("Bus name:",""+_Bus_Id);
        //Log.e("Bus id:",_Bus_Id);
        Bus = repo.getBusById(0,_Bus_Id);
        busDetail = (TextView)findViewById(R.id.bus_detail);
        busDetail.setText("id:"+Bus.id+"\n"+Bus.name+"\n"+Bus.seatNo+"\n"+Bus.route);
    }

}
