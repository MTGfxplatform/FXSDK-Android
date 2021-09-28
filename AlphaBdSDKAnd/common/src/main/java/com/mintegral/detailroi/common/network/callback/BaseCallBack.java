package com.mintegral.detailroi.common.network.callback;

import com.mbridge.msdk.thrid.okhttp.Call;
import com.mbridge.msdk.thrid.okhttp.Callback;
import com.mbridge.msdk.thrid.okhttp.Response;

import java.io.IOException;

public abstract class BaseCallBack implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        onFailed(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(response.isSuccessful() && response.body() != null ){
            onSucceed(response.body().string());
        }else {
            onFailed(response.message());
        }
    }

    protected abstract void onFailed(String msg);
    public abstract void onSucceed(String responseStr);

}
