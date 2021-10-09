package com.mintegral.detailroi.common.able;

import org.json.JSONObject;

public interface IUserEvent {
    void sendCustomEvent(String name,JSONObject jsonObject);
}
