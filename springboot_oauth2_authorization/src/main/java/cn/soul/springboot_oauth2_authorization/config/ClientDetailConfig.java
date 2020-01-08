package cn.soul.springboot_oauth2_authorization.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.List;

/**
 * annotates:
 * 1.a lombok provide automatic generate GET and SET annotate
 * <p>
 * 2.injection bean by configuration file
 * <p>
 * 3.generate client list entity by application.security.oauth in properties
 *
 * @author 冫soul丶
 * @version 1.0
 * @className ClientDetailsConfig
 * @date created in 下午4:43 2019/12/17
 * @modified
 */
@Data
@Configuration
@ConfigurationProperties("application.security.oauth")
public class ClientDetailConfig {
    private List<BaseClientDetails> client;
}
