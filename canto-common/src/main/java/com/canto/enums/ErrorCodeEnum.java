package com.canto.enums;


/**
 * The class Error code enum.
 *
 * @author iqunqunqun
 */
public enum ErrorCodeEnum {
    /**
     * Gl 99990100 error code enum.
     */
    GL99990100(9999100, "参数异常"),
    /**
     * Gl 99990401 error code enum.
     */
    GL99990401(99990401, "无访问权限"),
    /**
     * Gl 000500 error code enum.
     */
    GL99990500(500, "未知异常"),
    /**
     * Gl 000403 error code enum.
     */
    GL99990403(9999403, "无权访问"),
    /**
     * Gl 000404 error code enum.
     */
    GL9999404(9999404, "找不到指定资源"),
    /**
     * Gl 99990001 error code enum.
     */
    GL99990001(99990001, "注解使用错误"),
    /**
     * Gl 99990002 error code enum.
     */
    GL99990002(99990002, "微服务不在线,或者网络超时"),
    /**
     * Uac 10010001 error code enum.
     */

    // 1001用户模块
    UC10010001(10010001, "短信模板不能为空");

    private int code;
    private String msg;

    /**
     * Msg string.
     *
     * @return the string
     */
    public String msg() {
        return msg;
    }


    /**
     * Code int.
     *
     * @return the int
     */
    public int code() {
        return code;
    }

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Gets enum.
     *
     * @param code the code
     * @return the enum
     */
    public static ErrorCodeEnum getEnum(int code) {
        for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }
}
