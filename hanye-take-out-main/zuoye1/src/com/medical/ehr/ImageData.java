package com.medical.ehr;

/**
 * CT/MRI 影像数据
 */
public class ImageData {
    private String modality; // 模态：CT/MRI
    private String imagePath;
    private String description;

    public ImageData(String modality, String imagePath, String description) {
        this.modality = modality;
        this.imagePath = imagePath;
        this.description = description;
    }

    public String getModality() { return modality; }
    public String getImagePath() { return imagePath; }
    public String getDescription() { return description; }
}