package com.ozaytunctan.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.List;

public class ServiceResultDto<T> implements Serializable {

    private T data;

    private ServiceResultType resultType;

    private List<Error> errors;

    public ServiceResultDto() {
    }

    public ServiceResultDto(T data, ServiceResultType resultType, List<Error> errors) {
        this.data = data;
        this.resultType = resultType;
        this.errors = errors;
    }

    public ServiceResultDto(T data) {
        this(data, ServiceResultType.SUCCESS, null);
    }

    public ServiceResultDto(List<Error> errors, ServiceResultType resultType) {
        this(null, resultType, errors);
    }

    public ServiceResultDto(List<Error> errors) {
        this(errors, ServiceResultType.ERROR);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ServiceResultType getResultType() {
        return resultType;
    }

    public void setResultType(ServiceResultType resultType) {
        this.resultType = resultType;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @JsonProperty("isSuccess")
    public boolean isSuccess() {
        return this.resultType.isSuccess();
    }
}
