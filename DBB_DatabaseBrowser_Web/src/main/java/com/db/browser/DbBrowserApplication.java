package com.db.browser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages =  "com.db.browser")
@EnableAspectJAutoProxy
public class DbBrowserApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbBrowserApplication.class, args);
	}

}
