package org.yzh.web.commons;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**  
 * 该工具类只能获取xml配置文件定义的bean，不能获取注解定义的bean
 * @author:Lynn
 * @version:V1.0
 * 2017年9月6日  
 */
@Component
@Lazy(false)
public class BeanHelper implements ApplicationContextAware {
	
	/**
	 * Spring上下文
	 */
	public static ApplicationContext applicationContext;
	/**
	 * servlet上下文
	 */
	public static ServletContext servletContext;
		

	/**
	 * 从静态变量ApplicationContext中取得Bean, 
	 * 自动转型为所赋值对象的类型.
	 * 2017年9月6日
	 * @param beanName
	 * @return
	 * author:Lynn
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) getApplicationContext().getBean(beanName);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 
	 * 自动转型为所赋值对象的类型.
	 * 2017年9月6日
	 * @param clazz
	 * @return
	 * author:Lynn
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		return (T) getApplicationContext().getBean(clazz);
	}
	
	
	
	
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

//	public static void setApplicationContext(ApplicationContext applicationContext) {
//		BeanHelper.applicationContext = applicationContext;
//	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext) {
		BeanHelper.servletContext = servletContext;
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanHelper.applicationContext = applicationContext;
	}
}
