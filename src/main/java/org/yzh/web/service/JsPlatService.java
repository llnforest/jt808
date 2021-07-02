package org.yzh.web.service;

import org.yzh.web.model.entity.JsPlat;

public interface JsPlatService {

    JsPlat findById(int id);

    int update(JsPlat record);

}