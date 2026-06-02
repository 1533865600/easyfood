package com.medical.ehr;

public interface Validator<T> {
    void validate(T data) throws ValidationException;
}