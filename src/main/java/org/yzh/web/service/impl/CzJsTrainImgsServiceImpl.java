package org.yzh.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.yzh.web.commons.config.RedisKeyConfig;
import org.yzh.web.mapper.CzJsFileAttachmentsMapper;
import org.yzh.web.mapper.CzJsStudentsMapper;
import org.yzh.web.mapper.CzJsTrainImgsMapper;
import org.yzh.web.model.entity.CzJsFileAttachments;
import org.yzh.web.model.entity.CzJsStudents;
import org.yzh.web.model.entity.CzJsTrainImgs;
import org.yzh.web.service.CzJsTrainImgsService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/10 16:40
 */
@Slf4j
@Service
public class CzJsTrainImgsServiceImpl implements CzJsTrainImgsService {
    @Autowired
    private CzJsTrainImgsMapper czJsTrainImgsMapper;

    @Autowired
    private CzJsStudentsMapper czJsStudentsMapper;

    @Autowired
    private CzJsFileAttachmentsMapper czJsFileAttachmentsMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<String> getImgList(int imgId) {
        List<String> imgList = new ArrayList<>();
        CzJsTrainImgs trainImgs = czJsTrainImgsMapper.selectByPrimaryKey(imgId);
        if(trainImgs != null){
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            String key = RedisKeyConfig.studentPhoto+trainImgs.getStunum();
            String studentPhoto = operations.get(key);
            if(studentPhoto == null){
                CzJsStudents students = czJsStudentsMapper.find("stunum = "+trainImgs.getStunum());
                studentPhoto = students != null && StringUtils.isNotEmpty(students.getPhoto())?students.getPhoto():"";
                operations.set(key,studentPhoto,60, TimeUnit.MINUTES);
            }
            if(StringUtils.isEmpty(studentPhoto)) return imgList;
            imgList.add(studentPhoto);
            CzJsFileAttachments fileAttachments = czJsFileAttachmentsMapper.selectByPrimaryKey(Integer.valueOf(trainImgs.getFileid()));
            if(fileAttachments != null){
                imgList.add(fileAttachments.getFilePath());
            }
        }
        return imgList;
    }

    @Override
    public void updateCheckResult(CzJsTrainImgs trainImgs) {
        czJsTrainImgsMapper.updateByPrimaryKeySelective(trainImgs);
    }

    @Override
    public void failCheck(int id, int checkType) {
        CzJsTrainImgs trainImgs = new CzJsTrainImgs();
        trainImgs.setId(id);
        trainImgs.setCheckType(checkType);
        trainImgs.setCheckStatus(2);
        trainImgs.setSimilarScore("0");
        this.updateCheckResult(trainImgs);
    }
}
