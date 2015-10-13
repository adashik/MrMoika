package com.lessugly.mrmoika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.lessugly.mrmoika.registration.CarwashRegistration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void carwashRegistration(View view) {
        Intent intent = new Intent(this, CarwashRegistration.class);
        startActivity(intent);
    }
}
