package org.yzh.web.service;

import org.yzh.web.model.entity.CzJsDevices;
import org.yzh.web.model.entity.CzJsTrainImgs;

import java.util.List;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/10 16:35
 */
public interface CzJsTrainImgsService{
    List<String> getImgList(int imgId);
    void updateCheckResult(CzJsTrainImgs trainImgs);
    void failCheck(int id,int checkType);
}
