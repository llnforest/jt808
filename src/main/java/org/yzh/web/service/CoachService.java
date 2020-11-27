package org.yzh.web.service;

import org.yzh.protocol.t808.*;

public interface CoachService {

    int coachLogin(T8900_0900_coach_login request);

    int coachLogout(T8900_0900_coach_logout request);



}