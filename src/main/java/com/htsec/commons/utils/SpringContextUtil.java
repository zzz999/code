package com.htsec.commons.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @Project htsec-hangqing
 * @Title SpringContextUtil
 * @Package com.htsec.hangqing.service
 * @Email ycw10858@htsec.com
 * @Description:
 *
 * @Company htsec
 * @Create 2016年6月6日 下午5:07:28
 * @author lenovo
 * @version 1.0 Copyright (c) 2016 htsec, All Rights Reserved.
 *
 */
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;

	}

	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
		}
	}

}
