package com.medical.ehr;

import java.util.ArrayList;
import java.util.List;

/**
 * 建造者模式：分步构建复杂EHR
 */
public class EHRBuilder {
    private String patientId;
    private String textRecord;
    private List<ImageData> images = new ArrayList<>();
    private GenomeData genomeData;

    public EHRBuilder setPatientId(String patientId) {
        this.patientId = patientId;
        return this;
    }

    public EHRBuilder setTextRecord(String textRecord) {
        this.textRecord = textRecord;
        return this;
    }

    public EHRBuilder addImage(ImageData image) {
        this.images.add(image);
        return this;
    }

    public EHRBuilder setImages(List<ImageData> images) {
        this.images = images;
        return this;
    }

    public EHRBuilder setGenomeData(GenomeData genomeData) {
        this.genomeData = genomeData;
        return this;
    }

    public EHRRecord build() {
        return new EHRRecord(patientId, textRecord, images, genomeData);
    }
}