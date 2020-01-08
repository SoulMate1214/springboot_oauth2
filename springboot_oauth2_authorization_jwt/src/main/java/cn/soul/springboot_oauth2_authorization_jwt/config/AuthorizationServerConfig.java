package cn.soul.springboot_oauth2_authorization_jwt.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

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

    /**
     * get authentication endpoints,can set some endpoints non-security information
     * .tokenStore(jwtTokenStore()) means the token of request store for jwt
     * .accessTokenConverter(jwtAccessTokenConverter()) means the store by a token converter
     *
     * @param endpoints
     * @return void
     * @author Soul
     * @date 2019/12/15 下午2:46
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(this.authenticationManager)
                .tokenStore(jwtTokenStore())
                .accessTokenConverter(jwtAccessTokenConverter());
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
                .withClient("client9")
                .secret(passwordEncoder.encode("981214"))
                .resourceIds("client9")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .scopes("all")
                .accessTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
                .refreshTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
                .redirectUris("http://example.com");
    }

    /**
     * a token converter,used token store
     *
     * @param
     * @return org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
     * @author Soul
     * @date 2019/12/19 下午4:08
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("client9");
        return converter;
    }

    /**
     * the token of request store in jwt token converter
     *
     * @param
     * @return org.springframework.security.oauth2.provider.token.TokenStore
     * @author Soul
     * @date 2019/12/19 下午4:11
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
}
