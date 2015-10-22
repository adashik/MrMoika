package com.lessugly.mrmoika.carwash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lessugly.mrmoika.MainActivity;
import com.lessugly.mrmoika.R;

public class CarwashMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carwash_main);
    }

    public void logout(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("registration", false).putBoolean("carwash",false);
        editor.commit();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void exit(View view) {
        finish();
    }
}
