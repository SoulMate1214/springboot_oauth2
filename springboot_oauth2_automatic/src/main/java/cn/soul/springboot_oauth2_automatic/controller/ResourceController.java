package cn.soul.springboot_oauth2_automatic.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * a controller demo
 *
 * @author 冫soul丶
 * @version 1.0
 * @className resourceController
 * @date created in 下午9:09 2019/12/7
 * @modified
 */
@RestController
public class ResourceController {
    /**
     * a resource
     *
     * @param principal
     * @return org.springframework.http.HttpEntity<?>
     * @author Soul
     * @date 2019/12/7 下午9:47
     */
    @GetMapping("/resource")
    public HttpEntity<?> resource(Principal principal) {
        return ResponseEntity.ok(principal);
    }
}
