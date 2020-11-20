package com.vo.servicevod.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Data
public class ConstantPropertiesUtils implements InitializingBean {
    //读取配置文件的值，赋值给对应的变量
    @Value("${aliyun.vod.file.keyid}")
    private String keyId;
    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    public static String KEY_ID;
    public static String KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
    }
}
