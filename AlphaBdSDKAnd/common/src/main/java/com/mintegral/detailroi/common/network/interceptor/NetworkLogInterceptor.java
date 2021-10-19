package com.mintegral.detailroi.common.network.interceptor;

import static com.mbridge.alpha.thrid.okhttp.internal.Util.UTF_8;

import com.mbridge.alpha.thrid.okhttp.Interceptor;
import com.mbridge.alpha.thrid.okhttp.MediaType;
import com.mbridge.alpha.thrid.okhttp.Request;
import com.mbridge.alpha.thrid.okhttp.RequestBody;
import com.mbridge.alpha.thrid.okhttp.Response;
import com.mbridge.alpha.thrid.okhttp.ResponseBody;
import com.mbridge.alpha.thrid.okio.Buffer;
import com.mbridge.alpha.thrid.okio.BufferedSource;
import com.mintegral.detailroi.common.base.utils.SameLogTool;

import java.io.IOException;
import java.nio.charset.Charset;

public class NetworkLogInterceptor implements Interceptor {
    public static final String TAG = "NetWorkLogger";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        printRequestMessage(request);
        Response response = chain.proceed(request);
        printResponseMessage(response);
        return response;
    }
    private void printRequestMessage(Request request) {
        if (request == null) {
            return;
        }
        SameLogTool.d(TAG, "Url   : " + request.url().url().toString());
        SameLogTool.d(TAG, "Method: " + request.method());
        SameLogTool.d(TAG, "Heads : " + request.headers());
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            return;
        }
        try {
            Buffer bufferedSink = new Buffer();
            requestBody.writeTo(bufferedSink);
            Charset charset = requestBody.contentType().charset();
            charset = charset == null ? Charset.forName("utf-8") : charset;
            SameLogTool.d(TAG, "Params: " + bufferedSink.readString(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 打印返回消息
     *
     * @param response 返回的对象
     */
    private void printResponseMessage(Response response) {
        if (response == null || !response.isSuccessful()) {
            return;
        }
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        Charset charset = UTF_8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset();
        }
        if (contentLength != 0) {
            String result = buffer.clone().readString(charset);
            SameLogTool.d(TAG, "Response: " + result);
        }
    }
}
