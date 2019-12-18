package com.datangwealth.modules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@SpringBootApplication
@ImportResource("classpath:spring-context.xml")
@EnableApolloConfig
public class DcapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DcapiApplication.class, args);
	}
}
