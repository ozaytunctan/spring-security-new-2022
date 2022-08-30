package com.ozaytunctan;

import com.ozaytunctan.utils.FactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EntityScan(value = "com.ozaytunctan.entity.*")
//@EnableJpaRepositories(basePackages = {"com.ozaytunctan.respository.*"})
public class SpringSecurityBasicApplication {

	private static final  Logger logger= LoggerFactory.getLogger(SpringSecurityBasicApplication.class);

	public static void main(String[] args) {

		long startedTime = FactoryUtils.tic();
		SpringApplication.run(SpringSecurityBasicApplication.class, args);
		logger.info("=========================SpringSecurityBasicApplication.run() started...");
		logger.info(FactoryUtils.tocString(startedTime));

	}

}
