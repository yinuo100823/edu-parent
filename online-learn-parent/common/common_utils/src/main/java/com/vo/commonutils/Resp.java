package com.vo.commonutils;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class Resp {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private Resp(){}

    public static Resp ok(){
        Resp r = new Resp();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        return r;
    }

    public static Resp error(){
        Resp r = new Resp();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        return r;
    }

    public Resp success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Resp message(String message){
        this.setMessage(message);
        return this;
    }

    public Resp code(Integer code){
        this.setCode(code);
        return this;
    }

    public Resp data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public Resp data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
