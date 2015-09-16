package com.example.gytiz_000.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by gytiz_000 on 9/11/2015.
 */
public class Compass extends ActionBarActivity implements SensorEventListener {
    private ImageView compassImage;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnometer;
    private float currentCompassAngle = 0;
    private float[] readingmangnometer = new float[3];
    private float[] readingaccelerometer = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        compassImage = (ImageView) findViewById(R.id.compass);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, accelerometer);
        sensorManager.unregisterListener(this, magnometer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] rotation = new float[9];
        float[] orientation = new float[3];

        if (event.sensor == accelerometer) {
            readingaccelerometer[0] = event.values[0];
            readingaccelerometer[1] = event.values[1];
            readingaccelerometer[2] = event.values[2];
        }
        if (event.sensor == magnometer) {
            readingmangnometer[0] = event.values[0];
            readingmangnometer[1] = event.values[1];
            readingmangnometer[2] = event.values[2];
        }
        sensorManager.getRotationMatrix(rotation, null, readingaccelerometer, readingmangnometer);
        sensorManager.getOrientation(rotation, orientation);
        float azimuthRadians = orientation[0];
        float azimuthDegrees = (float) (Math.toDegrees(azimuthRadians) + 360) % 360;
        doAnimation(currentCompassAngle, azimuthDegrees, compassImage);
        currentCompassAngle = azimuthDegrees;
    }

    private void doAnimation(float from, float to, View rotateme) {
        RotateAnimation ra = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(200);
        ra.setFillAfter(true);
        rotateme.startAnimation(ra);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
