package com.medical.ehr;

/**
 * 第三方国产诊断插件（外部类，不可修改）
 */
public class ThirdPartyDiagnosisPlugin {
    public String pluginIdentifier() {
        return "第三方AI诊断插件";
    }

    public void runAnalysis(String patientId, String text, String genome) {
        System.out.println("\n【第三方插件】执行AI诊断：");
        System.out.println("患者ID：" + patientId + "，病历：" + text + "，基因：" + genome);
    }
}