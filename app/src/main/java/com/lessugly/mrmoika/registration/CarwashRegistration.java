package com.lessugly.mrmoika.registration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lessugly.mrmoika.R;
import com.lessugly.mrmoika.util.CheckInternet;
import com.lessugly.mrmoika.util.NonSwipeableViewPager;
import com.lessugly.mrmoika.util.PhoneFormatting;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import cz.msebera.android.httpclient.Header;

public class CarwashRegistration extends AppCompatActivity {

    private NonSwipeableViewPager viewPager;
    private static Button buttonNext;
    private static Toolbar toolbar;
    private static String regPhone = "";
    private static String regName = "";
    private static LatLng regLocation;
    private static String regAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carwash_registration);
        viewPager = (NonSwipeableViewPager) findViewById(R.id.container);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        toolbar.setTitle(R.string.step_one);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                switch (currentPage) {
                    case 0:
                        openQuitDialog();
                        break;
                    case 1:
                        viewPager.setCurrentItem(currentPage - 1);
                        break;
                    case 2:
                        if (!ThirdStep.carwashPhone.getText().toString().equals(regPhone)
                                || !ThirdStep.carwashName.getText().toString().equals(regName)) {
                            regPhone = ThirdStep.carwashPhone.getText().toString();
                            regName = ThirdStep.carwashName.getText().toString();
                            regAddress = ThirdStep.carwashAddress.getText().toString();
                        }
                        viewPager.setCurrentItem(currentPage - 1);
                        break;
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        FirstStep.carwashName.setText(regName);
                        FirstStep.carwashPhone.setText(regPhone);
                        toolbar.setTitle(R.string.step_one);
                        buttonNext.setText(R.string.registration_button_next);
                        break;
                    case 1:
                        toolbar.setTitle(R.string.step_two);
                        buttonNext.setText(R.string.registration_button_next);
                        break;
                    case 2:
                        ThirdStep.carwashName.setText(regName);
                        ThirdStep.carwashPhone.setText(regPhone);
                        if (ThirdStep.carwashAddress.getText().toString().equals(""))
                        ThirdStep.carwashAddress.setText(regAddress);
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
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        attemptMoveToNextStep(FirstStep.carwashName, FirstStep.carwashPhone, 0);
                        break;
                    case 1:
                        if (CheckInternet.getInstance(getApplicationContext()).isOnline())
                        SecondStep.setRegAddress(getApplicationContext(), regLocation);
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                        break;
                    case 2:
                        regPhone = ThirdStep.carwashPhone.getText().toString();
                        regName = ThirdStep.carwashName.getText().toString();
                        regAddress = ThirdStep.carwashAddress.getText().toString();
                        attemptMoveToNextStep(ThirdStep.carwashName, ThirdStep.carwashPhone, 2);
                        break;
                }
            }
        });

    }

    private void attemptMoveToNextStep(EditText carwashName, EditText carwashPhone, int pagerPosition) {
        carwashName.setError(null);
        carwashPhone.setError(null);
        String name = carwashName.getText().toString();
        String phone = carwashPhone.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (phone.length() != 15){
            carwashPhone.setError(getString(R.string.error_wrong_phone_number));
            focusView = carwashPhone;
            cancel = true;
        }
        else
        if (TextUtils.isEmpty(name)){
            carwashName.setError(getString(R.string.error_empty_carwash_name));
            focusView = carwashName;
            cancel = true;
        }

        if (cancel) focusView.requestFocus();
        else
        if (pagerPosition == 0){
            if (!FirstStep.carwashPhone.getText().toString().equals(regPhone)
                    || !FirstStep.carwashName.getText().toString().equals(regName)) {
                regPhone = FirstStep.carwashPhone.getText().toString();
                regName = FirstStep.carwashName.getText().toString();
            }
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        }
        else if (pagerPosition == 2) carwashRegistration();
    }

    private void carwashRegistration() {
        showProgress(true);
        RequestParams params = new RequestParams();
        params.put("phone",PhoneFormatting.phoneClear(regPhone));
        params.put("name",regName);
        params.put("address", regAddress);
        params.put("latitude",String.valueOf(regLocation.latitude));
        //params.put("longitude",String.valueOf(regLocation.longitude));

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://212.154.211.77:8080/backend/registration/carwash", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                showProgress(false);
                Toast.makeText(getApplicationContext(),"Success! ResponseCode: "+new String(bytes),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                showProgress(false);
                Toast.makeText(getApplicationContext(),"Fail! "+i,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            ThirdStep.regFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ThirdStep.regFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            ThirdStep.progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ThirdStep.progressView.setVisibility(show ? View.VISIBLE : View.GONE);

                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            ThirdStep.progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            ThirdStep.regFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        int currentPage = viewPager.getCurrentItem();
        switch (currentPage) {
            case 0:
                openQuitDialog();
                break;
            case 1:
                viewPager.setCurrentItem(currentPage - 1);
                break;
            case 2:
                if (!ThirdStep.carwashPhone.getText().toString().equals(regPhone)
                        || !ThirdStep.carwashName.getText().toString().equals(regName)){
                    regPhone = ThirdStep.carwashPhone.getText().toString();
                    regName = ThirdStep.carwashName.getText().toString();
                    regAddress = ThirdStep.carwashAddress.getText().toString();
                }
                viewPager.setCurrentItem(currentPage - 1);
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

        private static EditText carwashPhone;
        private static EditText carwashName;
        TextWatcher textWatcher = null;
        int mStart;
        int mCount;
        int mAfter;

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_carwash_registration_first, container, false);
            carwashName = (EditText) rootView.findViewById(R.id.carwashName);
            carwashPhone = (EditText) rootView.findViewById(R.id.carwashPhone);
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
                    carwashPhone.removeTextChangedListener(textWatcher);
                    carwashPhone.setText((String) PhoneFormatting.formatPhone(s, mStart, mCount, mAfter).get("phone"));
                    carwashPhone.setSelection((int) PhoneFormatting.formatPhone(s, mStart, mCount, mAfter).get("selection"));
                    carwashPhone.addTextChangedListener(textWatcher);
                }
            };
            carwashPhone.addTextChangedListener(textWatcher);
            return rootView;
        }

    }
    public static class SecondStep extends Fragment implements OnMapReadyCallback {

        private SupportMapFragment supportMapFragment;
        private static GoogleMap map;
        private static Geocoder geocoder;
        private static List<Address> addresses = null;
        private boolean locFinded = false;

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

            LatLng almaty = new LatLng(43.240227, 76.921157);
            map.addMarker(new MarkerOptions().position(almaty).title("Алматы"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(almaty, 15));
            regLocation = almaty;
            map.setMyLocationEnabled(true);
            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    if (!locFinded) {
                        map.clear();
                        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                        map.addMarker(new MarkerOptions().position(loc).title("Вы здесь"));
                        if (map != null) {
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                        }
                        if (loc != null) {
                            locFinded = true;
                            map.setMyLocationEnabled(false);
                            regLocation = loc;
                        }
                    }
                }
            });
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    map.clear();
                    map.addMarker(new MarkerOptions().position(latLng).title("Вы здесь"));
                    if (map != null) map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    regLocation = latLng;
                }
            });

        }

        public static void setRegAddress (Context context, LatLng latLng){
            try {
                geocoder = new Geocoder(context, Locale.getDefault());
                addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
                regAddress = addresses.get(0).getAddressLine(0)+", "+addresses.get(0).getAddressLine(1);
            } catch (IOException e) {}
        }

        public static GoogleMap getMap() {
            return map;
        }

    }



    public static class ThirdStep extends Fragment {

        public static ThirdStep newInstance() {
            return new ThirdStep();
        }

        public ThirdStep() {
        }

        private static EditText carwashPhone;
        private static EditText carwashName;
        private static EditText carwashAddress;
        private static View progressView;
        private static View regFormView;

        TextWatcher textWatcher = null;
        int mStart;
        int mCount;
        int mAfter;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_carwash_registration_third, container, false);
            carwashName = (EditText) rootView.findViewById(R.id.carwashName);
            carwashPhone = (EditText) rootView.findViewById(R.id.carwashPhone);
            carwashAddress = (EditText) rootView.findViewById(R.id.carwashAddress);
            progressView = rootView.findViewById(R.id.login_progress);
            regFormView = rootView.findViewById(R.id.registration_form);
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
                    carwashPhone.removeTextChangedListener(textWatcher);
                    carwashPhone.setText((String) PhoneFormatting.formatPhone(s, mStart, mCount, mAfter).get("phone"));
                    carwashPhone.setSelection((int) PhoneFormatting.formatPhone(s, mStart, mCount, mAfter).get("selection"));
                    carwashPhone.addTextChangedListener(textWatcher);
                }
            };
            carwashPhone.addTextChangedListener(textWatcher);
            return rootView;
        }
    }



}
