package com.maikel.blutoothvoip.http;

/**
 * Created by maikel on 2018/3/30.
 */

public class HttpStringRequest extends HttpRequest {
    public HttpStringRequest(String url) {
        super(url);
    }

    @Override
    public void submitRequest() {
        super.submitRequest();
    }

    @Override
    BaseTask buildTask() {
        return new HttpStringTask(this);
    }
}
