package org.yzh.web.service;

import org.yzh.web.model.entity.JsPlatLogin;

public interface JsPlatLoginService {

    JsPlatLogin findById(int id);

    int insert(JsPlatLogin record);

}