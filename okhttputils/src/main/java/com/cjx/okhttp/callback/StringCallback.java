package com.cjx.okhttp.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class StringCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {
        assert response.body() != null;
        return response.body().string();
    }
}
