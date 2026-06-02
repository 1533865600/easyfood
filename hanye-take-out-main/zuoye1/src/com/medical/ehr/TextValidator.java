package com.medical.ehr;

public class TextValidator implements Validator<String> {
    @Override
    public void validate(String text) {
        if (text == null || text.isBlank()) {
            throw new ValidationException("病历文本不能为空");
        }
    }
}