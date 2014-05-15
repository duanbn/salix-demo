package com.salix.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainServer {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("applicationContext.xml");
	}

}
