package com.devbeginner.testtasksitec.model.response;

public class ResultResponse {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResultResponse(int code){
        this.code = code;
    }
}
