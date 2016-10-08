package com.webservicesample.webserviceutils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


/**
 * Created by shashank.kapsime on 4/6/16.
 */

/**
 *  This class is an Async class to process the API calls.
 */
public class WebServiceAsync extends AsyncTask {

    private String request;
    private WebServiceStatus serviceStatus;
    private String url, methodName;
    private Context context;
    private ProgressDialog progressDialog;
    private boolean showProgress = true;
    private ServiceCall serviceCall;

    public WebServiceAsync(Context context, String request, String url, String methodName,
                           boolean showProgress, WebServiceStatus serviceStatus) {
        this.request = request;
        this.serviceStatus = serviceStatus;
        this.url = url;
        this.methodName = methodName;
        this.context = context;
        this.showProgress = showProgress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            if (showProgress) {
              // progressDialog = ProgressDialog.show(context,"","Please wait...");
                progressDialog = ProgressDialog.show(context,"", "Please wait...");
                progressDialog.show();
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {

        serviceCall = new ServiceCall();
        String response = serviceCall.getServiceResponse(url, request, methodName);
        try {
            if (response != null) {
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (o != null) {
            serviceStatus.onSuccess(o);
        } else {
            serviceStatus.onFailed("Error");
        }
    }
}
