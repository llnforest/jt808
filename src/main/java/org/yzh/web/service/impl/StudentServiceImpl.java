package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.mapper.*;
import org.yzh.web.model.entity.*;
import org.yzh.web.service.StudentService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class.getSimpleName());

    @Autowired
    private JsStudentMapper jsStudentMapper;

    @Autowired
    private JsDeviceMapper jsDeviceMapper;
    @Autowired
    private JsClassrecordMapper jsClassrecordMapper;
    @Autowired
    private JsCoachMapper jsCoachMapper;
    @Autowired
    private JsStudentLoginRecordMapper jsStudentLoginRecordMapper;


    @Override
    public T8900_0900_student_login_answer studentLogin(T8900_0900_student_login data,int serialNo) {
        T8900_0900_student_login_answer answer = new T8900_0900_student_login_answer(serialNo, data.getHeader().getMobileNo());
        try{
            JsStudent jsStudent = jsStudentMapper.selectByStunum(data.getStudentNo());
            if(jsStudent == null){
                answer.setLoginResult(2); //无效的学员编号
            }else if(jsStudent.getStatus() == 2){
                answer.setLoginResult(3); //禁止登录的学员
            }else{
                JsCoach jsCoach = jsCoachMapper.isCoachLogin(data.getCoachNo());
                if(jsCoach == null){
                    answer.setLoginResult(9); //其他
                    return answer;
                }
                if(!jsCoach.getTeachpermitted().equals(jsStudent.getTraintype())){
                    answer.setLoginResult(5);//准教车型与培训车型不符
                    return answer;
                }
                JsDevice jsDevice = jsDeviceMapper.getByDevnum(data.getTerminalNo());
                //允许登录(待完善添加电子教学日志)
                JsClassrecord jsClassrecord = new JsClassrecord();
                jsClassrecord.setInscode(jsStudent.getInscode());
                jsClassrecord.setStunum(jsStudent.getStunum());
                jsClassrecord.setInscode(jsStudent.getInscode());
                jsClassrecord.setCoachnum(data.getCoachNo());
                jsClassrecord.setCarnum(jsDevice.getBindCarnum());
                jsClassrecord.setSimunum(jsDevice.getDevnum());
                jsClassrecord.setPlatnum("1001");
                jsClassrecord.setSubjcode(data.getClassName());
                jsClassrecord.setStarttime(DateUtils.getNowDateStr("yyyy-MM-dd HH:mm:ss"));
                jsClassrecord.setStatus(0);
                jsClassrecord.setCreateTime(new Date());
                jsClassrecord.setRecnum("1");
                jsClassrecordMapper.insertSelective(jsClassrecord);
                log.info("1");
                //更新学员状态
                jsStudent.setStatus(1);
                jsStudentMapper.updateByPrimaryKeySelective(jsStudent);
                //学员登录记录
                JsStudentLoginRecord record = new JsStudentLoginRecord();
                record.setClassId(data.getClassId());
                record.setStunum(data.getStudentNo());
                record.setCoachnum(data.getCoachNo());
                record.setLoginTime(new Date());
                jsStudentLoginRecordMapper.insertSelective(record);
                //拼应答模块
                answer.setLoginResult(1);
                answer.setStudentNo(data.getStudentNo());
                answer.setTotalClassNum(jsStudent.getTotalStudyTime());
                answer.setCompletedClassNum(jsStudent.getNowStudyTime());
                answer.setTotalKm(jsStudent.getTotalStudyKm());
                answer.setCompletedKm(jsStudent.getNowStudyKm());

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
            JsStudent jsStudent = jsStudentMapper.selectByStunum(data.getStudentNo());
            log.info(data.getStudentNo());
            JsClassrecord jsClassrecord = jsClassrecordMapper.selectByStunum(data.getStudentNo(),0);
            if(jsStudent == null || jsClassrecord == null){
                answer.setLoginResult(2);
            }
            //修改学员状态
            jsStudent.setNowStudyKm(data.getTotalKm());
            jsStudent.setNowStudyTime(data.getTotalTime());
            jsStudent.setStatus(0);
            jsStudentMapper.updateByPrimaryKeySelective(jsStudent);
            //修改电子日志
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            jsClassrecord.setEndtime(df.format(data.getDateTime()));
            jsClassrecord.setDuration(String.valueOf(data.getTotalTime()));
            jsClassrecord.setMileage(String.valueOf(data.getTotalKm()));
            jsClassrecord.setStatus(1);
            jsClassrecordMapper.updateByPrimaryKeySelective(jsClassrecord);
            //修改学员登录日志
            JsStudentLoginRecord record = jsStudentLoginRecordMapper.getLastRecord(data.getStudentNo());
            record.setTime(String.valueOf(data.getTotalTime()));
            record.setMileage(String.valueOf(data.getTotalKm()));
            ZonedDateTime zonedDateTime = data.getDateTime().atZone(ZoneId.systemDefault());
            record.setLogoutTime(Date.from(zonedDateTime.toInstant()));
            jsStudentLoginRecordMapper.updateByPrimaryKeySelective(record);
            answer.setLoginResult(1);

        }catch (Exception ex){
            log.info("Exception:{}",ex.getMessage());
            answer.setLoginResult(9);
        }
        return answer;
    }
}