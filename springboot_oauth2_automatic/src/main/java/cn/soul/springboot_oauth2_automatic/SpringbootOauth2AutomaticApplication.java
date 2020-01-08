package cn.soul.springboot_oauth2_automatic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
public class SpringbootOauth2AutomaticApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootOauth2AutomaticApplication.class, args);
    }
}
