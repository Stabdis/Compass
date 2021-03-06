package com.example.gytiz_000.compass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button compass = (Button) findViewById(R.id.compass);
        compass.setOnClickListener(clickOnCompassButton);
        Button lightSaber = (Button) findViewById(R.id.lightSaber);
        lightSaber.setOnClickListener(clickOnLightSaberButton);
        Button counter = (Button) findViewById(R.id.counter);
        counter.setOnClickListener(clickOnCounterButton);
        Button quit = (Button) findViewById(R.id.quit);
        quit.setOnClickListener(clickOnQuitButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    View.OnClickListener clickOnCompassButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent compassIntent = new Intent(MainActivity.this, Compass.class);
            startActivity(compassIntent);
        }
    };
    View.OnClickListener clickOnLightSaberButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent lightSaberIntent = new Intent(MainActivity.this, LightSaberActivity.class);
            startActivity(lightSaberIntent);
        }
    };
    View.OnClickListener clickOnCounterButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent counterIntent = new Intent(MainActivity.this, CounterActivity.class);
            startActivity(counterIntent);
        }
    };
    View.OnClickListener clickOnQuitButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
