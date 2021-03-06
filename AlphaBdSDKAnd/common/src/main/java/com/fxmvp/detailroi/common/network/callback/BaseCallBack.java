package com.fxmvp.detailroi.common.network.callback;

import android.text.TextUtils;

import com.mbridge.alpha.thrid.okhttp.Call;
import com.mbridge.alpha.thrid.okhttp.Callback;
import com.mbridge.alpha.thrid.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public abstract class BaseCallBack implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        onFailed(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(response.isSuccessful() && response.body() != null ){
            String result = response.body().string();
            if(TextUtils.isEmpty(result)){
                onFailed("response body is null");
            }else {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getInt("code") != 200){
                        onFailed(jsonObject.optString("msg"));
                    }else {
                        onSucceed(jsonObject.optJSONObject("data"));
                    }
                } catch (JSONException e) {
                    onFailed("response body parse exception" +e.getMessage());
                    e.printStackTrace();
                }

            }

        }else {
            onFailed(response.message());
        }
    }

    protected abstract void onFailed(String msg);
    public abstract void onSucceed(JSONObject responseJsonObject);

}
