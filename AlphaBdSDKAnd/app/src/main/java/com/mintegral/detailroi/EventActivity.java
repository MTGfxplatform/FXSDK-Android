package com.mintegral.detailroi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mintegral.detailroi.common.able.IUserEvent;
import com.mintegral.detailroi.event.UserEventManager;
import com.mintegral.detailroi.event.bean.IAPEventBean;
import com.mintegral.detailroi.out.AlphaSDKFactory;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        UserEventManager m = (UserEventManager) AlphaSDKFactory.getAlphaSDK().getUserEventManager();
        IAPEventBean iapEventBean = new IAPEventBean();

        m.sendIAPEvent(iapEventBean);
    }
}