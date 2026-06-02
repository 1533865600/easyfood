package com.medical.ehr;

import java.util.List;

public class ImageValidator implements Validator<List<ImageData>> {
    @Override
    public void validate(List<ImageData> images) {
        if (images == null || images.isEmpty()) {
            throw new ValidationException("影像数据不能为空");
        }
    }
}