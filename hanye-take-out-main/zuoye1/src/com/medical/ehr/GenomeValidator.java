package com.medical.ehr;

public class GenomeValidator implements Validator<GenomeData> {
    @Override
    public void validate(GenomeData data) {
        if (data == null || data.getRawSequence().isBlank()) {
            throw new ValidationException("基因序列不能为空");
        }
    }
}