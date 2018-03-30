package com.maikel.blutoothvoip.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by zWX396902 on 2018/3/30.
 */

public class HttpRequest {
    private static final int HTTP_RESULT = 1;
    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HttpResponse response = (HttpResponse) msg.obj;
            if (response == null){
                response = new HttpResponse("server return null",HttpCodes.HTTP_FAILURE);
            }
            httpResponse(response.getResponse(),response.getCode());
        }
    };
    private ResponseListener listener;
    private boolean isResponseOnMainThread = false;
    private String url;
    private HttpHead mHead;
    private HttpMethod mHttpMethod = HttpMethod.GET;
    IHttpManager httpManager = HttpManagerImpl.getInstance();

    public HttpRequest(String url) {
        this.url = url;
    }

    public HttpRequest setHead(HttpHead head) {
        mHead = head;
        return this;
    }

    public HttpHead getHead() {
        return mHead;
    }

    public String getUrl() {
        return url;
    }

    public HttpRequest setMethod(HttpMethod method) {
        mHttpMethod = method;
        return this;
    }

    public HttpMethod getHttpMethod() {
        return mHttpMethod;
    }

    public HttpRequest setResponseOnMainThread(boolean onMainThread) {
        isResponseOnMainThread = onMainThread;
        return this;
    }

    public HttpRequest setResponseListener(ResponseListener l) {
        this.listener = l;
        return this;
    }

    public ResponseListener getResponseListener() {
        return listener;
    }

    public void submitRequest() {
        httpManager.submmitRequest(this);
    }

    public void onResponse(HttpResponse response) {
        HttpResponse tem = response;
        if (isResponseOnMainThread){
            mHandler.obtainMessage(HTTP_RESULT, tem).sendToTarget();
        }else {
            httpResponse(tem.getResponse(),tem.getCode());
        }
    }
    private void httpResponse(String response,int code){
        if (code == HttpCodes.HTTP_OK){
            if (listener!=null){
                listener.onSuccess(response);
            }
        }else if (code == HttpCodes.HTTP_FAILURE){
            listener.onFailure(response);
        }
    }

   public interface ResponseListener {
        void onSuccess(String response);

        void onFailure(String error);
    }


}
