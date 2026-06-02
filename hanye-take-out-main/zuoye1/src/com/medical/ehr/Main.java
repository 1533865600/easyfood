package com.medical.ehr;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. 初始化组件
        MedicalSubject subject = new MedicalSubject();
        PluginManager pluginManager = new PluginManager();
        ClinicalOperationFacade facade = new ClinicalOperationFacade(subject, pluginManager);

        // 2. 注册观察者
        subject.registerObserver(new PatientApp("张三"));
        subject.registerObserver(new PharmacySystem("中心药房"));
        subject.registerObserver(new InsuranceSettlementCenter("市医保中心"));

        // 3. 注册第三方插件
        facade.registerPlugin(new ThirdPartyPluginAdapter(new ThirdPartyDiagnosisPlugin()));

        // 4. 构建测试数据
        ImageData image = new ImageData("CT", "/data/ct1.dcm", "脑部CT");
        GenomeData genome = new GenomeData("ATCG...", "正常基因");

        // 5. 医生操作：创建病历
        EHRRecord record = facade.createEHR("P1001", "高血压2级", List.of(image), genome);

        // 6. 执行第三方诊断插件
        facade.executePlugin("第三方AI诊断插件", record);

        // 7. 发起会诊 + 开处方
        facade.requestConsultation(record, new ConsultationRequest("李教授", "疑难病例"));
        facade.issuePrescription(record, new Prescription("降压药", "1片/天", "饭后服用"));
    }
}