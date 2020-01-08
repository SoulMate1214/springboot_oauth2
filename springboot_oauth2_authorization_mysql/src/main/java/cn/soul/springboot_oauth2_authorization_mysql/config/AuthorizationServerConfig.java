package cn.soul.springboot_oauth2_authorization_mysql.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;


/**
 * annotates:
 * 1.injection bean by configuration file
 * <p>
 * 2.automatic generate a private and NotNull constructor
 * in lombok have three annotates can generate constructor:
 * NoArgsConstructor,RequiredArgsConstructor,AllArgsConstructor
 * <p>
 * 3.enable authorization server
 *
 * @author 冫soul丶
 * @version 1.0
 * @className AuthorizationServerConfig
 * @date created in 下午1:34 2019/12/19
 * @modified
 */
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private @NonNull
    final AuthenticationManager authenticationManager;
    // datasource
    private @NonNull
    final DataSource dataSource;

    /**
     * get authentication endpoints,can set some endpoints non-security information
     * .tokenStore(jdbcTokenStore()) means the token of request store in datasource
     *
     * @param endpoints
     * @return void
     * @author Soul
     * @date 2019/12/15 下午2:46
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(this.authenticationManager)
                .tokenStore(jdbcTokenStore());
    }

    /**
     * generate client details by datasource
     *
     * @param
     * @return org.springframework.security.oauth2.provider.ClientDetailsService
     * @author Soul
     * @date 2019/12/19 下午1:49
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(this.dataSource);
    }

    /**
     * client details configure to ClientDetailsServiceConfigurer
     *
     * @param clients
     * @return void
     * @author Soul
     * @date 2019/12/19 下午1:50
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }

    /**
     * the token of request store in datasource
     *
     * @param
     * @return org.springframework.security.oauth2.provider.token.TokenStore
     * @author Soul
     * @date 2019/12/19 下午2:50
     */
    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(this.dataSource);
    }
}
