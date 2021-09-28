package com.mintegral.detailroi.event.bean;

import androidx.annotation.NonNull;

import com.mintegral.detailroi.common.able.IEventBussBean;
import com.mintegral.detailroi.common.base.NoProguard;

public class IAPEventBean implements IEventBussBean,NoProguard {

    private String productName;
    private int productNum;
    private float amount;
    private String currency;
    private String payStatus;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }


    @NonNull
    @Override
    public String toString() {
        return "IAPEventBean{" +
                "productName='" + productName + '\'' +
                ", productNum=" + productNum +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", payStatus='" + payStatus + '\'' +
                '}';
    }
    @Override
    public String toJsonTypeString() {
        return "{" +
                "\"productName\":'" + productName + '\'' +
                ", \"productNum\":" + productNum +
                ", \"amount\":" + amount +
                ", \"currency\":'" + currency + '\'' +
                ", \"payStatus\":'" + payStatus + '\'' +
                '}';
    }
}
