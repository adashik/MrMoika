package com.lessugly.mrmoika.registration;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lessugly.mrmoika.R;
import com.lessugly.mrmoika.util.PhoneFormatting;

public class CarwashRegistration extends AppCompatActivity {

    private ViewPager mViewPager;
    private static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carwash_registration);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.step_one);
        final Button buttonNext = (Button) findViewById(R.id.buttonNext);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = mViewPager.getCurrentItem();
                switch (currentPage) {
                    case 0:
                        openQuitDialog();
                        break;
                    case 1:
                        mViewPager.setCurrentItem(currentPage - 1);
                        break;
                    case 2:
                        mViewPager.setCurrentItem(currentPage - 1);
                        break;
                }
            }
        });
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        toolbar.setTitle(R.string.step_one);
                        buttonNext.setText(R.string.registration_button_next);
                        break;
                    case 1:
                        toolbar.setTitle(R.string.step_two);
                        buttonNext.setText(R.string.registration_button_next);
                        break;
                    case 2:
                        toolbar.setTitle(R.string.step_three);
                        buttonNext.setText(R.string.registration_button_confirm);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = CarwashRegistration.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
            }
        });



    }
    @Override
    public void onBackPressed() {
        int currentPage = mViewPager.getCurrentItem();
        switch (currentPage) {
            case 0:
                openQuitDialog();
                break;
            case 1:
                mViewPager.setCurrentItem(currentPage - 1);
                break;
            case 2:
                mViewPager.setCurrentItem(currentPage - 1);
                break;
        }
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                CarwashRegistration.this);
        quitDialog.setTitle(R.string.registration_quit_dialog);

        quitDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CarwashRegistration.this.finish();
            }
        });

        quitDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        quitDialog.show();
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return FirstStep.newInstance();
                case 1:
                    return SecondStep.newInstance();
                case 2:
                    return ThirdStep.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }

    /**
     * Создаем вкладки
     */
    public static class FirstStep extends Fragment {

        public static FirstStep newInstance() {
            return new FirstStep();
        }

        public FirstStep() {
        }

        TextWatcher textWatcher = null;
        int mStart;
        int mCount;
        int mAfter;

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_carwash_registration_first, container, false);
            final EditText phoneNumber = (EditText) rootView.findViewById(R.id.carwashPhone);
            textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    mStart=start;
                    mCount=count;
                    mAfter=after;
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    phoneNumber.removeTextChangedListener(textWatcher);
                    phoneNumber.setText((String) PhoneFormatting.formatPhone(s, mStart, mCount, mAfter).get("phone"));
                    phoneNumber.setSelection((int) PhoneFormatting.formatPhone(s, mStart, mCount, mAfter).get("selection"));
                    phoneNumber.addTextChangedListener(textWatcher);
                }
            };
            phoneNumber.addTextChangedListener(textWatcher);
            return rootView;
        }
    }
    public static class SecondStep extends Fragment implements OnMapReadyCallback {

        private SupportMapFragment supportMapFragment;
        private GoogleMap map;

        public static SecondStep newInstance() {
            return new SecondStep();
        }

        public SecondStep() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_carwash_registration_second, container, false);
            if (Build.VERSION.SDK_INT < 21) {
                supportMapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
            } else {
                supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            }
            supportMapFragment.getMapAsync(this);
            return rootView;
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            LatLng sydney = new LatLng(43.227714, 76.951365);
            map.addMarker(new MarkerOptions().position(sydney).title("Marker in FCB"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
        }
    }
    public static class ThirdStep extends Fragment {

        public static ThirdStep newInstance() {
            return new ThirdStep();
        }

        public ThirdStep() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_carwash_registration_third, container, false);
            return rootView;
        }
    }



}
