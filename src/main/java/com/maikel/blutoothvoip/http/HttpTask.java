package com.maikel.blutoothvoip.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Callable;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by maikel on 2018/3/30.
 */

public class HttpTask implements Callable<HttpResponse> {
    private static final String TAG = "HttpTask";
    private static final int READ_TIME_OUT = 1000 * 10;
    private static final int CONNECT_TIME_OUT = READ_TIME_OUT;
    private HttpRequest mRequest;

    HttpTask(HttpRequest request) {
        this.mRequest = request;
    }


    private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private static void trustAllHosts() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Log.i(TAG, "checkClientTrusted");
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Log.i(TAG, "checkServerTrusted");
            }
        }};
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public HttpResponse call() throws Exception {
        if (this.mRequest == null) {
            return new HttpResponse("request is null", HttpCodes.HTTP_FAILURE);
        }
        if (this.mRequest.getUrl() == null || this.mRequest.getUrl().length() == 0) {

            return new HttpResponse("your url is empty please check", HttpCodes.HTTP_FAILURE);
        }
        BufferedReader bufferedReader = null;
        HttpURLConnection con = null;
        HttpResponse response = new HttpResponse();
        int code = 0;
        String result = null;
        try {
            URL url = new URL(mRequest.getUrl());
            if (url.getProtocol().toLowerCase().equals("https")) {
                trustAllHosts();
                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                con = https;
            } else {
                con = (HttpURLConnection) url.openConnection();
            }
            if (mRequest.getHttpMethod() == HttpMethod.GET) {
                con.setRequestMethod("GET");
            } else if (mRequest.getHttpMethod() == HttpMethod.POST) {
                con.setRequestMethod("POST");
            }
            if (mRequest.getHead() != null) {
                con.setRequestProperty(mRequest.getHead().getKey(), mRequest.getHead().getValue());
            }
            con.setConnectTimeout(CONNECT_TIME_OUT);
            con.setReadTimeout(READ_TIME_OUT);
            InputStream is = con.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is));
            String readLine = null;
            StringBuilder builder = new StringBuilder();
            while ((readLine = bufferedReader.readLine()) != null) {
                builder.append(readLine);
            }
            con.disconnect();
            result = builder.toString();
            code = HttpCodes.HTTP_OK;
            builder.setLength(0);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = e.getMessage();
            code = HttpCodes.HTTP_FAILURE;
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();
            code = HttpCodes.HTTP_FAILURE;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        response.setResponse(result);
        response.setCode(code);
        return response;
    }
}
