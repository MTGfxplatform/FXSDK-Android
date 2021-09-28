package com.mintegral.detailroi;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mintegral.detailroi.out.AlphaSDKFactory;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlphaSDKFactory.getAlphaSDK().init(getApplication(),"channel_888","appid_12345");
        Intent intent = new Intent(this,EventActivity.class);
        startActivity(intent);
    }
}