package com.cjx.okhttp.callback;

import com.cjx.okhttp.LibApplication;
import com.cjx.okhttp.callback.Callback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

/**
 * @author caicai
 * @create 2019/7/25
 * @Describe
 */
public abstract class ClassFormatCallBack<T> extends Callback<T> {

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) string;
        }
        T bean = LibApplication.getInstance().getmGson().fromJson(string, entityClass);
        return bean;
    }
}
