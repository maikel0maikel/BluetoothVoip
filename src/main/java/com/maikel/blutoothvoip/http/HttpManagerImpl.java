package com.maikel.blutoothvoip.http;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zWX396902 on 2018/3/30.
 */

public class HttpManagerImpl implements IHttpManager {
    private static IHttpManager httpManager = new HttpManagerImpl();

    private static final ThreadFactory tf = new ThreadFactory() {
        private final AtomicInteger atomicInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "HttpManagerImpl#" + atomicInteger.getAndIncrement());
        }
    };
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 5, 0l, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), tf);
    private HttpManagerImpl(){}
    static IHttpManager getInstance(){
        return httpManager;
    }

    @Override
    public void submmitRequest(final HttpRequest request) {
        final HttpTask task = new HttpTask(request);
        FutureTask<HttpResponse> futureTask = new FutureTask<HttpResponse>(task) {
            @Override
            protected void done() {
                HttpResponse response;
                try {
                    response = get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    response = new HttpResponse(e.getMessage(),HttpCodes.HTTP_FAILURE);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    response = new HttpResponse(e.getMessage(),HttpCodes.HTTP_FAILURE);
                }
                request.onResponse(response);
            }
        };
        threadPoolExecutor.execute(futureTask);
    }

    @Override
    public void get(String url, String headKey, String headValue) {
    }

    @Override
    public void post(String url, Map<String, String> params) {

    }

    @Override
    public void post(String url, String json) {

    }
}
