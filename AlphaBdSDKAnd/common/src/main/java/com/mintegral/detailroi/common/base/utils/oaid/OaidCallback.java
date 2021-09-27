/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2014-2019. All rights reserved.
 */

package com.mintegral.detailroi.common.base.utils.oaid;

public interface OaidCallback {
    void onSuccuss(String oaid, boolean isOaidTrackLimited);

    void onFail(String errMsg);
}
