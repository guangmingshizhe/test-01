package com.study.controller;

import com.study.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 12073 on 2020/3/11.
 */
@Slf4j
@Api(value = "/test",description = "测试hello Kitty类")
@RestController
@RequestMapping(value = "test")
public class TestController {
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @ApiOperation(value = "测试Hello", notes = "测试Hello")
    @GetMapping("hello")
    public String hello(){
        return "这是第一个程序,Hello,Kitty。。555554445577！";
    }



    /**
     * POST 方式接口测试
     * @param user
     * @return
     */
    @PostMapping("/user")
    public User testPost(@RequestBody User user) {
        logger.info("testPost ...");
        return user;
    }

    /**
     * GET 方式接口测试
     * @return
     */
    @GetMapping("/user")
    public String testGet(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        logger.info("testGet ...");
        return "success";
    }
}
