package com.smbms.controller;

import com.smbms.exception.UserException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * description:
 * Created by Ray on 2020-04-28
 */
@ControllerAdvice//全局异常处理器
public class ExceptionController {
    //异常处理器：处理编译异常
    @ExceptionHandler
    public String handleException(Model model,Exception e){
        System.out.println("-------------------------------"+e.getMessage());
        model.addAttribute("msg", "编译异常--"+e.getMessage());
        return "error";
    }

    //异常处理器：处理用户异常
    @ExceptionHandler(UserException.class)//处理用户异常
    public String handleUserException(Model model,Exception e){
        System.out.println("-------------------------------"+e.getMessage());
        model.addAttribute("msg", "用户异常--"+e.getMessage());
        return "error";
    }

    //异常处理器：处理运行时异常
    @ExceptionHandler(RuntimeException.class)//参数表示处理指定类型的异常
    public String handleRuntimeException(Model model, Exception e){
        System.out.println("-------------------------------"+e.getMessage());
        model.addAttribute("msg", "运行时异常--"+e.getMessage());
        return "error";
    }
}
