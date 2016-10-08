package com.webservicesample;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.webservicesample.parsers.ServiceResponse;
import com.webservicesample.webserviceutils.WebServiceAsync;
import com.webservicesample.webserviceutils.WebServiceStatus;

public class MainActivity extends AppCompatActivity {

    private Context mContext = MainActivity.this;
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.print("Hello World");

        if (isOnline(mContext)) {
            callAnAPI();
        }

    }

    /*
    * Here we are using a google geocoder API to see how we can integrate rest API and parse
     * data using GSON library.
    * */
    private void callAnAPI() {
        String googleGeocoderURL = "http://maps.google.com/maps/api/geocode/json?latlng=28.525354,77.284634";
        Log.e(TAG, " **googleGeocoderURL** " + googleGeocoderURL);
        WebServiceAsync serviceAsync = new WebServiceAsync(mContext, "", googleGeocoderURL, "GET", true, new WebServiceStatus() {
            @Override
            public void onSuccess(Object o) {
                ServiceResponse response = new Gson().fromJson(o.toString(), ServiceResponse.class);
                if (response != null) {

                    // here we can parse the response
                }
            }

            @Override
            public void onFailed(Object o) {

            }
        });
    }

    private boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }
}
