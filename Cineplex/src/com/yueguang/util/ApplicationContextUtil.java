package com.yueguang.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextUtil {
     static ApplicationContext applicationContext;
     static{
    	 applicationContext = new ClassPathXmlApplicationContext("app*.xml");
    	 
     }
     
     public  static synchronized  Object getBean(String target){
    	 return applicationContext.getBean(target);
     }
     
}
