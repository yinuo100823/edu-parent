package com.vo.servicebase.handler;

import com.vo.commonutils.Resp;
import com.vo.servicebase.entity.VoCodeException;
import com.vo.servicebase.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Resp error(Exception e) {
        e.printStackTrace();
        return Resp.error().message("系统异常，请稍后重试！");
    }

    @ExceptionHandler(VoCodeException.class)
    @ResponseBody
    public Resp error(VoCodeException e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return Resp.error().message(e.getMsg()).code(e.getCode());
    }
}
