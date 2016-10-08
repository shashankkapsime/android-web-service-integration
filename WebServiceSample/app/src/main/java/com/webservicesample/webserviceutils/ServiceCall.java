package com.webservicesample.webserviceutils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shashank.kapsime on 31/5/16.
 */

/**
 * This class is used to do the API call using the HttpURLConnection.
 */
public class ServiceCall {

    private final static int CONNECTION_TIMEOUT = 10000;
    private int statusCode = 0;
    private final String TAG = ServiceCall.class.getSimpleName();

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * This method is used to do the API call using the HttpURLConnection.
     */
    public String getServiceResponse(String requestUrl, String request, String methodName) {

        Log.e(TAG, "URL" + requestUrl);
        Log.e(TAG, " **RequestParameter** " + new Gson().toJson(request));
        HttpURLConnection conn = null;
        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.addRequestProperty("Content-Type", "application/json");
            if (methodName.equalsIgnoreCase("POST") || methodName.equalsIgnoreCase("PUT")) {
                conn.setRequestMethod(methodName);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(request);
                writer.flush();
                writer.close();
                os.close();
            } else if (methodName.equalsIgnoreCase("GET")) {
                conn.setRequestMethod("GET");
            } else if (methodName.equalsIgnoreCase("DELETE")) {
                conn.setRequestMethod("DELETE");
            }
            int status = conn.getResponseCode();
            setStatusCode(status);
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String response = readStream(in);
            Log.e("ServiceCall", "response" + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        return null;
    }

    /**
     * This method is used to do the API call using the HttpURLConnection.
     */

    public String getServiceResponseWithAuthentication(String requestUrl, String request, String methodName, Context mContext) {
        Log.e(TAG, "URL" + requestUrl);
        HttpURLConnection conn = null;
        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.addRequestProperty("Content-Type", "application/json");
            /*
            * these two are required if we have to pass authentication tokens in header.
            * Here X-User-Email and X-User-Token is key of authentication which is to be passed in header.
            * */
            conn.addRequestProperty("X-User-Email", "TOKEN");
            conn.addRequestProperty("X-User-Token", "TOKEN");

            if (methodName.equalsIgnoreCase("POST")
                    || methodName.equalsIgnoreCase("PUT")) {
                conn.setRequestMethod(methodName);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(request);
                writer.flush();
                writer.close();
                os.close();
            } else if (methodName.equalsIgnoreCase("GET")) {
                conn.setRequestMethod("GET");
            } else if (methodName.equalsIgnoreCase("DELETE")) {
                conn.setRequestMethod("DELETE");
            }
            int status = conn.getResponseCode();
            setStatusCode(status);
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String response = readStream(in);
            Log.e("ServiceCall", "response" + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        return null;
    }

    /**
     * This method is used to read the stream received from server.
     */
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

}