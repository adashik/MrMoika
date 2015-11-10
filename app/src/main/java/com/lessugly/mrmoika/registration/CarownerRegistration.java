package com.lessugly.mrmoika.registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.lessugly.mrmoika.R;

public class CarownerRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carowner_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        EditText cartype = (EditText) findViewById(R.id.car_type);
       /// cartype.setOnClickListener(viewClickListener);
        cartype.setInputType(InputType.TYPE_NULL); // отключаем клавиатуру при клике на тип кузова

        cartype.setOnFocusChangeListener(viewFocusListener);
    }

    View.OnFocusChangeListener viewFocusListener = new View.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
if (hasFocus){
    showPopupMenu(v);
}

        }


    };


        //View.OnClickListener viewClickListener = new View.OnClickListener() {
         //   @Override
         //   public void onClick(View v) {
         //       showPopupMenu(v);
         //   }
      //  };



    private void showPopupMenu(View v) {
        final EditText mCarType;
        mCarType = (EditText) findViewById(R.id.car_type);



        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu_cartype); // Для Android 4.0
        // для версии Android 3.0 нужно использовать длинный вариант
        // popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
        // popupMenu.getMenu());



        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Toast.makeText(PopupMenuDemoActivity.this,
                        // item.toString(), Toast.LENGTH_LONG).show();
                        // return true;
                        switch (item.getItemId()) {

                            case R.id.menu1:

                               mCarType.setText(item.getTitle());

                             //  Toast.makeText(getApplicationContext(),
                                 //       item.getTitle(),
                                 //      Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu2:
                                mCarType.setText(item.getTitle());
                                return true;
                            case R.id.menu3:
                                mCarType.setText(item.getTitle());
                                return true;
                            case R.id.menu4:
                                mCarType.setText(item.getTitle());
                                return true;
                            default:
                                return false;
                        }
                    }
                });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {
                //Toast.makeText(getApplicationContext(), "onDismiss",
                   //     Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();

    }


    @Override
    public void onBackPressed() {
                openQuitDialog();
    }


    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                CarownerRegistration.this);
        quitDialog.setTitle(R.string.registration_quit_dialog);

        quitDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CarownerRegistration.this.finish();
            }
        });

        quitDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        quitDialog.show();
    }



}
