package com.smbms.exception;

/**
 * description:
 * Created by Ray on 2020-04-28
 */
//自定义用户异常类
public class UserException extends Exception{
    private String message;

    public UserException(String message) {
        super(message);
        this.message = message;
    }

    public UserException() {
        super();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
