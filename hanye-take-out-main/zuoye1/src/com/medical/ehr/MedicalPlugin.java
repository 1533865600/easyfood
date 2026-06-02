package com.medical.ehr;

public interface MedicalPlugin {
    String getPluginName();
    void execute(EHRRecord record);
}