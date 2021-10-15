package com.mintegral.detailroi.common.able;

import org.json.JSONObject;

public interface IUserEvent {
    void track(String name, JSONObject jsonObject);
}
