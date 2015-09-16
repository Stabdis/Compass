package com.example.gytiz_000.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gytiz_000 on 9/11/2015.
 */
public class SensorsActivity extends AppCompatActivity {

    private SensorManager sm;
    private List<Sensor> availableSensors;
    private int selectedSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        availableSensors = sm.getSensorList(Sensor.TYPE_ALL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.action_load:
//                Toast.makeText(this, "You pressed load", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.action_save:
//                Toast.makeText(this,"you pressed save", Toast.LENGTH_LONG).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
        selectedSensor = item.getItemId();
        updateDescriptionInTextView();
        return true;
    }

    private void updateDescriptionInTextView() {
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        availableSensors = sm.getSensorList(Sensor.TYPE_ALL);
        String txt = "";
        Sensor sensor = availableSensors.get(selectedSensor);
        txt += "Name: " + sensor.getName() + "\n\n";
        txt += "Type: " + sensor.getType() + "\n\n";
        txt += "Power: " + sensor.getPower() + "\n\n";
        txt += "Range: " + sensor.getMaximumRange() + "\n\n";
        txt += "Resolution " + sensor.getResolution() + "\n\n";
        if (sensor.getMinDelay() == 0) {
            txt += "Not a streaming sensor";
        } else {
            txt += "Min delay: " + ((double) sensor.getMinDelay() / 1000000.0) + " seconds \n";
        }
        TextView description = (TextView) findViewById(R.id.desc);
        description.setText(txt);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        int cnt = 0;
        for (int i = 0; i < availableSensors.size(); i++) {
            Sensor s = availableSensors.get(i);
            menu.add(0, cnt, 0, s.getType() + " " + s.getName());
            cnt++;
        }
        return true;
    }
}
