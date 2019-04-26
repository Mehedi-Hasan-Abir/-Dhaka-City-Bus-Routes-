package com.example.dark_knight.dhakacitybusroutes;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main2Activity extends ListActivity {

    TextView Bus_name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //BusRepo repo = new BusRepo(this);
        //BusProfile Bus = new BusProfile();
        //Bus.seatNo= 20;
        //Bus.rent=50;
        //Bus.name="Bihongoo";
        //Bus.route="Mirpur-Farmgate";
        //Toast.makeText(this,"New Bus Insert",Toast.LENGTH_SHORT).show();

        //repo.insert(Bus);

        getAll();
    }
    protected  void getAll(){
        BusRepo repo = new BusRepo(this);

        ArrayList<HashMap<String, String>> BusList =  repo.getBusList();

        if(BusList.size()!=0) {
            ListView lv = getListView();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bus_name = (TextView) view.findViewById(R.id.bus_name);
                    String BusId = Bus_name.getText().toString();
                    Log.e("MainActivity bus name",BusId);
                    Intent objIndent = new Intent(getApplicationContext(),BusInfoDetail.class);
                    objIndent.putExtra("Bus_Name",BusId);
                    startActivity(objIndent);
                }
            });
            ListAdapter adapter = new SimpleAdapter( Main2Activity.this,BusList, R.layout.activity_bus_entry, new String[] { "id","name"}, new int[] {R.id.bus_id, R.id.bus_name});
            //ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Main2Activity.this,R.layout.activity_bus_entry,BusList);
            setListAdapter(adapter);
        }else{
            Toast.makeText(this,"Bus nai!", Toast.LENGTH_SHORT).show();
        }
    }


}
