package com.example.gytiz_000.compass;

import android.annotation.TargetApi;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by gytiz_000 on 9/16/2015.
 */
public class LightSaberActivity extends ActionBarActivity implements SensorEventListener {
    private SoundPool soundpool;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float mAccel;   // acceleration apart from gravity
    private float mAccelCurrent;    // current acceleration including gravity
    private float mAccelLast;   // last acceleration including gravity
    private int lightSaberOnSound;
    private int lightSaberHitSound;
    private int idLightSaberOnSound = 1;
    private int idLightSaberHitSound = 2;
    private boolean check = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightsaber);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
            Toast.makeText(this, "Using Lollipop or newer", Toast.LENGTH_LONG).show();
        } else {
            createOldSoundPool();
            Toast.makeText(this, "Using pre Lollipop", Toast.LENGTH_LONG).show();
        }

        soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) {
                    if(sampleId == idLightSaberOnSound)
                        soundPool.play(lightSaberOnSound, 0.2f, 0.2f, 1, -1, 1f);
                    if(sampleId == idLightSaberHitSound)
                        soundPool.play(lightSaberHitSound, 0.2f, 0.2f, 1, -1, 1f);
                } else {
                    Toast.makeText(LightSaberActivity.this, "Error loading sound: " + sampleId, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        soundpool.autoResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, accelerometer);
        soundpool.autoPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundpool.stop(idLightSaberOnSound);
        soundpool.stop(idLightSaberHitSound);
        soundpool.release();
    }

    @TargetApi(21)
    protected void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundpool = new SoundPool.Builder().setAudioAttributes(attributes).build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool() {
        soundpool = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor == accelerometer) {
//            TextView text = (TextView) findViewById(R.id.text);
//            text.setText("" + event.values[2]);
//        }
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta;
        if (mAccel > 12) {
            Toast.makeText(getApplicationContext(), "Device has shaken.", Toast.LENGTH_LONG).show();
            if(check) {
                soundpool.resume(idLightSaberOnSound);
                lightSaberOnSound = soundpool.load(this, R.raw.saberon, 1);
                check = false;
            }
            else{
                soundpool.pause(idLightSaberOnSound);
                check = true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
