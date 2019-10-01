package com.canto.result;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author iqunqunqun
 * @description: 自定义响应数据结构
 * 这个类是提供给门户，ios，安卓，微信商城用的
 * 门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 其他自行处理
 * 200：表示成功
 * 404: 表示未发现
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 555：异常抛出信息
 * ***:其他自定义异常
 */
@Data
@NoArgsConstructor
public class JsonResult {


    /**
     * 响应状态
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private Object data;

    public static JsonResult build(Integer status, String msg, Object data) {
        return new JsonResult(status, msg, data);
    }

    public static JsonResult ok(Object data) {
        return new JsonResult(data);
    }

    public static JsonResult ok(String msg, Object data) {
        return new JsonResult(msg, data);
    }

    public static JsonResult ok() {
        return new JsonResult("ok", null);
    }

    public static JsonResult errorMsg() {
        return new JsonResult(404, "not found", null);
    }

    public static JsonResult errorMsg(String msg) {
        return new JsonResult(500, msg, null);
    }

    public static JsonResult errorMap(Object data) {
        return new JsonResult(501, "error", data);
    }

    public static JsonResult errorTokenMsg(String msg) {
        return new JsonResult(502, msg, null);
    }

    public static JsonResult errorException(String msg) {
        return new JsonResult(555, msg, null);
    }

    public static JsonResult errorException(Integer status, String msg, Object data) {
        return new JsonResult(status, msg, data);
    }


    private JsonResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private JsonResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    private JsonResult(String msg, Object data) {
        this.status = 200;
        this.msg = msg;
        this.data = data;
    }
}
