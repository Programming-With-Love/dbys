package com.danbai.ys.utils;

import com.danbai.ys.entity.BaseResult;

/**
 * @author danbai
 * @date 2019-10-29 15:06
 */
public class ResultUtil {
    public static <T> BaseResult successOk() {
        return commonResult(1, 200, "请求成功", "ok");
    }

    public static <T> BaseResult<T> success(T data) {
        return commonResult(1, 200, "请求成功", data);
    }

    public static <T> BaseResult<T> error(String errorMsg) {
        return error(200, errorMsg);
    }

    public static <T> BaseResult<T> error(Integer code, String errorMsg) {
        return commonResult(0, code, errorMsg, null);
    }
    public static <T> BaseResult<T> successMsg(String msg) {
        return commonResult(1, 200, msg, null);
    }
    private static <T> BaseResult<T> commonResult(Integer status, Integer code, String errMsg, T data) {
        BaseResult<T> result = new BaseResult<>();
        result.setStatus(status);
        result.setCode(code);
        result.setMsg(errMsg);
        result.setData(data);
        return result;
    }
}
