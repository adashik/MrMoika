package com.lessugly.mrmoika.registration;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lessugly.mrmoika.R;

public class CarwashRegistration extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carwash_registration);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Шаг 1. Введите свои данные");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = mViewPager.getCurrentItem();
                switch (currentPage){
                    case 0:
                        CarwashRegistration.this.finish();
                        break;
                    case 1:
                        mViewPager.setCurrentItem(currentPage-1);
                        break;
                    case 2:
                        mViewPager.setCurrentItem(currentPage-1);
                        break;
                }
            }
        });


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
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
                        toolbar.setTitle("Шаг 1. Введите свои данные");
                        break;
                    case 1:
                        toolbar.setTitle("Шаг 2. Укажите местоположение");
                        break;
                    case 2:
                        toolbar.setTitle("Шаг 3. Проверка введенных данных ");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Button buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
            }
        });



    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return FirstStep.newInstance(position + 1);
                case 1:
                    return SecondStep.newInstance(position + 1);
                case 2:
                    return ThirdStep.newInstance(position + 1);
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "STEP 1";
                case 1:
                    return "STEP 2";
                case 2:
                    return "STEP 3";
            }
            return null;
        }
    }

    /**
     * Создаем вкладки
     */
    public static class FirstStep extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static FirstStep newInstance(int sectionNumber) {
            FirstStep fragment = new FirstStep();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public FirstStep() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_carwash_registration_first, container, false);
            return rootView;
        }
    }
    public static class SecondStep extends Fragment {

        private SupportMapFragment fragment;
        private GoogleMap map;
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static SecondStep newInstance(int sectionNumber) {
            SecondStep fragment = new SecondStep();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public SecondStep() {
        }
        private SupportMapFragment mSupportMapFragment;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_carwash_registration_second, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            FragmentManager fm = getChildFragmentManager();
            fragment = (SupportMapFragment) fm.findFragmentById(R.id.mapwhere);
            if (fragment == null) {
                fragment = SupportMapFragment.newInstance();
                fm.beginTransaction().replace(R.id.mapwhere, fragment).commit();
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            if (map == null) {
                map = fragment.getMap();
                map.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
            }
        }
    }
    public static class ThirdStep extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ThirdStep newInstance(int sectionNumber) {
            ThirdStep fragment = new ThirdStep();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
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
