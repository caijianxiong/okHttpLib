package com.cjx.okhttp.builder;

import com.cjx.okhttp.request.PostStringRequest;
import com.cjx.okhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * 上传json串信息
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder> {
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostStringRequest(url, tag, params, headers, content, mediaType, id).build();
    }


}
