package com.medical.ehr;

import java.util.List;

/**
 * 多维健康档案：文本+影像+基因
 */
public class EHRRecord {
    private String patientId;
    private String textRecord;
    private List<ImageData> images;
    private GenomeData genomeData;

    public EHRRecord(String patientId, String textRecord, List<ImageData> images, GenomeData genomeData) {
        this.patientId = patientId;
        this.textRecord = textRecord;
        this.images = images;
        this.genomeData = genomeData;
    }

    public String getPatientId() { return patientId; }
    public String getTextRecord() { return textRecord; }
    public List<ImageData> getImages() { return images; }
    public GenomeData getGenomeData() { return genomeData; }

    @Override
    public String toString() {
        return "患者ID：" + patientId + "\n病历文本：" + textRecord + "\n影像数量：" + images.size() + "\n基因数据：" + genomeData;
    }
}