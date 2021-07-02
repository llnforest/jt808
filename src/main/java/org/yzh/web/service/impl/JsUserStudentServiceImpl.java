package org.yzh.web.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.yzh.framework.commons.Const;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.BeanHelper;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.mapper.*;
import org.yzh.web.model.entity.*;
import org.yzh.web.service.JsTrainLocationService;
import org.yzh.web.service.JsUserStudentService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class JsUserStudentServiceImpl implements JsUserStudentService {

    private static final Logger log = LoggerFactory.getLogger(JsUserStudentServiceImpl.class.getSimpleName());

    @Autowired
    private JsUserStudentMapper jsUserStudentMapper;

    @Autowired
    private JsDeviceMapper jsDeviceMapper;
    @Autowired
    private JsTrainRecordMapper jsTrainRecordMapper;
    @Autowired
    private JsUserCoachMapper jsUserCoachMapper;
    @Autowired
    private JsUserStudentLoginMapper jsUserStudentLoginMapper;


    @Override
    public T8900_0900_student_login_answer studentLogin(T8900_0900_student_login data,int serialNo) {
        T8900_0900_student_login_answer answer = new T8900_0900_student_login_answer(serialNo, data.getHeader().getMobileNo());
        try{
            JsUserStudent jsUserStudent = jsUserStudentMapper.selectByStunum(data.getStudentNo());
            if(jsUserStudent == null){
                answer.setLoginResult(2); //无效的学员编号
            }else if(jsUserStudent.getStatus() == 2){
                answer.setLoginResult(3); //禁止登录的学员
            }else{
                JsUserCoach jsCoach = jsUserCoachMapper.isCoachLogin(data.getCoachNo());
                if(jsCoach == null){
                    answer.setLoginResult(9); //其他
                    return answer;
                }
                if(!jsCoach.getTeachpermitted().equals(jsUserStudent.getTraintype())){
                    answer.setLoginResult(5);//准教车型与培训车型不符
                    return answer;
                }
                JsDevice jsDevice = jsDeviceMapper.getByDevnum(data.getTerminalNo());
                //允许登录(待完善添加电子教学日志)
                JsTrainRecord jsTrainRecord = new JsTrainRecord();
                jsTrainRecord.setInscode(jsUserStudent.getInscode());
                jsTrainRecord.setStunum(jsUserStudent.getStunum());
                jsTrainRecord.setInscode(jsUserStudent.getInscode());
                jsTrainRecord.setCoachnum(data.getCoachNo());
                jsTrainRecord.setCarnum(jsDevice.getBindCarnum());
                jsTrainRecord.setDevnum(jsDevice.getDevnum());
                jsTrainRecord.setPlatnum(Const.getPlatNum());
                jsTrainRecord.setSubjcode(data.getClassName());
                jsTrainRecord.setClassCode(Integer.valueOf(data.getClassName().substring(0,1)));
                jsTrainRecord.setTrainCode(data.getClassName().substring(1,3));
                jsTrainRecord.setPartCode(Integer.valueOf(data.getClassName().substring(3,4)));
                jsTrainRecord.setProjectCode(data.getClassName().substring(4,6));
                jsTrainRecord.setClassId(String.valueOf(data.getClassId()));
                jsTrainRecord.setStarttime(DateUtils.getNowDateStr("yyyy-MM-dd HH:mm:ss"));
                jsTrainRecord.setStatus(0);
                jsTrainRecord.setCreateTime(new Date());
                jsTrainRecord.setRecnum(StringUtils.leftPad(String.valueOf(jsUserStudent.getId()),5,"0"));
                jsTrainRecordMapper.insertSelective(jsTrainRecord);
                //更新学员状态
                jsUserStudent.setStatus(1);
                jsUserStudentMapper.updateByPrimaryKeySelective(jsUserStudent);
                //学员登录记录
                JsUserStudentLogin record = new JsUserStudentLogin();
                record.setClassId(data.getClassId());
                record.setStunum(data.getStudentNo());
                record.setCoachnum(data.getCoachNo());
                record.setLoginTime(new Date());
                int loginId = jsUserStudentLoginMapper.insertSelective(record);
                //拼应答模块
                answer.setLoginResult(1);
                answer.setStudentNo(data.getStudentNo());
                answer.setTotalClassNum(jsUserStudent.getTotalStudyTime());
                answer.setCompletedClassNum(jsUserStudent.getNowStudyTime());
                answer.setTotalKm(jsUserStudent.getTotalStudyKm());
                answer.setCompletedKm(jsUserStudent.getNowStudyKm());
                //加入培训过程位置
                JsTrainLocationService jsTrainLocationService = BeanHelper.getBean(JsTrainLocationServiceImpl.class);
                jsTrainLocationService.insert(data.getT0200(),data.getHeader().getMobileNo(),2,loginId);
                answer.setIsRead(0);



            }

        }catch (Exception ex){
            log.info("Exception:{}",ex.getMessage());
            answer.setLoginResult(9);//其他
        }
        return answer;
    }

    @Override
    public T8900_0900_student_logout_answer studentLogout(T8900_0900_student_logout data,int serialNo) {
        T8900_0900_student_logout_answer answer = new T8900_0900_student_logout_answer(serialNo, data.getHeader().getMobileNo());
        answer.setStudentNo(data.getStudentNo());
        try{
            JsUserStudent jsUserStudent = jsUserStudentMapper.selectByStunum(data.getStudentNo());
            log.info(data.getStudentNo());
            JsTrainRecord jsTrainRecord = jsTrainRecordMapper.selectByStunum(data.getStudentNo(),0);
            //判断是否已退出
            if(jsUserStudent.getStatus() == 0){
                answer.setLoginResult(1);
                return answer;
            }
            //判断是否存在
            if(jsUserStudent == null || jsTrainRecord == null){
                answer.setLoginResult(2);
                return answer;
            }
            //修改学员状态
            jsUserStudent.setNowStudyKm(data.getTotalKm());
            jsUserStudent.setNowStudyTime(data.getTotalTime());
            jsUserStudent.setStatus(0);
            jsUserStudentMapper.updateByPrimaryKeySelective(jsUserStudent);
            //修改电子日志
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            jsTrainRecord.setEndtime(df.format(data.getDateTime()));
            jsTrainRecord.setDuration(String.valueOf(data.getTotalTime()));
            jsTrainRecord.setMileage(String.valueOf(data.getTotalKm()));
            jsTrainRecord.setStatus(1);
            jsTrainRecordMapper.updateByPrimaryKeySelective(jsTrainRecord);
            //修改学员登录日志
            JsUserStudentLogin record = jsUserStudentLoginMapper.getLastRecord(data.getStudentNo());
            record.setTime(String.valueOf(data.getTotalTime()));
            record.setMileage(String.valueOf(data.getTotalKm()));
            ZonedDateTime zonedDateTime = data.getDateTime().atZone(ZoneId.systemDefault());
            record.setLogoutTime(Date.from(zonedDateTime.toInstant()));
            jsUserStudentLoginMapper.updateByPrimaryKeySelective(record);
            //加入培训过程位置
            JsTrainLocationService jsTrainLocationService = BeanHelper.getBean(JsTrainLocationServiceImpl.class);
            jsTrainLocationService.insert(data.getT0200(),data.getHeader().getMobileNo(),3,record.getId());
            answer.setLoginResult(1);

        }catch (Exception ex){
            log.info("Exception:{}",ex);
            answer.setLoginResult(9);
        }
        return answer;
    }
}