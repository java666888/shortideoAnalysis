package top.cairedhai.shortvideoanalysis.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.cairedhai.shortvideoanalysis.common.ResponseResult;
import top.cairedhai.shortvideoanalysis.common.enums.ResponseCodeEnum;

/**
 * @Description: 全局异常处理
 * @Author: Tan
 * @CreateDate: 2021/8/1
 **/
//@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public JSONObject exceptionHandler(Exception ex){
        return JSONObject.parseObject("{\"code\":500,\"msg\":\""+ex.getMessage()+"\"}");
    }


}
