package com.mintegral.detailroi.event.bean;

import androidx.annotation.NonNull;

import com.mintegral.detailroi.common.able.IEventBussBean;
import com.mintegral.detailroi.common.base.NoProguard;
import com.mintegral.detailroi.event.out.IAPPayStateEnum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class IAPEventBean extends IEventBussBean implements NoProguard {

    private List<IAPItemPair> items;
    private String transactionId;
    private float amount;
    private String currency;
    private IAPPayStateEnum payStatus;
    private String failReason;



    public void setProductItems(List<IAPItemPair> items) {
        this.items = items;
        try {
            JSONArray jsonArray = new JSONArray();
            for (IAPItemPair p:items) {
                jsonArray.put(p.jsonObject);
            }
            jsonObject.put("items",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        try {
            jsonObject.put("transaction_id",transactionId);
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



    public void setPayStatus(IAPPayStateEnum payStatus) {
        this.payStatus = payStatus;
        try {
            jsonObject.put("paystatus",payStatus.getState());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
        try {
            jsonObject.put("fail_reason",failReason);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
