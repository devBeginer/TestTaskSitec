package com.devbeginner.testtasksitec.internet;

public interface OnResponse<T> {
    void onSuccess(T data);
    void onFailure(String error);
}
