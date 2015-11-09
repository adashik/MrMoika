package com.lessugly.mrmoika;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lessugly.mrmoika.carwash.CarwashMain;
import com.lessugly.mrmoika.registration.CarownerRegistration;
import com.lessugly.mrmoika.registration.CarwashRegistration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean regComplete = preferences.getBoolean("registration", false);
        boolean carwash = preferences.getBoolean("carwash", false);
        if (regComplete) {
            if (carwash)
            startActivity(new Intent(this, CarwashMain.class));
            finish();
        }else
        setContentView(R.layout.activity_main);
    }

    public void carwashRegistration(View view) {

        Intent goToCarwashRegistration = new Intent(this, CarwashRegistration.class);
        goToCarwashRegistration.putExtra("finisher",new ResultReceiver(null){
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                MainActivity.this.finish();
            }
        });
        startActivityForResult(goToCarwashRegistration, 1);
    }

    public void carownerRegistration(View view) {
        Intent intent = new Intent(MainActivity.this, CarownerRegistration.class);
        startActivity(intent);
    }
}
