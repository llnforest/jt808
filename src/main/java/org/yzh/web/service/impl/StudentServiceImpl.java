package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.mapper.JsClassrecordMapper;
import org.yzh.web.mapper.JsCoachMapper;
import org.yzh.web.mapper.JsStudentMapper;
import org.yzh.web.model.entity.JsClassrecord;
import org.yzh.web.model.entity.JsCoach;
import org.yzh.web.model.entity.JsStudent;
import org.yzh.web.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class.getSimpleName());

    @Autowired
    private JsStudentMapper jsStudentMapper;

    @Autowired
    private JsClassrecordMapper jsClassrecordMapper;
    @Autowired
    private JsCoachMapper jsCoachMapper;


    @Override
    public T8900_0900_student_login_answer studentLogin(T8900_0900_student_login data) {
        T8900_0900_student_login_answer answer = new T8900_0900_student_login_answer();
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
                }
                if(!jsCoach.getTeachpermitted().equals(jsStudent.getTraintype())){
                    answer.setLoginResult(5);//准教车型与培训车型不符
                }
                //允许登录(待完善添加电子教学日志)
                JsClassrecord jsClassrecord = new JsClassrecord();
                jsClassrecord.setInscode(jsStudent.getInscode());
                jsClassrecord.setStunum(jsStudent.getStunum());
                jsClassrecord.setInscode(jsStudent.getInscode());
                jsClassrecord.setSubjcode(data.getClassId());
                jsClassrecord.setStarttime(DateUtils.getNowDateStr("yyMMddHHmmss"));
                jsClassrecord.setStatus(0);
                jsClassrecordMapper.insertSelective(jsClassrecord);
                //更新学员状态
                jsStudent.setStatus(1);
                jsStudentMapper.updateByPrimaryKeySelective(jsStudent);

                //拼应答模块
                answer.setLoginResult(0);
                answer.setStudentNo(data.getStudentNo());
                answer.setTotalClassNum(jsStudent.getTotalStudyTime());
                answer.setCompletedClassNum(jsStudent.getNowStudyTime());
                answer.setTotalKm(jsStudent.getTotalStudyKm());
                answer.setCompletedKm(jsStudent.getNowStudyKm());

                answer.setIsRead(0);



            }

        }catch (Exception ex){
            answer.setLoginResult(9);//其他
        }
        return answer;
    }

    @Override
    public T8900_0900_student_logout_answer studentLogout(T8900_0900_student_logout data) {
        T8900_0900_student_logout_answer answer = new T8900_0900_student_logout_answer();
        try{
            JsStudent jsStudent = jsStudentMapper.selectByStunum(data.getStudentNo());
            JsClassrecord jsClassrecord = jsClassrecordMapper.selectByStunum(data.getStudentNo(),0);
            if(jsStudent == null || jsClassrecord == null){
                answer.setLoginResult(2);
            }
            //修改学员状态
            jsStudent.setNowStudyKm(jsStudent.getNowStudyKm() + data.getTotalKm());
            jsStudent.setNowStudyTime(jsStudent.getNowStudyTime() + data.getTotalTime());
            jsStudent.setStatus(0);
            jsStudentMapper.updateByPrimaryKeySelective(jsStudent);
            //修改电子日志
            jsClassrecord.setEndtime(String.valueOf(data.getDateTime()));
            jsClassrecord.setDuration(String.valueOf(data.getTotalTime()));
            jsClassrecord.setMileage(String.valueOf(data.getTotalKm()));
            jsClassrecord.setStatus(1);
            jsClassrecordMapper.updateByPrimaryKeySelective(jsClassrecord);
            answer.setLoginResult(1);

        }catch (Exception ex){
            answer.setLoginResult(9);
        }
        return answer;
    }
}