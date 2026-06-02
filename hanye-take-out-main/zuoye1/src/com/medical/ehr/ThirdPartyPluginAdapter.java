package com.medical.ehr;

/**
 * 适配器：将第三方插件适配为系统标准插件
 */
public class ThirdPartyPluginAdapter implements MedicalPlugin {
    private ThirdPartyDiagnosisPlugin thirdPartyPlugin;

    public ThirdPartyPluginAdapter(ThirdPartyDiagnosisPlugin plugin) {
        this.thirdPartyPlugin = plugin;
    }

    @Override
    public String getPluginName() {
        return thirdPartyPlugin.pluginIdentifier();
    }

    @Override
    public void execute(EHRRecord record) {
        // 转换参数，调用第三方插件
        thirdPartyPlugin.runAnalysis(
                record.getPatientId(),
                record.getTextRecord(),
                record.getGenomeData().getRawSequence()
        );
    }
}