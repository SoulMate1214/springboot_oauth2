package cn.soul.springboot_oauth2_authorization.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

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
 * @date created in 下午1:10 2019/12/15
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
    final ClientDetailConfig clientDetailConfig;

    /**
     * get authentication endpoints,can set some endpoints non-security information
     *
     * @param endpoints
     * @return void
     * @author Soul
     * @date 2019/12/15 下午2:46
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(this.authenticationManager);
    }

    /**
     * memory client config to code,it's fixed way
     * multi client can use sequence method or chain method
     * configuration profile method > chain method > sequence method
     *
     * @param clientDetailsServiceConfigurer
     * @return void
     * @author Soul
     * @date 2019/12/15 下午1:41
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
//        InMemoryClientDetailsServiceBuilder builder = clientDetailsServiceConfigurer.inMemory();
//        // sequence method
//        builder
//                // create a client,name is oauth2
//                .withClient("client1")
//                // set secret message,it's after encryption
//                .secret(passwordEncoder.encode("981214"))
//                // set ids allowed to access
//                .resourceIds("client1")
//                // set authorization grant types
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//                // set authorization roles
//                .authorities("ROLE_ADMIN", "ROLE_USER")
//                // set authorization scopes
//                .scopes("all")
//                // set access token validity seconds
//                .accessTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
//                // set refresh token validity seconds
//                .refreshTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
//                // set redirect uri
//                .redirectUris("http://example.com");
//        builder
//                .withClient("client2")
//                .secret(passwordEncoder.encode("981214"))
//                .resourceIds("client2")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//                .authorities("ROLE_ADMIN", "ROLE_USER")
//                .scopes("all")
//                .accessTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
//                .refreshTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
//                .redirectUris("http://example.com");

        //chain method
        clientDetailsServiceConfigurer.inMemory()
                .withClient("client3")
                .secret(passwordEncoder.encode("981214"))
                .resourceIds("client3")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .scopes("all")
                .accessTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
                .refreshTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
                .redirectUris("http://example.com")
                .and()
                .withClient("client4")
                .secret(passwordEncoder.encode("981214"))
                .resourceIds("client4")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .scopes("all")
                .accessTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
                .refreshTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
                .redirectUris("http://example.com");

//        // configuration profile method
//        configClient(clientDetailsServiceConfigurer);
    }

    /**
     * memory client config to configuration profile,it's agile way
     *
     * @param clientDetailsServiceConfigurer
     * @return void
     * @author Soul
     * @date 2019/12/18 下午1:43
     */
    private void configClient(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clientDetailsServiceConfigurer.inMemory();
        for (BaseClientDetails client : clientDetailConfig.getClient()) {
            ClientDetailsServiceBuilder<InMemoryClientDetailsServiceBuilder>.ClientBuilder clientBuilder =
                    builder.withClient(client.getClientId());
            clientBuilder
                    .secret(client.getClientSecret())
                    .resourceIds(client.getResourceIds().toArray(new String[0]))
                    .authorizedGrantTypes(client.getAuthorizedGrantTypes().toArray(new String[0]))
                    .authorities(
                            AuthorityUtils.authorityListToSet(client.getAuthorities())
                                    .toArray(new String[0]))
                    .scopes(client.getScope().toArray(new String[0]));
            if (client.getAutoApproveScopes() != null) {
                clientBuilder.autoApprove(
                        client.getAutoApproveScopes().toArray(new String[0]));
            }
            if (client.getAccessTokenValiditySeconds() != null) {
                clientBuilder.accessTokenValiditySeconds(
                        client.getAccessTokenValiditySeconds());
            }
            if (client.getRefreshTokenValiditySeconds() != null) {
                clientBuilder.refreshTokenValiditySeconds(
                        client.getRefreshTokenValiditySeconds());
            }
            if (client.getRegisteredRedirectUri() != null) {
                clientBuilder.redirectUris(
                        client.getRegisteredRedirectUri().toArray(new String[0]));
            }
        }
    }
}
