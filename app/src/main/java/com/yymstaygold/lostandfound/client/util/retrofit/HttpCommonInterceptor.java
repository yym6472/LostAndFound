package com.yymstaygold.lostandfound.client.util.retrofit;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpCommonInterceptor implements Interceptor {

    private static final String TAG = "HttpCommonInterceptor";

    private Map<String, String> headerParamsMap = new HashMap<>();

    public HttpCommonInterceptor() {}

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(TAG, "add common params");
        Request oldRequest = chain.request();
        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(), oldRequest.body());

        if (headerParamsMap.size() > 0) {
            for (Map.Entry<String, String> params : headerParamsMap.entrySet()) {
                requestBuilder.header(params.getKey(), params.getValue());
            }
        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }

    public static class Builder {
        HttpCommonInterceptor httpCommonInterceptor;
        public Builder() {
            httpCommonInterceptor = new HttpCommonInterceptor();
        }
        public Builder addHeaderParams(String key, String value) {
            httpCommonInterceptor.headerParamsMap.put(key, value);
            return this;
        }
        public Builder addHeaderParams(String key, int value) {
            return addHeaderParams(key, String.valueOf(value));
        }
        public Builder addHeaderParams(String key, float value) {
            return addHeaderParams(key, String.valueOf(value));
        }
        public Builder addHeaderParams(String key, double value) {
            return addHeaderParams(key, String.valueOf(value));
        }
        public Builder addHaderParams(String key, long value) {
            return addHeaderParams(key, String.valueOf(value));
        }
        public HttpCommonInterceptor build() {
            return httpCommonInterceptor;
        }
    }
}
