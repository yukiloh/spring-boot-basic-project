package com.example.demo.dto;

import java.io.Serializable;
import java.util.List;

public class BaseResult implements Serializable {
    private static final String RESULT_OK = "ok";
    private static final String RESULT_NOT_OK = "not_ok";
    private static final String SUCCESS = "操作成功";
    private static final String FAILED = "操作失败";

    private String result;
    private Object data;
    private String success;
    private List<Error> errors;


    public static BaseResult ok(){
        return createResult(RESULT_OK,null,SUCCESS,null);
    }
    public static BaseResult ok(Object data){
        return createResult(RESULT_OK,data,SUCCESS,null);
    }
    public static BaseResult notOk(List<Error> errors){
        return createResult(RESULT_NOT_OK,null,FAILED,errors);
    }



    private static  BaseResult createResult(String result,Object data,String success,List<Error> errors){
        BaseResult baseResult = new BaseResult();
        baseResult.setResult(result);
        baseResult.setData(data);
        baseResult.setSuccess(success);
        baseResult.setErrors(errors);

        return baseResult;
    }

    public static class Error {
        private String field;
        private String message;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Error(String field, String message) {
            this.field = field;
            this.message = message;

        }
    }

    public static String getResultOk() {
        return RESULT_OK;
    }

    public static String getResultNotOk() {
        return RESULT_NOT_OK;
    }

    public static String getSUCCESS() {
        return SUCCESS;
    }

    public static String getFAILED() {
        return FAILED;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
