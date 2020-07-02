package com.danbai.ys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 返回类
 *
 * @author danbai
 * @date 2019-10-29 15:05
 */
@ApiModel(value = "返回信息实体类", description = "序列化信息为json返回的类")
public class BaseResult<T> implements Serializable {
    @ApiModelProperty(value = "http状态码")
    private Integer code;
    @ApiModelProperty(value = "状态码")
    private Integer status;
    @ApiModelProperty(value = "消息")
    private String msg;
    @ApiModelProperty(value = "数据")
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
