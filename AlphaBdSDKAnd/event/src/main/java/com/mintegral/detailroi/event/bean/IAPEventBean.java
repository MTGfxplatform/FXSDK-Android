package com.mintegral.detailroi.event.bean;

import androidx.annotation.NonNull;

import com.mintegral.detailroi.common.able.IEventBussBean;
import com.mintegral.detailroi.common.base.NoProguard;

import org.json.JSONException;
import org.json.JSONObject;

public class IAPEventBean extends IEventBussBean implements NoProguard {

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
        try {
            jsonObject.put("productName",productName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
        try {
            jsonObject.put("productNum",productNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
        try {
            jsonObject.put("amount",amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
        try {
            jsonObject.put("currency",currency);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
        try {
            jsonObject.put("payStatus",payStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
