package org.yzh.web.service;

import org.yzh.protocol.t808.*;

public interface JsUserStudentService {

    T8900_0900_student_login_answer studentLogin(T8900_0900_student_login request,int serialNo);

    T8900_0900_student_logout_answer studentLogout(T8900_0900_student_logout request,int serialNo);




}