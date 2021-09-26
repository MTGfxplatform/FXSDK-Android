package com.mintegral.detailroi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mintegral.detailroi.out.AlphaSDKFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlphaSDKFactory.getAlphaSDK().init(getApplication(),"7777");
    }
}