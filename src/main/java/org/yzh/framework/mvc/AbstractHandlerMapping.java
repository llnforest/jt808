package org.yzh.framework.mvc;

import org.apache.commons.lang3.StringUtils;
import org.yzh.framework.mvc.annotation.AsyncBatch;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.mvc.handler.AsyncBatchHandler;
import org.yzh.framework.mvc.handler.Handler;
import org.yzh.framework.mvc.handler.SimpleHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class AbstractHandlerMapping implements HandlerMapping {

    private final Map<String, Handler> handlerMap = new HashMap(60);

    protected synchronized void registerHandlers(Object bean) {
        Class<?> beanClass = bean.getClass();
        Method[] methods = beanClass.getDeclaredMethods();
        if (methods == null)
            return;

        for (Method method : methods) {

            Mapping mapping = method.getAnnotation(Mapping.class);
            if (mapping != null) {

                String desc = mapping.desc();
                String[] types = mapping.types();

                AsyncBatch asyncBatch = method.getAnnotation(AsyncBatch.class);
                Handler handler;

                if (asyncBatch != null) {
                    handler = new AsyncBatchHandler(bean, method, desc, asyncBatch.poolSize(), asyncBatch.maxElements(), asyncBatch.maxWait());

                } else {
                    handler = new SimpleHandler(bean, method, desc);
                }

                for (String type : types) {
                    handlerMap.put(type, handler);
                }
            }
        }
    }

    public Handler getHandler(int messageId) {
        String msgId = "0x" + StringUtils.leftPad(Integer.toHexString(messageId), 4, "0");
        return handlerMap.get("0x" + msgId);
    }

    public Handler getHandler(String messageId) {
        return handlerMap.get(messageId);
    }
}