package com.mintegral.detailroi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.mintegral.detailroi.event.out.IAPPayStateEnum;
import com.mintegral.detailroi.event.out.UserEventManager;
import com.mintegral.detailroi.event.bean.IAPEventBean;
import com.mintegral.detailroi.out.AlphaSDKFactory;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

    }
    public void click(View view){
        UserEventManager m = (UserEventManager) AlphaSDKFactory.getAlphaSDK().getUserEventManager();
        IAPEventBean iapEventBean = new IAPEventBean();
        iapEventBean.setAmount(1.0f);
        iapEventBean.setCurrency("ccc");
        iapEventBean.setPayStatus(IAPPayStateEnum.success);
        m.sendIAPEvent(iapEventBean);
    }

    public void toDebugT(View view){
        AlphaSDKFactory.getAlphaSDK().enDebug(true);
    }
    public void toDebugF(View view){
        AlphaSDKFactory.getAlphaSDK().enDebug(false);
    }
}