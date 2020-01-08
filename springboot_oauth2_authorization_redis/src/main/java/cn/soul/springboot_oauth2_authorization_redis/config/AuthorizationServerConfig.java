package cn.soul.springboot_oauth2_authorization_redis.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.time.Duration;


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
    private @NonNull
    final PasswordEncoder passwordEncoder;
    private @NonNull
    final RedisConnectionFactory redisConnectionFactory;

    /**
     * get authentication endpoints,can set some endpoints non-security information
     * .tokenStore(redisTokenStore()) means the token of request store in redis
     * @param endpoints
     * @return void
     * @author Soul
     * @date 2019/12/15 下午2:46
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(this.authenticationManager)
        .tokenStore(redisTokenStore());
    }

    /**
     * memory client config to code,it's fixed way
     *
     * @param clientDetailsServiceConfigurer
     * @return void
     * @author Soul
     * @date 2019/12/19 下午3:29
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
        clientDetailsServiceConfigurer.inMemory()
                .withClient("client8")
                .secret(passwordEncoder.encode("981214"))
                .resourceIds("client8")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .scopes("all")
                .accessTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
                .refreshTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
                .redirectUris("http://example.com");
    }

    /**
     *  the token of request store in redis
     *
     * @author Soul
     * @date 2019/12/19 下午3:38
     * @param
     * @return org.springframework.security.oauth2.provider.token.TokenStore
     */
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(this.redisConnectionFactory);
    }
}
