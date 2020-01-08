package cn.soul.springboot_oauth2_authorization;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SpringbootOauth2AuthorizationApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     *  encryption “981214” test
     *
     * @author Soul
     * @date 2019/12/15 下午2:32
     * @param
     * @return void
     */
    @Test
    public void password(){
        System.out.println(new BCryptPasswordEncoder().encode("981214"));
    }

}
