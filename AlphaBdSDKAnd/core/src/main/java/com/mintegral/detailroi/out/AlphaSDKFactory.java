package com.mintegral.detailroi.out;


import com.mintegral.detailroi.core.AlphaSDKImpl;

public class AlphaSDKFactory {
    private AlphaSDKFactory(){}
    static class Holder{
        private static final AlphaSDKImpl instance = new AlphaSDKImpl();
    }

    public static AlphaSDKImpl getAlphaSDK(){
        return Holder.instance;
    }
}
