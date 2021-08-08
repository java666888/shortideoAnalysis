package top.cairedhai.shortvideoanalysis.common.enums;

/**
 * @Description: 响应code枚举类
 * @Author: Tan
 * @CreateDate: 2019/12/8
 **/
public enum ResponseCodeEnum {
    /*通用HTTP状态码 */
    SUCESS(200, "成功"),
    FAULURE(500,"失败"),
    PARAM_EMPTY(1001,"参数不能为空");


    private Integer code;
    private String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
