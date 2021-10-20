package com.fxmvp.detailroi.event.out;

public enum IAPPayStateEnum {
    success("success"),
    fail("fail"),
    restored("restored");
    private final String state;
    private IAPPayStateEnum(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
