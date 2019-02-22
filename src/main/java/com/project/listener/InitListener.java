package com.project.listener;

import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {
	private WebApplicationContext springContext;
	/**
	 * 销毁时启动
	 */
	public void contextDestroyed(ServletContextEvent context) {
	}
	
	/**
	 * 初始化时启动
	 */
	public void contextInitialized(ServletContextEvent context) {
	}

}
