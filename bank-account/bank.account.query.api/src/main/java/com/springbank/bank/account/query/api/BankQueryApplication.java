package com.springbank.bank.account.query.api;

import com.springbank.bank.account.core.configuration.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EntityScan(basePackages = {"com.springbank.bank.account.core.models"})
@Import(AxonConfig.class)
public class BankQueryApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankQueryApplication.class, args);
	}
}
