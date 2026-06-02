package com.medical.ehr;

import java.util.List;

/**
 * 外观模式：医生终端统一操作
 */
public class ClinicalOperationFacade {
    private MedicalSubject subject;
    private PluginManager pluginManager;

    public ClinicalOperationFacade(MedicalSubject subject, PluginManager pluginManager) {
        this.subject = subject;
        this.pluginManager = pluginManager;
    }

    // 创建EHR
    public EHRRecord createEHR(String patientId, String text, List<ImageData> images, GenomeData genome) {
        // 校验
        new TextValidator().validate(text);
        new ImageValidator().validate(images);
        new GenomeValidator().validate(genome);

        // 构建
        EHRRecord record = new EHRBuilder()
                .setPatientId(patientId)
                .setTextRecord(text)
                .setImages(images)
                .setGenomeData(genome)
                .build();

        // 通知
        subject.notifyObservers(new NotificationEvent("医生终端", "创建新病历", record));
        return record;
    }

    // 发起会诊
    public void requestConsultation(EHRRecord record, ConsultationRequest req) {
        System.out.println("\n发起会诊：专家=" + req.getSpecialist() + "，原因=" + req.getReason());
        subject.notifyObservers(new NotificationEvent("医生终端", "发起会诊", record));
    }

    // 开具处方
    public void issuePrescription(EHRRecord record, Prescription prescription) {
        System.out.println("\n开具处方：" + prescription.getMedication() + " " + prescription.getDosage());
        subject.notifyObservers(new NotificationEvent("医生终端", "开具处方", record));
    }

    // 插件操作
    public void registerPlugin(MedicalPlugin plugin) {
        pluginManager.registerPlugin(plugin);
    }

    public void executePlugin(String name, EHRRecord record) {
        pluginManager.executePlugin(name, record);
    }
}