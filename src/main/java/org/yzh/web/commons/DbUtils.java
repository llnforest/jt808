package org.yzh.web.commons;

import org.springframework.util.CollectionUtils;

import java.util.List;

public class DbUtils {
    public static <T> T find(List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);

    }
}
