package com.mintegral.detailroi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.mintegral.detailroi.common.able.IUserEvent;
import com.mintegral.detailroi.event.UserEventManager;
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
        iapEventBean.setProductName("pn1");
        iapEventBean.setProductNum(2);
        iapEventBean.setPayStatus("ok");
        m.sendIAPEvent(iapEventBean);
    }
}