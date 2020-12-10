package org.yzh.framework.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.codec.MessageDecoder;
import org.yzh.framework.commons.ClassUtils;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;

import java.beans.Introspector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DefaultLoadStrategy extends LoadStrategy {
    private static final Logger log = LoggerFactory.getLogger(DefaultLoadStrategy.class.getSimpleName());


    private Map<String, Map<Integer, BeanMetadata>> typeClassMapping = new HashMap(140);

    private Map<Object, Class<? extends AbstractMessage>> typeIdMapping = new HashMap<>(64);

    private Class<? extends AbstractHeader> headerClass = null;

    public DefaultLoadStrategy(String basePackage) {
        log.info("basePackage:{}",basePackage);
        List<Class<?>> types = ClassUtils.getClassList(basePackage);
        for (Class<?> type : types) {
            Class<?> aClass = getMessageClass(type);
            if (aClass != null){
                initClass(typeClassMapping, aClass);
            }
        }
        log.info("typeIdMapping:{}",typeIdMapping);
        log.info("typeClassMapping:{}",typeClassMapping);
        Introspector.flushCaches();
    }

    @Override
    public Class<? extends AbstractHeader> getHeaderClass() {
        return headerClass;
    }

    @Override
    public BeanMetadata getBeanMetadata(Object typeId, int version) {
        log.info("typeIdMapping",typeIdMapping);
        Class<? extends AbstractMessage> typeClass = typeIdMapping.get(typeId);
        if (typeClass == null)
            return null;
        return getBeanMetadata(typeClass, version);
    }

    @Override
    public <T> BeanMetadata<T> getBeanMetadata(Class<T> clazz, int version) {
        log.info("clazz.getName:{}",clazz.getName());
        log.info("typeClassMapping:{}",typeClassMapping);
        Map<Integer, BeanMetadata> beanMetadata = typeClassMapping.get(clazz.getName());
        if (beanMetadata != null)
            return beanMetadata.get(version);
        return null;
    }

    private Class<?> getMessageClass(Class<?> messageClass) {
        log.info("messageClass:{}",messageClass);
        Class<?> superclass = messageClass.getSuperclass();
        Class<?> result = null;
        if (superclass != null) {

            if (AbstractMessage.class.isAssignableFrom(superclass)) {
                result = messageClass;

                Message type = messageClass.getAnnotation(Message.class);
                log.info("type:{}",type);

                if (type != null) {
                    int[] values = type.value();
                    for (int value : values)
                        typeIdMapping.put(value, (Class<? extends AbstractMessage>) messageClass);
                }

            } else if (AbstractHeader.class.isAssignableFrom(superclass)) {
                headerClass = (Class<? extends AbstractHeader>) messageClass;
                result = messageClass;
            }
        } else {
            Class<?> enclosingClass = messageClass.getEnclosingClass();
            if (enclosingClass != null) {

                superclass = enclosingClass.getSuperclass();
                if (AbstractMessage.class.isAssignableFrom(superclass)) {
                    result = messageClass;
                }
            }
        }
        return result;
    }
}