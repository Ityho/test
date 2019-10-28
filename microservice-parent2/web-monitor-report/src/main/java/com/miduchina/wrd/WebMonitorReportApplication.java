package com.miduchina.wrd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;


@RefreshScope
@SpringBootApplication(scanBasePackages = {"com.miduchina.wrd"})
@ServletComponentScan
public class WebMonitorReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebMonitorReportApplication.class, args);
	}

}

