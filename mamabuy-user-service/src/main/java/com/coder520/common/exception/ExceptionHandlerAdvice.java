package com.coder520.common.exception;

import com.coder520.common.constants.Constants;
import com.coder520.common.resp.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by JackWangon[www.coder520.com] 2018/4/3.
 */
@ControllerAdvice    //controller层的切面处理类
@ResponseBody
@Slf4j
public class ExceptionHandlerAdvice {

    /**
     * 兜底异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ApiResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ApiResult(Constants.RESP_STATUS_INTERNAL_ERROR, "系统异常，请稍后再试");
    }

    /**
     * MamaBuyException异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MamaBuyException.class)
    public ApiResult handleException(MamaBuyException e) {
        log.error(e.getMessage(), e);
        return new ApiResult(e.getStatusCode(), e.getMessage());
    }

    /**
     * controller层入参异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handleIllegalParamException(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = "参数不合法";
        if (errors.size() > 0) {
            message = errors.get(0).getDefaultMessage();
        }
        ApiResult result = new ApiResult(Constants.RESP_STATUS_BADREQUEST, message);
        return result;
    }

}
