package org.yzh.web.service;

import org.yzh.protocol.t808.*;
import org.yzh.web.model.entity.JsStudent;

public interface StudentService {

    T8900_0900_student_login_answer studentLogin(T8900_0900_student_login request);

    T8900_0900_student_logout_answer studentLogout(T8900_0900_student_logout request);




}