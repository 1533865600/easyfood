package com.medical.ehr;

/**
 * 基因序列数据
 */
public class GenomeData {
    private String rawSequence;
    private String annotation;

    public GenomeData(String rawSequence, String annotation) {
        this.rawSequence = rawSequence;
        this.annotation = annotation;
    }

    public String getRawSequence() { return rawSequence; }
    public String getAnnotation() { return annotation; }
}