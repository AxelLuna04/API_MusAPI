package com.musapi;

import net.devh.boot.grpc.server.autoconfigure.GrpcServerSecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(exclude = {
        GrpcServerSecurityAutoConfiguration.class
})
public class ApiMusapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMusapiApplication.class, args);
	}

}
