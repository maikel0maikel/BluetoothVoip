package com.maikel.blutoothvoip.http;

/**
 * Created by maikel on 2018/3/30.
 */

public class HttpResponse {
    private String response;
    private int code;
    public HttpResponse(){}
    public HttpResponse(String response, int code) {
        this.response = response;
        this.code = code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
