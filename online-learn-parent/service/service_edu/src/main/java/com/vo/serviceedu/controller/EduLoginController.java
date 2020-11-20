package com.vo.serviceedu.controller;

import com.vo.commonutils.Resp;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api("用户相关接口")
@RestController
@RequestMapping("/edu/service/user")
@CrossOrigin//跨域请求
public class EduLoginController {

    @PostMapping("/login")
    public Resp login(){
        return Resp.ok().data("token","admin");
    }

    @GetMapping("/info")
    public Resp getUserInfo(){
        return Resp.ok().data("roles","[admin]").data("name","admin").data("avatar","https://edu100823.oss-cn-shanghai.aliyuncs.com/2020/11/10/7791bf658b5f42798241811e6cfc405dfile.png");
    }
}
