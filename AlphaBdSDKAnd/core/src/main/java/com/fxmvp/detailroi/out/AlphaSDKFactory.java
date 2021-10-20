package com.fxmvp.detailroi.out;


import com.fxmvp.detailroi.core.AlphaSDKImpl;

public class AlphaSDKFactory {
    private AlphaSDKFactory(){}
    static class Holder{
        private static final AlphaSDKImpl instance = new AlphaSDKImpl();
    }

    public static AlphaSDKImpl getAlphaSDK(){
        return Holder.instance;
    }
}
