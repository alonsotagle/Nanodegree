package com.alonsotagle.nanodegree;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button main_spotify;
    Button main_scores;
    Button main_library;
    Button main_bigger;
    Button main_reader;
    Button main_capstone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_spotify = (Button)findViewById(R.id.main_spotify);
        main_scores = (Button)findViewById(R.id.main_scores);
        main_library = (Button)findViewById(R.id.main_library);
        main_bigger = (Button)findViewById(R.id.main_bigger);
        main_reader = (Button)findViewById(R.id.main_reader);
        main_capstone = (Button)findViewById(R.id.main_capstone);

        main_spotify.setOnClickListener(this);
        main_scores.setOnClickListener(this);
        main_library.setOnClickListener(this);
        main_bigger.setOnClickListener(this);
        main_reader.setOnClickListener(this);
        main_capstone.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_spotify:
                Toast.makeText(getApplicationContext(), R.string.toast_spotify, Toast.LENGTH_SHORT).show();
                break;

            case R.id.main_scores:
                Toast.makeText(getApplicationContext(), R.string.toast_scores, Toast.LENGTH_SHORT).show();
                break;

            case R.id.main_library:
                Toast.makeText(getApplicationContext(), R.string.toast_library, Toast.LENGTH_SHORT).show();
                break;

            case R.id.main_bigger:
                Toast.makeText(getApplicationContext(), R.string.toast_bigger, Toast.LENGTH_SHORT).show();
                break;

            case R.id.main_reader:
                Toast.makeText(getApplicationContext(), R.string.toast_reader, Toast.LENGTH_SHORT).show();
                break;

            case R.id.main_capstone:
                Toast.makeText(getApplicationContext(), R.string.toast_capstone, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
