package com.webservicesample.webserviceutils;


abstract public class WebServiceStatus {

    public abstract  void onSuccess(Object o);
    public abstract  void onFailed(Object o);
}