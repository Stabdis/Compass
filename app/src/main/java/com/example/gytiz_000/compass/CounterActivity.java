package com.example.gytiz_000.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensormanager;
    private Sensor accelerometer;
    private float currentAngle = 0;
    private float[] readingAccelerometer = new float[3];
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        sensormanager   = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer   = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        text            = (TextView) findViewById(R.id.accelerometerY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_counter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float y = event.values[1];
            text.setText("AXIS-Y: " + y);

            if (getRandom(10, 0) > y+0.6)
                Log.d("ark", "tick: " + y);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensormanager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensormanager.unregisterListener(this, accelerometer);
    }

    private double getRandom(int min, int max) {
        return Math.random() * (max - min) + min;
    }
}
