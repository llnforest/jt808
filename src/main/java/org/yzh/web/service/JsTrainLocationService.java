package org.yzh.web.service;

import org.yzh.protocol.t808.T0200;

public interface JsTrainLocationService {
    void insert(T0200 t0200, String mobile, int type,int tableId);
}
