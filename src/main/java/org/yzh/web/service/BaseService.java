package org.yzh.web.service;

import java.util.List;

public interface BaseService<T> {
    /**
     * 查找一条记录
     * @param t
     * @return
     */
    T find(T t);

    /**
     * 查找多条记录
     * @param t
     * @return
     */
    List<T> select(T t);
}
