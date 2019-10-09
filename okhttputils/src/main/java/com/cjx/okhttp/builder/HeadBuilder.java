package com.cjx.okhttp.builder;

import com.cjx.okhttp.OkHttpUtils;
import com.cjx.okhttp.request.OtherRequest;
import com.cjx.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
