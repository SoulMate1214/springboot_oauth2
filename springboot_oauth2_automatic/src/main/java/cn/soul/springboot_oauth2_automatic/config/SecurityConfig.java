package cn.soul.springboot_oauth2_automatic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author 冫soul丶
 * @version 1.0
 * @className SecurityConfig
 * @date created in 下午10:20 2019/12/7
 * @modified
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * BCrypt encryption algorithm,after spring5,you must do it
     *
     * @param
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @author Soul
     * @date 2019/12/12 下午4:01
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * create memory user
     *
     * @param
     * @return org.springframework.security.core.userdetails.UserDetailsService
     * @author Soul
     * @date 2019/12/12 下午4:07
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager
                .createUser(User.withUsername("admin")
                        .password(passwordEncoder().encode("admin"))
                        .authorities("ROLE_ADMIN").build());
        inMemoryUserDetailsManager
                .createUser(User.withUsername("soul")
                        .password(passwordEncoder().encode("981214"))
                        .authorities("ROLE_USER").build());
        return inMemoryUserDetailsManager;
    }
}
