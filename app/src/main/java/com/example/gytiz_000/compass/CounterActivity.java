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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class CounterActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensormanager;
    private Sensor accelerometer;
    private TextView text;
    private SoundPool soundPool;
    private int soundTik, idSoundTik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
            Toast.makeText(this, "Using Lollipop or newer", Toast.LENGTH_LONG).show();
        } else {
            createOldSoundPool();
            Toast.makeText(this, "Using pre Lollipop", Toast.LENGTH_LONG).show();
        }

        soundTik = soundPool.load(this, R.raw.tik, 1);

        sensormanager   = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer   = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        text            = (TextView) findViewById(R.id.accelerometerY);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.stop(soundTik);
        soundPool.release();
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

            if (getRandom(10, 0) > y+0.5) {
                soundPool.play(soundTik, 0.3f, 0.3f, 1, 0, 1f);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing here
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensormanager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        soundPool.autoResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensormanager.unregisterListener(this, accelerometer);
        soundPool.autoPause();
    }

    private double getRandom(int min, int max) {
        return Math.random() * (max - min) + min;
    }

    @TargetApi(21)
    protected void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool() {
        soundPool = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
    }

}
